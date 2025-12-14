package auto.auto.controller;

import java.util.List;
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

import auto.auto.model.Auto;
import auto.auto.repository.AutoRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auto")

public class Autocontroller {

@Autowired

    private AutoRepository autorepository;

@GetMapping("/")
    public String index(
        Model model, 
        @RequestParam(name="brand", required=false)String brand,
        @RequestParam(name ="modello", required=false)String modello){

          List<Auto> result;

         if ((brand == null || brand.isBlank()) && (modello == null || modello.isBlank())) {
            result = autorepository.findAll();
        } else if (modello != null && !modello.isBlank()) {
            result = autorepository.findBymodelContainingIgnoreCase(modello);
        } else if (brand != null && !brand.isBlank()) {
            result = autorepository.findBybrandIgnoreCase(brand);
        } else {
            result = autorepository.findAll();
        }

        model.addAttribute("list", result);
    
        List<String> modelli;
        if ("Alfa Romeo".equals(brand)) {
            modelli = List.of("Giulia", "Stelvio", "Tonale", "Junior");
        } else if ("BMW".equals(brand)) {
            modelli = List.of("X1", "X3", "X5");
        } else if ("Audi".equals(brand)) {
            modelli = List.of("A3", "A4", "Q3");
        } else if("Mercedes".equals(brand)){
            modelli = List.of("Classe A","Classe E", "GLA");
        } else if ("Cupra".equals(brand)){
            modelli = List.of("Formentor", "Born", "Leon");
        } else {
            modelli = List.of(); // lista vuota se nessun brand selezionato
        }

    model.addAttribute("modelli", modelli);
    model.addAttribute("marcaSelezionata", brand);
    model.addAttribute("modelloSelezionato", modello);

    return "vetture/index";    
    }
        
    

@GetMapping("/show/{id}")
    public String show(@PathVariable("id") Integer id, Model model){
        Optional <Auto> optionalAuto = autorepository.findById(id);
        if(optionalAuto.isPresent()){
            model.addAttribute("auto", optionalAuto.get());
            model.addAttribute("empty",false);
        } else {
            model.addAttribute("empty", true);
        }

        return "/vetture/show";
    }

@GetMapping("/create")
    public String createAuto(Model model) {
        model.addAttribute("auto", new Auto());
        
        return "vetture/create"; // template del form
    }

@PostMapping("/create")
    public String createSubmit(@ModelAttribute Auto auto) {
        autorepository.save(auto);
    
        return "redirect:/auto/"; // torna alla homepage
    }

@GetMapping("/edit/{id}")
        public String edit(@PathVariable ("id") Integer id, Model model) {
        Optional <Auto> optAuto = autorepository.findById(id);
        Auto auto = optAuto.get();
        model.addAttribute("auto", auto);
        return "/vetture/edit";
    }

@PostMapping("/edit/{id}")
        public String update(@Valid @ModelAttribute("auto") Auto formAuto, BindingResult bindingResult, Model model) {    
        Auto oldAuto = autorepository.findById(formAuto.getId()).get();
        // //inseriamo un blocco, non si può modificare il nome e la descrizione delle pizze
        // if(!oldPizza.getNome().equals(formPizza.getNome())) { //verifichiamo se la vecchia pizza si chiama come la nuova. ! è il not che si mette all'inizio
        //     bindingResult.addError(new ObjectError("name", "Cannot change the name"));
        // }
        // if(!oldPizza.getDescrizione().equals(formPizza.getDescrizione())) {
        //     bindingResult.addError(new ObjectError("description", "Cannot change description"));
        // }
         if (bindingResult.hasErrors()) {
            return "/vetture/edit";
        }    

        autorepository.save(formAuto); 
        //essendo questo repository uguale a quello sopra come fa a capire 
        //se sta creando un elemento nuovo o se ne sta modificando uno esistente?
        //come fa il repository a sapere se siamo in creazione o in modifica?
        //Quando prende l'elemento che gli passiamo tramite l'id, capisce che c'è già un elemento con quell'id, ed è quello che deve aggiornare.
        // nella creazione si crea un nuovo ID invece. Nella modifica dice quell'elemento con quell'id esiste già e quindi è quello che deve modificare.
        
        return "redirect:/auto/";

    }

    @PostMapping("/delete/{id}")
        public String delete (@PathVariable("id") Integer id) {
        Auto auto = autorepository.findById(id).get();
        autorepository.deleteById(id);
        return "redirect:/auto/";
    }


}
