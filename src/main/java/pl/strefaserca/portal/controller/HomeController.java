package pl.strefaserca.portal.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.strefaserca.portal.service.ArticleService;

@Controller
@AllArgsConstructor
public class HomeController {

    ArticleService articleService;

    @GetMapping("/index")
    public ModelAndView index(){
        return new ModelAndView("index", "recentArticles", articleService.recentArticles());
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
        return new ModelAndView("testimonials");
    }

    @GetMapping("/about")
    public ModelAndView about(){
        return new ModelAndView("about_me");
    }
}
