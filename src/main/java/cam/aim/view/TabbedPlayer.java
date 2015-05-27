package cam.aim.view;

import cam.aim.calc.AimCalculatorImpl;
import cam.aim.calc.WGStoCK42;
import cam.aim.calc.WGStoCK42Impl;
import cam.aim.calibration.Calibration;
import cam.aim.comport.ComPortReaderImpl;
import cam.aim.domain.Aim;
import cam.aim.httprequests.HomePositionRequest;
import cam.aim.httprequests.MoveRequest;
import cam.aim.httprequests.Request;
import cam.aim.service.AimService;
import cam.aim.service.AimServiceImpl;
import com.sun.jna.NativeLibrary;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by victor on 25.05.15.
 */
public class TabbedPlayer extends JFrame {


    private EmbeddedMediaPlayer mediaPlayer;
    private MediaPlayerFactory playerFactory;
    private static OverlayWindow overlay;
    private MoveRequest moveRequest;
    private Calibration calibration;
    private Aim lastAim;
    private WGStoCK42 wgStoCK42;
    private Request status;

    public Aim getLastAim() {
        return lastAim;
    }

    public void setLastAim(Aim lastAim) {
        this.lastAim = lastAim;
    }

    //        private final String url = "rtsp://192.168.1.201//rtsp_tunnel";
    private final String url = "rtsp://admin:12345@192.168.1.64//rtsp_tunnel ";
    //    private final String url = "rtsp://admin:12345@192.168.1.64//ISAPI/Streaming/channels/3/rtsp_tunnel";
//    private final String url = "v4l2:///dev/video0";
    private final String streamOption=":network-caching=300";

    public MoveRequest getMoveRequest() {
        return moveRequest;
    }

    public void setMoveRequest(MoveRequest moveRequest) {
        this.moveRequest = moveRequest;
    }

    public Calibration getCalibration() {
        return calibration;
    }

