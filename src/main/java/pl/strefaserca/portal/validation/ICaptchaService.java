package pl.strefaserca.portal.validation;

import pl.strefaserca.portal.error.ReCaptchaInvalidException;

public interface ICaptchaService {


    GoogleResponse response(final String token);

    boolean processResponse(final String token) throws ReCaptchaInvalidException;

    void logResponse(final String token);

    String getReCaptchaSite();

    String getReCaptchaSecret();
}
