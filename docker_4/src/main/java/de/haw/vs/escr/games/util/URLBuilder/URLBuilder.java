package de.haw.vs.escr.games.util.URLBuilder;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Christian on 05.06.2016.
 */
public class URLBuilder {
    private URL url;

    public URLBuilder(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getProtocolAndAuthority() {
        return String.format("%s://%s", url.getProtocol(), url.getAuthority());
    }

    public String getPath() {
        return this.url.getPath();
    }
}
