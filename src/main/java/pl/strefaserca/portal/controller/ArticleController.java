package pl.strefaserca.portal.controller;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import pl.strefaserca.portal.service.ArticleService;

import java.net.URLEncoder;

@Controller
@AllArgsConstructor
public class ArticleController {

    private ArticleService articleService;

    @GetMapping("/articles")
    ModelAndView parse() {
        return new ModelAndView("articles", "articles", articleService.getArticleInfo());
    }

    @SneakyThrows
    @GetMapping("/next")
    ModelAndView nextPage(@RequestParam String fileName){
//        (required = false)
        System.out.println(fileName);

        String encodedId = URLEncoder.encode(fileName, "UTF-8");
        ModelAndView m = new ModelAndView(new RedirectView("/article/" + encodedId, true, true, false));
        return m;

//        return "redirect:/article/zawał-serca.html";
//        return new ModelAndView("/article/zawał-serca.html");

//                ="@{${'/article' + article.getFileName()}
    }
}



