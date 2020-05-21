package pl.strefaserca.portal.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.strefaserca.portal.model.dto.ArticleDto;

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


    //    @ResponseBody
    @GetMapping("/parse")
    ModelAndView parse() {
        ModelAndView model = new ModelAndView("/blog");

        List<String> htmlTitle = new ArrayList<>();
        List<String> htmlImageSrc = new ArrayList<>();

        List<ArticleDto> articles = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get("/home/tomek/Documents/StrefaHtml"))) {
//            paths
////                    .filter(Files::isRegularFile)
////                    .filter(f -> Files.isRegularFile(f) && !Files.isDirectory(f))
//                    .filter(f -> f.endsWith(".html"))
//                    .forEach(e -> {
//                      parseImages(e);
//                       parseNames(e);
//                    });


            paths.map(x -> x.toString())
                    .filter(f -> f.endsWith(".html"))
                    .forEach(e -> {
                        System.out.println(e + " CO  Z TEGO");
//                     htmlImageSrc.add( parseImages(e));
//                        htmlTitle.add(parseNames(e));
                        articles.add(new ArticleDto(parseTitle(e), parseImage(e), parseFileName(e), parseLead(e)));
                    });

        } catch (IOException e) {
            e.printStackTrace();

        }

//        System.out.println(htmlTitle);
//        System.out.println(htmlImageSrc);
//        System.out.println("SIZE " + htmlTitle.size());

        articles.stream()
                .forEach(a -> System.out.println(a.getLead()));


//        List<ArticleDto> articles = new ArrayList<>();

//        htmlTitle.stream()
//                .forEach(a -> {
//                    articles.add(new ArticleDto(a,);
//                });
//
        model.addObject("articles", articles);
//        model.addObject("images", htmlImageSrc);


        return model;
    }

    private String parseTitle(String path) {

        String htmlTitle = "";
        try {
            Document document = Jsoup.parse(new File(path), "utf-8");
            htmlTitle = document.title();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return htmlTitle;
    }

    private String parseImage(String path) {


        String imgSrc = "";
        try {
            Document document = Jsoup.parse(new File(String.valueOf(path)), "utf-8");
            Elements images = document.select("img[src~=(?i)\\.(png|jpe?g|gif)]");

            for (Element image : images) {
                if (image.attr("src").contains("main-img")) {
                    imgSrc = (image.attr("src"));

//                    this.imagesSrc.add(image.attr("src"));
                }
//                System.out.println("src : " + image.attr("src"));
//                System.out.println("height : " + image.attr("height"));
//                System.out.println("width : " + image.attr("width"));
//                System.out.println("alt : " + image.attr("alt"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgSrc;
    }

    private String parseFileName(String path) {
        return (path.substring(path.lastIndexOf("/")));
    }

    private String parseLead(String path) {
        String firstPTag="";
        try {
            Document document = Jsoup.parse(new File(path), "utf-8");
            firstPTag = document.select("p").first().text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int firstDot = firstPTag.indexOf(".");
        String articleLead = "";
        if (firstDot != -1) {
            articleLead = firstPTag.substring(0, firstDot);
        }
        return articleLead;
    }
}



