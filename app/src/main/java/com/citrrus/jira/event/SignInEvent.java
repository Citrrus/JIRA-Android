package com.citrrus.jira.event;

/**
 * Created with IntelliJ IDEA.
 * User: jonwillis
 * Date: 2/26/13
 * Time: 12:56 AM
 */
public class SignInEvent {
    protected String email;
    protected String password;
    protected String confirmPassword;

    public String getEmail() {
        return new String(email);
    }

    public String getPassword() {
        return new String(password);
    }

    public String getConfirmPassword() {
        return new String(confirmPassword);
    }

    public SignInEvent(String email, String password, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}
