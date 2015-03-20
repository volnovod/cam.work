package cam.aim.view;

import jssc.*;

import java.util.Arrays;

public class ComTest {

//  public static void main(String[] args) {
//        SerialPort serialPort = new SerialPort("/dev/ttyUSB0");
//        try {
//            serialPort.openPort();//Open serial port
//            serialPort.setParams(SerialPort.BAUDRATE_9600,
//                    SerialPort.DATABITS_8,
//                    SerialPort.STOPBITS_1,
//                    SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
//            serialPort.writeBytes("This is a test string \n".getBytes());
//            serialPort.closePort();//Close serial port
//        }
//        catch (SerialPortException ex) {
//            System.out.println(ex);
//        }
//    }
public static void main(String[] args) {
//    SerialPort serialPort = new SerialPort("/dev/ttyUSB0");
//    try {
//        serialPort.openPort();//Open serial port
//        serialPort.setParams(115200, 8, 1, 0);//Set params.
//        byte[] buffer = serialPort.readBytes(30);//Read 10 bytes from serial port
//        serialPort.closePort();//Close serial port
//        for(byte el : buffer){
//            System.out.print((char)el);
//        }
//    }
//    catch (SerialPortException ex) {
//        System.out.println(ex);
//    }

    String[] portNames = SerialPortList.getPortNames();
    for(int i = 0; i < portNames.length; i++){
        System.out.println(portNames[i]);
    }
}
}