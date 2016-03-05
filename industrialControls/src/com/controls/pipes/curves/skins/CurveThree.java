/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes.curves.skins;

import com.controls.pipes.curves.Curve;
import com.controls.util.CurveDirection;
import com.controls.util.Drawer;
import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;

/**
 * RIGHT-DOWN CURVE
 *
 * @author marcoisaac
 */
public class CurveThree extends SkinBase<Curve> implements Skin<Curve>, Drawer {

    private double START_X = 0;
    private double START_Y = 0;
    private double AV;

    ///
    //private double EDGE = AV - 1;
    private double EDGE;
    private double W; //witdh
    private double H; //height
    private double T; // pipe diameter

    private CurveDirection direction;

    public CurveThree(Curve control, CurveDirection direction) {
        super(control);
        this.direction = direction;
        init();
        initGraphics();
        //registerListeners();
    }

    private void init() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void initGraphics() {

        START_X = getSkinnable().getStartX();
        START_Y = getSkinnable().getStartY();

        W = getSkinnable().getW();
        H = getSkinnable().getH();
        T = getSkinnable().getT();
        AV = getSkinnable().getAV();
        EDGE = getSkinnable().getEDGE();

        switch (direction) {
            case TOP_LEFT:
                //
                double endX = START_X;
                double endY = START_Y;

                double ct = H / 8;

                double x = (((START_X + (H / 8)) + ((W - ((START_X + (H / 8)) - START_X) - T) / 2)) + ((W - ((START_X + (H / 8)) - START_X) - T) / 2)) - (EDGE * T);
                double x2 = START_X + W - T - EDGE * T;

                START_X = endX - W + T + EDGE * T;
                System.out.println("x= " + x);
                System.out.println("x2= " + x2);

                double y = START_Y + (EDGE * T) - (H - ((START_Y + (T * AV)) - (START_Y + (EDGE * T))) - (H / 8)) - (H / 8);
                double y2 = START_Y - H + T * AV;
                START_Y = endY + H - T * AV;

                System.out.println("y= " + y);
                System.out.println("y2= " + y2);
                break;
        }

        Pane root = new Pane();

        if (!(H >= 3 * T)) {
            System.out.println("error 1");
            System.exit(0);
        }

        if (!(W >= 3 * T)) {
            System.out.println("error 2");
            System.exit(0);
        }

        Path neck1 = new Path();
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new LineTo(START_X, START_Y + (T * AV)));
        neck1.getElements().add(new LineTo(START_X + (H / 8), START_Y + (T * AV) - (EDGE * T)));
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new LineTo(START_X + (H / 8), START_Y + (EDGE * T)));
        LinearGradient neck1Gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.REFLECT, new Stop(0, Color.web("#B6B6B6")), new Stop(1, Color.web("#727272")));
        //neck1.setFill(Color.ROSYBROWN);
        neck1.setFill(neck1Gradient);
        neck1.setStroke(Color.web("#727272"));

        double q = START_X + (H / 8);
        double neck1EndY = START_Y + (T * AV) - (EDGE * T);
        double restanteX = W - (q - START_X) - T;
        double z = q + (restanteX / 2);
        double x = z + (restanteX / 2);

        double restanteY = H - ((START_Y + (T * AV)) - (START_Y + (EDGE * T))) - (H / 8);

        neck1.getElements().add(new LineTo(q, neck1EndY));

        Path body = new Path();  //clockWise
        body.getElements().add(new MoveTo(q, neck1EndY)); //bottomLeftCorner
        body.getElements().add(new LineTo(q, START_Y + (EDGE * T)));
        body.getElements().add(new LineTo(z, START_Y + (EDGE * T)));

        double midLeft = (START_Y + (EDGE * T)) - (START_Y + (EDGE * T) - (restanteY / 2));

        body.getElements().add(new QuadCurveTo(x, START_Y + (EDGE * T) - (midLeft / 6), x, START_Y + (EDGE * T) - (restanteY / 2))); //X
        body.getElements().add(new LineTo(x, START_Y + (EDGE * T) - (restanteY)));//LeftD  UpperLeftCorner
        body.getElements().add(new LineTo(x + T, START_Y + (EDGE * T) - (restanteY)));
        body.getElements().add(new LineTo(x + T, START_Y + (EDGE * T) - (restanteY / 2)));//Y

        double midRight = neck1EndY - (START_Y + (EDGE * T) - (restanteY / 2));

        double cX = q + (1.4 * restanteX);
        double cY = START_Y + (T * AV) - (EDGE * T);

        body.getElements().add(new QuadCurveTo(cX, cY, z, neck1EndY));
        Circle dot = new Circle(cX, cY, 7, Color.YELLOW);

        body.getElements().add(new MoveTo(q, neck1EndY));
        body.getElements().add(new LineTo(z, neck1EndY));
        LinearGradient bodyGradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.REFLECT, new Stop(0, Color.web("#727272")), new Stop(1, Color.web("#727272")), new Stop(0, Color.web("#FFFFFF")), new Stop(1, Color.web("#727272")));
        body.setFill(bodyGradient);
        //body.setStroke(Color.web("#1976D2"));
        body.setStroke(Color.web("#9E9E9E"));

        Path neck2 = new Path();
        neck2.getElements().add(new MoveTo(x + T, START_Y + (EDGE * T) - (restanteY)));
        neck2.getElements().add(new LineTo(x, START_Y + (EDGE * T) - (restanteY)));
        neck2.getElements().add(new LineTo(x - (EDGE * T), START_Y + (EDGE * T) - (restanteY) - (H / 8)));
        neck2.getElements().add(new MoveTo(x + T, START_Y + (EDGE * T) - (restanteY)));
        neck2.getElements().add(new LineTo(x + T + (EDGE * T), START_Y + (EDGE * T) - (restanteY) - (H / 8)));
        neck2.getElements().add(new LineTo(x - (EDGE * T), START_Y + (EDGE * T) - (restanteY) - (H / 8)));
        LinearGradient neck2Gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.REFLECT, new Stop(0, Color.web("#B6B6B6")), new Stop(1, Color.web("#727272")));
        neck2.setFill(neck2Gradient);
        neck2.setStroke(Color.web("#727272"));

        Circle circle = new Circle(x - (EDGE * T), START_Y + (EDGE * T) - (restanteY) - (H / 8), 7, Color.YELLOWGREEN);
        root.getChildren().addAll(neck1, body, neck2, circle);
        this.getChildren().setAll(root);
    }

    @Override
    public void redraw() {
        this.getChildren().clear();
        initGraphics();
    }

}
