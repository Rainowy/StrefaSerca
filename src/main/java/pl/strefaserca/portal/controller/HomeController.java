package pl.strefaserca.portal.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.strefaserca.portal.service.ArticleService;
import pl.strefaserca.portal.service.TestimonialService;

@Controller
@AllArgsConstructor
public class HomeController {

    ArticleService articleService;
    TestimonialService testimonialService;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("index","recentArticles", articleService.recentArticles());
        model.addObject("testimonials",testimonialService.rotatedTestimonials());
        return model;
    }

    @GetMapping("/certificates")
    public ModelAndView certificates(){
        return new ModelAndView("certificates");
    }

    @GetMapping("/services")
    public ModelAndView services(){
        return new ModelAndView("services");
    }

    @GetMapping("/service-detail")
    public ModelAndView serviceDetail(){
        return new ModelAndView("service-detail");
    }

    @GetMapping("/contact")
    public ModelAndView contact(){
        return new ModelAndView("contact");
    }

    @GetMapping("/blog")
    public ModelAndView blog(){
        return new ModelAndView("articles");
    }

    @GetMapping("/testimonials")
    public ModelAndView testimonials(){
        return new ModelAndView("testimonials","testimonials",testimonialService.allTestimonials());
    }

    @GetMapping("/about")
    public ModelAndView about(){
        return new ModelAndView("about_me");
    }
}
