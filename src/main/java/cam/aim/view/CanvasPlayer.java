package cam.aim.view;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

import javax.swing.*;
import java.awt.*;

/**
 * Created by victor on 11.02.15.
 */
public class CanvasPlayer {

    private MediaPlayerFactory playerFactory;
    private EmbeddedMediaPlayer mediaPlayer;

    private final String url = "rtsp://192.168.2.201//rtsp_tunnel";
    private final String streamOprion=":network-caching=900";

    private JFrame frame;
    private JPanel panel;
    private Canvas canvas;

    public CanvasPlayer() {
        panel = new JPanel();
        //panel.setBackground(Color.BLACK);

        canvas = new Canvas();
        panel.add(canvas, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();

        playerFactory = new MediaPlayerFactory();
        mediaPlayer = playerFactory.newEmbeddedMediaPlayer();
        CanvasVideoSurface videoSurface = playerFactory.newVideoSurface(canvas);
        mediaPlayer.setVideoSurface(videoSurface);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(0, 0);
        frame.setSize(dimension);
        frame.add(panel);
        frame.setVisible(true);

        mediaPlayer.playMedia(url, streamOprion);
    }
}
