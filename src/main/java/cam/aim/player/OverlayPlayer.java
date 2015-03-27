package cam.aim.player;

import cam.aim.view.OverlayWindow;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.swing.*;
import java.awt.*;

/**
 * Created by victor on 24.03.15.
 */
public class OverlayPlayer extends JFrame {

    private Player player;
    private OverlayWindow overlayWindow;

    public OverlayPlayer() {
        this.player = new Player();
        this.overlayWindow = new OverlayWindow(this);

        JPanel panel = new JPanel();
        LayoutManager layout = new OverlayLayout(panel);
        panel.setLayout(layout);

        panel.add(player);

        JLabel button = new JLabel("ads");
        panel.add(button);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    }

    public static void main(String[] args) {
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "/usr/lib/");
        SwingUtilities.invokeLater(OverlayPlayer::new);
    }
}
