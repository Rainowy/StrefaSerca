package pl.strefaserca.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CardioServiceController {

    @GetMapping("/cardioService")
    ModelAndView cardiologyService(@RequestParam String name){

        System.out.println(name + " nazwa serwisu");

        return new ModelAndView("/cardio_services/" + name);
    }
}
