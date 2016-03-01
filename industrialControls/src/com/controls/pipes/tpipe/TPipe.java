/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes.tpipe;

import com.controls.pipes.tpipe.skins.TPipeSkin;
import com.controls.util.CurveDirection;
import com.controls.util.Point;
import com.controls.util.PointLocator;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 *
 * @author marcoisaac
 */
public class TPipe extends Control implements PointLocator {

    private Point endPoint;
    private double startX;
    private double startY;
    private double H, W, T, AV;
    private double EDGE;
    private CurveDirection direction;

    public TPipe(double startX, double startY, double W , double H, double T, double AV) {
        this.startX = startX;
        this.startY = startY;
        this.H = H;
        this.W = W;
        this.T = T;
        this.AV = AV;
        
        // CALCULAR ENDPOINT
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new TPipeSkin(this);
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

    public CurveDirection getDirection() {
        return direction;
    }

}
