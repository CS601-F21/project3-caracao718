package search;

public class XhtmlFile {
    private String path;
    private String method;
    private String version;

    private String header = "<!DOCTYPE html>\n" + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n";
    private String body;
    private String ending;


    private String file;


    public XhtmlFile(String path, String method) {
        this.path = path;
        this.method = method;

        this.ending = "</form>\n" + "</html>\n";
        this.file = header + "<form action=\"" +  path + "\" method=\"" + method + "\">\n";
    }

    public void constructBody() {
        body = "<li class=\"button \">" + "\n" +
                " <button type=\"submit \">" + method + "</button>\n" +
                "</li>\n" +
                "<li class=\"button \">\n" +
                "<button type=\"submit\">\n" +
                "<>\n";
    }
}



/*
<form action="/my-handling-form-page" method="post">
 <ul>
  <li>
    <label for="name">Name:</label>
    <input type="text" id="name" name="user_name" />
  </li>
  <li>
    <label for="mail">E-mail:</label>
    <input type="email" id="mail" name="user_email" />
  </li>
  <li>
    <label for="msg">Message:</label>
    <textarea id="msg" name="user_message"></textarea>
  </li>

  ...

 */
