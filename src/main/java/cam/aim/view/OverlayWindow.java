package cam.aim.view;

import cam.aim.httprequests.MoveRequest;
import cam.aim.httprequests.Request;
import com.sun.awt.AWTUtilities;
import com.sun.jna.platform.WindowUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by victor on 12.02.15.
 */
public class OverlayWindow extends Window {
    private JLabel dataLable;
    private String[] overlayText;
    private ControlPanel panel;

    public int getColorValue() {
        return colorValue;
    }

    public void setColorValue(int colorValue) {
        this.colorValue = colorValue;
        repaint();
    }

    private int colorValue;

    @Override
    public Window getOwner() {
        return owner;
    }

    public void setOwner(Window owner) {
        this.owner = owner;
    }

    private Window owner;

    public String[] getOverlayText() {
        return overlayText;
    }

    public void setOverlayText(String[] overlayText) {
        this.overlayText = overlayText;

    }

    public OverlayWindow(Window owner, MoveRequest moveRequest, Request request) {
        super(owner, owner.getGraphicsConfiguration());
//        WindowUtils.getAlphaCompatibleGraphicsConfiguration()
        AWTUtilities.setWindowOpaque(this, false);
        setLayout(null);
        setOwner(owner);
        panel = new ControlPanel(moveRequest, request);
        add(panel);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        panel.setLocation((int) dimension.getWidth() / 2 + 250, (int) dimension.getHeight() / 2 + 150);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int xpos=(int)(dimension.getWidth() /2 - dimension.getWidth()/8);
        int ypos=(int)dimension.getHeight()/2;

        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gp = new GradientPaint(xpos, ypos, new Color(255, 255, 255, 255), xpos+300, ypos+300, new Color(255, 255, 0, 0));
        g2.setPaint(gp);
        if(overlayText != null){
            g2.drawString("Широта: " + overlayText[0], xpos+30,ypos+60);
            g2.drawString("Довгота: " + overlayText[1], xpos+30, ypos+90);
        }
        if(getColorValue()<9097402){
            g2.setColor(Color.WHITE);
        }else{
            g2.setColor(Color.BLACK);
        }
        g2.setStroke(new BasicStroke(3.0f));
        g2.drawLine(xpos, (ypos-100), xpos, (ypos+100));
        g2.drawLine((xpos - 100), ypos, (xpos + 100), ypos);
    }
}
