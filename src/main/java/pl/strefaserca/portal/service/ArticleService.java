package pl.strefaserca.portal.service;

import lombok.SneakyThrows;
import org.apache.logging.log4j.util.PropertySource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import pl.strefaserca.portal.model.dto.ArticleDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ArticleService {

    /**
     * Create List of articles in StrefaHtml directory
     */
    @SneakyThrows
    private FileTime getCreated (String path){

        return (FileTime) Files.getAttribute(Paths.get(path), "creationTime");
    }

    public List<ArticleDto> recentArticles(){
        List<ArticleDto> recentArticles = new ArrayList<>();
        for (int i = 0; i <3 ; i++) {
            recentArticles.add(getArticleInfo().get(i));
        }
        return recentArticles;
    }
    @SneakyThrows
    public List<ArticleDto> getArticleInfo() {

        List<ArticleDto> articles;
        Stream<Path> paths = Files.walk(Paths.get("/home/tomek/Documents/StrefaHtml"));
        articles = paths.map(Path::toString)
                .filter(p -> !p.contains("article"))
                .filter(p -> p.endsWith(".html"))
                .map(p -> new ArticleDto(parseTitle(p), parseImage(p), parseFileName(p), parseLead(p), getCreated(p) )).collect(Collectors.toList());

        Collections.sort(articles, Comparator.comparing(ArticleDto::getCreated).reversed());

        List<ArticleDto> recentArticles = new ArrayList<>();

        for (int i = 0; i <3 ; i++) {
            recentArticles.add(articles.get(i));
        }

        recentArticles.forEach(a -> System.out.println(a.getImgSrc() + a.getFileName() + " nazwa" + a.getCreated() ));

//        articles.forEach(a -> System.out.println(a.getFileName() + " utworzono" +  a.getCreated()));


//        Stream<Path> paths2 = Files.walk(Paths.get("/home/tomek/Documents/StrefaHtml"));
//
//        List<FileTime> creationTime = paths2.map(path -> {
//            try {
//                return (FileTime) Files.getAttribute(path, "creationTime");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        })
//                .collect(Collectors.toList());
//
//        creationTime.forEach(System.out::println);


//        Stream<Path> paths2 = Files.walk(Paths.get("/home/tomek/Documents/StrefaHtml"));
//
//        try (Stream<FileTime> creationTime = paths2.map(path -> (FileTime) Files.getAttribute(path, "creationTime"));


//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        Files.walk(Paths.get("/home/tomek/Documents/StrefaHtml"))


//        paths
//                .map(s -> {
//                            try {
//                                FileTime creationTime = (FileTime) Files.getAttribute(s, "creationTime");
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//
//                );


        return articles;
    }

    /**
     * Parse img, name, title and lead from article
     */
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
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return imgSrc;
        return imgSrc;
    }

    private String parseFileName(String path) {
        String pathRefactored = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
        return pathRefactored;
    }

    private String parseLead(String path) {

        String firstPTag = "";
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

    /**
     * Previous or next Article
     */
    public ArticleDto nextArticle(String fileName) {
        List<ArticleDto> articleInfo = getArticleInfo();

        for (int i = 0; i < articleInfo.size(); i++) {
            if (articleInfo.get(i).getFileName().equals(fileName) && i != articleInfo.size() - 1) {
                return new ArticleDto(articleInfo.get(i + 1).getFileName(), articleInfo.get(i + 1).getTitle());
            }
        }
        return new ArticleDto(articleInfo.get(0).getFileName(), articleInfo.get(0).getTitle());
    }

    public ArticleDto prevArticle(String fileName) {
        List<ArticleDto> articleInfo = getArticleInfo();

        for (int i = 0; i < articleInfo.size(); i++) {
            if (articleInfo.get(i).getFileName().equals(fileName) && i != 0) {
                return new ArticleDto(articleInfo.get(i - 1).getFileName(), articleInfo.get(i - 1).getTitle());
            }
        }
        return new ArticleDto(articleInfo.get(articleInfo.size() - 1).getFileName(), articleInfo.get(articleInfo.size() - 1).getTitle());
    }
}
