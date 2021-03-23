package pl.strefaserca.portal.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import pl.strefaserca.portal.error.ReCaptchaInvalidException;
import pl.strefaserca.portal.validation.ICaptchaService;
import pl.strefaserca.portal.validation.CaptchaSettings;
import pl.strefaserca.portal.validation.GoogleResponse;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Arrays;
import java.util.regex.Pattern;

@Log4j2
@Service
public class CaptchaService implements ICaptchaService {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    private CaptchaSettings captchaSettings;

    private static Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");
    private static final String RECAPTCHA_URL_TEMPLATE = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s";

    @Override
    public boolean processResponse(String token) {
        if (response(token).isSuccess()) return true;
        else return false;
    }

    @Override
    public GoogleResponse response(String token) {
        if (!responseSanityCheck(token)) {
            throw new ReCaptchaInvalidException("Response contains invalid characters");
        }
        final URI verifyUri = URI.create(String.format(RECAPTCHA_URL_TEMPLATE, getReCaptchaSecret(), token, getClientIP()));
        RestTemplate restTemplate = new RestTemplate();

//        !!!!!!!!!
//        GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);
        return restTemplate.getForObject(verifyUri, GoogleResponse.class);
    }

    @Override
    public void logResponse(String token) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("reCaptcha error: ");
        Arrays.stream(response(token).getErrorCodes())
                .forEach(e -> stringBuilder.append(e + " "));
        stringBuilder
                .append("Hostname: " + response(token).getHostname() + " ")
                .append("Success: " + response(token).isSuccess() + " ")
                .append("ChallengeTs: " + response(token).getChallengeTs() + " ")
                .append("From addr: " + getClientIP());
        log.error(stringBuilder);
    }

    @Override
    public String getReCaptchaSite() {
        return captchaSettings.getSite();
    }

    @Override
    public String getReCaptchaSecret() {
        return captchaSettings.getSecret();
    }

    private boolean responseSanityCheck(String response) {
        return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches();
    }

    protected String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}