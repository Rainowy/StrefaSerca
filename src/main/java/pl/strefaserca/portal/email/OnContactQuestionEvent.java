package pl.strefaserca.portal.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class OnContactQuestionEvent extends ApplicationEvent {

    private String email;
    private String name;
    private String phone;
    private String message;

    public OnContactQuestionEvent(String email, String name, String phone, String message) {
        super(email);
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.message = message;
    }
}
