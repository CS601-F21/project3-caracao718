package utils;

import chat.Token;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that provides static methods to send HTTP GET and POST requests.
 */
public class HttpFetcher {
    private String url;
    private String body;
    private Map<String, String> headers;
    private HttpResponse<String> getResponse;
    private HttpResponse<String> postResponse;

    public HttpFetcher(String url, String body, Map<String, String> headers) {
        this.url = url;
        this.body = body;
        this.headers = headers;

        getResponse = doGet(url, headers);
        postResponse = doPost(url, headers, body);
    }

    public HttpResponse<String> getPostResponse() {
        return postResponse;
    }

    public HttpResponse<String> getGetResponse() {
        return getResponse;
    }

    /**
     * Execute an HTTP GET for the specified URL and return the
     * body of the response as a String.
     * @param url
     * @return
     */
    public static HttpResponse<String> doGet(String url) {
        return doGet(url, null);
    }

    /**
     * Execute an HTTP GET for the specified URL and return
     * the body of the response as a String. Allows request
     * headers to be set.
     * @param url
     * @param headers
     * @return
     */
    public static HttpResponse<String> doGet(String url, Map<String, String> headers) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(url));
            builder = setHeaders(builder, headers);
            HttpRequest request = builder.GET()
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch(URISyntaxException | IOException | InterruptedException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Execute an HTTP POST for the specified URL and return the body of the
     * response as a String.
     * Headers for the request are provided in the map headers.
     * The body of the request is provided as a String.
     *
     * @param url
     * @param headers
     * @param body
     * @return
     */
    public static HttpResponse<String> doPost(String url, Map<String, String> headers, String body) {

        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(url));
            builder = setHeaders(builder, headers);
            HttpRequest request = builder.POST((HttpRequest.BodyPublishers.ofString(body)))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
            return response;

        } catch(URISyntaxException | IOException | InterruptedException e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

    /**
     * Helper method to set the headers of any HttpRequest.Builder.
     * @param builder
     * @param headers
     * @return
     */
    private static HttpRequest.Builder setHeaders(HttpRequest.Builder builder, Map<String, String> headers) {
        if(headers != null) {
            for (String key : headers.keySet()) {
                builder = builder.setHeader(key, headers.get(key));
            }
        }
        return builder;
    }

    public static void main(String[] args) {

//        HttpFetcher fetcher = new HttpFetcher("http://localhost:8080/reviewsearch", "hi", null);

        String url = "http://localhost:8080/reviewsearch";
        HttpResponse<String> response = doGet(url);

        HttpResponse<String> postResponse = doPost(url, null, "msg=here is a message");
        System.out.println(postResponse);
        assert postResponse != null;
        System.out.println(HtmlValidator.isValid(postResponse.body()));

//        Gson gson = new Gson();
//
//        Token tokenObject = null;
//        try {
//            tokenObject = gson.fromJson(new FileReader("token.json"), Token.class);
//        } catch (FileNotFoundException fnf) {
//            fnf.printStackTrace();
//            System.exit(1);
//        }
//
//        String token = tokenObject.getToken();
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Authorization", "Bearer " + token);
//        String url = "https://slack.com/api/conversations.list";
//        HttpResponse<String> response = doGet(url, headers);
//
//        System.out.println("Response: " + response.body());


    }
}
