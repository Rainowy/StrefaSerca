package pl.strefaserca.portal.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleDto {

    private String title;
    private String imgSrc;
    private String fileName;
    private String lead;

    public ArticleDto(String title, String imgSrc, String fileName, String lead) {
        this.title = title;
        this.imgSrc = imgSrc;
        this.fileName = fileName;
        this.lead = lead;
    }
}