    public void setCalibration(Calibration calibration) {
        this.calibration = calibration;
    }

//    private static void setLookAndFeel() {
//        String lookAndFeelClassName;
//        if (RuntimeUtil.isNix()) {
//            lookAndFeelClassName = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
//        }
//
//        else {
//            lookAndFeelClassName = UIManager.getSystemLookAndFeelClassName();
//        }
//        try {
//            UIManager.setLookAndFeel(lookAndFeelClassName);
//        }
//        catch(Exception e) {
//            // Silently fail, it doesn't matter
//        }
//    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    Rectangle rectangle;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TabbedPlayer::new);
    }

    public TabbedPlayer() {



        HomePositionRequest homePositionRequest = new HomePositionRequest();
        homePositionRequest.start();

        wgStoCK42 = new WGStoCK42Impl();

        moveRequest = new MoveRequest();

        status = new Request();

        calibration = new Calibration();

//        controlPanel = new ControlPanel(moveRequest, status);


        JFrame frame = new JFrame("cam.aim.view.Overlay");
        overlay = new OverlayWindow(frame, moveRequest, status);


        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();


        Canvas canvas = new Canvas();


        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);


        frame.setLayout(new BorderLayout());
        AimService service = new ClassPathXmlApplicationContext("transactionalContext.xml").getBean("service", AimServiceImpl.class);
        java.util.List<Aim> list = service.getAllAim();

        frame.add(canvas);


        frame.setVisible(true);


        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcName(), "/usr/lib/vlc");

        playerFactory = new MediaPlayerFactory("-vvv", "--no-snapshot-preview",
                "--no-snapshot-sequential","--no-osd");
        mediaPlayer = playerFactory.newEmbeddedMediaPlayer();
        mediaPlayer.setVideoSurface(playerFactory.newVideoSurface(canvas));
        mediaPlayer.setOverlay(overlay);
        mediaPlayer.enableOverlay(true);
        mediaPlayer.playMedia(url, streamOption);



        MyTableModel model = new MyTableModel();
        model.setBeans(list);
        if (model.getBeans().size()!=0){

            this.setLastAim(model.getBeans().get(model.getBeans().size() - 1));
            String[] text = {String.valueOf(lastAim.getLatitudeck42()),String.valueOf(lastAim.getLongitudeck42())};

            overlay.setOverlayText(text);
        }

        JTable table = new JTable(model);
        int xpos=(int)dimension.getWidth()/8;
        int ypos=(int)dimension.getHeight()/3;

        table.setPreferredScrollableViewportSize(new Dimension(xpos, ypos));


        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setSize(new Dimension((int) dimension.getWidth() / 7, (int) dimension.getHeight()));

        JTextField h = new JTextField(7);
        JTextField lat = new JTextField(7);
        JTextField longt= new JTextField(7);
        JTextField azim= new JTextField(7);
        JTextField elev = new JTextField(7);
        JTextField dist= new JTextField(7);
        JTextField deltaA = new JTextField(6);// deviation to north
        JTextField deltaB = new JTextField(6);// deviation to vertical
        JTextField deltaC = new JTextField(6);// deviation to horizontal

        JLabel labelHeight = new JLabel();
        labelHeight.setText("Висота");

        JLabel labelLatitude = new JLabel();
        labelLatitude.setText("Широта");

        JLabel labelLongtitude = new JLabel();
        labelLongtitude.setText("Довгота");

        JLabel labelAzimuth = new JLabel();
        labelAzimuth.setText("Азимут");

        JLabel labelElevation = new JLabel();
        labelElevation.setText("Кут місця");

        JLabel labelDistance = new JLabel();
        labelDistance.setText("Дальність");

        JLabel labelDeltaA = new JLabel();
        labelDeltaA.setText("Відх від N");

        JLabel labelDeltaB = new JLabel();
        labelDeltaB.setText("Відх від вертик");

        JLabel labelDeltaC = new JLabel();
        labelDeltaC.setText("Відх від гориз");

        JButton gpsButton = new JButton("Отримати дані");
        gpsButton.addActionListener(e -> {
            SwingWorker gpsWorker = new SwingWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    ComPortReaderImpl reader = new ComPortReaderImpl();
                    int[] data = reader.readData();
                    if (data != null){
                        reader.getCoordinates(data);
                    }
                    lat.setText(reader.getLatitude());
                    longt.setText(reader.getLongitude());
                    deltaB.setText(reader.getPitch());
                    deltaC.setText(reader.getRoll());
                    return null;
                }
            };
            gpsWorker.execute();

            SwingWorker worker = new SwingWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    try {
                        if(moveRequest.isMoveAround()){
                            status.start();
                            double angle1 = Double.parseDouble(status.getAzimuth()) - 180;
                            String res = "";
                            res+= angle1;
                            azim.setText(res);
                            double angle2 = 180 - Double.parseDouble(status.getElevation());
                            String res2 = "";
                            res2+=angle2;
                            elev.setText(res2);
                        } else {
                            status.start();
                            azim.setText(status.getAzimuth());
                            elev.setText(status.getElevation());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            worker.execute();
        });

        JButton clear = new JButton("Очистити всі поля");
        clear.addActionListener(e -> {
            lat.setText("");
            longt.setText("");
            azim.setText("");
            elev.setText("");
            dist.setText("");
            deltaA.setText("");
            deltaB.setText("");
            deltaC.setText("");
        });


        JButton button1 =new JButton("Захват");
        button1.addActionListener(e -> {
            double latitude = Double.parseDouble(lat.getText());
            double longtitude = Double.parseDouble(longt.getText());
            double azimuth = Double.parseDouble(azim.getText());
            double distance = Double.parseDouble(dist.getText());
            double height = Double.parseDouble(h.getText());
            lat.setText("");
            longt.setText("");
            azim.setText("");
            elev.setText("");
            dist.setText("");
            deltaA.setText("");
            deltaB.setText("");
            deltaC.setText("");

            AimCalculatorImpl calculator = new AimCalculatorImpl();
            calculator.setHeight(height);
            calculator.calcCoordinate(latitude, longtitude, azimuth, distance);
            Aim aim = calculator.getAim();

            BufferedImage bi = mediaPlayer.getSnapshot();
            try {
                if(lastAim != null){
                    File image = new File("img/" + (lastAim.getId()+1) + ".png");
                    ImageIO.write(bi, "png", image);
                    aim.setPath(image.getPath());
                }

            } catch (IOException ex) {

                ex.printStackTrace();
            }


            setLastAim(aim);
            String[] text = {String.valueOf(aim.getLatitudeck42()), String.valueOf(aim.getLongitudeck42())};
            overlay.setOverlayText(text);

            service.createAim(aim);


            model.setBeans(service.getAllAim());
            table.revalidate();
            table.removeAll();

            table.setModel(model);


            overlay.revalidate();
            overlay.removeAll();
            overlay.repaint();
        });

        /**
         * this button give delta-angles
         * for calibrations cameras moving surface
         */
        JButton kalibrButton = new JButton("Калібровка");


        kalibrButton.addActionListener(e -> {
            SwingWorker worker = new SwingWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    Request request = new Request();
                    request.start();

                    int elevation = 10 * Integer.parseInt(deltaB.getText());
                    double azimuth = 10 * Double.parseDouble(request.getAzimuth());

                    calibration.setDeltaA((double) elevation);

                    boolean isMoveAround = moveRequest.isMoveAround();

                    boolean isChanged = false;

                    if (elevation > 900 && !isMoveAround && !isChanged) {
                        azimuth += 1800;

                        moveRequest.setElevation(elevation / 10);
                        moveRequest.setAzimuth((int) azimuth / 10);
                        elevation = 2 * 900 - elevation;
                        moveRequest.setIsMoveAround(true);
                        isChanged = true;
                    }
                    if (elevation > 900 && isMoveAround && !isChanged) {
                        elevation = 2 * 900 - elevation;
                        isChanged = true;
                    }
                    if (elevation < 900 && isMoveAround && !isChanged) {
                        azimuth -= 1800;
                        moveRequest.setIsMoveAround(false);
                        isChanged = true;
                    }


                    System.out.println(request.getAzimuth());
                    moveRequest.setRequest(elevation, azimuth);
                    moveRequest.start();
                    return null;
                }
            };
            worker.execute();
        });



        JPanel panel2 = new JPanel();
        SpringLayout layout = new SpringLayout();
        panel2.setLayout(layout);

        panel2.add(labelDistance);
        layout.putConstraint(SpringLayout.WEST, labelDistance, 2, SpringLayout.WEST, panel2);
        layout.putConstraint(SpringLayout.NORTH, labelDistance, 2, SpringLayout.NORTH, panel2);

        panel2.add(dist);
        layout.putConstraint(SpringLayout.EAST, dist, 1, SpringLayout.EAST, panel2);
        layout.putConstraint(SpringLayout.NORTH, dist, 2, SpringLayout.NORTH, panel2);

        panel2.add(labelLatitude);
        layout.putConstraint(SpringLayout.WEST, labelLatitude, 2, SpringLayout.WEST, panel2);
        layout.putConstraint(SpringLayout.NORTH, labelLatitude, 22, SpringLayout.NORTH, panel2);

        panel2.add(lat);
        layout.putConstraint(SpringLayout.EAST, lat, 1, SpringLayout.EAST, panel2);
        layout.putConstraint(SpringLayout.NORTH, lat, 22, SpringLayout.NORTH, panel2);

        panel2.add(labelLongtitude);
        layout.putConstraint(SpringLayout.WEST, labelLongtitude, 2, SpringLayout.WEST, panel2);
        layout.putConstraint(SpringLayout.NORTH, labelLongtitude, 42, SpringLayout.NORTH, panel2);

        panel2.add(longt);
        layout.putConstraint(SpringLayout.EAST, longt, 1, SpringLayout.EAST, panel2);
        layout.putConstraint(SpringLayout.NORTH, longt, 42, SpringLayout.NORTH, panel2);

        panel2.add(labelHeight);
        layout.putConstraint(SpringLayout.WEST, labelHeight, 2, SpringLayout.WEST, panel2);
        layout.putConstraint(SpringLayout.NORTH, labelHeight, 62, SpringLayout.NORTH, panel2);

        panel2.add(h);
        layout.putConstraint(SpringLayout.EAST, h, 1, SpringLayout.EAST, panel2);
        layout.putConstraint(SpringLayout.NORTH, h, 62, SpringLayout.NORTH, panel2);

        panel2.add(labelAzimuth);
        layout.putConstraint(SpringLayout.WEST, labelAzimuth, 2, SpringLayout.WEST, panel2);
        layout.putConstraint(SpringLayout.NORTH, labelAzimuth, 82, SpringLayout.NORTH, panel2);

        panel2.add(azim);
        layout.putConstraint(SpringLayout.EAST, azim, 1, SpringLayout.EAST, panel2);
        layout.putConstraint(SpringLayout.NORTH, azim, 82, SpringLayout.NORTH, panel2);

        panel2.add(labelElevation);
        layout.putConstraint(SpringLayout.WEST, labelElevation, 2, SpringLayout.WEST, panel2);
        layout.putConstraint(SpringLayout.NORTH, labelElevation, 102, SpringLayout.NORTH, panel2);

        panel2.add(elev);
        layout.putConstraint(SpringLayout.EAST, elev, 1, SpringLayout.EAST, panel2);
        layout.putConstraint(SpringLayout.NORTH, elev, 102, SpringLayout.NORTH, panel2);

        panel2.add(labelDeltaA);
        layout.putConstraint(SpringLayout.WEST, labelDeltaA, 2, SpringLayout.WEST, panel2);
        layout.putConstraint(SpringLayout.NORTH, labelDeltaA, 122, SpringLayout.NORTH, panel2);

        panel2.add(deltaA);
        layout.putConstraint(SpringLayout.EAST, deltaA, 1, SpringLayout.EAST, panel2);
        layout.putConstraint(SpringLayout.NORTH, deltaA, 122, SpringLayout.NORTH, panel2);

        panel2.add(labelDeltaB);
        layout.putConstraint(SpringLayout.WEST, labelDeltaB, 2, SpringLayout.WEST, panel2);
        layout.putConstraint(SpringLayout.NORTH, labelDeltaB, 142, SpringLayout.NORTH, panel2);

        panel2.add(deltaB);
        layout.putConstraint(SpringLayout.EAST, deltaB, 1, SpringLayout.EAST, panel2);
        layout.putConstraint(SpringLayout.NORTH, deltaB, 142, SpringLayout.NORTH, panel2);

        panel2.add(labelDeltaC);
        layout.putConstraint(SpringLayout.WEST, labelDeltaC, 2, SpringLayout.WEST, panel2);
        layout.putConstraint(SpringLayout.NORTH, labelDeltaC, 162, SpringLayout.NORTH, panel2);

        panel2.add(deltaC);
        layout.putConstraint(SpringLayout.EAST, deltaC, 1, SpringLayout.EAST, panel2);
        layout.putConstraint(SpringLayout.NORTH, deltaC, 162, SpringLayout.NORTH, panel2);

        Dimension butSize = panel.getSize();
        gpsButton.setPreferredSize(new Dimension(butSize.width-10, 30));
        button1.setPreferredSize(new Dimension(butSize.width-10, 30));
        clear.setPreferredSize(new Dimension(butSize.width-10, 30));
        kalibrButton.setPreferredSize(new Dimension(butSize.width-10, 30));


        panel2.add(gpsButton);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, gpsButton, 0, SpringLayout.HORIZONTAL_CENTER, panel2);
        layout.putConstraint(SpringLayout.NORTH, gpsButton, 195, SpringLayout.NORTH, panel2);


        panel2.add(button1);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, button1, 0, SpringLayout.HORIZONTAL_CENTER, panel2);
        layout.putConstraint(SpringLayout.NORTH, button1, 230, SpringLayout.NORTH, panel2);

        panel2.add(clear);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, clear, 0, SpringLayout.HORIZONTAL_CENTER, panel2);
        layout.putConstraint(SpringLayout.NORTH, clear, 265, SpringLayout.NORTH, panel2);


        panel2.add(kalibrButton);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, kalibrButton, 0, SpringLayout.HORIZONTAL_CENTER, panel2);
        layout.putConstraint(SpringLayout.NORTH, kalibrButton, 300, SpringLayout.NORTH, panel2);

        panel.add(scrollPane);
        panel.add(panel2);


