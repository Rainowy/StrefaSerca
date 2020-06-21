package pl.strefaserca.portal.email;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NewsLetterRequestListener implements ApplicationListener<OnNewsLetterRequestEvent> {

    @Override
    public void onApplicationEvent(OnNewsLetterRequestEvent event) {
        this.confirmNewsLetterRequest(event);
    }

    private void confirmNewsLetterRequest(OnNewsLetterRequestEvent event) {

        String emailToConfirm = event.getEmail();
        String token = UUID.randomUUID().toString();
        
    }
}
