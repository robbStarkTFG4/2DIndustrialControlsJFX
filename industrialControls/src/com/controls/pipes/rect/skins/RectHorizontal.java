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
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 *
 * @author marcoisaac
 */
public class RectHorizontal extends SkinBase<Rect> implements Skin<Rect> {

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

    private RectDirection direction;

    public RectHorizontal(Rect control, RectDirection direction) {
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
            case LEFT_RIGHT:
                break;
            case RIGHT_LEFT:
                double endX = START_X;
                double endY = START_Y;

                double c1 = (H / 8);
                double x = START_X + c1 + W - c1 + L + c1;
                START_X = endX - c1 - W + c1 - L - c1;

                double y = START_Y - (EDGE * T) + (EDGE * T);
                START_Y = endY;
                break;
        }

        Pane root = new Pane();

        /*if (!(H >= 3 * T)) {
            System.out.println("error 1");
            System.exit(0);
        }

        if (!(W > 3 * T)) {
            System.out.println("error 2");
            System.exit(0);
        }*/
        Path neck1 = new Path();

        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new LineTo(START_X, START_Y + (T * AV)));

        neck1.getElements().add(new LineTo(START_X + (H / 8), START_Y + (T * AV) - (EDGE * T)));
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new LineTo(START_X + (H / 8), START_Y + (EDGE * T)));

        LinearGradient neck1Gradient = new LinearGradient(START_X, START_Y, START_X + (H / 8), START_Y + (EDGE * T), false, CycleMethod.REFLECT, new Stop(0, Color.web("#B6B6B6")), new Stop(1, Color.web("#727272")));
        neck1.setFill(neck1Gradient);
        neck1.setStroke(Color.web("#727272"));

        double q = START_X + (H / 8);
        double neck1EndY = START_Y + (T * AV) - (EDGE * T);

        double restanteX = W - (H / 8) + L;

        neck1.getElements().add(new LineTo(q, neck1EndY));

        Path body = new Path();
        body.getElements().add(new MoveTo(q, neck1EndY));
        body.getElements().add(new LineTo(q, START_Y + (EDGE * T)));
        body.getElements().add(new LineTo(q + restanteX, START_Y + (EDGE * T)));//TOP END
        body.getElements().add(new MoveTo(q, neck1EndY));
        body.getElements().add(new LineTo(q + restanteX, neck1EndY));//BOTTOM END
        body.getElements().add(new LineTo(q + restanteX, START_Y + (EDGE * T)));

        LinearGradient bodyGradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.REFLECT, new Stop(0, Color.web("#727272")), new Stop(1, Color.web("#727272")), new Stop(0, Color.web("#FFFFFF")), new Stop(1, Color.web("#727272")));

        body.setFill(bodyGradient);
        //body.setStroke(Color.web("#1976D2"));
        body.setStroke(Color.web("#9E9E9E"));
        body.setStrokeWidth(1.3);

        Path neck2 = new Path();
        neck2.getElements().add(new MoveTo(q + restanteX, START_Y + (EDGE * T)));

        neck2.getElements().add(new LineTo(q + restanteX, neck1EndY));
        neck2.getElements().add(new LineTo(q + restanteX + (H / 8), neck1EndY + (EDGE * T)));
        neck2.getElements().add(new MoveTo(q + restanteX, START_Y + (EDGE * T)));
        neck2.getElements().add(new LineTo(q + restanteX + (H / 8), START_Y - (EDGE * T) + (EDGE * T)));
        neck2.getElements().add(new LineTo(q + restanteX + (H / 8), neck1EndY + (EDGE * T)));
        LinearGradient neck2Gradient = new LinearGradient(q + restanteX, START_Y + (EDGE * T), q + restanteX + (H / 8), neck1EndY + (EDGE * T), false, CycleMethod.REFLECT, new Stop(0, Color.web("#B6B6B6")), new Stop(1, Color.web("#727272")));
        neck2.setFill(neck2Gradient);
        neck2.setStroke(Color.web("#727272"));

        //Circle circle = new Circle(q + restanteX + (H / 8), START_Y - (EDGE * T) + (EDGE * T), 7, Color.RED);
        root.getChildren().addAll(neck1, body, neck2);
        this.getChildren().setAll(root);
    }
}
