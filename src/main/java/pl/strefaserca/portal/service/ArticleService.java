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

    /**Create List of articles in StrefaHtml directory */
    public List<ArticleDto> getArticleInfo() {

        List<ArticleDto> articles = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get("/home/tomek/Documents/StrefaHtml"))) {

            articles = paths.map(Path::toString)
                    .filter(p -> !p.contains("article"))
                    .filter(p -> p.endsWith(".html"))
                    .map(p -> new ArticleDto(parseTitle(p), parseImage(p), parseFileName(p), parseLead(p))).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        articles.stream()
                .forEach(e -> System.out.println(e.getImgSrc()));
        return articles;
    }
    /** Parse img, name, title and lead from article */
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
    /** Previous or next Article */
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
