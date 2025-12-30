package auto.auto.controller;

import java.time.temporal.ChronoUnit;
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
import jakarta.validation.Valid;


@Controller
@RequestMapping("/rents")
public class Rentalcontroller {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private AutoRepository autoRepository;

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
        return "/rents/quickrent"; // nome del template sopra
}

    
    @GetMapping("/create")
    public String createRent(Model model) {
        model.addAttribute("rent", new Rental());
        model.addAttribute("vetture", autoRepository.findAll());
        
        return "/rents/create"; // template del form
    }

    @PostMapping("/create")
    public String createSubmit(@ModelAttribute Rental rent) {
        

        // Calcolo quanti giorni dura il noleggio (incluso il giorno finale)
        long days = ChronoUnit.DAYS.between(rent.getRentStartDate(), rent.getRentEndDate()) + 1;

        // Calcolo il prezzo totale del noleggio: prezzo giornaliero dell'auto * numero giorni
        double totalPrice = rent.getAuto().getPrice() * days;

        // Imposto il prezzo totale nel Rental
        rent.setTotalPrice(totalPrice);

        rentalRepository.save(rent);
    
        return "redirect:/rents/"; // torna alla pagina dei noleggi
    }

    @GetMapping("/edit/{id}")
        public String edit(@PathVariable ("id") Integer id, Model model) {
        Optional<Rental> optionalRental = rentalRepository.findById(id);
        Rental rent = optionalRental.get();
        model.addAttribute("rent", rent);
        return "/rents/edit";
    }

    @PostMapping("/edit/{id}")
        public String update(@Valid @ModelAttribute("rent") Rental formRent, BindingResult bindingResult, Model model) {    
        Rental oldRent = rentalRepository.findById(formRent.getId()).get();
        
         if (bindingResult.hasErrors()) {
            return "/rents/edit";
        }    

        long days = ChronoUnit.DAYS.between(formRent.getRentStartDate(), formRent.getRentEndDate()) + 1;

        // Calcolo il prezzo totale
        double totalPrice = formRent.getAuto().getPrice() * days;

        // Imposto il prezzo totale
        formRent.setTotalPrice(totalPrice);

        rentalRepository.save(formRent); 
        
        
        return "redirect:/rents/";

    }

    @PostMapping("/delete/{id}")
        public String delete (@PathVariable("id") Integer id) {
        Rental rent = rentalRepository.findById(id).get();
        rentalRepository.deleteById(id);
        return "redirect:/rents/";
    }


}
