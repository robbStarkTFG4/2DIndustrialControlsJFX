/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes.devices.compuerta;

import com.controls.pipes.devices.compuerta.skins.CompuertaSkin;
import com.controls.util.Point;
import com.controls.util.PointLocator;
import com.controls.util.RectDirection;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 *
 * @author marcoisaac
 */
public class Compuerta extends Control implements PointLocator {

    private Point endPoint;
    private double startX;
    private double startY;
    private double H, W, T, AV;
    private double EDGE;
    private RectDirection direction;
    private CompuertaSkin skin;
    private boolean open = false;

    public Compuerta(RectDirection direction, double startX, double startY, double W, double H, double T, double AV) {
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

        //double aY = this.startY - (H / 8);
        //double controlHeight = H / 14;
        //double bY = (startY - (H / 8)) - (H / 12);
        //double gY = ((startY - (H / 8)) - (H / 12)) - (H / 14);
        //double twoBase = 2 * (H / 8);
        //double hY = (((startY - (H / 8)) - (H / 12)) - (H / 14)) - (2 * (H / 8));
        double lY = ((((startY - (H / 8)) - (H / 12)) - (H / 14)) - (2 * (H / 8))) - (H / 14) - (H / 12);
        double endY = lY - (H / 8);

        this.endPoint = new Point(endX, endY);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        skin = new CompuertaSkin(this);
        return skin;
    }

    public void open() {
        skin.openState();
        this.open = true;
    }

    public void close() {
        skin.closeState();
        this.open = false;
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

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

}
