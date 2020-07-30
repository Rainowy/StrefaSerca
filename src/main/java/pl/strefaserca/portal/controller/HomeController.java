package pl.strefaserca.portal.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.strefaserca.portal.email.OnContactQuestionEvent;
import pl.strefaserca.portal.service.ArticleService;
import pl.strefaserca.portal.service.ContactService;
import pl.strefaserca.portal.service.TestimonialService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Controller
@AllArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private ArticleService articleService;
    private TestimonialService testimonialService;
    private ContactService contactService;

    @GetMapping("/main")
    public ModelAndView home() {
        ModelAndView model = new ModelAndView("home", "recentArticles", articleService.recentArticles());
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

    @PostMapping("/askQuestion")
    public @ResponseBody
    Boolean askQuestion(@RequestParam String name,
                        @RequestParam String email,
                        @RequestParam String phone,
                        @RequestParam String textarea) throws InterruptedException, ExecutionException {

        OnContactQuestionEvent event = new OnContactQuestionEvent(email, name, phone, textarea);

        contactService.publishEvent(event);
        Future<Boolean> future = contactService.sendQuestion();

        while (true) {
            if (future.isDone()) {
                return future.get();
            }
        }
    }
}

