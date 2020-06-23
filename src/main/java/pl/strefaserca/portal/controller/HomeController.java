package pl.strefaserca.portal.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.strefaserca.portal.service.ArticleService;
import pl.strefaserca.portal.service.NewsLetterService;
import pl.strefaserca.portal.service.TestimonialService;

import java.util.Locale;
import java.util.Map;

@Controller
@AllArgsConstructor
public class HomeController {

    private ArticleService articleService;
    private TestimonialService testimonialService;
    private NewsLetterService newsLetterService;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("index", "recentArticles", articleService.recentArticles());
        model.addObject("testimonials", testimonialService.rotatedTestimonials());
        return model;
    }

    @GetMapping("/certificates")
    public ModelAndView certificates() {
        return new ModelAndView("certificates");
    }

    @GetMapping("/services")
    public ModelAndView services() {
        return new ModelAndView("services");
    }

    @GetMapping("/contact")
    public ModelAndView contact() {
        return new ModelAndView("contact");
    }

    @GetMapping("/blog")
    public ModelAndView blog() {
        return new ModelAndView("articles");
    }

    @GetMapping("/testimonials")
    public ModelAndView testimonials() {
        return new ModelAndView("testimonials", "testimonials", testimonialService.allTestimonials());
    }

    @GetMapping("/about")
    public ModelAndView about() {
        return new ModelAndView("about_me");
    }


    @PostMapping("/newsLetter")
    public @ResponseBody void saveUser(@RequestParam String newsLetter) {
        if(!newsLetter.isEmpty()) {
            newsLetterService.sendConfirmationMail(newsLetter);
            System.out.println(newsLetter);
        }
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration
            (WebRequest request, @RequestParam("token") String token, RedirectAttributes redirectAttributes) {

        Locale locale = request.getLocale();

        Map<String, String> confirmationToken = newsLetterService.getConfirmationToken(token);

        System.out.println(confirmationToken + " OTO JEST MAPA");

        return "redirect:/about";
    }
}

