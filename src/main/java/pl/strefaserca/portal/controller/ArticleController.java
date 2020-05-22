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
    ModelAndView parse() {
        return new ModelAndView("articles", "articles", articleService.getArticleInfo());
    }

    @SneakyThrows
    @GetMapping("/next")
    ModelAndView nextPage(@RequestParam String fileName){
        System.out.println(fileName);

        List<ArticleDto> articleInfo = articleService.getArticleInfo();

        articleInfo.stream()
                .forEach(e -> System.out.println(e.getFileName() + " nazwa"));

        String name ="";
        for (int i = 0; i <articleInfo.size() ; i++) {
            System.out.println(i);
            if(articleInfo.get(i).getFileName().equals(fileName) && i != articleInfo.size() -1){
                System.out.println(i);
                name = articleInfo.get(i +1).getFileName();
                break;
            }
            else {
                name = articleInfo.get(0).getFileName();
            }
        }

        System.out.println(name + " NAZZWA");


        String encodedId = URLEncoder.encode(name, "UTF-8");
        ModelAndView m = new ModelAndView(new RedirectView("/article/" + encodedId, true, true, false));
        return m;
    }

    @SneakyThrows
    @GetMapping("/previous")
    ModelAndView previousPage(@RequestParam String fileName){
        System.out.println(fileName);

        List<ArticleDto> articleInfo = articleService.getArticleInfo();

        String name ="";
        for (int i = 0; i <articleInfo.size() ; i++) {
            System.out.println(i);
            if(articleInfo.get(i).getFileName().equals(fileName) && i != 0){
                System.out.println(i);
                name = articleInfo.get(i -1).getFileName();
                break;
            }
            else {
                name = articleInfo.get(articleInfo.size() -1).getFileName();
            }
        }

        System.out.println(name + " NAZZWA");


        String encodedId = URLEncoder.encode(name, "UTF-8");
        ModelAndView m = new ModelAndView(new RedirectView("/article/" + encodedId, true, true, false));
        return m;
    }
}



