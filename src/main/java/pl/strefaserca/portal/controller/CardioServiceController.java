package pl.strefaserca.portal.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.strefaserca.portal.model.dto.ArticleDto;
import pl.strefaserca.portal.service.CardioService;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/service")
public class CardioServiceController {

    private CardioService cardioService;

    @GetMapping("/selected/{serviceName}")
    ModelAndView selectedArticle(@PathVariable String serviceName) {
        ModelAndView model = new ModelAndView("cardio_services/" + serviceName);
        model.addObject("nextService",cardioService.nextService(serviceName));
        model.addObject("prevService",cardioService.prevService(serviceName));
        return model;







    }

//    @GetMapping("/cardioService")
//    ModelAndView cardiologyService(@RequestParam String name){
//        System.out.println(name + " nazwa serwisu");
//        ModelAndView model = new ModelAndView("/cardio_services/" + name);
//        model.addObject("nextService",nextService(name));
//        model.addObject("prevService",prevService(name));
//
////        return new ModelAndView("/cardio_services/" + name);
//        return model;
//    }




}
