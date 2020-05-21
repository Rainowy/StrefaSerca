package pl.strefaserca.portal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ArticleDto {

    private String title;
    private String imgSrc;
    private String fileName;
    private String lead;
}
