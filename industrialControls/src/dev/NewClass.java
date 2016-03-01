/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev;

import com.controls.pipes.CurveElement;
import com.controls.pipes.DeviceElement;
import com.controls.pipes.PipePath;
import com.controls.pipes.RectElement;
import com.controls.util.CurveDirection;
import com.controls.util.DeviceType;
import com.controls.util.RectDirection;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author marcoisaac
 */
public class NewClass extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();

        PipePath path = new PipePath(root, 100, 200);
        path.add(new CurveElement(CurveDirection.RIGHT_DOWN));
        path.add(new CurveElement(CurveDirection.LEFT_UP));
        path.add(new CurveElement(CurveDirection.RIGHT_UP));
        path.add(new CurveElement(CurveDirection.LEFT_DOWN));
        path.add(new CurveElement(CurveDirection.RIGHT_DOWN));
        path.add(new CurveElement(CurveDirection.LEFT_UP));
        path.add(new RectElement(RectDirection.LEFT_RIGHT, 100));
        path.add(new CurveElement(CurveDirection.RIGHT_UP));
        path.add(new RectElement(RectDirection.DOWN_TOP, 20));
        //path.add(new TElement());
        path.add(new DeviceElement(RectDirection.DOWN_TOP, DeviceType.WATER_METER));
        path.add(new DeviceElement(RectDirection.DOWN_TOP, DeviceType.COMPUERTA));

        //root.getChildren().addAll(curve); 
        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
