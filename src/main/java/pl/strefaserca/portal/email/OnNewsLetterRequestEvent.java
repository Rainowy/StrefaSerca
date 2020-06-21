package pl.strefaserca.portal.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class OnNewsLetterRequestEvent extends ApplicationEvent {

    private String appUrl;
    private Locale locale;
    private String email;

    public OnNewsLetterRequestEvent(String appUrl, Locale locale, String email) {
        super(email);
        this.email = email;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}
