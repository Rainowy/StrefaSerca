package pl.strefaserca.portal.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.strefaserca.portal.email.OnContactQuestionEvent;
import pl.strefaserca.portal.email.OnNewsletterRequestEvent;
import pl.strefaserca.portal.service.ArticleService;
import pl.strefaserca.portal.service.ContactService;
import pl.strefaserca.portal.service.NewsLetterService;
import pl.strefaserca.portal.service.TestimonialService;

import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Controller
@AllArgsConstructor
public class HomeController {

    private ArticleService articleService;
    private TestimonialService testimonialService;
    private NewsLetterService newsLetterService;
    private ContactService contactService;
    private MessageSource messages;

//    private OnNewsletterRequestEvent event;

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


    @PostMapping("/newsletter")
    public @ResponseBody
    void setNewsLetter(@RequestParam("newsletter") String newsLetter) {
        if (!newsLetter.isEmpty()) {
            newsLetterService.sendConfirmationMail(newsLetter);
        }
    }

    @GetMapping("/newsletterConfirm")
    public String confirmNewsLetterRequest
            (WebRequest request, @RequestParam("token") String token, RedirectAttributes redirectAttributes) {

        Locale locale = request.getLocale();
        String newsLetterConfirmed = messages.getMessage("auth.message.newsLetterConfirmed", null, locale);
        String invalidEmail = messages.getMessage("auth.message.invalidEmail", null, locale);

        Optional<String> confirmationToken = newsLetterService.getConfirmationToken(token);
        confirmationToken.ifPresentOrElse(p -> redirectAttributes.addFlashAttribute("message", newsLetterConfirmed),
                () -> redirectAttributes.addFlashAttribute("message", invalidEmail));

        return "redirect:/newsletterMessage";
    }

    @GetMapping("/newsletterMessage")
    public ModelAndView registrationMessage(@ModelAttribute("message") String message) {
        return new ModelAndView("newsletter", "message", message);
    }

    @PostMapping("/askQuestion")
    public @ResponseBody
    void askQuestion(@RequestParam String name,
                     @RequestParam String email,
                     @RequestParam String phone,
                     @RequestParam String textarea)throws InterruptedException, ExecutionException {

        OnContactQuestionEvent event = new OnContactQuestionEvent(email,name,phone,textarea);

        contactService.sendQuestion(event);
//        Future<String> future = contactService.askQuestion(new OnContactQuestionEvent(""));
        Future<String> future = contactService.tryIt();
        System.out.println(future.get());
//        if (contactService.askQuestion(null)){
//            System.out.println("WYSŁANO");
//        }
//        else {
//            System.out.println("NIE WYSŁANO");
//        }
//        if (!newsLetter.isEmpty()) {
//            newsLetterService.sendConfirmationMail(newsLetter);
//        }
//        System.out.println(name + " " + email + " " + phone + " " + textarea);
    }
//    public void testAsyncAnnotationForMethodsWithReturnType()
//            throws InterruptedException, ExecutionException {
//        System.out.println("Invoking an asynchronous method. "
//                + Thread.currentThread().getName());
//        Future<String> future = contactService.askQuestion(null);
//
//        while (true) {
//            if (future.isDone()) {
//                System.out.println("Result from asynchronous process - " + future.get());
//                break;
//            }
//            System.out.println("Continue doing something else. ");
//            Thread.sleep(1000);
//        }
//    }
}

