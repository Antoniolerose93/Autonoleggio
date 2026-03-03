package auto.auto.controller;

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
import auto.auto.service.AutoService;
import auto.auto.service.CategoriesService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auto")

public class Autocontroller {

    @Autowired
    private AutoService autoService;

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("/")
        public String index(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) String fuel,
            Model model) {

            model.addAttribute("brands", autoService.findAllBrands());
            model.addAttribute("models", autoService.findModelsByBrand(brand));
            model.addAttribute("fuels", autoService.findFuelsByBrandAndModel(brand, modelName)); 


            model.addAttribute("auto", autoService.searchAuto(brand, modelName, fuel));
            
            model.addAttribute("selectedBrand", brand);
            model.addAttribute("selectedModel", modelName);
            model.addAttribute("selectedFuel", fuel);

        return "vetture/index";    
        }

        
    

    @GetMapping("/show/{id}")
        public String show(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes){
            try{
                Auto auto = autoService.findById(id);
                model.addAttribute("auto", auto);
            return"vetture/show";
        
            }  catch(IllegalArgumentException e){
                    redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
                return "redirect:/auto/";
            }

        }

    @GetMapping("/create")
        public String createAuto(Model model) {
            model.addAttribute("auto", new Auto());
            model.addAttribute("allCategories", categoriesService.findAll());
        
            return "vetture/create"; 
        }

    @PostMapping("/create")
        public String createSubmit
            (@Valid @ModelAttribute("auto") Auto formAuto,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
            if(bindingResult.hasErrors()){
                model.addAttribute("allCategories", categoriesService.findAll());
                return "vetture/create/";
            }
            try {
                autoService.createAuto(formAuto);
                redirectAttributes.addFlashAttribute("successMessage", "Auto aggiunta correttamente");
                return "redirect:/auto/";
            } catch (IllegalArgumentException e) {
                model.addAttribute("errorMessage", e.getMessage());
                model.addAttribute("allCategories", categoriesService.findAll());
                return "vetture/create/";
            }
    
        
    }

    @GetMapping("/edit/{id}")
        public String edit(@PathVariable ("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try{
            Auto auto = autoService.findById(id);
            model.addAttribute("auto", auto);
            model.addAttribute("allCategories", categoriesService.findAll());
            model.addAttribute("coloreBloccato", true);
            model.addAttribute("modelloBloccato", true);
            model.addAttribute("brandBloccato", true);
            model.addAttribute("alimentazioneBloccato", true);
            model.addAttribute("categoriaBloccato", true);

            return "vetture/edit";
        } catch(IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/auto/";
        }
    }

    @PostMapping("/edit/{id}")
        public String updateAuto(
            @PathVariable("id") Integer id, 
            @Valid @ModelAttribute("auto") Auto formAuto, 
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
                
            if(bindingResult.hasErrors()){
                model.addAttribute("auto", formAuto);
                model.addAttribute("allCategories", categoriesService.findAll());
                model.addAttribute("coloreBloccato", true);
                model.addAttribute("modelloBloccato", true);
                model.addAttribute("brandBloccato", true);
                model.addAttribute("alimentazioneBloccato", true);
                model.addAttribute("categoriaBloccato", true);
                model.addAttribute("targaBloccato", true);
                return "vetture/edit";
            }
            try {
                autoService.editAuto(id, formAuto);
                redirectAttributes.addFlashAttribute("successMessage", "Auto modificata con successo");
                return "redirect:/auto/";
            } catch(IllegalArgumentException e){
                model.addAttribute("errorMessage", e.getMessage());
                model.addAttribute("auto", formAuto);
                model.addAttribute("allCategories", categoriesService.findAll());
                model.addAttribute("coloreBloccato", true);
                model.addAttribute("modelloBloccato", true);
                model.addAttribute("brandBloccato", true);
                model.addAttribute("alimentazioneBloccato", true);
                model.addAttribute("categoriaBloccato", true);
                model.addAttribute("targaBloccato", true);

                return "vetture/index";
            }
        }


    @PostMapping("/delete/{id}")
        public String deleteAuto(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try{
            autoService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Auto eliminato con successo");
        } catch(IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/vetture/";
    }

}
