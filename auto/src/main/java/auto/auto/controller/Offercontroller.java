package auto.auto.controller;

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

import auto.auto.model.Auto;
import auto.auto.model.Offer;
import auto.auto.repository.AutoRepository;
import auto.auto.repository.OfferRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/offers")
public class Offercontroller {

    @Autowired
        private OfferRepository offerRepository;
    
    @Autowired
        private AutoRepository autorepository;


    @GetMapping("/create/{autoId}")
        public String newOffer(@PathVariable("autoId") Integer autoId, Model model) {
        Optional<Auto> optAuto = autorepository.findById(autoId);

        if (optAuto.isEmpty()) {
        // Se l'auto non esiste, torno alla lista auto
        return "redirect:/auto";
    }

    Offer offer = new Offer();
    offer.setAuto(optAuto.get()); // Associo l'auto all'offerta

    model.addAttribute("offer", offer);
    model.addAttribute("editMode", false); // modalità "creazione"
    return "offers/edit"; // template per creare/modificare l'offerta
    }

    @PostMapping("/create")
        public String create(
        @Valid @ModelAttribute("offer") Offer offer,
        BindingResult bindingResult,
        Model model) {

    // Se ci sono errori di validazione
        if (bindingResult.hasErrors()) {
        model.addAttribute("editMode", false);
        return "offers/edit";
    }

    // Recupero l'auto associata in modo sicuro
    Optional<Auto> optAuto = autorepository.findById(offer.getAuto().getId());

    if (optAuto.isEmpty()) {
        // Se l'auto non esiste, redirect o messaggio di errore
        return "redirect:/auto"; 
    }

    // Imposto l'auto vera sull'offerta
    offer.setAuto(optAuto.get());

    // Salvo l'offerta
    offerRepository.save(offer);

    return "redirect:/auto/show/" + offer.getAuto().getId();
}

    @GetMapping("/edit/{id}")
        public String edit(@PathVariable("id") Integer id, Model model) {

        // Recupero Optional dal repository
        Optional<Offer> optOffer = offerRepository.findById(id);

    
        if (optOffer.isEmpty()) {
            return "redirect:/offers";
        }

        // Estraggo l'oggetto Offer
        Offer offer = optOffer.get();

        //Modalità edit
        model.addAttribute("editMode", true);

        // Passo l'offerta alla view
        model.addAttribute("offer", offer);

        // Ritorno il template
        return "/offers/edit";
    }

     @PostMapping ("/delete/{id}")
        public String delete(@PathVariable("id")Integer id) {
        offerRepository.deleteById(id);
        return "redirect:/auto";
    }

}
