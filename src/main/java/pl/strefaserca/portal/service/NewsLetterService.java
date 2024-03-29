package pl.strefaserca.portal.service;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pl.strefaserca.portal.email.OnNewsletterRequestEvent;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@Log4j2
@Service
public class NewsLetterService {

    @Value("${path.newsletter}") //if @value variable exists lombok @AllArgsConstructor doesn't work so manual creation needed
    private String pathToNewsletterFiles;
    private ApplicationEventPublisher eventPublisher;
    private HttpServletRequest request;

    public NewsLetterService(ApplicationEventPublisher eventPublisher, HttpServletRequest request) {
        this.eventPublisher = eventPublisher;
        this.request = request;
    }

    public void sendConfirmationMail(String email) {
        String appUrl = request.getContextPath();
        Locale locale = request.getLocale();
        eventPublisher.publishEvent(new OnNewsletterRequestEvent(appUrl, locale, email));
    }

    @SneakyThrows
    public void saveConfirmationToken(String mail, String token) {

        OutputStream output = new FileOutputStream(new File(pathToNewsletterFiles + "newsletter.properties"), true);

        Properties prop = new Properties();
        prop.setProperty(token, mail);
        /** save properties file */
        prop.store(output, null);
    }

    @SneakyThrows
    public Optional<String> getConfirmationToken(String token) {

        InputStream input = new FileInputStream(new File(pathToNewsletterFiles + "newsletter.properties"));

        Properties prop = new Properties();
       /** load properties file */
        prop.load(input);
        Optional<String> property = Optional.ofNullable(prop.getProperty(token));
        property.ifPresent(this::saveConfirmedEmail);
        return property;
    }

    /**Temporary sollution till DB will rise */
    @SneakyThrows
    private void saveConfirmedEmail(String confirmedEmail) {

        FileWriter fw = new FileWriter(new File(pathToNewsletterFiles + "newsletter_emails.txt"),true);
        PrintWriter writer = new PrintWriter(fw);
        writer.write(confirmedEmail);
        writer.println();
        writer.flush();
        writer.close();
    }
}
