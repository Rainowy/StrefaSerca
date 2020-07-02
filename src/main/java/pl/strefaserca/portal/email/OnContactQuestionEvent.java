package pl.strefaserca.portal.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;
@Getter
@Setter
public class OnContactQuestionEvent extends ApplicationEvent {

    private String appUrl;
    private Locale locale;
    private String email;
    private String name;
    private String phone;
    private String message;

    public OnContactQuestionEvent(String appUrl, Locale locale, String email, String name, String phone, String message) {
        super(email);
        this.email = email;
        this.name= name;
        this.phone = phone;
        this.message = message;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}
