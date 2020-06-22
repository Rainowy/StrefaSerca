package pl.strefaserca.portal.email;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import pl.strefaserca.portal.service.NewsLetterService;

import javax.tools.JavaFileManager;
import java.util.UUID;

@Component
@AllArgsConstructor
public class NewsLetterRequestListener implements ApplicationListener<OnNewsLetterRequestEvent> {

    private NewsLetterService newsLetterService;
    private MessageSource messageSource;
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnNewsLetterRequestEvent event) {
        this.confirmNewsLetterRequest(event);
    }

    private void confirmNewsLetterRequest(OnNewsLetterRequestEvent event) {

        String emailToConfirm = event.getEmail();
        String token = UUID.randomUUID().toString();
        newsLetterService.saveConfirmationToken(token);
        // here can be createVeriToken in service
        String subject = "Użyj linka aby otrzymywać od nas wiadomości kardiologiczne";
        String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;
        String message = messageSource.getMessage("message.regSucc", null, event.getLocale());

        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(emailToConfirm);
        emailMessage.setSubject(subject);
        emailMessage.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);
        mailSender.send(emailMessage);
    }
}
