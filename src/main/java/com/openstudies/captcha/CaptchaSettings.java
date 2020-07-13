package com.openstudies.captcha;

public interface CaptchaSettings {

    String getSite();

    void setSite(String site);

    String getSecret();

    void setSecret(String secret);

}

