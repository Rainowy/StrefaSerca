package pl.strefaserca.portal.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import pl.strefaserca.portal.email.OnContactQuestionEvent;
import pl.strefaserca.portal.model.dto.AjaxResponse;
import pl.strefaserca.portal.service.ArticleService;
import pl.strefaserca.portal.service.ContactService;
import pl.strefaserca.portal.service.TestimonialService;
import pl.strefaserca.portal.validation.GoogleResponse;

import java.io.Serializable;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Controller
@AllArgsConstructor
@RequestMapping("/home")
public class HomeController {

//    @Bean
//    public RestOperations restTemplate() {
//        return new RestTemplate();
//    }
//@Bean
//public RestOperations restOperations(){
//    return restOperations;
//}


//private RestOperations restOperations;
//private RestOperations restTemplate;

    // private  RestTemplate restTemplate;
    private ArticleService articleService;
    private TestimonialService testimonialService;
    private ContactService contactService;


//    public HomeController(RestOperations restOperations, ArticleService articleService, TestimonialService testimonialService, ContactService contactService) {
////        this.restOperations = restOperations;
//        this.articleService = articleService;
//        this.testimonialService = testimonialService;
//        this.contactService = contactService;


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

    @RequestMapping(value = "/askQuestion", method = RequestMethod.POST,
            produces = "application/json")
//    @PostMapping("/askQuestion")
    public @ResponseBody
    Serializable askQuestion(@RequestParam String name,
                             @RequestParam String email,
                             @RequestParam String phone,
                             @RequestParam String textarea,
                             @RequestParam String token
    ) throws InterruptedException, ExecutionException {

//        @RequestParam String token
        System.out.println("TOKEN TO " + token);

        URI verifyUri = URI.create(String.format(
                "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s",
                "6LfqCYYaAAAAAEbL4mTHqh7Fg9BDVX8ZLqzKpXlx", token));
//&remoteip=%s
        System.out.println(verifyUri);
        RestTemplate restTemplate = new RestTemplate();

        GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);
        GoogleResponse googleResponse2 = restTemplate.getForObject(verifyUri, GoogleResponse.class);
//
//        System.out.println(googleResponse.getChallengeTs());
//        System.out.println(googleResponse.getHostname());
//        System.out.println(googleResponse.isSuccess());
//        System.out.println(googleResponse.hasClientError());
//        if(!googleResponse.isSuccess()) {
//            throw new ReCaptchaInvalidException("reCaptcha was not successfully validated");
//        }
//        System.out.println(googleResponse.getErrorCodes());
//        System.out.println("WYNIK TO " + googleResponse.isSuccess());

//        System.out.println(googleResponse.toString());

        boolean response = googleResponse2.isSuccess();
        Future<Boolean> future;
        if (response) {
            OnContactQuestionEvent event = new OnContactQuestionEvent(email, name, phone, textarea);
            contactService.publishEvent(event);
            future = contactService.sendQuestion();
        } else {
            System.out.println("LOG " + "captcha-error");
            System.out.println(googleResponse2.getChallengeTs());
//            System.out.println(googleResponse2.getErrorCodes());
//            googleResponse2.ge
            return response;
        }

        System.out.println("RESPONSE " + response);
        System.out.println("FUTURE " + future.get());


        while (true) {
            if (future.isDone() && future.get()) {
                return future.get();
            } else System.out.println("LOG " + "mail-error");
            return future.get();
        }


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


