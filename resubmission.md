Project 3 - HTTP Server - Resubmission Tests
=======================


I have implemented the following tests using JUnit:

- Unit Tests -> 3 tests
- Integration Tests -> 3 tests
- System Tests -> 17 tests



## Part 1 - Unit Tests

Tested the three GET methods

#### Test the GET method in ReviewSearchHandler

```
    @RepeatedTest(2)
    void testSearchDoGetMethod() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest.Builder builder = HttpRequest.newBuilder(new URI("http://localhost:8080/reviewsearch"));
        HttpRequest request = builder.GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(ReviewSearchConstants.GET_REVIEW_SEARCH_PAGE, response.body().trim());
    }
```

This is a Unit test to test the doGet method in ReviewSearchHandler. This test compared the expected HTML document
with the actual HTML response from the server.

#### Test the GET method in FindHandler

```
    @RepeatedTest(2)
    void testFindDoGetMethod() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest.Builder builder = HttpRequest.newBuilder(new URI("http://localhost:8080/find"));
        HttpRequest request = builder.GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(FindConstants.GET_FIND_PAGE, response.body().trim());
    }
```
This is a Unit test to test the doGet method in FindHandler. This test compared the expected HTML document
with the actual HTML response from the server.

#### Test the GET method in ChatHandler

```
@RepeatedTest(2)
    void testChatDoGetMethod() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest.Builder builder = HttpRequest.newBuilder(new URI("http://localhost:9090/slackbot"));
        HttpRequest request = builder.GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(ChatConstants.GET_CHAT_PAGE, response.body().trim());
    }
```

This is a Unit test to test the doGet method in ChatHandler. This test compared the expected HTML document
with the actual HTML response from the server.


## Part 2 - Integration Tests

Tested three flows in the HttpRequest class.
The integrationTest.txt file contains the test sample code.

#### Test the constructor calling findContentLength method

```
    @RepeatedTest(2)
    void testContentLength() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = new HttpRequest(new PrintWriter(System.out), new BufferedReader(new FileReader("src/test/integrationTest.txt")));
        assertEquals(2, request.getContentLength());
    }
```

This is an integration test that tests the performance of the HttpRequest class constructor calling the findContentLength method.
The Content-Length specified in the .txt file is 2. So, I compared the test result and the expected value: 2.

#### Test the constructor calling validMethod method

```
    @RepeatedTest(2)
    void testValidMethod() throws FileNotFoundException {
        HttpRequest request = new HttpRequest(new PrintWriter(System.out), new BufferedReader(new FileReader("src/test/integrationTest.txt")));
        assertTrue(request.validMethod());
    }
```
This is an integration test that tests the performance of the HttpRequest class constructor calling the validMethod method.
The method specified in the .txt file is POST. So, as long as the method is POST or GET, validMethod method should return true.

#### Test the constructor calling readline method

```
    @RepeatedTest(2)
    void testReadLine() throws FileNotFoundException {
        HttpRequest request = new HttpRequest(new PrintWriter(System.out), new BufferedReader(new FileReader("src/test/integrationTest.txt")));
        assertEquals("POST", request.getMethod());
    }
```
This is an integration test that tests the performance of the HttpRequest class constructor calling the readLine method.
Whether the readLine method works can be determined by whether the method field is equal to the test value in integration.txt file.
The method specified in the .txt file is POST. So, I compared the test result and the expected value: POST.


## Part 3 - System Tests

### Response Code Tests

Test the response code for each request

#### Test /reviewsearch Status Code 400

```
    @RepeatedTest(10)
    void testSearchStatusCode400() {
            String url = "http://localhost:8080/reviewsearch";
            HttpFetcher fetcher = new HttpFetcher(url, "notmsg=hi", null);
            assertEquals(400, fetcher.getPostResponse().statusCode());
    }
```

This is a system test to test if the server(SearchApplication /reviewsearch) will respond with an appropriate 400 Bad Request error code, while the input
is an incorrect query -> "notmsg".

#### Test /find Status Code 400

```
    @RepeatedTest(10)
    void testFindStatusCode400() {
        String url = "http://localhost:8080/find";
        HttpFetcher fetcher = new HttpFetcher(url, "notmsg=hi", null);
        assertEquals(400, fetcher.getPostResponse().statusCode());
    }
```
This is a system test to test if the server(SearchApplication /find) will respond with an appropriate 400 Bad Request error code, while the input
is an incorrect query -> "notmsg".

#### Test /reviewsearch Status Code 200

```
    @RepeatedTest(10)
    void testSearchStatusCode200() {
        String url = "http://localhost:8080/reviewsearch";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=hi", null);
        assertEquals(200, fetcher.getPostResponse().statusCode());
    }
```
This is a system test to test if the server(SearchApplication /reviewsearch) will respond with an appropriate 200 ok code, while the input
is correct.

#### Test /find Status Code 200

```
    @RepeatedTest(10)
    void testFindStatusCode200() {
        String url = "http://localhost:8080/find";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=hi", null);
        assertEquals(200, fetcher.getPostResponse().statusCode());
    }
```
This is a system test to test if the server(SearchApplication /find) will respond with an appropriate 200 ok code, while the input
is correct.

#### Test /slackbot Status Code 400

```
    @RepeatedTest(10)
    void testChatStatusCode400() {
        String url = "http://localhost:9090/slackbot";
        HttpFetcher fetcher = new HttpFetcher(url, "notmsg=400 test", null);
        assertEquals(400, fetcher.getPostResponse().statusCode());
    }
```
This is a system test to test if the server(ChatApplication /slackbot) will respond with an appropriate 400 Bad Request error code, while the input
is an incorrect query -> "notmsg".