//        canvas.addComponentListener(new ComponentListener() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//
//                double size = e.getComponent().getWidth();
//                if (size > 704) {
//                    ResizeRequest request = new ResizeRequest(1280, 720);
//                    request.start();
//                }
//                if (size < 705 & size > 352) {
//                    ResizeRequest request = new ResizeRequest(704, 576);
//                    request.start();
//                }
//                if(size < 353 & size > 176){
//                    ResizeRequest request = new ResizeRequest(352, 288);
//                    request.start();
//                }
//                if (size < 177){
//                    ResizeRequest request = new ResizeRequest(176, 144);
//                    request.start();
//
//
//                }
//
//
//                canvas.repaint();
//
//            }
//
//            @Override
//            public void componentMoved(ComponentEvent e) {
//
//            }
//
//            @Override
//            public void componentShown(ComponentEvent e) {
//
//            }
//
//            @Override
//            public void componentHidden(ComponentEvent e) {
//
//            }
//        });

//        JButton button = new JButton("Створити ціль");
//        frame.pack();
//        frame.add(panel, BorderLayout.WEST);
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlBackgroundFon(mediaPlayer,dimension);
            }
        });

        JButton showPanel = new BasicArrowButton(1);
        showPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.add(panel, BorderLayout.WEST);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        frame.add(showPanel, BorderLayout.WEST);

        timer.start();




    }

    public void controlBackgroundFon(EmbeddedMediaPlayer mediaPlayer, Dimension dimension){

        BufferedImage image = mediaPlayer.getSnapshot();
        int w = (int)dimension.getWidth()/6;
        int h = (int)dimension.getHeight()/6;

        int[] dataBuff = image.getRGB((int)(dimension.getWidth()/2-100), (int)(dimension.getHeight()/2-100), w, h, null, 0, w);
        Color color = new Color(dataBuff[100]);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        String hexRed = Integer.toHexString(red);
        String hexGreen = Integer.toHexString(green);
        String hexBlue = Integer.toHexString(blue);
        String hexColor = hexRed + hexGreen + hexBlue;
        int color1 = Integer.parseInt(hexColor,16);
        overlay.setColorValue(color1);
    }


}
