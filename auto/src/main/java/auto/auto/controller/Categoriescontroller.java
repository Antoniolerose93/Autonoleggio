package auto.auto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import auto.auto.model.Categories;
import auto.auto.repository.CategoriesRepository;

@Controller
@RequestMapping("/categories")

public class Categoriescontroller {

    @Autowired 
    private CategoriesRepository categoriesRepository;

    @GetMapping("/")
    public String category(Model model){
    model.addAttribute("categories", categoriesRepository.findAll());
    model.addAttribute("categoriesObj", new Categories()); 
        //ingredientObj serve come contenitore.
        //E' un oggetto vuoto che permette di creare un nuovo ingrediente.

        return "categories/category";
    }



    @PostMapping("/create")
    public String createCategory(@ModelAttribute("categoriesObj") Categories category) {
        categoriesRepository.save(category);
        return "redirect:/categories/";
    }
     
    @PostMapping("/delete/{id}")
        public String deleteCategory (@PathVariable("id") Integer id) {
        Categories category = categoriesRepository.findById(id).get();
        categoriesRepository.deleteById(id);
        return "redirect:/categories/";
    }

    
    }






