/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes.devices.digital.skins;

import com.controls.pipes.devices.digital.Digital;
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
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 *
 * @author marcoisaac
 */
public class DigitalSkin extends SkinBase<Digital> implements Skin<Digital> {

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
    private Path indi;

    public DigitalSkin(Digital control) {
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
        EDGE = getSkinnable().getEDGE();

        Pane root = new Pane();

        Path first = new Path();
        first.getElements().add(new MoveTo(START_X, START_Y));

        double a = START_X + (W / 8);

        first.getElements().add(new LineTo(a, START_Y));//A
        first.getElements().add(new LineTo(a, START_Y + H));
        first.getElements().add(new LineTo(START_X, START_Y + H));
        first.getElements().add(new LineTo(START_X, START_Y));
        first.setStroke(Color.web("#727272"));
        LinearGradient firstGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, new Stop(0, Color.web("#FFFFFF")), new Stop(0, Color.web("#2196F3")), new Stop(1, Color.web("#1976D2")));
        first.setFill(firstGradient);

        Path second = new Path();
        second.getElements().add(new MoveTo(a, START_Y));//A

        double b = START_X + (3 * W / 8);

        second.getElements().add(new LineTo(b, START_Y));
        second.getElements().add(new LineTo(b, START_Y + H));
        second.getElements().add(new LineTo(a, START_Y + H));
        second.getElements().add(new LineTo(a, START_Y));
        second.setStroke(Color.web("#727272"));
        //LinearGradient secondGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, new Stop(0, Color.web("#FFFFFF")), new Stop(0, Color.web("#727272")), new Stop(1, Color.web("#212121")));
        //second.setFill(secondGradient);

        double alpha = b - a;
        double AV = 9;

        for (int i = 1; i <= AV; i++) {
            Line div = new Line(a + (i * alpha / AV), START_Y, a + (i * alpha / AV), START_Y + H);
            root.getChildren().add(div);
        }

        Path third = new Path();
        third.getElements().add(new MoveTo(b, START_Y));

        double t = 0.45 * H;
        double f = START_Y - t;
        double restanteX = W - (b - START_X);
        double c = b + (restanteX / 8);

        third.getElements().add(new LineTo(b, f));
        third.getElements().add(new LineTo(c, f));
        third.getElements().add(new LineTo(c, START_Y + H + t));
        third.getElements().add(new LineTo(b, START_Y + H + t));
        third.getElements().add(new LineTo(b, START_Y));
        third.setStroke(Color.web("#727272"));
        LinearGradient thirdGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, new Stop(0, Color.web("#FFFFFF")), new Stop(0, Color.web("#727272")), new Stop(1, Color.web("#212121")));
        third.setFill(thirdGradient);

        double m = b + (6 * restanteX / 8);
        double n = m + (2 * restanteX / 8);
        double l = n;

        Path fouth = new Path();
        fouth.getElements().add(new MoveTo(c, START_Y));
        fouth.getElements().add(new LineTo(l, START_Y));
        fouth.getElements().add(new LineTo(l, START_Y + H));
        fouth.getElements().add(new LineTo(c, START_Y + H));
        fouth.getElements().add(new LineTo(c, START_Y));
        fouth.setStroke(Color.web("#727272"));
        LinearGradient fouthGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, new Stop(0, Color.web("#FFFFFF")), new Stop(0, Color.web("#727272")), new Stop(1, Color.web("#212121")));
        fouth.setFill(fouthGradient);

        Path soporte = new Path();
        soporte.getElements().add(new MoveTo(m, START_Y));
        soporte.getElements().add(new LineTo(m, f));
        soporte.getElements().add(new LineTo(n, f));
        soporte.getElements().add(new LineTo(n, START_Y));
        soporte.getElements().add(new LineTo(m, START_Y));
        soporte.setStroke(Color.web("#727272"));
        LinearGradient soporteGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, new Stop(0, Color.web("#FFFFFF")), new Stop(0, Color.web("#727272")), new Stop(1, Color.web("#212121")));
        soporte.setFill(fouthGradient);

        double indiX = l - c;
        double indiY = H;

        indi = new Path();
        indi.getElements().add(new MoveTo(c + (indiX / 8), START_Y + (indiY / 8)));
        indi.getElements().add(new LineTo(l - (indiX / 8), START_Y + (indiY / 8)));
        indi.getElements().add(new LineTo(l - (indiX / 8), START_Y + H - (indiY / 8)));
        indi.getElements().add(new LineTo(c + (indiX / 8), START_Y + H - (indiY / 8)));
        indi.getElements().add(new LineTo(c + (indiX / 8), START_Y + (indiY / 8)));
        indi.setStroke(Color.web("#B6B6B6"));
        indi.setFill(Color.web("#D32F2F"));

        root.getChildren().addAll(first, second, third, fouth, indi, soporte);
        this.getChildren().setAll(root);
    }

    public void ActivadedState() {

        FadeTransition act2 = new FadeTransition(Duration.millis(200));
        act2.setNode(indi);
        act2.setFromValue(0);
        act2.setToValue(1);

        FadeTransition act = new FadeTransition(Duration.millis(200));
        act.setNode(indi);
        act.setFromValue(1);
        act.setToValue(0);
        act.play();
        act.setOnFinished(d -> {
            indi.setFill(Color.web("7FFF00"));
            act2.play();
        });

    }

    public void deActivadedState() {
        FadeTransition act2 = new FadeTransition(Duration.millis(200));
        act2.setNode(indi);
        act2.setFromValue(0);
        act2.setToValue(1);

        FadeTransition act = new FadeTransition(Duration.millis(200));
        act.setNode(indi);
        act.setFromValue(1);
        act.setToValue(0);
        act.play();
        act.setOnFinished(d -> {
            indi.setFill(Color.web("#B6B6B6"));
            act2.play();
        });
        act.play();
    }
}
