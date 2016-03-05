/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes;

import com.controls.pipes.curves.Curve;
import com.controls.pipes.devices.compuerta.Compuerta;
import com.controls.pipes.devices.watermeter.WaterMeter;
import com.controls.pipes.rect.Rect;
import com.controls.pipes.tpipe.TPipe;
import com.controls.util.PipeElement;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author marcoisaac
 */
public class PipePath {

    private final Map<String, Node> nodeMap = new HashMap<>();
    private final DoubleProperty startXproperty = new SimpleDoubleProperty(0);
    private final DoubleProperty startYproperty = new SimpleDoubleProperty(0);
    private Group root;

    private double W = 61;
    private double H = 100;
    private double T = 20;

    private double AV = 1;
    private double EDGE = AV - 1;

    public PipePath(Group root, double startX, double startY) {
        this.root = root;
        startXproperty.set(startX);
        startYproperty.set(startY);
    }

    public void add(PipeElement element) {

        switch (element.getTypeElement()) {
            case CURVE:
                buildCurve((CurveElement) element);
                break;
            case T_ELEMENT:
                buildTElement((TElement) element);
                break;
            case RECT:
                buildRect((RectElement) element);
                break;
            case DEVICE:
                buildDevice((DeviceElement) element);
                break;
        }
    }

    private void buildDevice(DeviceElement deviceElement) {
        switch (deviceElement.getDeviceType()) {
            case WATER_METER:
                WaterMeter device = new WaterMeter(deviceElement.getDirection(), startXproperty.get(), startYproperty.get(), W, H, T, AV);
                startXproperty.set(device.getEndPoint().getX());
                startYproperty.set(device.getEndPoint().getY());
                root.getChildren().add(device);
                if (deviceElement.getKey() != null) {
                    nodeMap.put(deviceElement.getKey(), device);
                }

                break;
            case COMPUERTA:
                Compuerta dev = new Compuerta(deviceElement.getDirection(), startXproperty.get(), startYproperty.get(), W, H, T, AV);
                startXproperty.set(dev.getEndPoint().getX());
                startYproperty.set(dev.getEndPoint().getY());
                root.getChildren().add(dev);
                if (deviceElement.getKey() != null) {
                    nodeMap.put(deviceElement.getKey(), dev);
                }
                break;
        }

        jointPoint();

    }

    private void buildTElement(TElement element) {
        //double startX, double startY, double W , double H, double T, double AV
        TPipe curve = new TPipe(startXproperty.get(), startYproperty.get(), W, H, T, AV);

        /*startXproperty.set(curve.getEndPoint().getX());
        startYproperty.set(curve.getEndPoint().getY());

        Circle circle = new Circle(startXproperty.get(), startYproperty.get(), 7);
        circle.setFill(Color.RED);

        root.getChildren().add(circle);*/
        root.getChildren().add(curve);
        //nodeList.add(curve);
    }

    private void buildRect(RectElement element) {
        Rect rect = new Rect(element.getDirection(), startXproperty.get(), startYproperty.get(), element.getLength(), W, H, T, AV);
        startXproperty.set(rect.getEndPoint().getX());
        startYproperty.set(rect.getEndPoint().getY());

        jointPoint();
        root.getChildren().add(rect);
        //nodeList.add(rect);
    }

    private void jointPoint() {
        Circle circle = new Circle(startXproperty.get(), startYproperty.get(), 2);
        circle.setFill(Color.RED);

        root.getChildren().add(circle);
    }

    private void buildCurve(CurveElement element) {

        Curve curve = new Curve(this, element.getDirection(), startXproperty.get(), startYproperty.get(), W, H, T, AV);
        startXproperty.set(curve.getEndPoint().getX());
        startYproperty.set(curve.getEndPoint().getY());

        jointPoint();
        root.getChildren().add(curve);

        if (element.getKey() != null) {
            nodeMap.put(element.getKey(), curve);
        }
    }

    // GETTER-SETTERS
    private double getStartXproperty() {
        return startXproperty.get();
    }

    public void setStartXproperty(double value) {
        startXproperty.set(value);
    }

    private DoubleProperty startXpropertyProperty() {
        return startXproperty;
    }

    private double getStartYproperty() {
        return startYproperty.get();
    }

    public void setStartYproperty(double value) {
        startYproperty.set(value);
    }

    private DoubleProperty startYpropertyProperty() {
        return startYproperty;
    }

    public void setW(double W) {
        this.W = W;
    }

    public void setH(double H) {
        this.H = H;
    }

    public Map<String, Node> getNodeMap() {
        return nodeMap;
    }

}
