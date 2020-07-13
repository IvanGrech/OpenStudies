package com.openstudies.captcha;

public interface CaptchaService {
    boolean isValid(String response, String ip);
}
