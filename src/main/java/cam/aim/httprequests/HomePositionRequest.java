package cam.aim.httprequests;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by victor on 27.03.15.
 */
public class HomePositionRequest {

    private String url = "http://192.168.1.64/ISAPI/PTZCtrl/channels/1/homeposition/goto";
    private final String USER_AGENT = "Chrome/40.0.2214.111";



    public HomePositionRequest() {
    }


    public void start() {
        try {
            URL urladr = new URL(this.url);

            HttpURLConnection urlConnection = (HttpURLConnection) urladr.openConnection();


            urlConnection.setRequestMethod("PUT");
            String userpass = "admin" + ":" + "12345";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
            urlConnection.setRequestProperty("Authorization", basicAuth);
            urlConnection.setRequestProperty("User-Agent", USER_AGENT);
            urlConnection.setDoOutput(true);

            int responseCode = urlConnection.getResponseCode();
            System.out.println("\nSending 'PUT' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);





        } catch (Exception e){
            e.printStackTrace();
        }


    }






    public static void main(String[] args) {
        HomePositionRequest request = new HomePositionRequest();
        request.start();


    }
}
