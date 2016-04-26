package main.client;

/**
 * Created by Jana Mareike on 26.04.2016.
 */
public class client {
    // Create an URL and a HTTP connection
    URL url = new URL( "http://vs.haw/people" );
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod( "GET" );
    connection.setDoOutput(true);

    if(body != null) {// Body?
        connection.getOutputStream().write(body.getBytes());
    }
    connection.connect();// Do it!
    // Get the response
      int code = connection.getResponseCode();
    // Which stream is available depends on the return code...
    String body = code < 400 ? IOUtils.toString(connection.getInputStream())
                     : IOUtils.toString(connection.getErrorStream());
    return new Response(code, body);
}
