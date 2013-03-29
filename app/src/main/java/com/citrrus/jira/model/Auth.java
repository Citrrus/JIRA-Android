package com.citrrus.jira.model;

/**
 * Created with IntelliJ IDEA.
 * User: jonwillis
 * Date: 3/29/13
 * Time: 12:08 AM
 */
public class Auth {

    public Auth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public final String username;
    public final String password;
}
