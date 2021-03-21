package pl.strefaserca.portal.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AjaxResponse {

    private boolean success;
    private String message;
}
