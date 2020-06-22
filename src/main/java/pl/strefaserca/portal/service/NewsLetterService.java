package pl.strefaserca.portal.service;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pl.strefaserca.portal.email.OnNewsLetterRequestEvent;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NewsLetterService {

    private ApplicationEventPublisher eventPublisher;
    private HttpServletRequest request;
    private List<String> confirmationTokens = new ArrayList<>();

    public void sendConfirmationMail(String email){
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnNewsLetterRequestEvent(appUrl,request.getLocale(),email));
    }



    public void saveConfirmationToken(String token){
        confirmationTokens.add(token);
        System.out.println(confirmationTokens);
    }
}
