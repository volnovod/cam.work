package cam.aim.calc;

import cam.aim.httprequests.MoveRequest;
import cam.aim.rotate.RotateSurfaceImpl;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by Виктор on 03.04.2015.
 */
public class JavaFXExample extends Application {

    private int i=0;

    public int getI() {
        return i;
    }

    public void setI() {
        this.i = i + 2 ;
        if(i>360){
            i=0;
        }
    }

    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Example");
        MoveRequest moveRequest = new MoveRequest();

        RotateSurfaceImpl surface = new RotateSurfaceImpl();
        surface.setTiltX(15);
        surface.setTiltY(0);



        button = new Button();
        button.setText("Click me");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                double[] curCoord = surface.calculateIdealCoordinates(90, getI());
                surface.setxCoordinate(curCoord[0]);
                surface.setyCoordinate(curCoord[1]);
                surface.setzCoordinate(curCoord[2]);

                double[] coord = surface.rotateAroundOx(surface.getTiltX());
                double[] angles = surface.fromDecToSpherical(coord);
                int azimuth = (int) angles[1];
                if (azimuth == 360) {
                    azimuth = 0;
                }
                moveRequest.setRequest((90 - (int) angles[0])*10, azimuth * 10);
                moveRequest.start();
                setI();
            }
        });

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
