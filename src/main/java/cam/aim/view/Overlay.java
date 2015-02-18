package cam.aim.view;

import com.sun.jna.platform.WindowUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by maxymkasyanov on 02.02.15.
 */
public class Overlay extends Window {

    private BufferedImage overlayImage;

    public Overlay(Window owner, BufferedImage overlayImage) {
        super(owner, WindowUtils.getAlphaCompatibleGraphicsConfiguration());
        setLayout(null);
        this.overlayImage = overlayImage;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(overlayImage, 0, 0, getOwner());
    }
}
