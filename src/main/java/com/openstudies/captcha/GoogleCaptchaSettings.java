package com.openstudies.captcha;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:google.recaptcha.key.properties")
public class GoogleCaptchaSettings implements CaptchaSettings {

    @Value("${google.recaptcha.key.site}")
    private String site;
    @Value("${google.recaptcha.key.secret}")
    private String secret;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
