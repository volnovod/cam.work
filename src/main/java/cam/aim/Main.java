package cam.aim;

import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.swing.*;

/**
 * Created by maxymkasyanov on 30.01.15.
 */
public class Main {


    public static void main(String[] args) {
 //     SwingUtilities.invokeLater(cam.aim.MainFrame::new);
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "/usr/lib/");
//        SwingUtilities.invokeLater(cam.aim.RTSPPlayer::new);
//        SwingUtilities.invokeLater(cam.aim.CanvasPlayer::new);
    }


}
