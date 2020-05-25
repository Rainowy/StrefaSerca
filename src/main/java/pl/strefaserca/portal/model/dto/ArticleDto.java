package pl.strefaserca.portal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ArticleDto {

    private String title;
    private String imgSrc;
    private String fileName;
    private String lead;

    public ArticleDto( String fileName, String title) {
        this.title = title;
        this.fileName = fileName;
    }
}
