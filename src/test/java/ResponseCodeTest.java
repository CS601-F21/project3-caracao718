import org.junit.jupiter.api.RepeatedTest;
import utils.HttpFetcher;
import utils.HttpFetcher_Sami;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResponseCodeTest {
    @RepeatedTest(2)
    void testSearchStatusCode400() {
        String url = "http://localhost:8080/reviewsearch";
        HttpFetcher fetcher = new HttpFetcher(url, "notmsg=hi", null);
//        System.out.println(fetcher.getPostResponse().body());
        assertEquals(400, fetcher.getPostResponse().statusCode());
    }

    @RepeatedTest(2)
    void testFindStatusCode400() {
        String url = "http://localhost:8080/find";
        HttpFetcher fetcher = new HttpFetcher(url, "notmsg=hi", null);
        assertEquals(400, fetcher.getPostResponse().statusCode());
    }

    @RepeatedTest(2)
    void testSearchStatusCode200() {
        String url = "http://localhost:8080/reviewsearch";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=hi", null);
        assertEquals(200, fetcher.getPostResponse().statusCode());
    }

    @RepeatedTest(2)
    void testFindStatusCode200() {
        String url = "http://localhost:8080/find";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=hi", null);
        assertEquals(200, fetcher.getPostResponse().statusCode());
    }

    @RepeatedTest(2)
    void testChatStatusCode400() {
        String url = "http://localhost:9090/slackbot";
        HttpFetcher fetcher = new HttpFetcher(url, "notmsg=400 test", null);
        assertEquals(400, fetcher.getPostResponse().statusCode());
    }

    @RepeatedTest(2)
    void testChatStatusCode200() {
        String url = "http://localhost:9090/slackbot";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=200 test", null);
        assertEquals(200, fetcher.getPostResponse().statusCode());
    }
}
