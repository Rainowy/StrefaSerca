package pl.strefaserca.portal.service;

import lombok.SneakyThrows;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ArticleService {

    /**
     * Create List of articles in StrefaHtml directory
     */
    @SneakyThrows
    public List<ArticleDto> getArticleInfo() {

        List<ArticleDto> articles;
        Stream<Path> paths = Files.walk(Paths.get("/home/tomek/Documents/StrefaHtml"));
//        Stream<Path> paths = Files.walk(Paths.get("/home/kasiazen/Documents/StrefaHtml"));
//        Stream<Path> paths = Files.walk(Paths.get("/volume1/web/StrefaHtml"));
        articles = paths.map(Path::toString)
                .filter(p -> !p.contains("article"))
                .filter(p -> p.endsWith(".html"))
                .map(p -> new ArticleDto(parseTitle(p), parseImage(p), parseFileName(p), parseLead(p), getCreated(p))).collect(Collectors.toList());

        Collections.sort(articles, Comparator.comparing(ArticleDto::getCreated).reversed());
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
        return imgSrc;
    }

    private String parseFileName(String path) {
        return path.substring(path.lastIndexOf('/') + 1, path.lastIndexOf('.'));

    }

    private String parseLead(String path) {

        String firstPTag = "";
        try {
            Document document = Jsoup.parse(new File(path), "utf-8");
            firstPTag = document.select("p").first().text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int firstDot = firstPTag.indexOf('.');
        String articleLead = "";
        if (firstDot != -1) {
            articleLead = firstPTag.substring(0, firstDot);
        }
        return articleLead;
    }

    /** Get file creation time as FileTime and converts to LocalDateTime */
    @SneakyThrows
    private LocalDateTime getCreated(String path) {
        FileTime creationTime = (FileTime) Files.getAttribute(Paths.get(path), "creationTime");
        return LocalDateTime.parse(creationTime.toInstant().toString(), DateTimeFormatter.ISO_DATE_TIME);
    }

    public List<ArticleDto> recentArticles() {
        List<ArticleDto> recentArticles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            recentArticles.add(getArticleInfo().get(i));
        }
        return recentArticles;
    }

    public ArticleDto currentArticle(String fileName){
        return getArticleInfo().stream()
                .filter(a -> a.getFileName().equals(fileName))
                .findFirst()
                .orElseThrow();
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
