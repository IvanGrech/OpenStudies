package com.openstudies.model.entities.forms;

import com.openstudies.model.entities.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AddForm extends EditForm{
    @NotNull
    @Size(min=4, max = 16, message = "Login must be 4-16 characters long")
    @Pattern(regexp = "[A-Za-z0-9_]{4,16}", message = "Should not contain spaces. Numbers and char's only")
    private String login;
    @NotNull
    @Size(min = 4, max = 16, message = "Password must be 4-16 characters long")
    @Pattern(regexp = "[A-Za-z0-9]{4,16}", message = "Must not contain spaces. Char's and digits only")
    private String password;
    @NotNull
    @Size(min = 4, max = 16, message = "Password must be 4-16 characters long")
    @Pattern(regexp = "[A-Za-z0-9]{4,16}", message = "Must not contain spaces. Char's and digits only")
    private String repeatPassword;

    public void toUser(User user){
        this.login =user.getLogin();
        this.password=user.getPassword();
        this.repeatPassword = password;
        this.birthday = user.getBirthday().toString();
        this.email = user.getEmail();
        this.firstName =user.getFirstName();
        this.lastName = user.getFirstName();
        this.role = user.getRole().getName();
    }

    private String captcha;


    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
