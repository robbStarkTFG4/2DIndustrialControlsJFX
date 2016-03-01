/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes;

import com.controls.util.CurveDirection;
import com.controls.util.ElementType;
import com.controls.util.PipeElement;
import com.controls.util.RectDirection;

/**
 *
 * @author marcoisaac
 */
public class RectElement implements PipeElement {

    private RectDirection direction;
    private final ElementType type = ElementType.RECT;
    private double length = 0;

    public RectElement(RectDirection direction, double length) {
        this.direction = direction;
        this.length = length;
    }

    @Override
    public ElementType getTypeElement() {
        return type;
    }

    @Override
    public RectDirection getDirection() {
        return direction;
    }

    public double getLength() {
        return length;
    }

}
