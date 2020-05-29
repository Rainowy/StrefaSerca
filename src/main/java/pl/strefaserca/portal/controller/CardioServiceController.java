package pl.strefaserca.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/service")
public class CardioServiceController {

    @GetMapping("/selected/{serviceName}")
    ModelAndView selectedArticle(@PathVariable String serviceName) {
        List<String> services = new ArrayList<>();
        services.add("echokardiografia");
        services.add("holter");






        ModelAndView model = new ModelAndView("/cardio_services/" +serviceName);
//        model.addObject("nextService", articleService.nextArticle(articleName));
//        model.addObject("prevService", articleService.prevArticle(articleName));
        return model;
    }

    @GetMapping("/cardioService")
    ModelAndView cardiologyService(@RequestParam String name){

        System.out.println(name + " nazwa serwisu");

        return new ModelAndView("/cardio_services/" + name);
    }
}
