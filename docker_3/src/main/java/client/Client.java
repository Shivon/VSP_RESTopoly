package client;

import sun.misc.IOUtils;

import javax.xml.ws.Response;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jana Mareike on 26.04.2016.
 */
public class Client {

    public static Response createClient() throws IOException {
        // Create an URL and a HTTP connection
        URL url = new URL("http://localhost:4567/player");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);

        if (body != null) {// Body?
            connection.getOutputStream().write(body.getBytes());
        }
        connection.connect();// Do it!
        // Get the response
        int code = connection.getResponseCode();
        // Which stream is available depends on the return code...
        String body = code < 400 ? IOUtils.toString(connection.getInputStream())
                : IOUtils.toString(connection.getErrorStream());
        return new Response(code, body)throws IOException;
    }
}
