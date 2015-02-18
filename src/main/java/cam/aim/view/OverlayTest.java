package cam.aim.view;

import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import javax.swing.*;
import java.awt.*;


/**
 * Created by victor on 12.02.15.
 */
public class OverlayTest {

    private EmbeddedMediaPlayer mediaPlayer;
    private MediaPlayerFactory playerFactory;
    private static OverlayWindow overlay;
    private final String url = "rtsp://192.168.2.232//rtsp_tunnel";
    private final String streamOption=":network-caching=150";

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
        Canvas canvas = new Canvas();
        frame.add(canvas, BorderLayout.CENTER);
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
