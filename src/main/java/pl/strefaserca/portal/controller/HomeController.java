package pl.strefaserca.portal.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import pl.strefaserca.portal.email.OnContactQuestionEvent;
import pl.strefaserca.portal.service.ArticleService;
import pl.strefaserca.portal.service.ContactService;
import pl.strefaserca.portal.service.TestimonialService;
import pl.strefaserca.portal.validation.CaptchaSettings;
import pl.strefaserca.portal.validation.GoogleResponse;

import java.io.Serializable;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Controller
@AllArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private ArticleService articleService;
    private TestimonialService testimonialService;
    private ContactService contactService;
    private CaptchaSettings captchaSettings;

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

    //    @RequestMapping(value = "/askQuestion", method = RequestMethod.POST,
//            produces = "application/json")
    @PostMapping("/askQuestion")
    public @ResponseBody
    Serializable askQuestion(@RequestParam String name,
                             @RequestParam String email,
                             @RequestParam String phone,
                             @RequestParam String textarea,
                             @RequestParam String token
    ) throws InterruptedException, ExecutionException {

        System.out.println("TOKEN TO " + token);

        URI verifyUri = URI.create(String.format(
                "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s",
                captchaSettings.getSecret(), token));

        RestTemplate restTemplate = new RestTemplate();

        GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);
        GoogleResponse googleResponse2 = restTemplate.getForObject(verifyUri, GoogleResponse.class);


//        boolean response = googleResponse2.isSuccess();
        Future<Boolean> future;
        if (googleResponse2.isSuccess()) {
            OnContactQuestionEvent event = new OnContactQuestionEvent(email, name, phone, textarea);
            contactService.publishEvent(event);
            future = contactService.sendQuestion();
        } else {
            System.out.println("LOG " + "captcha-error");
            System.out.println(googleResponse2.getChallengeTs());
//            System.out.println(googleResponse2.getErrorCodes());
            return googleResponse2.isSuccess();
        }

        while (true) {
            if (future.isDone()) {
                if (future.get()) {
                    return true;
                } else System.out.println("LOG " + "mail-error");
                return false;
            }
//            if
//                    &&future.get()){
//                return future.get();
//            } else System.out.println("LOG " + "mail-error");
//            return false;
//        }


//        OnContactQuestionEvent event = new OnContactQuestionEvent(email, name, phone, textarea);
//        contactService.publishEvent(event);
//        Future<Boolean> future = contactService.sendQuestion();

//        return JsonProperty( success = false, responseText = "The attached file is not supported." }, JsonRequestBehavior.AllowGet);

//        return googleResponse.getHostname();

//        System.out.println(googleResponse.isSuccess() + "czy sukces");

//        System.out.println(googleResponse2.isSuccess() + "czy sukces");
//        System.out.println(googleResponse2.getErrorCodes() + " kodzik");
//
//        GoogleResponse.ErrorCode[] errorCodes = googleResponse2.getErrorCodes();
//        Arrays.stream(errorCodes)
//        .forEach(System.out::println);


//        AjaxResponse ajaxResponse = new AjaxResponse();
////        ajaxResponse.setSuccess(googleResponse.isSuccess());
//
//
//        System.out.println("future " + future.isDone());
////        System.out.println("captcha " + googleResponse.isSuccess());


//        while (true) {
//            if (future.isDone() && ) {
//
//                if (googleResponse2.isSuccess() && future.get()) {
//                    return true;
////                    ajaxResponse.setSuccess(true);
////                    ajaxResponse.setMessage("mail-send");
//                } else if (googleResponse2.isSuccess() && !future.get()) {
//                    System.out.println("LOG " + "mail-error");
//                    return false;
//                } else System.out.println("LOG " + "captcha-error");
//                return false;
////            } else {
//////                        ajaxResponse.setMessage("captcha-error");
//////                    }
////                return ajaxResponse;
//            }
//            }

        }


//            if (future.isDone() && googleResponse.isSuccess()) {
//                ajaxResponse.setSuccess(true);
//
//            } else if (!future.isDone() && googleResponse.isSuccess()) {
//                ajaxResponse.setSuccess(false);
//                ajaxResponse.setMessage("mail-error");
//            } else if (future.isDone() && !googleResponse.isSuccess()) {
//                ajaxResponse.setSuccess(false);
//                ajaxResponse.setMessage("captcha-error");
//            } else {
//                ajaxResponse.setSuccess(false);
//                ajaxResponse.setMessage("mail-error");
//            }


//        if(googleResponse.isSuccess()){
//            ajaxResponseBody.setMessage("kolacja");
//        }


//        return ajaxResponseBody;
//        return  ResponseEntity.ok(ajaxResponseBody);

//        return googleResponse;

//        return ajaxResponseBody;
//        while (true) {
//            if (future.isDone() && googleResponse.isSuccess() ) {
//                return googleResponse.getHostname();
//            }
//        }
    }
}


