package cam.aim.view;

import org.usb4java.Context;
import org.usb4java.DeviceHandle;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

/**
 * Created by victor on 15.04.15.
 */
public class MouseReader {

    private static final int TIMEOUT = 5000;

    private static final short VENDOR_ID = 0x046d;

    private static final short PRODUCT_ID = (short) 0xc05a;

    public static void main(String[] args) {
        Context context = new Context();
        int result = LibUsb.init(context);
        if (result != LibUsb.SUCCESS)
        {
            throw new LibUsbException("Unable to initialize libusb", result);
        }

        // Open test device (Samsung Galaxy Nexus)
        DeviceHandle handle = LibUsb.openDeviceWithVidPid(context, VENDOR_ID,
                PRODUCT_ID);
        if (handle == null)
        {
            System.err.println("Test device not found.");
            System.exit(1);
        }
        handle.toString();
        LibUsb.exit(context);
    }
}
