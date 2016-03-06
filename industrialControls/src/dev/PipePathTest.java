/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev;

import com.controls.pipes.CurveElement;
import com.controls.pipes.PipePath;
import com.controls.pipes.RectElement;
import com.controls.util.CurveDirection;
import com.controls.util.RectDirection;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/**
 *
 * @author marcoisaac
 */
public class PipePathTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        ScrollPane pane = new ScrollPane();

        Group root = new Group();

        PipePath path = new PipePath(root, 200, 150);
        path.add(new RectElement(RectDirection.LEFT_RIGHT, 100));
        path.add(new CurveElement(CurveDirection.LEFT_TOP));// anterior: RIGHT_UP
        path.add(new CurveElement(CurveDirection.BOTTOM_RIGHT));//  anterior: LEFT_DOWN
        path.add(new CurveElement(CurveDirection.LEFT_BOTTOM));
        path.add(new CurveElement(CurveDirection.TOP_LEFT));
        path.add(new CurveElement(CurveDirection.RIGHT_BOTTOM));
        path.add(new RectElement(RectDirection.TOP_DOWN, 0));
        path.add(new CurveElement(CurveDirection.TOP_RIGHT));
        path.add(new RectElement(RectDirection.LEFT_RIGHT, 0));
        path.add(new CurveElement(CurveDirection.LEFT_TOP));
        path.add(new CurveElement(CurveDirection.BOTTOM_RIGHT));
        path.add(new CurveElement(CurveDirection.LEFT_BOTTOM));

        pane.setContent(root);

        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
