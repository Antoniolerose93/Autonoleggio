package auto.auto.controller;

import java.time.LocalDate;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import auto.auto.model.Auto;
import auto.auto.model.Rental;
import auto.auto.service.AutoService;
import auto.auto.service.RentalService;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/rents")
public class Rentalcontroller {

    @Autowired
    private AutoService autoService;

    @Autowired
    private RentalService rentalService;

    @GetMapping("/")
    public String indexRents(Model model) {
    model.addAttribute("rentals", rentalService.findAll());
    return "rents/rentals"; // template corretto
    }
    
    @GetMapping("/show/{id}")
    public String showRent(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes){
        try{
            Rental rent = rentalService.findById(id);
            model.addAttribute("rentals", rent);
            return "rents/show";
        } catch(IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/rents/";
        }
        
    }

    @GetMapping("/quickrent")
    public String quickCreate(@RequestParam("autoId") Integer autoId, Model model, RedirectAttributes redirectAttributes) {
    try{
        Auto selectedAuto = autoService.findById(autoId);
        Rental rent = new Rental();
        rent.setAuto(selectedAuto);

        model.addAttribute("rent", rent);
        model.addAttribute("auto", selectedAuto);
        model.addAttribute("availabilityMessage", rentalService.getAvailabilityMessage(autoId, LocalDate.now()));
        
        return "rents/quickrent";

    } catch (Exception e){
        redirectAttributes.addFlashAttribute("errorMessage", "L'auto selezionata non esiste.");
        return "redirect:/auto/";
    }

    }

    @PostMapping("/quickrent")
    public String quickStore(
        @Valid @ModelAttribute("rent") Rental rent,
        BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
            if(bindingResult.hasErrors()){
                model.addAttribute("auto", autoService.findById(rent.getAuto().getId()));
                model.addAttribute("availabilityMessage", rentalService.getAvailabilityMessage(rent.getAuto().getId(), LocalDate.now()));
                return "rents/quickrent";
            }
            try{
                rentalService.saveRent(rent);
                redirectAttributes.addFlashAttribute("successMessage", "Prenotazione effettuata con successo");
                return "redirect:/auto/";
            } catch(IllegalArgumentException e){
                model.addAttribute("errorMessage", e.getMessage());
                model.addAttribute("auto", autoService.findById(rent.getAuto().getId()));
                model.addAttribute("availabilityMessage", rentalService.getAvailabilityMessage(rent.getAuto().getId(), LocalDate.now()));
                return "rents/quickrent";
            }

        }
        
    @GetMapping("/create")
        public String createRent(@RequestParam(value = "autoId", required = false) Integer autoId, Model model) {
        Rental rent = new Rental();
    
        if (autoId != null) {
            Auto selectedAuto = autoService.findById(autoId);
            rent.setAuto(selectedAuto);
            model.addAttribute("availabilityMessage", rentalService.getAvailabilityMessage(autoId, LocalDate.now()));
            }

            model.addAttribute("rent", rent);
            model.addAttribute("vetture", autoService.findAll());
        return "rents/create"; 
    }

    @PostMapping("/create")
    public String createSubmit(
        @Valid @ModelAttribute("rent") Rental rent,
        BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
    
        if(bindingResult.hasErrors()){
        model.addAttribute("vetture", autoService.findAll());
        
        if (rent.getAuto() != null && rent.getAuto().getId() != null) {
            model.addAttribute("availabilityMessage", rentalService.getAvailabilityMessage(rent.getAuto().getId(), LocalDate.now()));
        }
        return "rents/create";
    }

        try {
            rentalService.saveRent(rent);
            redirectAttributes.addFlashAttribute("successMessage", "Prenotazione effettuata con successo");
            return "redirect:/auto/";

        } catch (IllegalArgumentException e) {
            model.addAttribute("availabilityMessage", e.getMessage());
            model.addAttribute("vetture", autoService.findAll());
    
            model.addAttribute("availabilityMessage", rentalService.getAvailabilityMessage(rent.getAuto().getId(), LocalDate.now()));
            return "rents/create"; 
        }
       
    }

    @PostMapping("/delete/{id}")
        public String deleteRent(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try{
            rentalService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Noleggio eliminato con successo");
            return "redirect:/rents/";
        } catch(IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/rents/";
        }

        
    }



}
