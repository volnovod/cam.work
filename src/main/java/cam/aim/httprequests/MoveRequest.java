package cam.aim.httprequests;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by victor on 26.03.15.
 */
public class MoveRequest {

    private String url = "http://192.168.2.64/ISAPI/PTZCtrl/channels/1/absolute";
    private final String USER_AGENT = "Chrome/40.0.2214.111";
    private boolean isMoveAround = false;
    private int elevation;
    private int azimuth;

    public int getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(int azimuth) {
        this.azimuth = azimuth;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }



    public boolean isMoveAround() {
        return isMoveAround;
    }

    public void setIsMoveAround(boolean isMoveAround) {
        this.isMoveAround = isMoveAround;
    }

    public void setRequest(int elevation, double azimuth) {
        this.request =  "<PTZData version=\"2.0\" xmlns=\"http://www.isapi.org/ver20/XMLSchema\">\n" +
                "<AbsoluteHigh>\n" +
                "<elevation>" + elevation + "</elevation>\n" +
                "<azimuth>" + (int)azimuth + "</azimuth>\n" +
                "<absoluteZoom> 10 </absoluteZoom>\n" +
                "</AbsoluteHigh>\n" +
                "</PTZData>";
    }

    private String request;

    public MoveRequest() {
    }

    public MoveRequest(String url) {
        this.url = url;
    }

    public MoveRequest(int elevation, double azimuth){
        this.setRequest(elevation, (int)azimuth);
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

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());

            outputStreamWriter.write(request);

            outputStreamWriter.close();

            int responseCode = urlConnection.getResponseCode();
            System.out.println("\nSending 'PUT' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);





        } catch (Exception e){
            e.printStackTrace();
        }


    }






    public static void main(String[] args) {
        MoveRequest moveRequest = new MoveRequest(0, 0);
        moveRequest.start();


    }

}
