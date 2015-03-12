package cam.aim.view;

import cam.aim.Request;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * Created by victor on 12.02.15.
 */
public class OverlayTest implements MouseListener {

    private EmbeddedMediaPlayer mediaPlayer;
    private MediaPlayerFactory playerFactory;
    private static OverlayWindow overlay;
    private final String url = "rtsp://192.168.2.232//rtsp_tunnel";
    private final String streamOption=":network-caching=160";

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
        InfoTable table = new InfoTable();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        JTextField elev = new JTextField();
        JTextField  azim= new JTextField();
        JTextField inf = new JTextField();

        JLabel labelElevation = new JLabel();
        labelElevation.setText("Кут місця");

        JLabel labelAzimuth = new JLabel();
        labelAzimuth.setText("Азимут");

        JLabel labelInfo = new JLabel();
        labelInfo.setText("Инфо");

        JButton button1 =new JButton("Calc");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elevation = Long.parseLong(elev.getText());
                long azimuth = Long.parseLong(azim.getText());
                String info = inf.getText();
                elev.setText("");
                azim.setText("");
                inf.setText("");
                Aim aim = new Aim();
                aim.setAzimuth(azimuth);
                aim.setElevation(elevation);
                aim.setInfo(info);

                AimService service = new ClassPathXmlApplicationContext("transactionalContext.xml").getBean("service", AimServiceImpl.class);
                service.createAim(aim);

                table.repaint();
            }
        });

        panel.add(table);
        panel.add(labelElevation);
        panel.add(elev);
        panel.add(labelAzimuth);
        panel.add(azim);
        panel.add(labelInfo);
        panel.add(inf);
        panel.add(button1);
        Canvas canvas = new Canvas();


        JButton button = new JButton("Створити ціль");
        button.addMouseListener(this);
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

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse click");
        Request request = new Request();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
