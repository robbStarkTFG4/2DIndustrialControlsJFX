/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes.rect.skins;

import com.controls.pipes.rect.Rect;
import com.controls.util.RectDirection;
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

/**
 *
 * @author marcoisaac
 */
public class RectVertical extends SkinBase<Rect> implements Skin<Rect> {

    private double START_X = 0;
    private double START_Y = 0;
    private double AV;

    ///
    //private double EDGE = AV - 1;
    private double EDGE;
    private double W; //witdh
    private double H; //height
    private double T; // pipe diameter
    private double L;
    private final RectDirection direction;

    public RectVertical(Rect control, RectDirection direction) {
        super(control);
        this.direction = direction;
        init();
        initGraphics();
    }

    private void init() {

    }

    private void initGraphics() {
        START_X = getSkinnable().getStartX();
        START_Y = getSkinnable().getStartY();

        W = getSkinnable().getW();
        H = getSkinnable().getH();
        T = getSkinnable().getT();
        AV = getSkinnable().getAV();
        EDGE = getSkinnable().getEDGE();
        L = getSkinnable().getLength();

        switch (direction) {
            case DOWN_TOP:
                break;
            case TOP_DOWN:
                //
                double endX = START_X;
                double endY = START_Y;

                double x = (START_X + (EDGE * T)) - (EDGE * T);
                double x2 = START_X + EDGE * T - EDGE * T;
                System.out.println("x: " + x);
                System.out.println("x2: " + x2);
                //START_X=
                START_X = endX - EDGE * T + EDGE * T;
                double y = ((START_Y - (H / 8)) - (H - (2 * H / 8) + L)) - (H / 8);
                double y2 = START_Y - H / 8 - H + 2 * H / 8 - L - (H / 8);
                System.out.println("y: " + y);
                System.out.println("y2: " + y2);
                START_Y = endY + H / 8 + H - 2 * H / 8 + L + (H / 8);
                //START_Y=

                break;
        }

        Pane root = new Pane();

        if (!(H >= 3 * T)) {
            System.out.println("error 1");
            System.exit(0);
        }

        if (!(W > 3 * T)) {
            System.out.println("error 2");
            System.exit(0);
        }

        Path neck1 = new Path();
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new LineTo(START_X + (AV * T), START_Y));

        double bX = START_X + (AV * T) - (EDGE * T);
        double bY = START_Y - (H / 8);
        double aX = START_X + (EDGE * T);

        neck1.getElements().add(new LineTo(bX, bY));//B
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new LineTo(aX, bY));//A
        neck1.getElements().add(new LineTo(bX, bY));
        LinearGradient neck1Gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.REFLECT, new Stop(0, Color.web("#B6B6B6")), new Stop(1, Color.web("#727272")));
        neck1.setFill(neck1Gradient);
        neck1.setStroke(Color.web("#727272"));

        Path body = new Path();
        body.getElements().add(new MoveTo(bX, bY));
        body.getElements().add(new LineTo(aX, bY));

        double restanteY = H - (2 * H / 8) + L;
        double c = bY - restanteY;
        body.getElements().add(new LineTo(aX, c));//C
        body.getElements().add(new MoveTo(bX, bY));
        body.getElements().add(new LineTo(bX, c));//D
        body.getElements().add(new LineTo(aX, c));//C
        LinearGradient bodyGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, new Stop(0, Color.web("#727272")), new Stop(1, Color.web("#727272")), new Stop(0, Color.web("#FFFFFF")), new Stop(1, Color.web("#727272")));

        body.setFill(bodyGradient);
        //body.setStroke(Color.web("#1976D2"));
        body.setStroke(Color.web("#9E9E9E"));
        body.setStrokeWidth(1.3);

        Path neck2 = new Path();
        neck2.getElements().add(new MoveTo(aX, c));//C
        neck2.getElements().add(new LineTo(bX, c));//D
        neck2.getElements().add(new LineTo(bX + (EDGE * T), c - (H / 8)));//D1
        neck2.getElements().add(new MoveTo(aX, c));//C
        neck2.getElements().add(new LineTo(aX - (EDGE * T), c - (H / 8)));//C1
        neck2.getElements().add(new LineTo(bX + (EDGE * T), c - (H / 8)));//D1
        LinearGradient neck2Gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.REFLECT, new Stop(0, Color.web("#B6B6B6")), new Stop(1, Color.web("#727272")));
        neck2.setFill(neck2Gradient);
        neck2.setStroke(Color.web("#727272"));

        //Circle circle = new Circle(aX - (EDGE * T), c - (H / 8), 7, Color.RED);
        root.getChildren().addAll(neck1, body, neck2);
        this.getChildren().setAll(root);
    }
}
