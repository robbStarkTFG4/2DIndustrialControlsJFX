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
public class CurveTwo extends SkinBase<Curve> implements Skin<Curve>, Drawer {

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

    public CurveTwo(Curve control, CurveDirection direction) {
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
            case LEFT_TOP:
                break;
            case LEFT_UP_2:
                //
                double endX = START_X;
                double endY = START_Y;

                double x = START_X + T * 2 + (W - ((START_X + T * 2) - START_X - (H / 8))) + (H / 8);
                double x2 = W + START_X + 2 * (H / 8);
                //START_X = endX - 2 * (T * 2);
                START_X = endX - W - 2 * (H / 8);

                // System.out.println("x= " + x);
                //System.out.println("x2= " + x2);
                double y = (START_Y + (H / 8)) + 2 * (4 * H / 8) - T - (EDGE * T);
                double y2 = START_Y + (H / 8) + 2 * (4 * H / 8) - T - (EDGE * T);
                //START_Y = endY - (H / 8) - 2 * (4 * H / 8) + T + (EDGE * T);
                START_Y = endY - (H / 8) - 2 * (4 * H / 8) + T + (EDGE * T);

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
        neck1.getElements().addAll(new MoveTo(START_X, START_Y), new LineTo(START_X + (T * AV), START_Y));
        neck1.getElements().add(new QuadCurveTo(START_X + (T * AV), START_Y + (H / 8) / 2, START_X + T, START_Y + (H / 8))); // right curve
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new QuadCurveTo(START_X, START_Y + (H / 8) / 2, START_X + (1.10 * T * EDGE), START_Y + (H / 8)));
        neck1.getElements().add(new LineTo(START_X + T, START_Y + (H / 8)));
        LinearGradient neck1Gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.REFLECT, new Stop(0, Color.web("#B6B6B6")), new Stop(1, Color.web("#727272")));
        //neck1.setFill(Color.ROSYBROWN);
        neck1.setFill(neck1Gradient);
        neck1.setStroke(Color.web("#727272"));

        Path body = new Path();
        body.getElements().add(new MoveTo(START_X + (1.10 * T * EDGE), START_Y + (H / 8)));//upperLeft clockWise
        body.getElements().add(new LineTo(START_X + T, START_Y + (H / 8)));
        body.getElements().add(new LineTo(START_X + T, START_Y + (H / 8) + (3 * H / 8)));

        double g = START_Y + (H / 8);

        double cX = START_X + 1.1 * T;
        double cY = START_Y + (H / 8) + (5.7 * H / 8);

        body.getElements().add(new QuadCurveTo(cX, cY, START_X + T * 2, g + 2 * (4 * H / 8) - T)); // M
        Circle dot = new Circle(cX, cY, 7, Color.YELLOW);

        double b = START_X + T * 2;
        double restante = W - (b - START_X - (H / 8));
        System.out.println("restante: " + restante);

        body.getElements().add(new LineTo(START_X + T * 2 + restante, g + 2 * (4 * H / 8) - T));
        body.getElements().add(new LineTo(START_X + T * 2 + restante, g + 2 * (4 * H / 8)));// X
        body.getElements().add(new LineTo(START_X + T * 2, g + 2 * (4 * H / 8)));
        body.getElements().add(new QuadCurveTo(START_X + (1.5 * T * EDGE), g + (8 * H / 8), START_X + (1.10 * T * EDGE), g + (4 * H / 8)));// B
        body.getElements().add(new LineTo(START_X + (1.10 * T * EDGE), g));
        LinearGradient bodyGradient = new LinearGradient(1, 1, 0, 0, true, CycleMethod.REFLECT, new Stop(0, Color.web("#727272")), new Stop(1, Color.web("#727272")), new Stop(0, Color.web("#FFFFFF")), new Stop(1, Color.web("#9E9E9E")), new Stop(1, Color.web("#727272")));
        body.setFill(bodyGradient);
        //body.setStroke(Color.web("#1976D2"));
        body.setStroke(Color.web("#9E9E9E"));
        body.setStrokeWidth(1.3);

        Path neck2 = new Path();
        neck2.getElements().add(new MoveTo(START_X + T * 2 + restante, g + 2 * (4 * H / 8) - T));
        neck2.getElements().add(new LineTo(START_X + T * 2 + restante + (H / 8), g + 2 * (4 * H / 8) - T - (EDGE * T)));//ENDPOINT
        neck2.getElements().add(new LineTo(START_X + T * 2 + restante + (H / 8), g + 2 * (4 * H / 8) + (EDGE * T)));
        neck2.getElements().add(new LineTo(START_X + T * 2 + restante, g + 2 * (4 * H / 8)));
        neck2.getElements().add(new LineTo(START_X + T * 2 + restante, g + 2 * (4 * H / 8) - T));
        LinearGradient neck2Gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.REFLECT, new Stop(0, Color.web("#B6B6B6")), new Stop(1, Color.web("#727272")));
        neck2.setFill(neck2Gradient);
        neck2.setStroke(Color.web("#727272"));

        root.getChildren().addAll(neck1, body, neck2);
        this.getChildren().setAll(root);
    }

    @Override
    public void redraw() {
        this.getChildren().clear();
        initGraphics();
    }

}
