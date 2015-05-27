package cam.aim.view;

import cam.aim.httprequests.HomePositionRequest;
import cam.aim.httprequests.MoveRequest;
import cam.aim.httprequests.Request;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by victor on 14.05.15.
 * кнопки для віртуального джойстика
 */
public class ControlButton extends JButton  {

    public ControlButton(String text) {
        super(text);
        Font font = new Font("Arial", Font.ITALIC, 13);
        setPreferredSize(new Dimension(50, 50));
        setFont(font);

    }
}
