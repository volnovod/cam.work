package cam.aim;

import scala.Char;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * Created by victor on 06.03.15.
 */
public class Request {

    private String url="http://192.168.2.64/ISAPI/PTZCtrl/channels/1/status";
    private final String USER_AGENT = "Chrome/40.0.2214.111";

    public Request() {
    }

    public Request(String url) {
        this.url = url;
    }

    public byte[] makeGet() throws IOException{
        URL urladr = new URL(this.url);

        HttpURLConnection urlConnection = (HttpURLConnection) urladr.openConnection();


        urlConnection.setRequestMethod("GET");
        String userpass = "admin" + ":" + "12345";
        String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
        urlConnection.setRequestProperty ("Authorization", basicAuth);
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
                System.out.println(n);
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

    public static void main(String[] args) {
        Request request =new Request();
        try {
            byte[] res = request.makeGet();
            char ch;
            for(byte el: res){
                ch = (char)el;
                System.out.print(ch);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
