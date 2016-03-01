/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes.devices.pump1;

import com.controls.pipes.devices.pump1.skins.Pump1Skin;
import com.controls.util.Point;
import com.controls.util.PointLocator;
import com.controls.util.RectDirection;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 *
 * @author marcoisaac
 */
public class Pump1 extends Control implements PointLocator {

    private static int noOfNodes = 0;
    private double startX = 200;
    private double startY = 200;
    private double AV = 1.15;
    private double EDGE = AV - 1;
    private double W = 30; //witdh
    private double H = 30; //height
    private double T = 7; // pipe diameter

    private RectDirection direction;
    private final Point endPoint = null;

    private Pump1Skin skin;
    private boolean running = false;

    public Pump1(RectDirection direction, double startX, double startY, double W, double H, double T, double AV) {
        this.startX = startX;
        this.startY = startY;
        this.H = H;
        this.W = W;
        this.AV = AV;

        this.direction = direction;
        // CALCULAR ENDPOINT
        double aX = startX + (T * AV) - (EDGE * T);
        double endX = aX - T;

        //double aY = startY - (H / 8);
        //double restanteY = H - (2 * ((H / 8))) - (2 * (H / 12)) - (2 * (H / 14));
        //double bY = (startY - (H / 8)) - (H / 12);
        //double hY = ((startY - (H / 8)) - (H / 12)) - (H / 14) - (H - (2 * ((H / 8))) - (2 * (H / 12)) - (2 * (H / 14)));
        //double lY = hY - (H / 14) - (H / 12);
        //double endY = lY - (H / 8);
        //this.endPoint = new Point(endX, endY);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        skin = new Pump1Skin(this);
        return skin;
    }

    public void startPump() {
        skin.run();
        running = true;
    }

    public void stopPump() {
        skin.stop();
        running = false;
    }

    @Override
    public Point getEndPoint() {
        return endPoint;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getH() {
        return H;
    }

    public double getW() {
        return W;
    }

    public double getT() {
        return T;
    }

    public double getAV() {
        return AV;
    }

    public double getEDGE() {
        return EDGE;
    }

    public RectDirection getDirection() {
        return direction;
    }

    public boolean isRunning() {
        return running;
    }

}
