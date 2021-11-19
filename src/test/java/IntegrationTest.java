import framework.HttpRequest;
import org.junit.jupiter.api.RepeatedTest;

import java.io.*;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegrationTest {
    @RepeatedTest(2)
    void testContentLength() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = new HttpRequest(new PrintWriter(System.out), new BufferedReader(new FileReader("src/test/integrationTest.txt")));
        assertEquals(2, request.getContentLength());
    }

    @RepeatedTest(2)
    void testValidMethod() throws FileNotFoundException {
        HttpRequest request = new HttpRequest(new PrintWriter(System.out), new BufferedReader(new FileReader("src/test/integrationTest.txt")));
        assertTrue(request.validMethod());
    }

    @RepeatedTest(2)
    void testReadLine() throws FileNotFoundException {
        HttpRequest request = new HttpRequest(new PrintWriter(System.out), new BufferedReader(new FileReader("src/test/integrationTest.txt")));
        assertEquals("POST", request.getMethod());
    }

}
