package cam.aim.view;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SpringSample {


    public SpringSample() {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel1 = new JPanel();
        SpringLayout layout= new SpringLayout();
        panel1.setLayout(layout);

        JButton button = new JButton("Okkkk");
        JLabel label = new JLabel();
        label.setText("afasf");

        Canvas canvas = new Canvas();


        panel1.add(label);
        panel1.add(button);
//        frame.add(canvas, BorderLayout.EAST);
        frame.add(panel1, BorderLayout.WEST);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SpringSample sample = new SpringSample();
    }
}