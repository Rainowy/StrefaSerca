package pl.strefaserca.portal.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.strefaserca.portal.service.ArticleService;

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
}



