package cam.aim.rotate;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;

/**
 * Created by victor on 17.04.15.
 * this class calculate cam-coordinates, when cam tilt
 */
public class RotateSurface {

    private double tiltX;//show value of tilting to oX axis
    private double tiltY;//show value of tilting to oY axis

    private double xCoordinate;
    private double yCoordinate;
    private double zCoordinate;

    public RotateSurface() {

        this.xCoordinate = 1;
        this.yCoordinate = 0;
        this.zCoordinate = 0;

    }

    public static void main(String[] args) {
        RotateSurface surface = new RotateSurface();
        surface.setTiltX(45);
        surface.setTiltY(0);



        double[] coordinates = surface.rotateAroundOy(surface.getTiltX());

        for (double el: coordinates){

            System.out.println((float)el);
        }

        System.out.println("///");
        //next step

//        double[][] coord = new double[360][2];

        String elevation = new String();
        String azimuth = new String();

        String x = new String();
        String y = new String();
        String z = new String();

        DecimalFormatSymbols s = new DecimalFormatSymbols();
        s.setDecimalSeparator('.');
        DecimalFormat f = new DecimalFormat("#,##0.00", s);

        for(int i=0; i < 360; i++){
            double [] curCoord = surface.calculateIdealCoordinates(90, i);
            surface.setxCoordinate(curCoord[0]);
            surface.setyCoordinate(curCoord[1]);
            surface.setzCoordinate(curCoord[2]);

            System.out.println("ідеальний азимут " + i);


            double[] newCoord1 = surface.rotateAroundOx(5);
            surface.setxCoordinate(newCoord1[0]);
            surface.setyCoordinate(newCoord1[1]);
            surface.setzCoordinate(newCoord1[2]);

            double[] newCoord = surface.rotateAroundOy(7);
            double[] spherical = surface.fromDecToSpherical(newCoord);

//            coord[i][0] = spherical[0];
//            coord[i][1] = spherical[1];
//            elevation+= f.format(spherical[0]);
//            elevation +="\n";
//            azimuth +=f.format(spherical[1]);
//            azimuth +="\n";
            x+= f.format(newCoord[0]);
            x +="\n";
            y +=f.format(newCoord[1]);
            y +="\n";
            z +=f.format(newCoord[2]);
            z +="\n";

            System.out.println("Elevation - " + f.format(spherical[0]));
            System.out.println("Azimuth - " + spherical[1]);
            System.out.println();

        }

        File file1 = new File("x.txt");
        File file2 = new File("y.txt");
        File file3 = new File("z.txt");

        try {
            //проверяем, что если файл не существует то создаем его
            if(!file1.exists()  && !file2.exists() && !file3.exists() ){
                file1.createNewFile();
                file2.createNewFile();
                file3.createNewFile();
            }

            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out1 = new PrintWriter(file1.getAbsoluteFile());

            PrintWriter out2 = new PrintWriter(file2.getAbsoluteFile());

            PrintWriter out3 = new PrintWriter(file3.getAbsoluteFile());
            try {
                //Записываем текст у файл
                out1.print(x);
                out2.print(y);
                out3.print(z);

            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out1.close();
                out2.close();
                out3.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     *
     * @return coordinates in format - X, Y, Z
     */
    public double[] calculateIdealCoordinates(double elevation, double azimuth){
        elevation = Math.toRadians(elevation);
        azimuth = Math.toRadians(azimuth);
        double x = Math.cos(azimuth) * Math.sin(elevation);
        double y = Math.sin(elevation) * Math.sin(azimuth);
        double z = Math.cos(elevation);
        return new double[]{x, y, z};

    }


    /**
     *
     * @param coordinates - x,y,z point coordinates
     * @return coordinates in spherical system
     */
    public double[] fromDecToSpherical(double[] coordinates){

        if((coordinates[0]>=0 && coordinates[1]>=0) || (coordinates[0]<=0 && coordinates[1]>=0)){
            double elevation = Math.toDegrees(Math.acos(coordinates[2]));
            double azimuth = Math.toDegrees(Math.acos(coordinates[0] / (Math.sqrt(1 - coordinates[2] * coordinates[2]))));
            return new double[]{elevation, azimuth};
        }

        if((coordinates[0]<0 && coordinates[1]<=0) || ( coordinates[0]>0 && coordinates[1]<=0 )){
            double elevation = Math.toDegrees(Math.acos(coordinates[2]));
            double azimuth = 360 - Math.toDegrees(Math.acos(coordinates[0] / (Math.sqrt(1 - coordinates[2] * coordinates[2]))));
            return new double[]{elevation, azimuth};
        }
//        if( coordinates[0]>=0 && coordinates[1]<=0){
//
//        }
        return null;


    }


    /**
     *
     *@param angle - Degrees
     * @return coordinates after rotation around oX axis
     */
    public double[] rotateAroundOx(double angle){

        angle = Math.toRadians(angle);

        double newX = this.xCoordinate;
        double newY = this.yCoordinate * Math.cos(angle) - this.zCoordinate * Math.sin(angle);
        double newZ = this.yCoordinate * Math.sin(angle) + this.zCoordinate * Math.cos(angle);

        return new double[]{newX, newY, newZ};
    }

    /**
     *
     * @param angle - Degrees
     * @return coordinates after rotation around oY axis
     */

    public double[] rotateAroundOy(double angle){

        angle = Math.toRadians(angle);

        double newX = this.xCoordinate * Math.cos(angle) + this.zCoordinate * Math.sin(angle);
        double newY = this.yCoordinate ;
        double newZ = -this.xCoordinate * Math.sin(angle) + this.zCoordinate * Math.cos(angle);

        return new double[]{newX, newY, newZ};
    }

    public double getTiltX() {
        return tiltX;
    }

    public void setTiltX(double tiltX) {
        this.tiltX = tiltX;
    }

    public double getTiltY() {
        return tiltY;
    }

    public void setTiltY(double tiltY) {
        this.tiltY = tiltY;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public double getzCoordinate() {
        return zCoordinate;
    }

    public void setzCoordinate(double zCoordinate) {
        this.zCoordinate = zCoordinate;
    }
}
