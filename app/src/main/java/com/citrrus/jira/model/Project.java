package com.citrrus.jira.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: jonwillis
 * Date: 3/31/13
 * Time: 11:56 PM
 * Example:
 "self": "http://www.example.com/jira/rest/api/2/project/EX",
 "id": "10000",
 "key": "EX",
 "name": "Example",
 "avatarUrls": {
 "16x16": "http://www.example.com/jira/secure/projectavatar?size=small&pid=10000",
 "48x48": "http://www.example.com/jira/secure/projectavatar?size=large&pid=10000"
 */
public class Project {

    public final String self;
    public final long id;
    public final String key;
    public final String name;
    public final AvatarUrl avatarUrl;

    public Project(String self, long id, String key, String name, AvatarUrl avatarUrl) {
        this.self = self;
        this.id = id;
        this.key = key;
        this.name = name;
        this.avatarUrl = avatarUrl;
    }

    public class AvatarUrl {
        @SerializedName("16x16") public final String small;
        @SerializedName("48x48") public final String large;

        public AvatarUrl(String small, String large) {
            this.small = small;
            this.large = large;
        }
    }

}
