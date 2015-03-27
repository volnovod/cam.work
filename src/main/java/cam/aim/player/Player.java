package cam.aim.player;

import cam.aim.view.OverlayWindow;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.swing.*;
import java.awt.*;

/**
 * Created by victor on 24.03.15.
 */
public class Player extends JComponent {

    private EmbeddedMediaPlayer mediaPlayer;
    private MediaPlayerFactory playerFactory;
    private final String url = "rtsp://192.168.2.232//rtsp_tunnel";
    private final String streamOption=":network-caching=500";

    public Player() {


        Canvas canvas = new Canvas();
        playerFactory = new MediaPlayerFactory("-vvv");
        mediaPlayer = playerFactory.newEmbeddedMediaPlayer();
        mediaPlayer.setVideoSurface(playerFactory.newVideoSurface(canvas));
        mediaPlayer.playMedia(url, streamOption);

    }

    public static void main(String[] args) {

        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "/usr/lib/");
        SwingUtilities.invokeLater(Player::new);
    }


}
