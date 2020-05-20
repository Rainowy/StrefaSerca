package pl.strefaserca.portal.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Controller
@RequestMapping("/article")
public class ArticleController {

//    @ResponseBody
    @GetMapping("/1")
     String post1() {
//        return new ModelAndView("/zawał-serca");
        return "/portal/zawał-serca";
    }

    List<String> htmlNames = new ArrayList<>();
    List<String> imagesSrc = new ArrayList<>();

//    @ResponseBody
    @GetMapping("/parse")
    ModelAndView parse() {
        ModelAndView model = new ModelAndView("/blog");



        try (Stream<Path> paths = Files.walk(Paths.get("/home/tomek/Documents/StrefaHtml"))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(e -> {
                      parseImages(e);
                       parseNames(e);
                    });



//                    .peek(p -> p.forEach(ArticleController::parseImages))
//                    .peek(p -> p.forEach(ArticleController::parseNames));
//                    .forEach(ArticleController::parseNames);
//                    .peek(ArticleController::parseImages)
//            .forEach(ArticleController::parseImages);

//            paths
//                    .filter(Files::isRegularFile)
//                    .forEach(ArticleController::parseNames);

        } catch (IOException e) {
            e.printStackTrace();

        }
        System.out.println(htmlNames);
        System.out.println("SIZE " + htmlNames.size());

        model.addObject("articles", htmlNames);
        model.addObject("images",imagesSrc);




        return model;
    }

    private void parseNames(Path path) {

//        List<String> htmlNames = new ArrayList<>();
        try {
            Document document = Jsoup.parse(new File(String.valueOf(path)), "utf-8");
//            System.out.println(document.title());
            this.htmlNames.add(document.title());
        } catch (IOException e) {
            e.printStackTrace();
        }

//        htmlNames.stream()
//                .forEach(System.out::println);


        System.out.println(htmlNames);
//        this.htmlNames = htmlNames;
//        return htmlNames;
    }

    private void  parseImages(Path path) {

        List<String> imagesSrc = new ArrayList<>();
        try {
            Document document = Jsoup.parse(new File(String.valueOf(path)), "utf-8");
            Elements images = document.select("img[src~=(?i)\\.(png|jpe?g|gif)]");





            for (Element image : images) {
                if(image.attr("src").contains("blog-post")) this.imagesSrc.add(image.attr("src"));
//                System.out.println("src : " + image.attr("src"));
//                System.out.println("height : " + image.attr("height"));
//                System.out.println("width : " + image.attr("width"));
//                System.out.println("alt : " + image.attr("alt"));
            }
            imagesSrc.stream()
                    .forEach(System.out::println);



        } catch (IOException e) {
            e.printStackTrace();
        }
//        return imagesSrc;
    }


}
