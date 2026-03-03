package auto.auto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import auto.auto.model.Categories;
import auto.auto.service.CategoriesService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")

public class Categoriescontroller {

    @Autowired 
    private CategoriesService categoriesService;

    @GetMapping("/")
    public String indexCategorries(Model model){

        List<Categories> listCategories = categoriesService.findAll();

        model.addAttribute("listCategories", listCategories);
        model.addAttribute("categoriesObj", new Categories()); 
        
        return "categories/index";
    }



    @PostMapping("/create")
    public String createCategory(
        @Valid @ModelAttribute("categoriesObj") Categories category,
        BindingResult bindingResult,
        Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            model.addAttribute("listCategories", categoriesService.findAll());
            return "categories/index";
        }
        try {
            categoriesService.createCategory(category);
            redirectAttributes.addFlashAttribute("successMessage", "Categoria aggiunta con successo");
            return "redirect:/categories/";
            
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("listCategories", categoriesService.findAll());
            return "categories/index";
        }
    }
     
    @PostMapping("/delete/{id}")
        public String deleteCategory (@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try{
            categoriesService.deleteCategory(id);
            redirectAttributes.addFlashAttribute("successMessage", "Categoria eliminata correttamente");
        } catch(IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/categories/";
    }

    
    }






