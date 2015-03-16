package cam.aim;



import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringJoiner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.*;

/**
 * Created by victor on 06.03.15.
 */
public class Request {

    private String url="http://192.168.2.64/ISAPI/PTZCtrl/channels/1/status";
    private final String USER_AGENT = "Chrome/40.0.2214.111";
    private String azimuth;
    private String elevation;

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public String getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(String azimuth) {
        this.azimuth = azimuth;
    }

    public Request() {
    }

    public Request(String url) {
        this.url = url;
    }

    public void start() throws Exception
    {
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

        Document doc = parseXML(urlConnection.getInputStream());
        NodeList elevationList = doc.getElementsByTagName("elevation");
        NodeList azimuthList = doc.getElementsByTagName("azimuth");

        double elev;
        double azim;


        if(elevationList.getLength() == azimuthList.getLength()) {
            for (int i = 0; i < elevationList.getLength(); i++) {
                elev = (Double.parseDouble(elevationList.item(i).getTextContent()))/10;
                azim = (Double.parseDouble(azimuthList.item(i).getTextContent()))/10;
                this.azimuth = new Double(azim).toString();
                this.elevation = new Double(elev).toString();
            }
        }


    }

    public Document parseXML(InputStream stream)
            throws Exception
    {
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        try
        {
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

            doc = objDocumentBuilder.parse(stream);
        }
        catch(Exception ex)
        {
            throw ex;
        }

        return doc;
    }




    public static void main(String[] args) {
       Request request = new Request();

        try {
            request.start();
            System.out.println("elev "+request.elevation+" azim " + request.azimuth);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
