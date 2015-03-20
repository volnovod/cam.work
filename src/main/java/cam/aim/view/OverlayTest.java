package cam.aim.view;

import cam.aim.Request;
import cam.aim.calc.AimCalculatorImpl;
import cam.aim.comport.ComPortReader;
import cam.aim.domain.Aim;
import cam.aim.service.AimService;
import cam.aim.service.AimServiceImpl;
import com.sun.jna.NativeLibrary;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by victor on 12.02.15.
 */
public class OverlayTest extends JFrame  {

    private EmbeddedMediaPlayer mediaPlayer;
    private MediaPlayerFactory playerFactory;
    private static OverlayWindow overlay;
//    private final String url = "rtsp://192.168.2.232//rtsp_tunnel";
private final String url = "rtsp://admin:12345@192.168.2.64//rtsp_tunnel ";
//    private final String url = "v4l2:///dev/video0";
    private final String streamOption=":network-caching=250";

    public static void main(String[] args) {
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "/usr/lib/");
        SwingUtilities.invokeLater(OverlayTest::new);


    }

    public OverlayTest() {
        JFrame frame = new JFrame("cam.aim.view.Overlay");
        overlay = new OverlayWindow(frame);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setBackground(Color.BLACK);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setLayout(new BorderLayout());
        AimService service = new ClassPathXmlApplicationContext("transactionalContext.xml").getBean("service", AimServiceImpl.class);
        java.util.List<Aim> list = service.getAllAim();

        MyTableModel model = new MyTableModel();
        model.setBeans(list);
        JTable table = new JTable(model);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int xpos=(int)dimension.getWidth()/8;
        int ypos=(int)dimension.getHeight()/3;

        table.setPreferredScrollableViewportSize(new Dimension(xpos, ypos));


        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        JTextField lat = new JTextField();
        JTextField longt= new JTextField();
        JTextField azim= new JTextField();
        JTextField elev = new JTextField();
        JTextField dist= new JTextField();
        JTextField deltaA = new JTextField();
        JTextField deltaB = new JTextField();

        JLabel labelLatitude = new JLabel();
        labelLatitude.setText("Широта спостерігання");

        JLabel labelLongtitude = new JLabel();
        labelLongtitude.setText("Довгота спостерігання");

        JLabel labelAzimuth = new JLabel();
        labelAzimuth.setText("Азимут");

        JLabel labelElevation = new JLabel();
        labelElevation.setText("Кут місця");

        JLabel labelDistance = new JLabel();
        labelDistance.setText("Відстань до цілі");

        JLabel labelDeltaA = new JLabel();
        labelDeltaA.setText("Відхилення від півночі");

        JLabel labelDeltaB = new JLabel();
        labelDeltaB.setText("Відхилення від вертикалі");

        JButton gpsButton = new JButton("отримати коордитанти від GPS");
        gpsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComPortReader reader = new ComPortReader();
                byte[] data = reader.readData();
                reader.getCoordinates(data);
                lat.setText(reader.getLatitude());
                longt.setText(reader.getLongtitude());
            }
        });

        JButton button2 = new JButton("Отримати азимут та кут місця");
        button2.addActionListener(e -> {
            Request request = new Request();
            try {
                request.start();
                azim.setText(request.getAzimuth());
                elev.setText(request.getElevation());
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        });

        JButton button1 =new JButton("Розрахувати та зберегти");
        button1.addActionListener(e -> {
            double latitude = Double.parseDouble(lat.getText());
            double longtitude = Double.parseDouble(longt.getText());
            double azimuth = Double.parseDouble(azim.getText());
            double distance = Double.parseDouble(dist.getText());
            lat.setText("");
            longt.setText("");
            azim.setText("");
            elev.setText("");
            dist.setText("");
            deltaA.setText("");
            deltaB.setText("");

            AimCalculatorImpl calculator = new AimCalculatorImpl();
            calculator.calcCoordinate(latitude, longtitude, azimuth, distance);
            Aim aim = calculator.getAim();

            service.createAim(aim);


            model.setBeans(service.getAllAim());
            table.revalidate();
            table.removeAll();

            table.setModel(model);
        });


        panel.add(scrollPane);
        panel.add(labelLatitude);
        panel.add(lat);
        panel.add(labelLongtitude);
        panel.add(longt);
        panel.add(labelAzimuth);
        panel.add(azim);
        panel.add(labelElevation);
        panel.add(elev);
        panel.add(labelDistance);
        panel.add(dist);
        panel.add(labelDeltaA);
        panel.add(deltaA);
        panel.add(labelDeltaB);
        panel.add(deltaB);
        panel.add(button2);
        panel.add(gpsButton);
        panel.add(button1);

        Canvas canvas = new Canvas();


        JButton button = new JButton("Створити ціль");
        frame.add(panel, BorderLayout.WEST);
        frame.add(canvas);
        frame.add(button, BorderLayout.SOUTH);


        frame.setVisible(true);




        playerFactory = new MediaPlayerFactory("-vvv");
        mediaPlayer = playerFactory.newEmbeddedMediaPlayer();
        mediaPlayer.setVideoSurface(playerFactory.newVideoSurface(canvas));
        mediaPlayer.setOverlay(overlay);
        mediaPlayer.enableOverlay(true);
        mediaPlayer.playMedia(url, streamOption);
        frame.pack();



    }



}
