/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes.devices.watermeter.skins;

import com.controls.pipes.devices.watermeter.WaterMeter;
import com.controls.util.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
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
public class WaterMeterSkin extends SkinBase<WaterMeter> implements Skin<WaterMeter> {

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
    private Line noFlow;

    private List<Node> flowSignList = new ArrayList<>();
    private FadeTransition ft;
    private FadeTransition ft2;

    public WaterMeterSkin(WaterMeter control) {
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
        LinearGradient neck1Gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.REFLECT, new Stop(0, Color.web("#B6B6B6")), new Stop(0, Color.web("#727272")));
        //neck1.setFill(Color.ROSYBROWN);
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
        LinearGradient bodyGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, new Stop(0, Color.web("#FFFFFF")), new Stop(1, Color.web("#1976D2")));
        body.setFill(bodyGradient);
        body.setStroke(Color.web("#727272"));

        double restanteY = H - (2 * ((H / 8))) - (2 * (H / 12)) - (2 * (H / 14));
        double bX = START_X + (T * AV) - (EDGE * T) - offset;
        double bY = aY - (H / 12);

        Path lowerNeck = new Path();
        lowerNeck.getElements().add(new MoveTo(bX, bY));//B
        lowerNeck.getElements().add(new LineTo(aX - T + offset, aY - (H / 12)));//C
        lowerNeck.getElements().add(new LineTo(aX - T + offset, bY - (H / 14)));//G
        lowerNeck.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T) - offset, bY - (H / 14)));//F
        lowerNeck.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T) - offset, aY - (H / 12)));//B
        LinearGradient lowerNeckGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, new Stop(0, Color.web("#FFFFFF")), new Stop(1, Color.web("#1976D2")));
        lowerNeck.setFill(lowerNeckGradient);
        lowerNeck.setStroke(Color.web("#727272"));

        double radio = (restanteY / 2);

        Circle center = new Circle(aX - (T / 2), bY - (H / 14) - (restanteY / 2), radio, null);
        center.setStroke(Color.web("#727272"));
        LinearGradient centerRingGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, new Stop(0, Color.web("#FFFFFF")), new Stop(1, Color.web("#1976D2")));
        center.setFill(centerRingGradient);

        root.getChildren().add(center);
        //System.out.println("radio: "+());
        double hX = aX - T + offset;
        //double hY = center.getBoundsInParent().getMinY();
        double hY = bY - (H / 14) - (restanteY);

        Path upperNeck = new Path();
        upperNeck.getElements().add(new MoveTo(hX, hY));//H
        upperNeck.getElements().add(new LineTo(bX, hY));//I
        upperNeck.getElements().add(new LineTo(bX, hY - (H / 14)));//J
        upperNeck.getElements().add(new LineTo(hX, hY - (H / 14)));//K
        upperNeck.getElements().add(new LineTo(hX, hY));//H
        LinearGradient upperNeckGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, new Stop(0, Color.web("#FFFFFF")), new Stop(1, Color.web("#1976D2")));
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
        LinearGradient mangaNeckGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, new Stop(0, Color.web("#FFFFFF")), new Stop(1, Color.web("#1976D2")));
        mangaNeck.setFill(mangaNeckGradient);
        mangaNeck.setStroke(Color.web("#727272"));

        Path neck2 = new Path();
        neck2.getElements().add(new MoveTo(aX - T, lY));//M
        neck2.getElements().add(new LineTo(aX, lY));//L
        neck2.getElements().add(new LineTo(aX, lY - (H / 8)));
        neck2.getElements().add(new LineTo(aX - T, lY - (H / 8)));
        neck2.getElements().add(new LineTo(aX - T, lY));
        LinearGradient neck2Gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.REFLECT, new Stop(0, Color.web("#B6B6B6")), new Stop(1, Color.web("#727272")));
        //neck1.setFill(Color.ROSYBROWN);
        neck2.setFill(neck2Gradient);
        neck2.setStroke(Color.web("#727272"));

        Circle outerRing = new Circle(center.getCenterX(), center.getCenterY(), radio * 0.85, null);
        outerRing.setStroke(Color.BLACK);
        outerRing.setFill(Color.WHITE);
        root.getChildren().add(outerRing);

        noFlow = noFlowSign(center, radio, mangaNeck);

        flowSign(center, radio, root, mangaNeck);

        for (Node d : flowSignList) {
            d.setOpacity(0);
        }

        //Circle point = new Circle(aX - T, lY - (H / 8), 7, Color.RED);
        //root.getChildren().add(point);
        root.getChildren().addAll(neck1, body, neck2, lowerNeck, upperNeck, mangaNeck, noFlow);
        this.getChildren().setAll(root);
    }

    private Line noFlowSign(Circle center, double radio, Path mangaNeck) {
        Line noFlow = new Line(center.getCenterX() - (radio * 0.5), center.getCenterY(), center.getCenterX() + (radio * 0.5), center.getCenterY());
        noFlow.setStrokeWidth(mangaNeck.getStrokeWidth() * 4);
        //noFlow.setOpacity(0);
        noFlow.setFill(Color.web("#009688"));
        return noFlow;
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
        test.setFill(Color.web("#009688"));
        test.setStroke(Color.web("#009688"));
        root.getChildren().add(test);
        flowSignList.add(test);

        List<Point> points1 = new ArrayList<>();
        double a1 = center.getCenterX();
        double b1 = center.getCenterY() - (2 * r / 4);

        for (double d : angle) {
            double x = a1 + r * Math.cos(d);
            double y = b1 + r * Math.sin(d);
            points1.add(new Point(x, y));
        }

        Circle test1 = new Circle(a1, b1, 2);
        test1.setFill(Color.web("#009688"));
        test1.setStroke(Color.web("#009688"));
        root.getChildren().add(test1);
        flowSignList.add(test1);

        for (Point w : points) {
            Path upperWing = new Path();
            upperWing.setStrokeWidth(mangaNeck.getStrokeWidth() * 4);
            upperWing.getElements().add(new MoveTo(w.getX(), w.getY()));
            upperWing.getElements().add(new LineTo(a, b));
            upperWing.getElements().add(new LineTo(w.getX(), w.getY()));
            upperWing.setFill(Color.PINK);
            root.getChildren().add(upperWing);
            flowSignList.add(upperWing);
        }

        for (Point w : points1) {
            Path upperWing = new Path();
            upperWing.setStrokeWidth(mangaNeck.getStrokeWidth() * 4);
            upperWing.getElements().add(new MoveTo(w.getX(), w.getY()));
            upperWing.getElements().add(new LineTo(a1, b1));
            upperWing.getElements().add(new LineTo(w.getX(), w.getY()));
            upperWing.setFill(Color.web("#009688"));
            root.getChildren().add(upperWing);
            flowSignList.add(upperWing);
        }
    }

    public void fadeFlowOut() {
        for (Node d : flowSignList) {
            ft = new FadeTransition(Duration.millis(200), d);
            ft.setFromValue(1.0);
            ft.setToValue(0);
            //ft.setCycleCount(4);
            ft.setAutoReverse(false);

            ft.setOnFinished(e -> {
                System.out.println("FLUJOOOOOOOO!!!!");
                fadeNoFlowIn();
            });
            ft.play();

        }
    }

    private void fadeFlowIn() {
        for (Node d : flowSignList) {
            FadeTransition ft = new FadeTransition(Duration.millis(200), d);
            ft.setFromValue(0);
            ft.setToValue(1);
            //ft.setCycleCount(4);
            ft.setAutoReverse(false);
            ft.play();
        }
    }

    public void fadeNoFlowOut() {

        ft2 = new FadeTransition(Duration.millis(200), noFlow);
        ft2.setFromValue(1);
        ft2.setToValue(0);
        //ft.setCycleCount(4);
        ft2.setAutoReverse(false);
        ft2.setOnFinished(e -> {
            System.out.println("ASDSADSADSAWQEWQEVCXVXC");
            fadeFlowIn();
        });
        ft2.play();

    }

    private void fadeNoFlowIn() {
        FadeTransition ft = new FadeTransition(Duration.millis(200), noFlow);
        ft.setFromValue(0);
        ft.setToValue(1);
        //ft.setCycleCount(4);
        ft.setAutoReverse(false);
        ft.play();
    }
}
