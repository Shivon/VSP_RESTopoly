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

    @Entity
    @Table(name = "Client")
    public class Client {
        @Id
        @Column(name = "id")
        // @Expose on a field you're telling Gson to include that property into your JSON String
        @Expose(serialize = false)
        private String id;

        // required params

        @Column(name = "name")
        @Expose
        private String name;

        @Column(name = "uri")
        @Expose
        private URI uri;


        // leerer Konstruktor notwendig, weil gson das braucht, sonst Probleme mit ID
        public Client(){
        }

        public Client(String name, URI uri) {
            this.id = UUID.randomUUID().toString();
            this.name = name;
            this.uri = uri;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public URI getUri() {
            return uri;
        }

        public void setUri(URI uri) {
            this.uri = uri;
        }

        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            if (!super.equals(object)) return false;

            Client client = (Client) object;

            if (id != null ? !id.equals(client.id) : client.id != null) return false;
            if (!name.equals(client.name)) return false;
            if (!uri.equals(client.uri)) return false;

            return true;
        }

        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (id != null ? id.hashCode() : 0);
            result = 31 * result + name.hashCode();
            result = 31 * result + uri.hashCode();
            return result;
        }

        public static Response createClient() throws IOException {
        // Create an URL and a HTTP connection
        URL url = new URL("http://localhost:4567/user");
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
