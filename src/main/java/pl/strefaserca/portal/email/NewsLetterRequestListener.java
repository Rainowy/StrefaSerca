package pl.strefaserca.portal.email;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.strefaserca.portal.service.NewsLetterService;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.UUID;

//@Slf4j
@Log4j2
@Component
@AllArgsConstructor
public class NewsLetterRequestListener implements ApplicationListener<OnNewsletterRequestEvent> {

    private NewsLetterService newsLetterService;
    private JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    @Override
    public void onApplicationEvent(OnNewsletterRequestEvent event) {
        this.confirmNewsLetterRequest(event);
    }

    private void confirmNewsLetterRequest(OnNewsletterRequestEvent event) {

        String emailToConfirm = event.getEmail();
        String token = UUID.randomUUID().toString();
        newsLetterService.saveConfirmationToken(emailToConfirm, token);
        // here can be createVeriToken in service
        String subject = "Zapraszam do Newslettera Strefy Serca.";
        String confirmationUrl = event.getAppUrl() + "/newsletterConfirm?token=" + token;
        try {
            mailSender.send(prepareMimeMessage(emailToConfirm, subject, confirmationUrl));
        } catch (MailException ex) {
            log.error(ex.getMessage());
        }
    }

    @SneakyThrows
    private MimeMessage prepareMimeMessage(String emailToConfirm, String subject, String confirmationUrl) {
        String body = getMailBody(confirmationUrl);
        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(emailToConfirm);
        helper.setSubject(subject);
        helper.setText(body, true);
        helper.addInline("logo", new File("/home/tomek/Workspace/portal/src/main/resources/static/images/logo-main.jpg"));
        return mail;
    }

    private String getMailBody(String confirmationUrl) {
        Context context = new Context();
        context.setVariable("header", "Portal Strefa Serca zaprasza do Newslettera");
        context.setVariable("title", "Użyj linku poniżej aby zapisać się do biuletynu informacyjnego Strefy Serca");
        context.setVariable("description", "http://localhost:8080" + confirmationUrl);
        return templateEngine.process("template", context);
    }
}

