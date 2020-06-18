package pl.strefaserca.portal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ArticleDto {

    private String title;
    private String imgSrc;
    private String fileName;
    private String lead;
    private LocalDateTime created;

    public ArticleDto( String fileName, String title) {
        this.title = title;
        this.fileName = fileName;
    }
}
