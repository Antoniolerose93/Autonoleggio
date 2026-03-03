package auto.auto.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auto.auto.model.Auto;
import auto.auto.service.AutoService;

@RestController 
@CrossOrigin
@RequestMapping("/api/auto")
public class AutoRestController {

    @Autowired
    private AutoService autoService;

    @GetMapping
    public List<Auto> showAutoOutside(@RequestParam(name="keyword", required = false) String keyword){
        return autoService.findByKeyword(keyword);
    }

    @GetMapping("{id}")
    public Auto get (@PathVariable("id") Integer id){
        return autoService.findById(id);
    }

    @PostMapping()
    public Auto createFast(@ModelAttribute Auto auto){
        return autoService.createAuto(auto);
    }


}
