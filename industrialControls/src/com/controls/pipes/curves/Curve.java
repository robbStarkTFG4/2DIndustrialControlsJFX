/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes.curves;

import com.controls.pipes.PipePath;
import com.controls.pipes.curves.skins.CurveFour;
import com.controls.pipes.curves.skins.CurveOne;
import com.controls.pipes.curves.skins.CurveThree;
import com.controls.pipes.curves.skins.CurveTwo;
import com.controls.util.CurveDirection;
import com.controls.util.Drawer;
import com.controls.util.Point;
import com.controls.util.PointLocator;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 *
 * @author marcoisaac
 */
public class Curve extends Control implements PointLocator {

    private Point endPoint;
    private double startX;
    private double startY;
    private double H, W, T, AV;
    private double EDGE;
    private CurveDirection direction;
    private Drawer drawer;
    private PipePath path;

    public Curve() {
        System.out.println("constructor");
    }

    public Curve(PipePath path, CurveDirection direction, double startX, double startY, double W, double H, double T, double AV) {
        System.out.println("constructor");
        this.path = path;
        this.startX = startX;
        this.startY = startY;
        this.direction = direction;
        this.H = H;
        this.W = W;
        this.T = T;
        this.AV = AV;
        this.EDGE = AV - 1;

        findEndPoint(direction, W, H, startX, T, startY, AV);
    }

    private void findEndPoint(CurveDirection direction1, double W1, double H1, double startX1, double T1, double startY1, double AV1) {
        double restanteX;
        double EndPointX;
        double EndPointY;
        double restanteY;
        switch (direction1) {
            case LEFT_BOTTOM:
                //
                restanteX = W1 - (H1 / 8);
                double d = 0.60 * restanteX;
                double a = 0.40 * restanteX;
                double bottomNeckXLeft = startX1 + (H1 / 8) + d + a - T1;
                EndPointX = bottomNeckXLeft - (T1 * EDGE);
                ///
                double alpha = (startY1 + (EDGE * T1) / 2) + (2.4 * T1);
                double freeLeft = H1 - (alpha - startY1 - (H1 / 8));
                double bottomNeckYLeft = (startY1 + (EDGE * T1) / 2) + (2.4 * T1) + freeLeft;
                EndPointY = bottomNeckYLeft + (H1 / 8);
                this.endPoint = new Point(EndPointX, EndPointY);
                break;
            case RIGHT_TOP:
                double q = startX1 + (H1 / 8);
                restanteX = W1 - (q - startX1) - T1;
                double z = q + (restanteX / 2);
                double x = z + (restanteX / 2);
                EndPointX = x - (EDGE * T1);
                //START_Y + (EDGE * T) - (restanteY) - (H / 8)
                restanteY = H1 - ((startY1 + (T1 * AV1)) - (startY1 + (EDGE * T1))) - (H1 / 8);
                EndPointY = startY1 + (EDGE * T1) - (restanteY) - (H1 / 8);
                this.endPoint = new Point(EndPointX, EndPointY);
                break;
            case RIGHT_BOTTOM:
                double endX3 = startX1;
                double endY3 = startY1;
                EndPointX = endX3 - W1 - 2 * (H1 / 8);
                double g1 = H1 / 8;
                EndPointY = endY3 + g1 + (3 * (H1 - g1) / 4) + (2 * (H1 - g1) / 4) + (EDGE * T1);
                this.endPoint = new Point(EndPointX, EndPointY);
                /* THIS SHOULD BE ADDED AGAIN
                double E = startX1 + (EDGE * T1);
                restanteX = W1 - ((E + (3 * T1)) - startX1 - (H1 / 8));
                double neck2StartX = E + (3 * T1) + restanteX;
                EndPointX = neck2StartX + (H1 / 8);
                
                double K = startY1 - (H1 / 8);
                restanteY = H1 - (H1 / 8);
                double neck2StartY = K - (3 * restanteY / 4) - (2 * restanteY / 4);
                EndPointY = neck2StartY - (EDGE * T1);
                this.endPoint = new Point(EndPointX, EndPointY);*/
                break;
            case LEFT_TOP:
                /*
                double b = startX1 + T1 * 2;
                double restante = b - startX1 - (H1 / 8);
                EndPointX = startX1 + T1 * 2 + restante + (H1 / 8);
                
                double g = startY1 + (H1 / 8);
                EndPointY = g + 2 * (4 * H1 / 8) - T1 - (EDGE * T1);
                this.endPoint = new Point(EndPointX, EndPointY);*/
                double q1 = startX1 + (H1 / 8);
                restanteX = W1 - (q1 - startX1) - T1;
                double z1 = q1 + (restanteX / 2);
                double x1 = z1 + (restanteX / 2);
                EndPointX = x1 - (EDGE * T1);
                //START_Y + (EDGE * T) - (restanteY) - (H / 8)
                restanteY = H1 - ((startY1 + (T1 * AV1)) - (startY1 + (EDGE * T1))) - (H1 / 8);
                EndPointY = startY1 + (EDGE * T1) - (restanteY) - (H1 / 8);
                this.endPoint = new Point(EndPointX, EndPointY);
                break;
            case BOTTOM_LEFT:
                double endX = startX1;
                double endY = startY1;
                double c3 = H1 / 8;
                double x2 = startX1 + c3 + (0.60 * (W1 - c3)) + (0.40 * (W1 - c3)) - T1 - (T1 * EDGE);
                EndPointX = endX - c3 - (0.60 * (W1 - c3)) - (0.40 * (W1 - c3)) + T1 + (T1 * EDGE);
                double c1 = (EDGE * T1) / 2;
                double c2 = 2 * T1;
                EndPointY = endY - c1 - c2 - H1 + c1 + c2 - 2 * c3;
                this.endPoint = new Point(EndPointX, EndPointY);
                break;
            case LEFT_UP_2://
                //
                double endX2 = startX1;
                double endY2 = startY1;
                EndPointX = endX2 - W - 2 * (H / 8);

                EndPointY = endY2 - (H / 8) - 2 * (4 * H / 8) + T + (EDGE * T);

                this.endPoint = new Point(EndPointX, EndPointY);
                break;
            /* case LEFT_DOWN_2:
                // curva 4
                //
                double endX3 = startX1;
                double endY3 = startY1;
                EndPointX = endX3 - W1 - 2 * (H1 / 8);
                double g1 = H1 / 8;
                EndPointY = endY3 + g1 + (3 * (H1 - g1) / 4) + (2 * (H1 - g1) / 4) + (EDGE * T1);
                this.endPoint = new Point(EndPointX, EndPointY);
                break;*/
            case BOTTOM_RIGHT:
                //
                double E1 = startX1 + (EDGE * T1);
                restanteX = W1 - ((E1 + (3 * T1)) - startX1 - (H1 / 8));
                double neck2StartX1 = E1 + (3 * T1) + restanteX;
                EndPointX = neck2StartX1 + (H1 / 8);
                //
                double K1 = startY1 - (H1 / 8);
                restanteY = H1 - (H1 / 8);
                double neck2StartY1 = K1 - (3 * restanteY / 4) - (2 * restanteY / 4);
                EndPointY = neck2StartY1 - (EDGE * T1);
                this.endPoint = new Point(EndPointX, EndPointY);
                break;
            case TOP_LEFT:
                EndPointX = startX - W + T + EDGE * T;
                EndPointY = startY + H - T * AV;
                this.endPoint = new Point(EndPointX, EndPointY);
                break;
            case TOP_RIGHT:
                //

                double b = startX1 + T1 * 2;
                double restante = b - startX1 - (H1 / 8);
                EndPointX = startX1 + T1 * 2 + restante + (H1 / 8);

                double g = startY1 + (H1 / 8);
                EndPointY = g + 2 * (4 * H1 / 8) - T1 - (EDGE * T1);
                this.endPoint = new Point(EndPointX, EndPointY);
                break;
        }
    }

