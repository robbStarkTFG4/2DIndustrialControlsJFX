/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes.devices.compuerta.skins;

import com.controls.pipes.devices.compuerta.Compuerta;
import com.controls.util.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.RotateTransition;
import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 *
 * @author marcoisaac
 */
public class CompuertaSkin extends SkinBase<Compuerta> implements Skin<Compuerta> {

    private double START_X = 0;
    private double START_Y = 0;
    private double AV;

    ///
    //private double EDGE = AV - 1;
    private double EDGE;
    private double W; //witdh
    private double H; //height
    private double T; // pipe diameter

    private double angle[] = {2.35, 0.78};
    private Line key;

    public CompuertaSkin(Compuerta control) {
        super(control);
        init();
        initGraphics();
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

        Pane root = new Pane();

        if (!(H >= 3 * T)) {
            System.out.println("error 1");
            System.exit(0);
        }

        if (!(W > 3 * T)) {
            System.out.println("error 2");
            System.exit(0);
        }

        Path neck1 = new Path();//Bottom
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new LineTo(START_X + (T * AV), START_Y));
        neck1.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T), START_Y - (H / 8)));
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new LineTo(START_X + (EDGE * T), START_Y - (H / 8)));
        neck1.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T), START_Y - (H / 8)));//A
        LinearGradient neck1Gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, new Stop(0, Color.web("#FFFFFF")), new Stop(0, Color.web("#B6B6B6")), new Stop(0, Color.web("#727272")), new Stop(1, Color.web("#212121")), new Stop(1, Color.BLACK));
        neck1.setFill(neck1Gradient);
        neck1.setStroke(Color.web("#727272"));

        //Circle circle = new Circle(START_X + (T * AV) - (EDGE * T), START_Y - (H / 8), 7, Color.RED);
        double offset = T * .2;
        double aX = START_X + (T * AV) - (EDGE * T);
        double aY = START_Y - (H / 8);
        double offsetCircle = T * .10;

        Path body = new Path();
        body.getElements().add(new MoveTo(aX, aY));
        body.getElements().add(new LineTo(aX - T, aY));//D
        body.getElements().add(new LineTo(aX - T + offset, aY - (H / 12)));//C
        body.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T) - offset, aY - (H / 12)));//B
        body.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T), START_Y - (H / 8)));//A
        LinearGradient bodyGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, new Stop(0, Color.web("#FFFFFF")), new Stop(0, Color.web("#B6B6B6")), new Stop(0, Color.web("#727272")), new Stop(1, Color.web("#212121")), new Stop(1, Color.BLACK));
        body.setFill(bodyGradient);
        body.setStroke(Color.web("#727272"));

        final double controlHeight = H / 14;

        double restanteY = H - (2 * ((H / 8))) - (2 * (H / 12)) - (2 * controlHeight);
        System.out.println("altura: " + restanteY);
        double bX = START_X + (T * AV) - (EDGE * T) - offset;
        double bY = aY - (H / 12);

        Path lowerNeck = new Path();
        lowerNeck.getElements().add(new MoveTo(bX, bY));//B
        lowerNeck.getElements().add(new LineTo(aX - T + offset, aY - (H / 12)));//C
        lowerNeck.getElements().add(new LineTo(aX - T + offset, bY - controlHeight));//G
        lowerNeck.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T) - offset, bY - controlHeight));//F
        lowerNeck.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T) - offset, aY - (H / 12)));//B
        LinearGradient lowerGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, new Stop(0, Color.web("#FFFFFF")), new Stop(0, Color.web("#B6B6B6")), new Stop(0, Color.web("#727272")), new Stop(1, Color.web("#212121")), new Stop(1, Color.BLACK));
        lowerNeck.setFill(lowerGradient);
        lowerNeck.setStroke(Color.web("#727272"));

        double gX = aX - T + offset;
        double gY = bY - controlHeight;

        double distance = (bX - gX) / 2;
        double gateWidth = (1.5 * T) - distance;
        double twoBase = 2 * (H / 8);

        Path gateBody = new Path();
        gateBody.getElements().add(new MoveTo(gX, gY));
        gateBody.getElements().add(new LineTo(gX - gateWidth, gY));//1
        gateBody.getElements().add(new LineTo(gX - gateWidth, gY - twoBase));//2
        gateBody.getElements().add(new LineTo(gX, gY - twoBase));//G1
        gateBody.getElements().add(new LineTo(bX, gY - twoBase));//B1
        gateBody.getElements().add(new LineTo(bX + gateWidth, gY - twoBase));//3
        gateBody.getElements().add(new LineTo(bX + gateWidth, gY));//4
        gateBody.getElements().add(new LineTo(gX, gY));//GX

        LinearGradient gradientGateBody = new LinearGradient(0, 0, 0, 1, true, CycleMethod.REFLECT, new Stop(0, Color.web("#B6B6B6")), new Stop(1, Color.web("#F5F5F5")));

        gateBody.setFill(gradientGateBody);
        gateBody.setStroke(Color.web("#727272"));

        Line midLine = new Line(gX - gateWidth, gY - (twoBase / 2), bX + gateWidth, gY - (twoBase / 2));

        RadialGradient gradient = new RadialGradient(30,
                (twoBase / 4) * 0.40,
                gX + distance,
                gY - (twoBase / 2),
                (twoBase / 4),
                false,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.GREY),
                new Stop(1, Color.web("#607D8B")));

        Circle centerMid = new Circle(gX + distance, gY - (twoBase / 2), twoBase / 4, gradient);

        /// BEGIN state indicator
        Path indicatorBase = new Path();
        indicatorBase.getElements().add(new MoveTo(gX - gateWidth, gY - (4 * twoBase / 12)));//s1
        indicatorBase.getElements().add(new LineTo(gX - gateWidth, gY - (8 * twoBase / 12)));//s2
        indicatorBase.getElements().add(new LineTo(gX - gateWidth - distance * 0.7, gY - (8 * twoBase / 12)));//S2LEFT
        indicatorBase.getElements().add(new LineTo(gX - gateWidth - distance * 0.7, gY - (4 * twoBase / 12)));//S1LEFT
        indicatorBase.getElements().add(new LineTo(gX - gateWidth, gY - (4 * twoBase / 12)));//S1
        LinearGradient indicatorBaseGateBody = new LinearGradient(0, 0, 0, 1, true, CycleMethod.REFLECT, new Stop(0, Color.web("#B6B6B6")), new Stop(1, Color.web("#F5F5F5")));
        indicatorBase.setFill(indicatorBaseGateBody);
        indicatorBase.setStroke(Color.web("#727272"));

        double baseX = gX - gateWidth - distance * 0.7;
        double baseY = (gY - (2 * twoBase / 8)) - (((gY - (2 * twoBase / 8)) - (gY - (6 * twoBase / 8))) / 2);

        double radioIndi = T / 2;

        Circle indi = new Circle(baseX - (T / 2), baseY, radioIndi);
        indi.setFill(Color.WHITE);
        indi.setStroke(Color.web("#727272"));

        key = new Line(baseX - (T / 2) - radioIndi * 0.60, baseY, baseX - (T / 2) + radioIndi * 0.60, baseY);
        key.setStrokeWidth(indicatorBase.getStrokeWidth() * 2);

        //END state
        //System.out.println("radio: "+());
        double hX = aX - T + offset;
        double hY = gY - twoBase;

        Path upperNeck = new Path();
        upperNeck.getElements().add(new MoveTo(hX, hY));//H
        upperNeck.getElements().add(new LineTo(bX, hY));//I
        upperNeck.getElements().add(new LineTo(bX, hY - (H / 14)));//J
        upperNeck.getElements().add(new LineTo(hX, hY - (H / 14)));//K
        upperNeck.getElements().add(new LineTo(hX, hY));//H
        LinearGradient upperNeckGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, new Stop(0, Color.web("#FFFFFF")), new Stop(0, Color.web("#B6B6B6")), new Stop(0, Color.web("#727272")), new Stop(1, Color.web("#212121")), new Stop(1, Color.BLACK));
        upperNeck.setFill(upperNeckGradient);
        upperNeck.setStroke(Color.web("#727272"));

        Path mangaNeck = new Path();
        mangaNeck.getElements().add(new MoveTo(hX, hY - (H / 14)));//K
        mangaNeck.getElements().add(new LineTo(bX, hY - (H / 14)));//J
        mangaNeck.getElements().add(new LineTo(aX, hY - (H / 14) - (H / 12)));//L

        double lX = aX;
        double lY = hY - (H / 14) - (H / 12);

        mangaNeck.getElements().add(new LineTo(aX - T, lY));//M
        mangaNeck.getElements().add(new LineTo(hX, hY - (H / 14)));//K
        LinearGradient mangaNeckNeckGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, new Stop(0, Color.web("#FFFFFF")), new Stop(0, Color.web("#B6B6B6")), new Stop(0, Color.web("#727272")), new Stop(1, Color.web("#212121")), new Stop(1, Color.BLACK));
        mangaNeck.setFill(mangaNeckNeckGradient);
        mangaNeck.setStroke(Color.web("#727272"));

        Path neck2 = new Path();
        neck2.getElements().add(new MoveTo(aX - T, lY));//M
        neck2.getElements().add(new LineTo(aX, lY));//L
        neck2.getElements().add(new LineTo(aX, lY - (H / 8)));
        neck2.getElements().add(new LineTo(aX - T, lY - (H / 8)));
        neck2.getElements().add(new LineTo(aX - T, lY));
        LinearGradient neck2NeckGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, new Stop(0, Color.web("#FFFFFF")), new Stop(0, Color.web("#B6B6B6")), new Stop(0, Color.web("#727272")), new Stop(1, Color.web("#212121")), new Stop(1, Color.BLACK));
        neck2.setFill(neck2NeckGradient);
        neck2.setStroke(Color.web("#727272"));

        //Circle pn = new Circle(aX - T, lY - (H / 8), 7, Color.RED);
        //root.getChildren().add(pn);
        root.getChildren().addAll(neck1, body, lowerNeck, gateBody, midLine, centerMid, indicatorBase, indi, upperNeck, mangaNeck, neck2, key);
        this.getChildren().setAll(root);
    }

    private void flowSign(Circle center, double radio, Pane root, Path mangaNeck) {
        List<Point> points = new ArrayList<>();

        double a = center.getCenterX();
        double b = center.getCenterY();
        double r = (radio * 0.5);

        for (double d : angle) {
            double x = a + r * Math.cos(d);
            double y = b + r * Math.sin(d);
            points.add(new Point(x, y));
        }

        Circle test = new Circle(a, b, 2);
        root.getChildren().add(test);

        List<Point> points1 = new ArrayList<>();
        double a1 = center.getCenterX();
        double b1 = center.getCenterY() - (2 * r / 4);

        for (double d : angle) {
            double x = a1 + r * Math.cos(d);
            double y = b1 + r * Math.sin(d);
            points1.add(new Point(x, y));
        }

        Circle test1 = new Circle(a1, b1, 2);
        root.getChildren().add(test1);

        for (Point w : points) {
            Path upperWing = new Path();
            upperWing.setStrokeWidth(mangaNeck.getStrokeWidth() * 4);
            upperWing.getElements().add(new MoveTo(w.getX(), w.getY()));
            upperWing.getElements().add(new LineTo(a, b));
            upperWing.getElements().add(new LineTo(w.getX(), w.getY()));
            root.getChildren().add(upperWing);
        }

        for (Point w : points1) {
            Path upperWing = new Path();
            upperWing.setStrokeWidth(mangaNeck.getStrokeWidth() * 4);
            upperWing.getElements().add(new MoveTo(w.getX(), w.getY()));
            upperWing.getElements().add(new LineTo(a1, b1));
            upperWing.getElements().add(new LineTo(w.getX(), w.getY()));
            root.getChildren().add(upperWing);
        }
    }

    public void closeState() {
        System.out.println("cerrar");
        RotateTransition rot = new RotateTransition(Duration.millis(200));
        rot.setNode(key);
        rot.setByAngle(90);
        rot.setAutoReverse(false);
        rot.play();
    }

    public void openState() {
        System.out.println("abrir");
        RotateTransition rot = new RotateTransition(Duration.millis(200));
        rot.setNode(key);
        rot.setByAngle(-90);
        rot.setAutoReverse(false);
        rot.play();
    }

}
