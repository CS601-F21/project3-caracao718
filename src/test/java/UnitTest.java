import chat.ChatConstants;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import project1.AmazonSearch;
import search.FindConstants;
import search.ReviewSearchConstants;
import search.ReviewSearchHandler;
import utils.HttpFetcher;
import utils.HttpFetcher_Sami;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnitTest {
    @RepeatedTest(2)
    void testSearchDoGetMethod() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest.Builder builder = HttpRequest.newBuilder(new URI("http://localhost:8080/reviewsearch"));
        HttpRequest request = builder.GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(ReviewSearchConstants.GET_REVIEW_SEARCH_PAGE, response.body().trim());
    }

    @RepeatedTest(2)
    void testFindDoGetMethod() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest.Builder builder = HttpRequest.newBuilder(new URI("http://localhost:8080/find"));
        HttpRequest request = builder.GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(FindConstants.GET_FIND_PAGE, response.body().trim());
    }

    @RepeatedTest(2)
    void testChatDoGetMethod() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest.Builder builder = HttpRequest.newBuilder(new URI("http://localhost:9090/slackbot"));
        HttpRequest request = builder.GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(ChatConstants.GET_CHAT_PAGE, response.body().trim());
    }
}
