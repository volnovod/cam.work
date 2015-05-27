package cam.aim.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by maxymkasyanov on 30.01.15.
 */
public class MainFrame {

    private final String IP_CAMERA_SNAPSHOT_URL = "http://192.168.2.232/snap.jpg?JpegCam=1&JpegDomain=8961747";

    private JToolBar toolBar = new JToolBar();

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

    public MainFrame() {

        JFrame frame = new JFrame();

//        ControlPanel panel = new ControlPanel();
//
//        executorService.scheduleAtFixedRate(() -> capture(frame), 0, 1, TimeUnit.MICROSECONDS);
//        executorService.scheduleAtFixedRate(() -> capture(frame), 1, 2, TimeUnit.MICROSECONDS);
//        executorService.scheduleAtFixedRate(() -> capture(frame), 2, 1, TimeUnit.MICROSECONDS);
//        executorService.scheduleAtFixedRate(() -> capture(frame), 3, 2, TimeUnit.MICROSECONDS);
//        executorService.scheduleAtFixedRate(() -> capture(frame), 4, 1, TimeUnit.MICROSECONDS);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(150,150);
//        frame.add(panel);
        frame.setVisible(true);



    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
    }

    private void capture(JFrame frame) {
        try {
            frame.removeAll();
            HttpRequest request = new HttpRequest(IP_CAMERA_SNAPSHOT_URL);
            byte[] bytes = request.makeGet();
            BufferedImage myPicture = ImageIO.read(new ByteArrayInputStream(bytes));
            Path img = Paths.get("printer_PNG7756.png");
            BufferedImage png = ImageIO.read(new ByteArrayInputStream(Files.readAllBytes(img)));
            BufferedImage ready = overlayImages(myPicture, png);
            frame.getGraphics().drawImage(ready, 0, 0, frame);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to overlay Images
     *
     * @param bgImage --> The background Image
     * @param fgImage --> The foreground Image
     * @return --> overlayed image (fgImage over bgImage)
     */
    public static BufferedImage overlayImages(BufferedImage bgImage,
                                              BufferedImage fgImage) {

        /**
         * Doing some preliminary validations.
         * Foreground image height cannot be greater than background image height.
         * Foreground image width cannot be greater than background image width.
         *
         * returning a null value if such condition exists.
         */
        if (fgImage.getHeight() > bgImage.getHeight()
                || fgImage.getWidth() > fgImage.getWidth()) {
            JOptionPane.showMessageDialog(null,
                    "Foreground Image Is Bigger In One or Both Dimensions"
                            + "\nCannot proceed with overlay."
                            + "\n\n Please use smaller Image for foreground");
            return null;
        }

        /**Create a Graphics  from the background image**/
        Graphics2D g = bgImage.createGraphics();
        /**Set Antialias Rendering**/
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        /**
         * Draw background image at location (0,0)
         * You can change the (x,y) value as required
         */
        g.drawImage(bgImage, 0, 0, null);

        /**
         * Draw foreground image at location (0,0)
         * Change (x,y) value as required.
         */
        g.drawImage(fgImage, 0, 0, null);

        g.dispose();
        return bgImage;
    }


}
