package cam.aim.view;

import cam.aim.calc.WGStoCK42;
import cam.aim.calc.WGStoCK42Impl;
import cam.aim.calibration.Calibration;
import cam.aim.httprequests.HomePositionRequest;
import cam.aim.httprequests.MoveRequest;
import cam.aim.httprequests.Request;
import cam.aim.calc.AimCalculatorImpl;
import cam.aim.comport.ComPortReader;
import cam.aim.domain.Aim;
import cam.aim.forresize.ResizeRequest;
import cam.aim.service.AimService;
import cam.aim.service.AimServiceImpl;
import com.sun.jna.NativeLibrary;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by victor on 12.02.15.
 */
public class OverlayTest extends JFrame  {

    private EmbeddedMediaPlayer mediaPlayer;
    private MediaPlayerFactory playerFactory;
    private static OverlayWindow overlay;
    private MoveRequest moveRequest;
    private Calibration calibration;
    private Aim lastAim;
    private WGStoCK42 wgStoCK42;

    public Aim getLastAim() {
        return lastAim;
    }

    public void setLastAim(Aim lastAim) {
        this.lastAim = lastAim;
    }

//        private final String url = "rtsp://192.168.1.201//rtsp_tunnel";
//private final String url = "rtsp://admin:12345@192.168.1.64//rtsp_tunnel ";
//    private final String url = "rtsp://admin:12345@192.168.1.64//ISAPI/Streaming/channels/3/rtsp_tunnel";
    private final String url = "v4l2:///dev/video0";
    private final String streamOption=":network-caching=500";

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

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    Rectangle rectangle;

    public static void main(String[] args) {
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "/usr/lib/");
        SwingUtilities.invokeLater(OverlayTest::new);


    }

    public OverlayTest() {

        HomePositionRequest homePositionRequest = new HomePositionRequest();
//        homePositionRequest.start();

        wgStoCK42 = new WGStoCK42Impl();

        moveRequest = new MoveRequest();

        calibration = new Calibration();


        JFrame frame = new JFrame("cam.aim.view.Overlay");
        overlay = new OverlayWindow(frame);

        Canvas canvas = new Canvas();

        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setBackground(Color.BLACK);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setLayout(new BorderLayout());
        AimService service = new ClassPathXmlApplicationContext("transactionalContext.xml").getBean("service", AimServiceImpl.class);
        java.util.List<Aim> list = service.getAllAim();

        frame.add(canvas);


        frame.setVisible(true);




        playerFactory = new MediaPlayerFactory("-vvv");
        mediaPlayer = playerFactory.newEmbeddedMediaPlayer();
        mediaPlayer.setVideoSurface(playerFactory.newVideoSurface(canvas));
        mediaPlayer.setOverlay(overlay);
        mediaPlayer.enableOverlay(true);
        mediaPlayer.playMedia(url, streamOption);


        MyTableModel model = new MyTableModel();
        model.setBeans(list);
        if (model.getBeans().size()!=0){

            this.setLastAim(model.getBeans().get(model.getBeans().size()-1));

            overlay.setOverlayText(lastAim.toString());
        }

        JTable table = new JTable(model);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int xpos=(int)dimension.getWidth()/8;
        int ypos=(int)dimension.getHeight()/3;

        table.setPreferredScrollableViewportSize(new Dimension(xpos, ypos));


        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setSize(new Dimension((int) dimension.getWidth() / 8, (int) dimension.getHeight()));

        JTextField h = new JTextField();
        JTextField lat = new JTextField();
        JTextField longt= new JTextField();
        JTextField azim= new JTextField();
        JTextField elev = new JTextField();
        JTextField dist= new JTextField();
        JTextField deltaA = new JTextField();// deviation to north
        JTextField deltaB = new JTextField();// deviation to vertical
        JTextField deltaC = new JTextField();// deviation to horizontal

        JLabel labelHeight = new JLabel();
        labelHeight.setText("Висота");

        JLabel labelLatitude = new JLabel();
        labelLatitude.setText("Широта спостерігання");

        JLabel labelLongtitude = new JLabel();
        labelLongtitude.setText("Довгота спостерігання");

        JLabel labelAzimuth = new JLabel();
        labelAzimuth.setText("Азимут");

        JLabel labelElevation = new JLabel();
        labelElevation.setText("Кут місця");

        JLabel labelDistance = new JLabel();
        labelDistance.setText("Відстань до цілі");

        JLabel labelDeltaA = new JLabel();
        labelDeltaA.setText("Відхилення від півночі");

        JLabel labelDeltaB = new JLabel();
        labelDeltaB.setText("Відхилення від вертикалі");

        JLabel labelDeltaC = new JLabel();
        labelDeltaC.setText("Відхилення від горизонталі");

        JButton gpsButton = new JButton("Отримати дані");
        gpsButton.addActionListener(e -> {
            SwingWorker gpsWorker = new SwingWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    ComPortReader reader = new ComPortReader();
                    int[] data = reader.readData();
                    if (data != null){
                        reader.getCoordinates(data);
                    }
                    lat.setText(reader.getLatitude());
                    longt.setText(reader.getLongtitude());
                    deltaB.setText(reader.getPitch());
                    deltaC.setText(reader.getRoll());
                    return null;
                }
            };
            gpsWorker.execute();

            SwingWorker worker = new SwingWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    Request request = new Request();
                    try {
                        if(moveRequest.isMoveAround()){
                            request.start();
                            double angle1 = Double.parseDouble(request.getAzimuth()) - 180;
                            String res = "";
                            res+= angle1;
                            azim.setText(res);
                            double angle2 = 180 - Double.parseDouble(request.getElevation());
                            String res2 = "";
                            res2+=angle2;
                            elev.setText(res2);
                        } else {
                            request.start();
                            azim.setText(request.getAzimuth());
                            elev.setText(request.getElevation());
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


        JButton button1 =new JButton("Розрахувати та зберегти");
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
                File image = new File("img/" + (lastAim.getId()+1) + ".png");
                ImageIO.write(bi, "png", image);
                aim.setPath(image.getPath());
            } catch (IOException ex) {

                ex.printStackTrace();
            }


            setLastAim(aim);
            overlay.setOverlayText(lastAim.toString());

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


        panel.add(scrollPane);
        panel.add(labelHeight);
        panel.add(h);
        panel.add(labelLatitude);
        panel.add(lat);
        panel.add(labelLongtitude);
        panel.add(longt);
        panel.add(labelAzimuth);
        panel.add(azim);
        panel.add(labelElevation);
        panel.add(elev);
        panel.add(labelDistance);
        panel.add(dist);
        panel.add(labelDeltaA);
        panel.add(deltaA);
        panel.add(labelDeltaB);
        panel.add(deltaB);
        panel.add(labelDeltaC);
        panel.add(deltaC);
        panel.add(gpsButton);
        panel.add(button1);
        panel.add(clear);
        panel.add(kalibrButton);



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
        frame.add(panel, BorderLayout.WEST);




    }



}
