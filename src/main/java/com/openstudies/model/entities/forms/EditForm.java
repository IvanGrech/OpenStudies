package com.openstudies.model.entities.forms;

import com.openstudies.model.entities.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EditForm {

    private String login;

    private String password;

    private String repeatPassword;

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+")
    protected String email;
    @NotNull
    @Size(min = 2, max = 32)
    @Pattern(regexp = "[A-Za-z]{2,32}")
    protected String firstName;
    @NotNull
    @Size(min = 2, max = 32)
    @Pattern(regexp = "[A-Za-z]{2,32}")
    protected String lastName;

    protected String birthday;
    @Pattern(regexp = "user|admin")
    protected String role;

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
