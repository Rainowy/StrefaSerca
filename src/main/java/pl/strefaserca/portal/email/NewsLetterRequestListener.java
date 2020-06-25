package pl.strefaserca.portal.email;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
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
public class NewsLetterRequestListener implements ApplicationListener<OnNewsLetterRequestEvent> {

    private NewsLetterService newsLetterService;
    private MessageSource messageSource;
    private JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    @Override
    public void onApplicationEvent(OnNewsLetterRequestEvent event) {
        this.confirmNewsLetterRequest(event);
    }

    private void confirmNewsLetterRequest(OnNewsLetterRequestEvent event) {

        String emailToConfirm = event.getEmail();
        String token = UUID.randomUUID().toString();
        newsLetterService.saveConfirmationToken( emailToConfirm, token);
        // here can be createVeriToken in service
        String subject = "Zapraszam do Newslettera Strefy Serca.";
        String confirmationUrl = event.getAppUrl() + "/newsletterConfirm?token=" + token;
        String message = messageSource.getMessage("message.newsletter", null, event.getLocale());

//        SimpleMailMessage emailMessage = new SimpleMailMessage();
//        emailMessage.setTo(emailToConfirm);
//        emailMessage.setSubject(subject);
//        emailMessage.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);
//        mailSender.send(emailMessage);
        Context context = new Context();

        context.setVariable("header", "Portal Strefa Serca zaprasza do NewsLettera");
//        context.setVariable("title", message + "\r\n" + "http://localhost:8080" + confirmationUrl);
        context.setVariable("title", "Użyj linku poniżej aby zapisać się do biuletynu informacyjnego Strefy Serca");
        context.setVariable("description", "http://localhost:8080" + confirmationUrl );
//        context.setVariable("logo",new File("/home/tomek/Workspace/portal/src/main/resources/static/images/logo-main.jpg"));
        String body = templateEngine.process("template", context);




        MimeMessage mail = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(emailToConfirm);
            helper.setReplyTo("rainowy@hotmail.com");  //ustawia do kogo robić reply
            helper.setFrom("strefaserca@gmail.com"); //do czego to
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.addInline("logo",new File("/home/tomek/Workspace/portal/src/main/resources/static/images/logo-main.jpg"));

//            helper.setSubject("Email with Inline images Example");
//                        helper.setTo(emailToConfirm);
//            helper.setReplyTo("newsletter@codecouple.pl");
//            helper.setFrom("newsletter@codecouple.pl");
//            helper.setSubject(subject);
//            helper.setText(
//                    "<html>"
//                            + "<body>"
//                            + "<div>Dear student,"
//                            + "<div><strong>Add the image to the right:</strong></div>"
//                            + "<div>"
//                            + "<img src='cid:rightSideImage'/>"
//                            + "<div>Adding a inline resource/image on to the right of the paragraph.</div>"
//                            + "<div>Adding a inline resource/image on to the right of the paragraph.</div>"
//                            + "<div>Adding a inline resource/image on to the right of the paragraph.</div>"
//                            + "<div>Adding a inline resource/image on to the right of the paragraph.</div>"
//                            + "</div>"
//                            + "<div><strong>Add the image to the left :</strong></div>"  + "<div>"
//                            + "<img src='cid:leftSideImage' style='float:left;width:50px;height:50px;'/>"
//                            + "<div>Adding a inline resource/image on to the left of the paragraph.</div>"
//                            + "<div>Adding a inline resource/image on to the left of the paragraph.</div>"
//                            + "<div>Adding a inline resource/image on to the left of the paragraph.</div>"
//                            + "<div>Adding a inline resource/image on to the left of the paragraph.</div>"
//                            + "</div>"
//                            + "<div>Thanks,</div>"
//                            + "kalliphant"
//                            + "</div></body>"
//                            + "</html>", true);
//            helper.addInline("rightSideImage",
//                    new File("/home/tomek/Workspace/portal/src/main/resources/static/images/logo-main.jpg"));
//
//            helper.addInline("leftSideImage",
//                    new File("/home/tomek/Workspace/portal/src/main/resources/static/images/logo-main.jpg"));

            mailSender.send(mail);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(mail);



    }
}
