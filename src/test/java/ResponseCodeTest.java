import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import utils.HttpFetcher;
import utils.HttpFetcher_Sami;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResponseCodeTest {
    @RepeatedTest(10)
    void testSearchStatusCode400() {
        String url = "http://localhost:8080/reviewsearch";
        HttpFetcher fetcher = new HttpFetcher(url, "notmsg=hi", null);
        assertEquals(400, fetcher.getPostResponse().statusCode());
    }

    @RepeatedTest(10)
    void testFindStatusCode400() {
        String url = "http://localhost:8080/find";
        HttpFetcher fetcher = new HttpFetcher(url, "notmsg=hi", null);
        assertEquals(400, fetcher.getPostResponse().statusCode());
    }

    @RepeatedTest(10)
    void testSearchStatusCode200() {
        String url = "http://localhost:8080/reviewsearch";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=hi", null);
        assertEquals(200, fetcher.getPostResponse().statusCode());
    }

    @RepeatedTest(10)
    void testFindStatusCode200() {
        String url = "http://localhost:8080/find";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=hi", null);
        assertEquals(200, fetcher.getPostResponse().statusCode());
    }

    @RepeatedTest(10)
    void testChatStatusCode400() {
        String url = "http://localhost:9090/slackbot";
        HttpFetcher fetcher = new HttpFetcher(url, "notmsg=400 test", null);
        assertEquals(400, fetcher.getPostResponse().statusCode());
    }

    @RepeatedTest(10)
    void testChatStatusCode200() {
        String url = "http://localhost:9090/slackbot";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=200 test", null);
        assertEquals(200, fetcher.getPostResponse().statusCode());
    }

    @RepeatedTest(10)
    void testSearchStatusCode411() {
        String url = "http://localhost:8080/reviewsearch";
        HttpFetcher fetcher = new HttpFetcher(url, "", null);
        assertEquals(411, fetcher.getPostResponse().statusCode());
    }

    @RepeatedTest(10)
    void testStatusCode404() {
        String url = "http://localhost:8080/not-found";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=hi", null);
        System.out.println(fetcher.getGetResponse().statusCode());
        assertEquals(404, fetcher.getGetResponse().statusCode());
    }

    @RepeatedTest(10)
    void testSearchStatusCode405() {
        String url = "http://localhost:8080/reviewsearch";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=hi", null);
        assertEquals(405, fetcher.getDeleteResponse().statusCode());
    }
}
