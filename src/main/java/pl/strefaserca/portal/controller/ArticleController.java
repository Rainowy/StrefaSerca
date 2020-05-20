package pl.strefaserca.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @GetMapping("/1")
    ModelAndView post1 (){
        return new ModelAndView("/articles/zawał-serca");
    }
}
