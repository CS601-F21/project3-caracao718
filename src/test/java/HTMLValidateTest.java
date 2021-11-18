import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import utils.HtmlValidator;
import utils.HttpFetcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class HTMLValidateTest {
    @RepeatedTest(2)
    void testSearchHTMLWithCode400() {
        String url = "http://localhost:8080/reviewsearch";
        HttpFetcher fetcher = new HttpFetcher(url, "notmsg=hi", null);
        assertTrue(HtmlValidator.isValid(fetcher.getPostResponse().body()));
    }

    @RepeatedTest(2)
    void testFindHTMLWithCode400() {
        String url = "http://localhost:8080/find";
        HttpFetcher fetcher = new HttpFetcher(url, "notmsg=hi", null);
        assertTrue(HtmlValidator.isValid(fetcher.getPostResponse().body()));
    }

    @RepeatedTest(2)
    void testSearchHTMLWithCode200() {
        String url = "http://localhost:8080/reviewsearch";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=hi", null);
        assertTrue(HtmlValidator.isValid(fetcher.getPostResponse().body()));
    }

    @RepeatedTest(2)
    void testFindHTMLWithCode200() {
        String url = "http://localhost:8080/find";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=hi", null);
        assertTrue(HtmlValidator.isValid(fetcher.getPostResponse().body()));
    }

    @Test
    void testChatHTMLWithCode200() {
        String url = "http://localhost:9090/slackbot";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=200 html", null);
        assertTrue(HtmlValidator.isValid(fetcher.getPostResponse().body()));
    }
}
