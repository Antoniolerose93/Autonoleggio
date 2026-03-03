package auto.auto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import auto.auto.model.Drivers;
import auto.auto.service.DriverService;


@Controller
@RequestMapping("/clienti")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @GetMapping("/")
    public String listDrivers(Model model){
        List <Drivers> allDrivers = driverService.findAll();
        model.addAttribute("drivers", allDrivers);
        return"drivers/index";
    }


}
