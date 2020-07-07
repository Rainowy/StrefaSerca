package pl.strefaserca.portal.service;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import pl.strefaserca.portal.email.OnContactQuestionEvent;
import pl.strefaserca.portal.email.OnNewsletterRequestEvent;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class ContactService {

    private ApplicationEventPublisher eventPublisher;
//    private HttpServletRequest request;

    public void sendQuestion(String name, String email, String phone, String textarea){
        eventPublisher.publishEvent(new OnContactQuestionEvent(email,name,phone,textarea));
    }
}



//    void askQuestion(@RequestParam String name,
//                     @RequestParam String email,
//                     @RequestParam String phone,
//                     @RequestParam String textarea) {