package com.citrrus.jira.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jonwillis
 * Date: 3/29/13
 * Time: 12:14 AM
 */
public class AuthResponse {

    public AuthResponse(Session session, LoginInfo loginInfo) {
        this.session = session;
        this.loginInfo = loginInfo;
    }

    public final Session session;
    public final LoginInfo loginInfo;

    public class Session {

        public Session(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public final String name;
        public final String value;
    }

    public class LoginInfo {

        public LoginInfo(Integer loginCount, Date previousLoginTime) {
            this.loginCount = loginCount;
            this.previousLoginTime = previousLoginTime;
        }

        public final Integer loginCount;
        public final Date previousLoginTime;
    }
}