    // RIGHT_DOWN, RIGHT_UP, LEFT_DOWN, LEFT_UP
    @Override
    protected Skin<?> createDefaultSkin() {
        System.out.println("crea skin DSADASDASDASD");
        switch (direction) {
            case LEFT_BOTTOM:
                CurveOne curve = new CurveOne(this, direction);
                drawer = curve;
                return curve;

            case RIGHT_TOP:
                CurveThree curve1 = new CurveThree(this, direction);
                drawer = curve1;
                return curve1;

            case RIGHT_BOTTOM:
                CurveFour curve2 = new CurveFour(this, direction);
                drawer = curve2;
                return curve2;

            case LEFT_TOP:
                //CurveTwo curve3 = new CurveTwo(this, direction);
                //drawer = curve3;
                CurveThree curve3 = new CurveThree(this, direction);
                drawer = curve3;
                return curve3;

            case BOTTOM_LEFT:
                CurveOne curve4 = new CurveOne(this, direction);
                drawer = curve4;
                return curve4;

            case LEFT_UP_2:
                CurveTwo curve5 = new CurveTwo(this, direction);
                drawer = curve5;
                return curve5;

            /*case LEFT_DOWN_2:
                CurveFour curve6 = new CurveFour(this, direction);
                drawer = curve6;
                return curve6;*/
            case BOTTOM_RIGHT:
                CurveFour curve9 = new CurveFour(this, direction);
                drawer = curve9;
                return curve9;

            case TOP_LEFT:
                CurveThree curve10 = new CurveThree(this, direction);
                drawer = curve10;
                return curve10;
            case TOP_RIGHT:
                CurveTwo curve99 = new CurveTwo(this, direction);
                drawer = curve99;
                return curve99;
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

    public void setW(double W) {
        this.W = W;
        //drawer.redraw();
        findEndPoint(direction, W, H, startX, T, startY, AV);
        path.setStartXproperty(this.endPoint.getX());
        path.setStartYproperty(this.endPoint.getY());
    }

}
