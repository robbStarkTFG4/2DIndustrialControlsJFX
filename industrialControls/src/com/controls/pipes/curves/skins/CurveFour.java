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
public class CurveFour extends SkinBase<Curve> implements Skin<Curve>, Drawer {

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

    public CurveFour(Curve control, CurveDirection direction) {
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
            case LEFT_BOTTOM:
                //
                double endX = START_X;
                double endY = START_Y;

                double e = START_X + (EDGE * T);
                double c1 = (3 * T);
                double c2 = (H / 8);

                double x = (e + (3 * T) + (W - ((e + (3 * T)) - START_X - (H / 8)))) + (H / 8);
                double x2 = W + START_X + 2 * c2;
                //System.out.println("x: " + x);
                //System.out.println("x2: " + x2);
                START_X = endX - W - 2 * c2;

                double g1 = (H / 8);

                double y = ((START_Y - (H / 8)) - (3 * (H - (H / 8)) / 4) - (2 * (H - (H / 8)) / 4)) - (EDGE * T);
                double y2 = START_Y - g1 - (3 * (H - g1) / 4) - (2 * (H - g1) / 4) - (EDGE * T);
                START_Y = endY + g1 + (3 * (H - g1) / 4) + (2 * (H - g1) / 4) + (EDGE * T);
                System.out.println("y: " + y);
                System.out.println("y2: " + y2);

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
        neck1.getElements().add(new LineTo(START_X + (T * AV), START_Y));
        neck1.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T), START_Y - (H / 8)));
        neck1.getElements().add(new LineTo(START_X + (EDGE * T), START_Y - (H / 8)));
        neck1.getElements().add(new LineTo(START_X, START_Y));
        neck1.setFill(Color.BURLYWOOD);

        double E = START_X + (EDGE * T);
        double K = START_Y - (H / 8);
        double restanteY = H - (H / 8);

        Path body = new Path();
        body.getElements().add(new MoveTo(E, K));

        body.getElements().add(new LineTo(E, K - (3 * restanteY / 4)));

        double midLeft = (K - (3 * restanteY / 4)) - (K - (3 * restanteY / 4) - (restanteY / 4));

        body.getElements().add(new QuadCurveTo(E, K - (3 * restanteY / 4) - (2 * restanteY / 4), E + (3 * T), K - (3 * restanteY / 4) - (2 * restanteY / 4)));//J

        double restanteX = W - ((E + (3 * T)) - START_X - (H / 8));
        double c = (K - (3 * restanteY / 4) - (2 * restanteY / 4)) + T;
        System.out.println("restanteX: " + restanteX);

        body.getElements().add(new LineTo(E + (3 * T) + restanteX, K - (3 * restanteY / 4) - (2 * restanteY / 4)));
        body.getElements().add(new LineTo(E + (3 * T) + restanteX, c));
        body.getElements().add(new LineTo(E + (3 * T), c));//here
        double neck2StartX = E + (3 * T) + restanteX;
        double neck2StartY = K - (3 * restanteY / 4) - (2 * restanteY / 4);

        double ERight = START_X + (T * AV) - (EDGE * T);
        double f = K - (3 * restanteY / 4) * .75;

        double cX = START_X + (1.50 * T * AV) - (EDGE * T);
        double cY = K - (restanteY) * .95;

        body.getElements().add(new QuadCurveTo(cX, cY, ERight, f));
        Circle dot = new Circle(cX, cY, 7, Color.YELLOW);

        double midRight = f - c;
        body.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T), START_Y - (H / 8)));

        body.setFill(Color.DARKSLATEBLUE);

        Path neck2 = new Path();
        neck2.getElements().add(new MoveTo(neck2StartX, neck2StartY));
        neck2.getElements().add(new LineTo(neck2StartX, c));
        neck2.getElements().add(new LineTo(neck2StartX + (H / 8), c + (EDGE * T)));
        neck2.getElements().add(new MoveTo(neck2StartX, neck2StartY));
        neck2.getElements().add(new LineTo(neck2StartX + (H / 8), neck2StartY - (EDGE * T)));
        neck2.getElements().add(new LineTo(neck2StartX + (H / 8), c + (EDGE * T)));
        neck2.setFill(Color.KHAKI);

        //Circle circle = new Circle(neck2StartX + (H / 8), neck2StartY - (EDGE * T), 7, Color.RED);
        root.getChildren().addAll(neck1, body, neck2);
        this.getChildren().setAll(root);
    }

    @Override
    public void redraw() {
        this.getChildren().clear();
        initGraphics();
    }

}
