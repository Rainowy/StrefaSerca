package pl.strefaserca.portal.email;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ContactQuestionListener implements ApplicationListener<OnContactQuestionEvent> {

    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnContactQuestionEvent event) {
        this.askQuestion(event);
    }

    private void askQuestion(OnContactQuestionEvent event) {

        String recipient = "strefaserca@gmail.com";
        String subject = "Strefa Serca pytanie";

        String name = event.getName();
        String phone = event.getPhone();
        String email = event.getEmail();
        String message = event.getMessage();

        StringBuilder sb = new StringBuilder();
        sb.append("Imię: " + name).append("\n");
        sb.append("Email: " + email).append("\n");
        sb.append("Telefon: " + phone).append("\n");
        sb.append("Napisał(a): " + message);

        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(recipient);
        emailMessage.setSubject(subject);
        emailMessage.setText(sb.toString());
        emailMessage.setReplyTo(email);
        try {
        mailSender.send(emailMessage);
        }catch (MailException ex) {
            log.error(ex.getMessage());
        }
    }
}
