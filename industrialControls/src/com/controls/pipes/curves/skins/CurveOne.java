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
public class CurveOne extends SkinBase<Curve> implements Skin<Curve>, Drawer {

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

    public CurveOne(Curve control, CurveDirection direction) {
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
            case RIGHT_BOTTOM:
                break;
            case BOTTOM_LEFT:
                double endX = START_X;
                double endY = START_Y;

                double c3 = H / 8;

                double x = (START_X + (H / 8) + (0.60 * (W - (H / 8))) + (0.40 * (W - (H / 8))) - T) - (T * EDGE);
                double x2 = START_X + c3 + (0.60 * (W - c3)) + (0.40 * (W - c3)) - T - (T * EDGE);
                START_X = endX - c3 - (0.60 * (W - c3)) - (0.40 * (W - c3)) + T + (T * EDGE);
                //System.out.println("x= " + x);
                //System.out.println("x2= " + x2);

                //double y = ((START_Y + (EDGE * T) / 2) + (2 * T) + (H - (((START_Y + (EDGE * T) / 2) + (2 * T)) - START_Y - (H / 8)))) + (H / 8);
                double c1 = (EDGE * T) / 2;
                double c2 = 2 * T;

                START_Y = endY - c1 - c2 - H + c1 + c2 - 2 * c3;
                //double y2 = c1 + c2 + H - c1 - c2 + START_Y + 2 * c3;
                //System.out.println("y= " + y);
                //System.out.println("y2= " + y2);
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
        neck1.getElements().add(new LineTo(START_X, START_Y + (AV * T)));
        neck1.getElements().add(new QuadCurveTo(START_X + (H / 8) / 2, START_Y + (AV * T) - (EDGE * T) / 4, START_X + (H / 8), START_Y + (AV * T) - (EDGE * T) / 2));
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new QuadCurveTo(START_X + (H / 8) / 2, START_Y + (EDGE * T) / 4, START_X + (H / 8), START_Y + (EDGE * T) / 2));
        neck1.getElements().add(new LineTo(START_X + (H / 8), START_Y + (AV * T) - (EDGE * T) / 2));
        LinearGradient neck1Gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.REFLECT, new Stop(0, Color.web("#B6B6B6")), new Stop(1, Color.web("#727272")));
        //neck1.setFill(Color.ROSYBROWN);
        neck1.setFill(neck1Gradient);
        neck1.setStroke(Color.web("#727272"));

        double restanteX = W - (H / 8);
        double d = 0.60 * restanteX;
        double a = 0.40 * restanteX;
        double m = d * 0.9;

        Path body = new Path();
        body.getElements().add(new MoveTo(START_X + (H / 8), START_Y + (EDGE * T) / 2));// upNeckPoint
        body.getElements().add(new LineTo(START_X + (H / 8), START_Y + (AV * T) - (EDGE * T) / 2)); //bottom neckPoint
        body.getElements().add(new LineTo(START_X + (H / 8) + m, START_Y + (AV * T) - (EDGE * T) / 2));

        double mid = ((START_Y + (EDGE * T) / 2) + (2 * T)) - (START_Y + (AV * T) - (EDGE * T) / 2);

        body.getElements().add(new QuadCurveTo(START_X + (H / 8) + d + a - T, (START_Y + (AV * T) - (EDGE * T) / 2) + (mid / 6), START_X + (H / 8) + d + a - T, (START_Y + (EDGE * T) / 2) + (2.4 * T)));

        double alpha = (START_Y + (EDGE * T) / 2) + (2.4 * T);
        double freeLeft = H - (alpha - START_Y - (H / 8));
        double bottomNeckXLeft = START_X + (H / 8) + d + a - T;
        double bottomNeckYLeft = (START_Y + (EDGE * T) / 2) + (2.4 * T) + freeLeft;

        body.getElements().add(new LineTo(bottomNeckXLeft, bottomNeckYLeft));//

        double teta = (START_Y + (EDGE * T) / 2) + (2 * T);
        double freeRight = H - (teta - START_Y - (H / 8));
        double bottomNeckXRight = START_X + (H / 8) + d + a;
        double bottomNeckYRight = (START_Y + (EDGE * T) / 2) + (2 * T) + freeRight;

        body.getElements().add(new LineTo(bottomNeckXRight, bottomNeckYRight));
        body.getElements().add(new LineTo(START_X + (H / 8) + d + a, (START_Y + (EDGE * T) / 2) + (2 * T)));

        double midA = (((START_Y + (EDGE * T) / 2) + (2 * T)) - (START_Y + (EDGE * T) / 2)) / 2; // buena tecnica, calcular distancia media en y

        body.getElements().add(new QuadCurveTo(START_X + (H / 8) + d * 1.6, (START_Y + (EDGE * T) / 2) - (midA / 6), START_X + (H / 8) + d, START_Y + (EDGE * T) / 2));// A
        body.getElements().add(new LineTo(START_X + (H / 8), START_Y + (EDGE * T) / 2));
        LinearGradient bodyGradient = new LinearGradient(1, 1, 0, 0, true, CycleMethod.REFLECT, new Stop(0, Color.web("#727272")), new Stop(1, Color.web("#727272")), new Stop(0, Color.web("#FFFFFF")), new Stop(1, Color.web("#9E9E9E")), new Stop(1, Color.web("#727272")));
        body.setFill(bodyGradient);
        //body.setStroke(Color.web("#1976D2"));
        body.setStroke(Color.web("#9E9E9E"));
        body.setStrokeWidth(1.3);

        Path neck2 = new Path();
        neck2.getElements().add(new MoveTo(bottomNeckXLeft, bottomNeckYRight));
        neck2.getElements().add(new LineTo(bottomNeckXLeft - (T * EDGE), bottomNeckYLeft + (H / 8)));
        neck2.getElements().add(new LineTo(bottomNeckXRight + (T * EDGE), bottomNeckYRight + (H / 8)));
        neck2.getElements().add(new LineTo(bottomNeckXRight, bottomNeckYRight));
        neck2.getElements().add(new LineTo(bottomNeckXLeft, bottomNeckYRight));
        LinearGradient neck2Gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.REFLECT, new Stop(0, Color.web("#B6B6B6")), new Stop(1, Color.web("#727272")));
        neck2.setFill(neck2Gradient);
        neck2.setStroke(Color.web("#727272"));

        Circle dot = new Circle(teta, teta, 7, Color.YELLOW);

        root.getChildren().addAll(neck1, body, neck2, dot);
        this.getChildren().setAll(root);
    }

    @Override
    public void redraw() {
        this.getChildren().clear();
        initGraphics();
    }

}
