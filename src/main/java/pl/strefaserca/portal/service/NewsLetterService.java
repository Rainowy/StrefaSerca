package pl.strefaserca.portal.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pl.strefaserca.portal.email.OnNewsLetterRequestEvent;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@Service
@AllArgsConstructor
public class NewsLetterService {

    private ApplicationEventPublisher eventPublisher;
    private HttpServletRequest request;

    public void sendConfirmationMail(String email) {
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnNewsLetterRequestEvent(appUrl, request.getLocale(), email));
    }

    @SneakyThrows
    public void saveConfirmationToken(String mail, String token) {

        File file = new File("/home/tomek/Documents/StrefaHtml/newsletter.properties");
        OutputStream output = new FileOutputStream(file, true);

        Properties prop = new Properties();
        prop.setProperty(token, mail);
        // save prop to project root folder
        prop.store(output, null);
    }

    @SneakyThrows
    public Optional<String> getConfirmationToken(String token) {

        File file = new File("/home/tomek/Documents/StrefaHtml/newsletter.properties");
        InputStream input = new FileInputStream(file);

        Properties prop = new Properties();
        // load a properties file
        prop.load(input);
        return Optional.ofNullable(prop.getProperty(token));
    }
}
