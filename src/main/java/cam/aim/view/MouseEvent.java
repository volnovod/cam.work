package cam.aim.view;

import java.awt.event.MouseListener;

/**
 * Created by victor on 11.03.15.
 */
public class MouseEvent implements MouseListener {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        System.out.println("OKKKKK");
        String text = e.paramString();
        System.out.println(text);
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {

    }
}
