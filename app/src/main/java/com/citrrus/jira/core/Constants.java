package com.citrrus.jira.core;

/**
 * Bootstrap constants
 */
public class Constants {

    public static class Auth {
        /**
         * Account type id
         */
        public static final String BOOTSTRAP_ACCOUNT_TYPE = "com.citrrus.jira";
        /**
         * Account name
         */
        public static final String BOOTSTRAP_ACCOUNT_NAME = "JIRA";
        /**
         * Provider id
         */
        public static final String BOOTSTRAP_PROVIDER_AUTHORITY = "com.citrrus.jira.sync";
        /**
         * Auth token type
         */
        public static final String AUTHTOKEN_TYPE = BOOTSTRAP_ACCOUNT_TYPE;

        private Auth() {
        }
    }

    /**
     * All HTTP is done through a REST style API built for demonstration purposes on Parse.com
     * Thanks to the nice people at Parse for creating such a nice system for us to use for bootstrap!
     */
    public static class Http {
        /**
         * Base URL for all requests
         */
        public static final String URL_BASE = "https://citrrus.atlassian.net/rest/api/2";
        /**
         * Authentication URL
         */
        public static final String URL_AUTH = "https://citrrus.atlassian.net/rest/auth/latest/session";

        public static final String URL_PROJECT = URL_BASE + "/project";
        /**
         * List Checkin's URL
         */
        public static final String URL_CHECKINS = URL_BASE + "/1/classes/Locations";
        public static final String HEADER_AUTH_COOKIE = "JSESSIONID";
        public static final String CONTENT_TYPE_JSON = "application/json";

        private Http() {
        }


    }

    public static class Extra {
        public static final String NEWS_ITEM = "news_item";
        public static final String USER = "user";

        private Extra() {
        }

    }

    public static class Intent {
        /**
         * Action prefix for all intents created
         */
        public static final String INTENT_PREFIX = "com.citrrus.jira.";

        private Intent() {
        }

    }

    public static class Notification {
        public static final int TIMER_NOTIFICATION_ID = 1000; // Why 1000? Why not? :)

        private Notification() {
        }
    }

}


