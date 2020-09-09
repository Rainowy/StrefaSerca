package pl.strefaserca.portal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import pl.strefaserca.portal.email.OnContactQuestionEvent;
import java.util.concurrent.Future;

@Log4j2
@Service
@RequiredArgsConstructor //adds a constructor for all fields that are either @NonNull or final.
public class ContactService {

    private final ApplicationEventPublisher eventPublisher;
    private final JavaMailSender mailSender;
    private OnContactQuestionEvent event;

    //publish event
    public void publishEvent(OnContactQuestionEvent event) {
        eventPublisher.publishEvent(event);
    }

    //create listener for published event
    @EventListener
    public void questionEventListener(OnContactQuestionEvent event) {
        this.event = event;
    }

    private StringBuilder createQuestionText(){
        String name = event.getName();
        String phone = event.getPhone();
        String email = event.getEmail();
        String message = event.getMessage();

        StringBuilder sb = new StringBuilder();
        sb.append("Imię: " + name).append("\n");
        sb.append("Email: " + email).append("\n");
        sb.append("Telefon: " + phone).append("\n");
        sb.append("Napisał(a): " + message);
        return sb;
    }

    @Async("threadPoolTaskExecutor")
    public Future<Boolean> sendQuestion() {
        String recipient = "strefaserca@gmail.com";
        String subject = "Strefa Serca pytanie";

        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(recipient);
        emailMessage.setSubject(subject);
        emailMessage.setText(createQuestionText().toString());
        emailMessage.setReplyTo(event.getEmail());

        try {
            mailSender.send(emailMessage);
        } catch (MailException ex) {
            log.error(ex.getMessage());
            return new AsyncResult<>(false);
        }
        return new AsyncResult<>(true);
    }
}



