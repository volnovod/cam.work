package cam.aim;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by maxymkasyanov on 29.01.15.
 */
public class HttpRequest {


    private String url;

    private final String USER_AGENT = "Mozilla/5.0";

    public HttpRequest(String url) {
        this.url = url;
    }

    public byte[] makeGet() throws IOException {

        URL urlAddress = new URL(this.url);

        HttpURLConnection urlConnection = (HttpURLConnection) urlAddress.openConnection();

        urlConnection.setRequestMethod("GET");

        //add request header
        urlConnection.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = urlConnection.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        ByteArrayOutputStream bais = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            is = urlConnection.getInputStream();
            byte[] byteChunk = new byte[4096];
            int n;
            while ( (n = is.read(byteChunk)) > 0 ) {
                bais.write(byteChunk, 0, n);
            }
        }
        catch (IOException e) {
            System.err.printf ("Failed while reading bytes from %s: %s", urlConnection.getURL(), e.getMessage());
            e.printStackTrace ();
        }
        finally {
            if (is != null) { is.close(); }
        }

        return bais.toByteArray();
    }

}
