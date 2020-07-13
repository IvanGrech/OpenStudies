package com.openstudies.captcha;



import java.net.URI;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private CaptchaSettings captchaSettings;

    @Autowired
    private RestOperations restTemplate;

    private static Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");

    @Override
    public boolean isValid(String response, String ip) {

        if (!responseSanityCheck(response)) {
            return false;
        }
        URI verifyUri = URI.create(String.format(
                "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s",
                captchaSettings.getSecret(), response, ip));
        GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);

        return googleResponse.isSuccess();
    }

    private boolean responseSanityCheck(String response) {
        return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches();
    }
}
