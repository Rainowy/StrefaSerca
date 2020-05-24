package pl.strefaserca.portal.service;

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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ArticleService {

    public List<ArticleDto> getArticleInfo() {

        List<ArticleDto> articles = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get("/home/tomek/Documents/StrefaHtml"))) {

            articles = paths.map(Path::toString)
                    .filter(p -> p.endsWith(".html"))
                    .map(p -> new ArticleDto(parseTitle(p), parseImage(p), parseFileName(p), parseLead(p))).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articles;
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
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
