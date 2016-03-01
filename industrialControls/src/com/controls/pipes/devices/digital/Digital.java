/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes.devices.digital;

import com.controls.pipes.devices.digital.skins.DigitalSkin;
import com.controls.util.RectDirection;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 *
 * @author marcoisaac
 */
public class Digital extends Control {

    private double startX;
    private double startY;
    private double H, W;
    private double EDGE;
    private RectDirection direction;
    private DigitalSkin skin;
    private boolean state = false;

    public Digital(double startX, double startY, double W, double H) {
        this.startX = startX;
        this.startY = startY;
        this.H = H;
        this.W = W;

    }

    @Override
    protected Skin<?> createDefaultSkin() {
        skin = new DigitalSkin(this);
        return skin;
    }

    public void activaded() {

        if (state == false) {
            skin.ActivadedState();
            state = true;
        }
    }

    public void deactivated() {
        if (state == true) {
            skin.deActivadedState();
            state = false;
        }
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

    public double getEDGE() {
        return EDGE;
    }

    public RectDirection getDirection() {
        return direction;
    }
}
