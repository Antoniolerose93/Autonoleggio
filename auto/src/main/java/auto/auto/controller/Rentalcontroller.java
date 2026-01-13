package auto.auto.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import auto.auto.model.Rental;
import auto.auto.repository.AutoRepository;
import auto.auto.repository.RentalRepository;
import auto.auto.service.RentalService;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/rents")
public class Rentalcontroller {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private AutoRepository autoRepository;

    @Autowired
    private RentalService rentalService;

    @GetMapping("/")
    public String rentals(Model model) {
    model.addAttribute("rentals", rentalRepository.findAll());
    return "rents/rentals"; // template corretto
    }
    
    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Integer id, Model model){
        Optional<Rental> optionalRental = rentalRepository.findById(id);
        if(optionalRental.isPresent()){
            model.addAttribute("rental", optionalRental.get());
            model.addAttribute("empty",false);
        } else {
            model.addAttribute("empty", true);
        }

        return "/rents/show";
    }

    @GetMapping("/quickrent")
    public String quickCreate(@RequestParam("autoId") Integer autoId, Model model) {
    Rental rent = new Rental();
    autoRepository.findById(autoId).ifPresent(rent::setAuto);

    model.addAttribute("rent", rent);
    model.addAttribute("auto", rent.getAuto()); // per mostrare i dettagli

    // Messaggio di disponibilità completo
    RentalService.DisponibilitaResidua disponibilita = rentalService.prossimaDataDisponibile(autoId, LocalDate.now());
    model.addAttribute("messaggioDisponibilita",
        "Auto disponibile fino al: " + disponibilita.getDisponibileFino() +
        ". uccessivamente disponibile nuovamente dal: " + disponibilita.getDisponibileDa());

    return "/rents/quickrent";
    }

    
    @GetMapping("/create")
    public String createRent(Model model) {
        model.addAttribute("rent", new Rental());
        model.addAttribute("vetture", autoRepository.findAll());
        
        
        return "rents/create"; // template del form
    }

    @PostMapping("/create")
    public String createSubmit(@ModelAttribute Rental rent, Model model) {
    // Recupero l'auto dal repository
        autoRepository.findById(rent.getAuto().getId()).ifPresent(rent::setAuto);

    LocalDate today = LocalDate.now();
    if (rent.getRentStartDate().isBefore(today) || rent.getRentEndDate().isBefore(today)) {
        model.addAttribute("error", "Le date di inizio e fine del noleggio non possono essere nel passato");
        model.addAttribute("auto", rent.getAuto());
        model.addAttribute("rent", rent);
        model.addAttribute("vetture", autoRepository.findAll());

        // Calcolo il messaggio di disponibilità anche qui
        RentalService.DisponibilitaResidua disponibilita = rentalService.prossimaDataDisponibile(rent.getAuto().getId(), today);
        model.addAttribute("messaggioDisponibilita",
            "Auto disponibile fino al: " + disponibilita.getDisponibileFino() +
            " e disponibile nuovamente dal: " + disponibilita.getDisponibileDa());

        return "rents/create";
    }

    // Controllo disponibilità tramite il service
    if (!rentalService.disponibilita(rent)) {
        model.addAttribute("error", "Auto non disponibile per le date selezionate");
    }

    // Calcolo sempre il messaggio di disponibilità
    RentalService.DisponibilitaResidua disponibilita = rentalService.prossimaDataDisponibile(rent.getAuto().getId(), today);
    model.addAttribute("messaggioDisponibilita",
        "Auto disponibile fino al: " + disponibilita.getDisponibileFino() +
        " e disponibile nuovamente dal: " + disponibilita.getDisponibileDa());

    // Se l'auto non è disponibile, rimando al form
    if (!rentalService.disponibilita(rent)) {
        model.addAttribute("auto", rent.getAuto());
        model.addAttribute("rent", rent);
        model.addAttribute("vetture", autoRepository.findAll());
        return "rents/create";
    }

    // Salvataggio noleggio
    rent.setTotalPrice(rentalService.calcolaPrezzoTotale(rent));
    rentalRepository.save(rent);

        return "redirect:/rents/";
    }


    @PostMapping("/delete/{id}")
        public String delete (@PathVariable("id") Integer id) {
        Rental rent = rentalRepository.findById(id).get();
        rentalRepository.deleteById(id);
        return "redirect:/rents/";
    }


}
