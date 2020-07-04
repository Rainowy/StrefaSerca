package pl.strefaserca.portal.email;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.strefaserca.portal.service.NewsLetterService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.UUID;

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
        newsLetterService.saveConfirmationToken( emailToConfirm, token);
        // here can be createVeriToken in service
        String subject = "Zapraszam do Newslettera Strefy Serca.";
        String confirmationUrl = event.getAppUrl() + "/newsletterConfirm?token=" + token;

        mailSender.send(getMimeMessage(emailToConfirm, subject, confirmationUrl));
    }

    private MimeMessage getMimeMessage(String emailToConfirm, String subject, String confirmationUrl) {
        String body = getMailBody(confirmationUrl);
        MimeMessage mail = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(emailToConfirm);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.addInline("logo",new File("/home/tomek/Workspace/portal/src/main/resources/static/images/logo-main.jpg"));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return mail;
    }

    private String getMailBody(String confirmationUrl) {
        Context context = new Context();
        context.setVariable("header", "Portal Strefa Serca zaprasza do Newslettera");
        context.setVariable("title", "Użyj linku poniżej aby zapisać się do biuletynu informacyjnego Strefy Serca");
        context.setVariable("description", "http://localhost:8080" + confirmationUrl );
        return templateEngine.process("template", context);
    }
}

