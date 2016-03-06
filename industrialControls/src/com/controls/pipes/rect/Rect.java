/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes.rect;

import com.controls.pipes.rect.skins.RectHorizontal;
import com.controls.pipes.rect.skins.RectVertical;
import com.controls.util.Point;
import com.controls.util.PointLocator;
import com.controls.util.RectDirection;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 *
 * @author marcoisaac
 */
public class Rect extends Control implements PointLocator {

    private Point endPoint;
    private double startX;
    private double startY;
    private double H, W, T, AV;
    private double EDGE;
    private double length;
    private RectDirection direction;

    public Rect(RectDirection direction, double startX, double startY, double Length, double W, double H, double T, double AV) {
        this.startX = startX;
        this.startY = startY;
        this.direction = direction;
        this.H = H;
        this.W = W;
        this.T = T;
        this.AV = AV;
        this.EDGE = AV - 1;
        this.length = Length;
        /// calculate end point here.
        // TOP_DOWN, DOWN_TOP, LEFT_RIGHT, RIGHT_LEFT
        double EndPointX = 0;
        double EndPointY = 0;
        switch (direction) {
            case TOP_DOWN:
                //
                EndPointX = this.startX - EDGE * T + EDGE * T;
                ;

                EndPointY = startY + H / 8 + H - 2 * H / 8 + Length + (H / 8);
                this.endPoint = new Point(EndPointX, EndPointY);
                break;
            case DOWN_TOP:
                //
                double aX = startX + (EDGE * T);
                EndPointX = aX - (EDGE * T);
                //
                double bY = startY - (H / 8);
                double restanteY = H - (2 * H / 8) + Length;
                double c = bY - restanteY;
                EndPointY = c - (H / 8);

                this.endPoint = new Point(EndPointX, EndPointY);
                break;
            case LEFT_RIGHT:
                //
                double q = startX + (H / 8);
                double restanteX = W - (H / 8);
                EndPointX = q + restanteX + (H / 8) + Length;

                //
                EndPointY = startY - (EDGE * T) + (EDGE * T);
                this.endPoint = new Point(EndPointX, EndPointY);
                break;
            case RIGHT_LEFT:
                //
                double endX = startX;
                double endY = this.startY;

                double c1 = (H / 8);
                double x = startX + c1 + W - c1 + length + c1;
                EndPointX = endX - c1 - W + c1 - length - c1;

                double y = startY - (EDGE * T) + (EDGE * T);
                EndPointY = endY;
                this.endPoint = new Point(EndPointX, EndPointY);
                break;
            default:
                break;
        }
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        switch (direction) {
            case TOP_DOWN:
                return new RectVertical(this, direction);
            case DOWN_TOP:
                return new RectVertical(this, direction);
            case LEFT_RIGHT:
                return new RectHorizontal(this, direction);
            case RIGHT_LEFT:
                return new RectHorizontal(this, direction);//new
            default:
                return null;
        }
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

    public double getLength() {
        return length;
    }

    public void setW(double W) {
        this.W = W;
    }

}
