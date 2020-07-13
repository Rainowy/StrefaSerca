package pl.strefaserca.portal.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.strefaserca.portal.email.OnContactQuestionEvent;
import pl.strefaserca.portal.email.OnNewsletterRequestEvent;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Future;

@Log4j2
@Service
//@AllArgsConstructor
public class ContactService {

    private ApplicationEventPublisher eventPublisher;

    //próba
    private NewsLetterService newsLetterService;
    private JavaMailSender mailSender;

    private final TemplateEngine templateEngine;
    //    private ContextStartedEvent event;
//    private OnNewsletterRequestEvent event;
//  SimpleMailMessage simpleMailMessage = null;
    private OnContactQuestionEvent event;


    public ContactService(ApplicationEventPublisher eventPublisher, NewsLetterService newsLetterService, JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.eventPublisher = eventPublisher;
        this.newsLetterService = newsLetterService;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    //publikuje event
    public void sendQuestion(OnContactQuestionEvent event) {
        eventPublisher.publishEvent(event);
    }

    //próba

    //    @Async
    @EventListener
    public void askQuestion(OnContactQuestionEvent event) {
        this.event = event;
//            public Future<String> askQuestion(OnContactQuestionEvent event) {

//        eventPublisher.publishEvent(event);
//        Optional<OnContactQuestionEvent> event1 = Optional.ofNullable(event);

//        if (event1.isPresent()) {


//            simpleMailMessage = emailMessage;

//        tryIt(emailMessage);
//        return;
    }

    @Async
    public Future<Boolean> tryIt() {
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
        } catch (MailException ex) {
            log.error(ex.getMessage());
            return new AsyncResult<>(false);
        }
        return new AsyncResult<>(true);
    }

}



