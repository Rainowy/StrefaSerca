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
import java.util.List;
import java.util.OptionalInt;
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
    ModelAndView selectedArticle(@PathVariable String articleName){
        return new ModelAndView(articleName);
    }


    @GetMapping("/next")
    ModelAndView nextPage(@RequestParam String fileName){

        List<ArticleDto> articleInfo = articleService.getArticleInfo();

        String name ="";
        for (int i = 0; i <articleInfo.size() ; i++) {
            if(articleInfo.get(i).getFileName().equals(fileName) && i != articleInfo.size() -1){
                name = articleInfo.get(i +1).getFileName();
                break;
            } else name = articleInfo.get(0).getFileName();
        }
        return URLEncoder(name);
    }


    @GetMapping("/previous")
    ModelAndView previousPage(@RequestParam String fileName){

        List<ArticleDto> articleInfo = articleService.getArticleInfo();

        String name ="";
        for (int i = 0; i <articleInfo.size() ; i++) {
            if(articleInfo.get(i).getFileName().equals(fileName) && i != 0){
                name = articleInfo.get(i -1).getFileName();
                break;
            } else name = articleInfo.get(articleInfo.size() -1).getFileName();
        }
        return URLEncoder(name);
    }

    @SneakyThrows
    private ModelAndView URLEncoder(String fileName){
        String encodedName = URLEncoder.encode(fileName, "UTF-8");
        return new ModelAndView(new RedirectView("/article/" + encodedName, true, true, false));
    }
}



