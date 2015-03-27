package cam.aim.view;

import com.sun.awt.AWTUtilities;
import com.sun.jna.platform.WindowUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by victor on 12.02.15.
 */
public class OverlayWindow extends Window {
    private JLabel dataLable;

    public OverlayWindow(Window owner) {
        super(owner, WindowUtils.getAlphaCompatibleGraphicsConfiguration());
        AWTUtilities.setWindowOpaque(this, false);
        setLayout(null);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int xpos=(int)(dimension.getWidth() /2 - dimension.getWidth()/8);
        int ypos=(int)dimension.getHeight()/2;

        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gp = new GradientPaint(180.0f, 280.0f, new Color(255, 255, 255, 255), 250.0f, 380.0f, new Color(255, 255, 0, 0));
        g2.setPaint(gp);
        g2.drawString("Cam1", 100, 100);
        g2.setColor(Color.red);
        g2.setStroke(new BasicStroke(5.0f));
        g2.drawLine(xpos, (ypos-100), xpos, (ypos+100));
        g2.drawLine((xpos - 100), ypos, (xpos + 100), ypos);
//        g2.drawRect((xpos-xpos/10), (ypos-ypos/10), xpos/5, ypos/5);
        g2.drawRect((xpos-xpos/20), (ypos-ypos/20), xpos/10, ypos/10);
    }
}
