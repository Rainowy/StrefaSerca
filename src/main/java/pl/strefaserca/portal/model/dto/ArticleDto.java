package pl.strefaserca.portal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.nio.file.attribute.FileTime;

@Getter
@Setter
@AllArgsConstructor
public class ArticleDto {

    private String title;
    private String imgSrc;
    private String fileName;
    private String lead;
    private FileTime created;

    public ArticleDto( String fileName, String title) {
        this.title = title;
        this.fileName = fileName;
    }
}
