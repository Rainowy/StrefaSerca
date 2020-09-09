package pl.strefaserca.portal.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import pl.strefaserca.portal.service.ArticleService;

@Controller
@AllArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private ArticleService articleService;

    @GetMapping("/list")
    public ModelAndView allArticles() {
        return new ModelAndView("articles", "articles", articleService.getArticleInfo());
    }

    @GetMapping("/selected/{articleName}")
    public ModelAndView selectedArticle(@PathVariable String articleName) {
        ModelAndView model = new ModelAndView(articleName,"currentArticle",articleService.currentArticle(articleName));
        model.addObject("nextArticle", articleService.nextArticle(articleName));
        model.addObject("prevArticle", articleService.prevArticle(articleName));
        return model;
    }

    @GetMapping("/redirect")
    public RedirectView redirectToUrl(@RequestParam String name) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://" + name);
        return redirectView;
    }
}



