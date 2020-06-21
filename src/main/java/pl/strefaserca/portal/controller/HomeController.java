package pl.strefaserca.portal.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.strefaserca.portal.model.dto.SysUserDto;
import pl.strefaserca.portal.service.ArticleService;
import pl.strefaserca.portal.service.TestimonialService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class HomeController {

    ArticleService articleService;
    TestimonialService testimonialService;

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

    //    @RequestMapping("/newsLetter")
//    public void newsLetter(@RequestParam(required = false) String newsLetter){
//
//        System.out.println(newsLetter);
////        System.out.println(newsLetter);
////        return null;
////        new ModelAndView("redirect:/")
//    }
//    @RequestMapping(value = "saveUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
////public void saveUser(@RequestBody SysUserDto user) {
//    public @ResponseBody
//    void getPatientDetails(
//            SysUserDto user) {
//        //code to save user object
//        System.out.println(user.getName() + "imię ");
//
//
//    }
    @RequestMapping(value = "saveUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody void saveUser(   @RequestParam String newsLetter) {
        //code to save user object
        List<SysUserDto> list = new ArrayList<>();

        System.out.println(newsLetter);
    }
}

