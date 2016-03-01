/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes.devices.watermeter;

import com.controls.pipes.devices.watermeter.skins.WaterMeterSkin;
import com.controls.util.Point;
import com.controls.util.PointLocator;
import com.controls.util.RectDirection;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 *
 * @author marcoisaac
 */
public class WaterMeter extends Control implements PointLocator {

    private Point endPoint;
    private double startX;
    private double startY;
    private double H, W, T, AV;
    private double EDGE;
    private RectDirection direction;
    private WaterMeterSkin skin;

    public WaterMeter(RectDirection direction, double startX, double startY, double W, double H, double T, double AV) {
        this.startX = startX;
        this.startY = startY;
        this.H = H;
        this.W = W;
        this.T = T;
        this.AV = AV;
        this.direction = direction;
        // CALCULAR ENDPOINT
        double aX = startX + (T * AV) - (EDGE * T);
        double endX = aX - T;

        double hY = ((startY - (H / 8)) - (H / 12)) - (H / 14) - (H - (2 * ((H / 8))) - (2 * (H / 12)) - (2 * (H / 14)));
        double lY = hY - (H / 14) - (H / 12);
        double endY = lY - (H / 8);

        this.endPoint = new Point(endX, endY);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        skin = new WaterMeterSkin(this);
        return skin;
    }

    public void fadeFlowSign() {
        skin.fadeFlowOut();
    }

    public void fadeFlowInSign() {
        skin.fadeNoFlowOut();
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

}
