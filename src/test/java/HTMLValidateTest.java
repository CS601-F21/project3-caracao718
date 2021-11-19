import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import utils.HtmlValidator;
import utils.HttpFetcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class HTMLValidateTest {
    @RepeatedTest(10)
    void testSearchHTMLWithCode400() {
        String url = "http://localhost:8080/reviewsearch";
        HttpFetcher fetcher = new HttpFetcher(url, "notmsg=hi", null);
        assertTrue(HtmlValidator.isValid(fetcher.getPostResponse().body()));
    }

    @RepeatedTest(10)
    void testFindHTMLWithCode400() {
        String url = "http://localhost:8080/find";
        HttpFetcher fetcher = new HttpFetcher(url, "notmsg=hi", null);
        assertTrue(HtmlValidator.isValid(fetcher.getPostResponse().body()));
    }

    @RepeatedTest(10)
    void testSearchHTMLWithCode200() {
        String url = "http://localhost:8080/reviewsearch";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=hi", null);
        assertTrue(HtmlValidator.isValid(fetcher.getPostResponse().body()));
    }

    @RepeatedTest(10)
    void testFindHTMLWithCode200() {
        String url = "http://localhost:8080/find";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=hi", null);
        assertTrue(HtmlValidator.isValid(fetcher.getPostResponse().body()));
    }

    @RepeatedTest(10)
    void testChatHTMLWithCode400() {
        String url = "http://localhost:9090/slackbot";
        HttpFetcher fetcher = new HttpFetcher(url, "notmsg=hi", null);
        assertTrue(HtmlValidator.isValid(fetcher.getPostResponse().body()));
    }

    @RepeatedTest(10)
    void testChatHTMLWithCode200() {
        String url = "http://localhost:9090/slackbot";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=200 html", null);
        assertTrue(HtmlValidator.isValid(fetcher.getPostResponse().body()));
    }

    @RepeatedTest(10)
    void testSearchHTMLWithCode411() {
        String url = "http://localhost:8080/reviewsearch";
        HttpFetcher fetcher = new HttpFetcher(url, "", null);
        assertTrue(HtmlValidator.isValid(fetcher.getPostResponse().body()));
    }

    @RepeatedTest(10)
    void testSearchHTMLWithCode405() {
        String url = "http://localhost:8080/reviewsearch";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=hi", null);
        assertTrue(HtmlValidator.isValid(fetcher.getDeleteResponse().body()));
    }
}
