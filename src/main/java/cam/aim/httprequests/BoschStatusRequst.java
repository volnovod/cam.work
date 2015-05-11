package cam.aim.httprequests;

import org.apache.commons.codec.binary.Hex;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by victor on 24.04.15.
 */
public class BoschStatusRequst {

    private final String panAction = "0112";
    private final String tiltAction = "0113";
    private final String getAction = "0x81";
    private final String serverID = "0006";
    private final String operationID = "01";


        private String url="http://192.168.1.201/rcp.xml?command=0x09A5&type" +
                "=P_OCTET&direction=WRITE&num=1&payload=";
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

        public BoschStatusRequst() {
        }

    public void setUrlForGetTiltPosition(){

        this.url += getAction + serverID + tiltAction + operationID;

    }

    public void setUrlForGetPanPosition(){

        this.url += getAction + serverID + panAction + operationID;

    }


        public void start() {
            try {
                URL urladr = new URL(this.url);

                HttpURLConnection urlConnection = (HttpURLConnection) urladr.openConnection();


                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("User-Agent", USER_AGENT);

                int responseCode = urlConnection.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

                Document doc = parseXML(urlConnection.getInputStream());
                NodeList elevationList = doc.getElementsByTagName("elevation");
                NodeList azimuthList = doc.getElementsByTagName("azimuth");

                double elev;
                double azim;


                if (elevationList.getLength() == azimuthList.getLength()) {
                    for (int i = 0; i < elevationList.getLength(); i++) {
                        elev = (Double.parseDouble(elevationList.item(i).getTextContent())) / 10;
                        azim = (Double.parseDouble(azimuthList.item(i).getTextContent())) / 10;
                        this.azimuth = new Double(azim).toString();
                        this.elevation = new Double(elev).toString();
                    }
                }
            } catch (Exception e){
                setAzimuth("Помилка підключення до камери");
                setElevation("Помилка підключення до камери");
                e.printStackTrace();
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
        double degrees =30;

        degrees = Math.toRadians(degrees);

        degrees*= 10000;
        System.out.println(degrees);

        String result = Integer.toHexString((int) degrees);

        System.out.println(result);

        String byte1 = new String();
        String byte2 =  new String();

        if(result.length() == 4 ){

            byte1+= result.charAt(0);
            byte1+= result.charAt(1);

            byte2 += result.charAt(2);
            byte2 += result.charAt(3);
        }

        if(result.length() == 3 ){

            byte1+= 0;
            byte1+= result.charAt(0);

            byte2 += result.charAt(1);
            byte2 += result.charAt(2);

        }
        if(result.length() == 2 ){

            byte1+= 0;
            byte1+= 0;

            byte2 += result.charAt(0);
            byte2 += result.charAt(1);

        }

        if(result.length() == 1 ){

            byte1+= 0;
            byte1+= 0;

            byte2 += 0;
            byte2 += result.charAt(2);

        }


        System.out.println("Byte 1 - " + byte1);
        System.out.println("Byte 2 - " + byte2);
    }





    }


