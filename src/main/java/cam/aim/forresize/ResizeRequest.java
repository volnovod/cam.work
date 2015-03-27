package cam.aim.forresize;


import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by victor on 23.03.15.
 */
public class ResizeRequest {

    private String url = "http://192.168.2.64/ISAPI/Streaming/channels/3";
    private final String USER_AGENT = "Chrome/40.0.2214.111";



    public void setRequest(int width, int height) {
        this.request = "<StreamingChannel version=\"2.0\"xmlns=\"http://www.isapi.org/ver20/XMLSchema\">\n" +
                "<id>1</id>\n" +
                "<channelName>IPdome</channelName>\n" +
                "<enabled>true</enabled>\n" +
                "<Transport>\n" +
                "<maxPacketSize>1000</maxPacketSize>\n" +
                "<ControlProtocolList>\n" +
                "<ControlProtocol>\n" +
                "<streamingTransport>RTSP</streamingTransport>\n" +
                "</ControlProtocol>\n" +
                "<ControlProtocol>\n" +
                "<streamingTransport>HTTP</streamingTransport>\n" +
                "</ControlProtocol>\n" +
                "<ControlProtocol>\n" +
                "<streamingTransport>SHTTP</streamingTransport>\n" +
                "</ControlProtocol>\n" +
                "</ControlProtocolList>\n" +
                "<Unicast>\n" +
                "<enabled>true</enabled>\n" +
                "<rtpTransportType>RTP/TCP</rtpTransportType>\n" +
                "</Unicast>\n" +
                "<Multicast>\n" +
                "<enabled>true</enabled>\n" +
                "<destIPAddress>0.0.0.0</destIPAddress>\n" +
                "<videoDestPortNo>8600</videoDestPortNo>\n" +
                "<audioDestPortNo>8600</audioDestPortNo>\n" +
                "</Multicast>\n" +
                "<Security>\n" +
                "<enabled>true</enabled>\n" +
                "</Security>\n" +
                "</Transport>\n" +
                "<Video>\n" +
                "<enabled>true</enabled>\n" +
                "<videoInputChannelID>1</videoInputChannelID>\n" +
                "<videoCodecType>H.264</videoCodecType>\n" +
                "<videoScanType>progressive</videoScanType>\n" +
                "<videoResolutionWidth>" + width + "</videoResolutionWidth>\n" +
                "<videoResolutionHeight>" + height + "</videoResolutionHeight>\n" +
                "<videoQualityControlType>VBR</videoQualityControlType>\n" +
                "<constantBitRate>1024</constantBitRate>\n" +
                "<fixedQuality>60</fixedQuality>\n" +
                "<vbrUpperCap>1024</vbrUpperCap>\n" +
                "<vbrLowerCap>32</vbrLowerCap>\n" +
                "<maxFrameRate>2500</maxFrameRate>\n" +
                "<keyFrameInterval>2000</keyFrameInterval>\n" +
                "<snapShotImageType>JPEG</snapShotImageType>\n" +
                "<H264Profile>Main</H264Profile>\n" +
                "<GovLength>50</GovLength>\n" +
                "<PacketType>PS</PacketType>\n" +
                "<PacketType>RTP</PacketType>\n" +
                "</Video>\n" +
                "<Audio>\n" +
                "<enabled>true</enabled>\n" +
                "<audioInputChannelID>1</audioInputChannelID>\n" +
                "<audioCompressionType>G.711ulaw</audioCompressionType>\n" +
                "</Audio>\n" +
                "</StreamingChannel>";
    }

    private String request;

    public ResizeRequest() {
    }

    public ResizeRequest(String url) {
        this.url = url;
    }

    public ResizeRequest(int width, int height){
        this.setRequest(width, height);
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


            double elev;
            double azim;


        } catch (Exception e){
            e.printStackTrace();
        }


    }






    public static void main(String[] args) {
        ResizeRequest request = new ResizeRequest();

        request.start();



    }
}
