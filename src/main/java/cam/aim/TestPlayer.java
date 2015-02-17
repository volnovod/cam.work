package cam.aim;

import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.x.XFullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.swing.*;
import java.awt.*;

/**
 * Created by victor on 11.02.15.
 */
public class TestPlayer extends JFrame{

    private EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent;
    private final String url = "rtsp://192.168.2.232//rtsp_tunnel";
    private final String streamOprion=":network-caching=500";


    public static void main(String[] args) {
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "/usr/lib/");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TestPlayer();
            }
        });

    }

    public TestPlayer() {

        final JFrame frame =new JFrame("Test player");
        embeddedMediaPlayerComponent = new EmbeddedMediaPlayerComponent(){
            protected String[] onGetMediaPlayerFactoryArgs() {
                return new String[] {"--no-video-title-show", "--ffmpeg-hw", "-vvv"};
            }

            protected FullScreenStrategy onGetFullScreenStrategy() {
                return new XFullScreenStrategy(frame);
            }

            public void videoOutputAvailable(MediaPlayer mediaPlayer, boolean videoOutput) {
            }

            public void error(MediaPlayer mediaPlayer) {
            }

            public void finished(MediaPlayer mediaPlayer) {
            }
        };


        Graphics g= frame.getGraphics();
        frame.setContentPane(embeddedMediaPlayerComponent);
        frame.setLocation(0,0);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        embeddedMediaPlayerComponent.getMediaPlayer().playMedia(url, streamOprion);



    }

    public void paint(Graphics g){
        g.drawLine(20, 20, 360, 20);
        Color oldColor = g.getColor();
        Color newColor = new Color(0, 0, 255);
        g.setColor(newColor);
    }


}
