package com.citrrus.jira.core;

import com.citrrus.jira.model.Project;
import com.citrrus.jira.util.Strings;
import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import static com.citrrus.jira.core.Constants.Http.HEADER_AUTH_COOKIE;
import static com.citrrus.jira.core.Constants.Http.URL_PROJECT;

/**
 * Bootstrap API service
 */
public class BootstrapService {

    private UserAgentProvider userAgentProvider;

    /**
     * GSON instance to use for all request  with date format set up for proper parsing.
     */
    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

    /**
     * You can also configure GSON with different naming policies for your API. Maybe your api is Rails
     * api and all json values are lower case with an underscore, like this "first_name" instead of "firstName".
     * You can configure GSON as such below.
     *
     * public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").setFieldNamingPolicy
     * (LOWER_CASE_WITH_UNDERSCORES).create();
     *
     */


    /**
     * Read and connect timeout in milliseconds
     */
    private static final int TIMEOUT = 30 * 1000;


    private static class UsersWrapper {

        private List<User> results;
    }

    private static class NewsWrapper {

        private List<News> results;
    }

    private static class ModelWrapper<T> {
        private List<T> results;
    }

    private static class JsonException extends IOException {

        private static final long serialVersionUID = 3774706606129390273L;

        /**
         * Create exception from {@link JsonParseException}
         *
         * @param cause
         */
        public JsonException(JsonParseException cause) {
            super(cause.getMessage());
            initCause(cause);
        }
    }


    private final String apiKey;
    private final String username;
    private final String password;

    /**
     * Create bootstrap service
     *
     * @param username
     * @param password
     */
    public BootstrapService(final String username, final String password) {
        this.username = username;
        this.password = password;
        this.apiKey = null;
    }

    /**
     * Create bootstrap service
     *
     * @param userAgentProvider
     * @param apiKey
     */
    public BootstrapService(final String apiKey, final UserAgentProvider userAgentProvider) {
        this.userAgentProvider = userAgentProvider;
        this.username = null;
        this.password = null;
        this.apiKey = apiKey;
    }

    /**
     * Execute request
     *
     * @param request
     * @return request
     * @throws IOException
     */
    protected HttpRequest execute(HttpRequest request) throws IOException {
        if (!configure(request).ok()) {
            throw new IOException("Unexpected response code: " + request.code());
        }
        return request;
    }

    private HttpRequest configure(final HttpRequest request) {
        request.connectTimeout(TIMEOUT).readTimeout(TIMEOUT);
        request.userAgent(userAgentProvider.get());

        if (isPostOrPut(request)) {
            // All PUT & POST requests to Parse.com api must
            // be in JSON - https://www.parse.com/docs/rest#general-requests
            request.contentType(Constants.Http.CONTENT_TYPE_JSON);
        }

        return addCredentialsTo(request);
    }

    private boolean isPostOrPut(HttpRequest request) {
        return request.getConnection().getRequestMethod().equals(HttpRequest.METHOD_POST)
                || request.getConnection().getRequestMethod().equals(HttpRequest.METHOD_PUT);

    }

    private HttpRequest addCredentialsTo(HttpRequest request) {

        // Required params for
        request.header(HEADER_AUTH_COOKIE, apiKey);

        /**
         * NOTE: This may be where you want to add a header for the api token that was saved when you
         * logged in. In the bootstrap sample this is where we are saving the session id as the token.
         * If you actually had received a token you'd take the "apiKey" (aka: token) and add it to the
         * header or form values before you make your requests.
         */

        /**
         * Add the user name and password to the request here if your service needs username or password for each
         * request. You can do this like this:
         * request.basic("myusername", "mypassword");
         */

        return request;
    }

    private <V> V fromJson(HttpRequest request, Class<V> target) throws IOException {
        try {
            // for debugging purposes we save str
            String str = Strings.toString(request.reader());
            return GSON.fromJson(str, target);
        } catch (JsonParseException e) {
            throw new JsonException(e);
        } finally {
        }
        /*
        Reader reader = request.bufferedReader();

        try {
            return GSON.fromJson(reader, target);
        } catch (JsonParseException e) {
            throw new JsonException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException ignored) {
                // Ignored
            }
        }*/
    }

    private <V> V fromJson(HttpRequest request, Type target) throws IOException {
        try {
            // for debugging purposes we save str
            String str = Strings.toString(request.reader());
            return GSON.fromJson(str, target);
        } catch (JsonParseException e) {
            throw new JsonException(e);
        } finally {
        }
        /*
        Reader reader = request.bufferedReader();
        try {
            return GSON.fromJson(reader, target);
        } catch (JsonParseException e) {
            throw new JsonException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException ignored) {
                // Ignored
            }
        }*/
    }

    /*
     _______  _______ _________
    (  ___  )(  ____ )\__   __/
    | (   ) || (    )|   ) (
    | (___) || (____)|   | |
    |  ___  ||  _____)   | |
    | (   ) || (         | |
    | )   ( || )      ___) (___
    |/     \||/       \_______/

     */

    public List<Project> getProjects() throws IOException {
        try {
            HttpRequest request = execute(HttpRequest.get(URL_PROJECT));

            List<Project> response = fromJson(request, new TypeToken<List<Project>>(){}.getType());
            if (response != null) {
                return response;
            }
            return Collections.emptyList();
        } catch(HttpRequestException e) {
            throw e.getCause();
        }
    }
}
