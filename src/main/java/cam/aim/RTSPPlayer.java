package cam.aim;

import uk.co.caprica.vlcj.binding.internal.libvlc_marquee_position_e;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.Marquee;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by maxymkasyanov on 01.02.15.
 */
public class RTSPPlayer extends JFrame {

    private final String url = "rtsp://192.168.2.232//rtsp_tunnel";
    private final String streamOprion=":network-caching=500";
//    private final String url = "rtsp://192.168.2.201//rtsp_tunnel";

//   private final String url = "v4l2:///dev/video0";

    private EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private JPanel panel;

    private Canvas canvas;

    public RTSPPlayer() {
        panel = new JPanel();

        try {
            this.play();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setVisible(true);


    }

    public void play() throws IOException {
        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory("-vvv");
        FullScreenStrategy fullScreenStrategy = new DefaultFullScreenStrategy(this);

        EmbeddedMediaPlayer embeddedMediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer(fullScreenStrategy);

        this.setLocation(100, 100);
        this.setSize(1050, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        embeddedMediaPlayer.playMedia(url, streamOprion);
        canvas = new Canvas();
        embeddedMediaPlayer.setVideoSurface(mediaPlayerFactory.newVideoSurface(canvas));


//        Path img = Paths.get("printer_PNG7756.png");
//        BufferedImage overlayImage = ImageIO.read(new ByteArrayInputStream(Files.readAllBytes(img)));
//
//        cam.aim.Overlay overlay = new cam.aim.Overlay(this, overlayImage);
//        embeddedMediaPlayer.enableOverlay(true);
//        embeddedMediaPlayer.setOverlay(overlay);

        panel.add(canvas, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
        this.add(panel);


        Marquee.marquee()
                .opacity(200)
                .position(libvlc_marquee_position_e.bottom)
                .colour(Color.CYAN)
                .timeout(5000)
                .text("Text1")
                .size(20)
                .enable()
                .apply(embeddedMediaPlayer);
    }


}
