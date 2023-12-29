package pl.strefaserca.portal.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
//Konstruktor bezargumentowy lombok niezbędny aby JsonMapper działał lub konstruktor @JsonCreator
//@NoArgsConstructor //(force = true, access = AccessLevel.PRIVATE) //force=true gdy są pola final force oznacza że zostaną ustawione na defaultowe wartości
//@AllArgsConstructor
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

    @JsonCreator
    public ArticleDto(@JsonProperty("title")String title,
                      @JsonProperty("imgSrc")String imgSrc,
                      @JsonProperty("fileName")String fileName,
                      @JsonProperty("lead")String lead,
                      @JsonProperty("created")LocalDateTime created) {
        this.title = title;
        this.imgSrc = imgSrc;
        this.fileName = fileName;
        this.lead = lead;
        this.created = created;
    }

    @Override
    public String toString() {
        return "ArticleDto{" +
                "title='" + title + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                ", fileName='" + fileName + '\'' +
                ", lead='" + lead + '\'' +
                ", created=" + created +
                '}';
    }
}
