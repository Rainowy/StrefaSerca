package pl.strefaserca.portal.controller;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import pl.strefaserca.portal.model.dto.ArticleDto;
import pl.strefaserca.portal.service.ArticleService;

import java.net.URLEncoder;
import java.util.*;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class ArticleController {

    private ArticleService articleService;

    @GetMapping("/articles")
    ModelAndView allArticles() {
        return new ModelAndView("articles", "articles", articleService.getArticleInfo());
    }

    @GetMapping("/selected/{articleName}")
    ModelAndView selectedArticle(@PathVariable String articleName) {
        ModelAndView model = new ModelAndView(articleName);
        model.addObject("nextArticle", articleService.nextArticle(articleName));
        model.addObject("prevArticle", articleService.prevArticle(articleName));
        return model;
    }



//    @SneakyThrows
//    private ModelAndView URLEncoder(String fileName) {
//        String encodedName = URLEncoder.encode(fileName, "UTF-8");
//        return new ModelAndView(new RedirectView("/article/" + encodedName, true, true, false));
//    }
}