#### Test /slackbot Status Code 200

```
    @RepeatedTest(10)
    void testChatStatusCode200() {
        String url = "http://localhost:9090/slackbot";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=200 test", null);
        assertEquals(200, fetcher.getPostResponse().statusCode());
    }
```

This is a system test to test if the server(ChatApplication /slackbot) will respond with an appropriate 200 ok code, while the input
is correct.

#### Test /reviewsearch Status Code 411

```
    @RepeatedTest(10)
    void testSearchStatusCode411() {
        String url = "http://localhost:8080/reviewsearch";
        HttpFetcher fetcher = new HttpFetcher(url, "", null);
        assertEquals(411, fetcher.getPostResponse().statusCode());
    }
```
This is a system test to test if the server(SearchApplication /reviewsearch) will respond with an appropriate 411 Length Required error code, while the input
is an empty string.

#### Test Status Code 404

```
    @RepeatedTest(10)
    void testStatusCode404() {
        String url = "http://localhost:8080/not-found";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=hi", null);
        System.out.println(fetcher.getGetResponse().statusCode());
        assertEquals(404, fetcher.getGetResponse().statusCode());
    }
```

This is a system test to test if the server will respond with an appropriate 404 Not Found error code, while the path
is not supported.

#### Test /reviewsearch Status Code 405

```
    @RepeatedTest(10)
    void testSearchStatusCode405() {
        String url = "http://localhost:8080/reviewsearch";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=hi", null);
        assertEquals(405, fetcher.getDeleteResponse().statusCode());
    }
```
This is a system test to test if the server(SearchApplication) will respond with an appropriate 405 Method Not Allowed code, while the method
is not supported.
I added a doDelete method in the HTTPFetcher for this test, so that it can send a DELETE method request to the server.
The server wasn't changed for this test.

### HTML validation Tests

The following tests are conducted to validate the response HTML

#### Test /reviewsearch HTML response with status code 400

```
    @RepeatedTest(10)
    void testSearchHTMLWithCode400() {
        String url = "http://localhost:8080/reviewsearch";
        HttpFetcher fetcher = new HttpFetcher(url, "notmsg=hi", null);
        assertTrue(HtmlValidator.isValid(fetcher.getPostResponse().body()));
    }
```
This is a system test to test if the server (SearchApplication /reviewsearch) will respond with a well-formatted 400 Bad Request HTML document.

#### Test /find HTML response with status code 400

```
    @RepeatedTest(10)
    void testFindHTMLWithCode400() {
        String url = "http://localhost:8080/find";
        HttpFetcher fetcher = new HttpFetcher(url, "notmsg=hi", null);
        assertTrue(HtmlValidator.isValid(fetcher.getPostResponse().body()));
    }
```
This is a system test to test if the server (SearchApplication /find) will respond with a well-formatted 400 Bad Request HTML document.

#### Test /reviewsearch response with status code 200

```
    @RepeatedTest(10)
    void testSearchHTMLWithCode200() {
        String url = "http://localhost:8080/reviewsearch";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=hi", null);
        assertTrue(HtmlValidator.isValid(fetcher.getPostResponse().body()));
    }
```
This is a system test to test if the server (SearchApplication /reviewsearch) will respond with a well-formatted 200 OK HTML document.

#### Test /find response with status code 200

```
    @RepeatedTest(10)
    void testSearchHTMLWithCode200() {
        String url = "http://localhost:8080/find";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=hi", null);
        assertTrue(HtmlValidator.isValid(fetcher.getPostResponse().body()));
    }
```
This is a system test to test if the server (SearchApplication /find) will respond with a well-formatted 200 OK HTML document.

#### Test /slackbot response with status code 400

```
    @RepeatedTest(10)
    void testChatHTMLWithCode400() {
        String url = "http://localhost:9090/slackbot";
        HttpFetcher fetcher = new HttpFetcher(url, "notmsg=hi", null);
        assertTrue(HtmlValidator.isValid(fetcher.getPostResponse().body()));
    }
```
This is a system test to test if the server (ChatApplication /slackbot) will respond with a well-formatted 400 Bad Request HTML document.

#### Test /slackbot response with status code 200

```
    @RepeatedTest(10)
    void testChatHTMLWithCode200() {
        String url = "http://localhost:9090/slackbot";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=hi", null);
        assertTrue(HtmlValidator.isValid(fetcher.getPostResponse().body()));
    }
```
This is a system test to test if the server (ChatApplication /slackbot) will respond with a well-formatted 200 OK HTML document.

#### Test /reviewsearch response with status code 411

```
    @RepeatedTest(10)
    void testSearchHTMLWithCode411() {
        String url = "http://localhost:8080/reviewsearch";
        HttpFetcher fetcher = new HttpFetcher(url, "", null);
        assertTrue(HtmlValidator.isValid(fetcher.getPostResponse().body()));
    }
```

This is a system test to test if the server (SearchApplication /reviewsearch) will respond with a well-formatted 411 Length Required HTML document.

#### Test /reviewsearch response with status code 405

```
@RepeatedTest(10)
    void testSearchHTMLWithCode405() {
        String url = "http://localhost:8080/reviewsearch";
        HttpFetcher fetcher = new HttpFetcher(url, "msg=hi", null);
        assertTrue(HtmlValidator.isValid(fetcher.getDeleteResponse().body()));
    }
```

This is a system test to test if the server (SearchApplication /reviewsearch) will respond with a well-formatted 405 Method Not Allowed HTML document.



