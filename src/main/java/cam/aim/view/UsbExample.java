package cam.aim.view;

import org.usb4java.*;

/**
 * Created by victor on 15.04.15.
 */
public class UsbExample {

    public static void main(String[] args) {
        UsbExample usbExample = new UsbExample();
    }

    public UsbExample() {
        Context context = new Context();
        int result = LibUsb.init(context);
        if (result != LibUsb.SUCCESS) throw new LibUsbException("Unable to initialize libusb.", result);
        this.findDevice();
        LibUsb.exit(context);


    }

//    libusb device 0x7f1ba01c4ff0
//    libusb device 0x7f1ba01c3000
//    libusb device 0x7f1ba01c2f60
//    libusb device 0x7f1ba01c32a0
//    libusb device 0x7f1ba01c31e0
//    libusb device 0x7f1ba01c3120


    public void findDevice()
    {
        // Read the USB device list
        DeviceList list = new DeviceList();
        int result = LibUsb.getDeviceList(null, list);
        if (result < 0) throw new LibUsbException("Unable to get device list", result);

        try
        {
            // Iterate over all devices and scan for the right one
            for (Device device: list)
            {
                DeviceDescriptor descriptor = new DeviceDescriptor();
                result = LibUsb.getDeviceDescriptor(device, descriptor);
                System.out.println(descriptor.toString());
                if (result != LibUsb.SUCCESS) throw new LibUsbException("Unable to read device descriptor", result);
//                if (descriptor.idVendor() == vendorId && descriptor.idProduct() == productId) return device;
            }
        }
        finally
        {
            // Ensure the allocated device list is freed
            LibUsb.freeDeviceList(list, true);
        }

        // Device not found
//        return null;
    }


}
