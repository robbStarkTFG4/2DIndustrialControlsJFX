/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes.devices.pump1.skins;

import com.controls.pipes.devices.pump1.Pump1;
import com.controls.util.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
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
import javafx.util.Duration;

/**
 *
 * @author marcoisaac
 */
public class Pump1Skin extends SkinBase<Pump1> implements Skin<Pump1> {
    
    private double START_X = 0;
    private double START_Y = 0;
    
    private double W; //witdh
    private FadeTransition ft;
    private FadeTransition ft2;

    //circle points 
    private double d = 150;
    private double a;//centerX
    private double b;//centerY
    private double r = d / 2;
    private double angle[] = {1.57, 0.78, 0, 5.49, 4.71, 3.92, 3.14, 2.35};
    private List<Point> points = new ArrayList<>();
    private List<Path> pathList = new ArrayList<>();
    private Timeline time;
    private int i = 0;
    
    public Pump1Skin(Pump1 control) {
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
        
        a = START_X;
        b = START_Y;
        d = W / 2;
        r = d / 2;
        
        Pane root = new Pane();
        
        Circle core = new Circle(a, b, 2);
        core.setStroke(Color.RED);
        
        root.getChildren().add(core);
        
        Path armor = new Path();
        armor.getElements().add(new MoveTo(START_X, START_Y));
        armor.getElements().add(new MoveTo(START_X - (1.25 * (d / 2)), START_Y));
        
        double h = (1.20 * (d / 2));
        
        armor.getElements().add(new LineTo(START_X - (1.25 * (d / 2)), START_Y - (3 * h / 4)));//A
        armor.getElements().add(new LineTo(START_X - (1.25 * (d / 2)) + (2 * h / 4), START_Y - h));//B
        armor.getElements().add(new LineTo(START_X - (1.25 * (d / 2)) + (2 * h / 4) + (3 * 2 * h / 4), START_Y - h));//C
        armor.getElements().add(new QuadCurveTo(START_X + 1.3 * h, START_Y, START_X - (1.25 * (d / 2)) + (2 * h / 4) + (3 * 2 * h / 4), START_Y + h));//B

        armor.getElements().add(new LineTo(START_X - (1.25 * (d / 2)) + (2 * h / 4), START_Y + h));//E
        armor.getElements().add(new LineTo(START_X - (1.25 * (d / 2)), START_Y + h - (h / 4)));
        armor.getElements().add(new LineTo(START_X - (1.25 * (d / 2)), START_Y));
        
        LinearGradient armorGradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.REFLECT, new Stop(0, Color.web("#0288D1")), new Stop(1, Color.web("#212121")));
        armor.setFill(armorGradient);
        armor.setStroke(Color.web("#727272"));
        
        root.getChildren().add(armor);
        
        for (double d : angle) {
            double x = a + r * Math.cos(d);
            double y = b + r * Math.sin(d);
            points.add(new Point(x, y));
        }
        
        for (int i = 0; i < points.size(); i++) {
            Path body = new Path();
            body.getElements().add(new MoveTo(a, b));
            Point p1 = points.get(i);
            if (i != 7) {
                Point p2 = points.get(i + 1);
                
                body.getElements().add(new LineTo(p1.getX(), p1.getY()));
                body.getElements().add(new LineTo(p2.getX(), p2.getY()));
                
                Point mid = new Point();
                mid.setX((p1.getX() + p2.getX()) / 2);
                mid.setY((p1.getY() + p2.getY()) / 2);
                
                body.getElements().add(new LineTo(a, b));
                
            } else {
                Point p2 = points.get(0);
                
                body.getElements().add(new LineTo(p1.getX(), p1.getY()));
                body.getElements().add(new LineTo(p2.getX(), p2.getY()));
                double midY = p2.getY() - p1.getY();
                System.out.println("midY: " + midY);
                
                body.getElements().add(new LineTo(a, b));
                
            }
            body.setFill(Color.GRAY);
            body.setStroke(Color.GRAY);
            
            root.getChildren().add(body);
            pathList.add(body);
        }
        
        KeyFrame frame = new KeyFrame(Duration.millis(70), d -> {
            if (i != 0) {
                Path old1 = pathList.get(i - 1);
                old1.setFill(Color.GRAY);
                Path old2 = pathList.get(i - 1 + 4);
                old2.setFill(Color.GRAY);
            } else {
                Path old1 = pathList.get(3);
                old1.setFill(Color.GRAY);
                Path old2 = pathList.get(7);
                old2.setFill(Color.GRAY);
            }
            
            Path p1 = pathList.get(i);
            p1.setFill(Color.web("#B3E5FC"));
            Path p2 = pathList.get(i + 4);
            p2.setFill(Color.web("#B3E5FC"));
            i++;
            if (i > 3) {
                i = 0;
            }
        });
        time = new Timeline(frame);
        time.setCycleCount(Timeline.INDEFINITE);
        
        Circle outer = new Circle(START_X, START_Y, 1.10 * (d / 2), Color.BLACK);
        outer.setStrokeWidth(2);
        outer.setFill(null);
        outer.setStroke(Color.web("#212121"));
        
        Circle border = new Circle(START_X, START_Y, r, Color.BLACK);
        border.setStrokeWidth(outer.getStrokeWidth() * 4);
        border.setFill(null);
        border.setStroke(Color.web("#212121"));
        
        root.getChildren().addAll(outer, border);
        this.getChildren().setAll(root);
    }
    
    public void run() {
        time.play();
    }
    
    public void stop() {
        time.stop();
        pathList.stream().forEach((node) -> {
            ((Path) node).setFill(Color.GRAY);
        });
    }
}
