package auto.auto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import auto.auto.model.Rental;
import auto.auto.repository.RentalRepository;


@Controller
@RequestMapping("/rents")
public class Rentalcontroller {

    @Autowired
    private RentalRepository rentalRepository;

    @GetMapping("/")
    public String rentals(
        Model model, 
        @RequestParam Integer autoId,
        @RequestParam Integer driverId) {
            return "rentals/rents";
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
    
    @GetMapping("/create")
    public String createAuto(Model model) {
        model.addAttribute("rent", new Rental());
        
        return "/rents/create"; // template del form
    }

    @PostMapping("/create")
    public String createSubmit(@ModelAttribute Rental rent) {
        rentalRepository.save(rent);
    
        return "redirect:/rents/"; // torna alla homepage
    }


}
