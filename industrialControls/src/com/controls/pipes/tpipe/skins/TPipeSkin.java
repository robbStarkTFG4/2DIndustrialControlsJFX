/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes.tpipe.skins;

import com.controls.pipes.tpipe.TPipe;
import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;

/**
 *
 * @author marcoisaac
 */
public class TPipeSkin extends SkinBase<TPipe> implements Skin<TPipe> {

    private double START_X = 0;
    private double START_Y = 0;
    private double AV;

    ///
    //private double EDGE = AV - 1;
    private double EDGE;
    private double W; //witdh
    private double H; //height
    private double T; // pipe diameter

    public TPipeSkin(TPipe control) {
        super(control);
        init();
        initGraphics();
    }

    private void init() {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

        if (!(W >= 3 * T)) {
            System.out.println("error 2");
            System.exit(0);
        }

        Path neck1 = new Path();
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new LineTo(START_X + (T * AV), START_Y));
        neck1.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T), START_Y - (H / 8)));//E
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new LineTo(START_X + (EDGE * T), START_Y - (H / 8)));//D
        neck1.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T), START_Y - (H / 8)));

        Path body = new Path();
        body.getElements().add(new MoveTo(START_X + (EDGE * T), START_Y - (H / 8)));//D
        body.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T), START_Y - (H / 8)));//E

        double restanteY = H - (H / 8) - T;
        double eX = START_X + (T * AV) - (EDGE * T);
        double dX = START_X + (EDGE * T);
        double fY = (START_Y - (H / 8)) - (restanteY / 2);
        double cY = fY;//eje y
        double midPointX = (eX - dX) / 2;
        double throwX = (W / 2) - midPointX - (H / 8);

        double gY = cY - (restanteY / 2);
        double gX = (throwX / 2) + eX;
        double h = (throwX / 2) + gX;
        double bX = dX - (throwX / 2);
        double aX = bX - (throwX / 2);

        body.getElements().add(new LineTo(eX, fY));//F
        double mid1 = (gY - fY) / 2;
        //fY - (gY / 4)
        body.getElements().add(new QuadCurveTo(eX, gY + (mid1 / 4), gX, gY));//G
        body.getElements().add(new LineTo(h, gY));//H
        body.getElements().add(new MoveTo(dX, START_Y - (H / 8)));//D
        body.getElements().add(new LineTo(dX, cY));//C
        //cY - (gY / 4)
        body.getElements().add(new QuadCurveTo(dX, gY + (mid1 / 4), bX, gY));//B
        body.getElements().add(new LineTo(aX, gY));//A
        body.getElements().add(new LineTo(aX, gY - T));//A1
        body.getElements().add(new LineTo(h, gY - T));//H1
        body.getElements().add(new LineTo(h, gY));//H

        Path neck2 = new Path();//topRight
        neck2.getElements().add(new MoveTo(h, gY));
        neck2.getElements().add(new LineTo(h, gY - T));
        neck2.getElements().add(new LineTo(h + (H / 8), gY - T - (EDGE * T)));//PP
        neck2.getElements().add(new MoveTo(h, gY));
        neck2.getElements().add(new LineTo(h + (H / 8), gY + (EDGE * T)));//ttt
        neck2.getElements().add(new LineTo(h + (H / 8), gY - T - (EDGE * T)));

        Path neck3 = new Path();//topLeft
        neck3.getElements().add(new MoveTo(aX, gY));//A
        neck3.getElements().add(new LineTo(aX, gY - T));//A1
        neck3.getElements().add(new LineTo(aX - (H / 8), gY - T - (EDGE * T)));
        neck3.getElements().add(new MoveTo(aX, gY));//A
        neck3.getElements().add(new LineTo(aX - (H / 8), gY + (EDGE * T)));//A
        neck3.getElements().add(new LineTo(aX - (H / 8), gY - T - (EDGE * T)));//A1

        root.getChildren().addAll(neck1, body, neck2, neck3);

        this.getChildren().setAll(root);

    }
}
