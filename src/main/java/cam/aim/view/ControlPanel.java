package cam.aim.view;

import cam.aim.httprequests.ContinuousMove;
import cam.aim.httprequests.MoveRequest;
import cam.aim.httprequests.Request;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by victor on 14.05.15.
 * клас, який створює віртуальний джойстик
 */
public class ControlPanel extends JPanel {
    private MoveRequest moveRequest;
    private Request status;
    private ContinuousMove continuousMove;

    public MoveRequest getMoveRequest() {
        return moveRequest;
    }

    public void setMoveRequest(MoveRequest moveRequest) {
        this.moveRequest = moveRequest;
    }

    public Request getStatus() {
        return status;
    }

    public void setStatus(Request status) {
        this.status = status;
    }

    public ContinuousMove getContinuousMove() {
        return continuousMove;
    }

    public void setContinuousMove(ContinuousMove continuousMove) {
        this.continuousMove = continuousMove;
    }


    /**
     * конструктор, який створює зовнішній вигляд джойстика і описує його дії
     * @param moveRequest
     * @param status
     */
    public ControlPanel(MoveRequest moveRequest, Request status) {
        this.status = status;
        this.moveRequest  = moveRequest;
        continuousMove  =new ContinuousMove();

        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setSize(150, 150);



        JButton[] buttons = new ControlButton[5];


        buttons[0] = new ControlButton("l");//to left
        buttons[0].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                status.start();
                moveRequest.setRequest(Double.parseDouble(status.getElevation())*10, ((Double.parseDouble(status.getAzimuth())-1)* 10));
                moveRequest.start();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                continuousMove.setRequest(-50, 0);
                continuousMove.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                continuousMove.setRequest(0,0);
                continuousMove.start();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        buttons[1] = new ControlButton("r");//to right
        buttons[1].addMouseListener(new MouseListener() {


            @Override
            public void mouseClicked(MouseEvent e) {
                status.start();
                moveRequest.setRequest(Double.parseDouble(status.getElevation())*10, ((Double.parseDouble(status.getAzimuth())+1) * 10));
                moveRequest.start();
            }

            @Override
            public void mousePressed(MouseEvent e) {

                continuousMove.setRequest(50,0);
                continuousMove.start();

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                continuousMove.setRequest(0,0);
                continuousMove.start();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        buttons[2] = new ControlButton("u");// to up
        buttons[2].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                status.start();
                moveRequest.setRequest(((Double.parseDouble(status.getElevation()))-1) * 10, (Double.parseDouble(status.getAzimuth())) * 10);
                moveRequest.start();

            }

            @Override
            public void mousePressed(MouseEvent e) {
                continuousMove.setRequest(0,25);
                continuousMove.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                continuousMove.setRequest(0,0);
                continuousMove.start();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        buttons[3] = new ControlButton("d");// to down
        buttons[3].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                status.start();
                moveRequest.setRequest(((Double.parseDouble(status.getElevation()))+1)*10, (Double.parseDouble(status.getAzimuth()))*10);
                moveRequest.start();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                continuousMove.setRequest(0, -25);
                continuousMove.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                continuousMove.setRequest(0, 0);
                continuousMove.start();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        buttons[4] = new ControlButton("ok");// ok button

        this.add(buttons[2]);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, buttons[2], 0, SpringLayout.HORIZONTAL_CENTER, this);

        this.add(buttons[0]);
        layout.putConstraint(SpringLayout.WEST, buttons[0], 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, buttons[0], 50, SpringLayout.NORTH, this);

        this.add(buttons[4]);
        layout.putConstraint(SpringLayout.WEST, buttons[4], 50, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, buttons[4], 50, SpringLayout.NORTH, this);

        this.add(buttons[1]);
        layout.putConstraint(SpringLayout.WEST, buttons[1], 100, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, buttons[1], 50, SpringLayout.NORTH, this);

        this.add(buttons[3]);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, buttons[3], 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, buttons[3], 100, SpringLayout.NORTH, this);
    }
}
