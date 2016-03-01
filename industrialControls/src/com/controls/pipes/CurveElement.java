/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes;

import com.controls.util.CurveDirection;
import com.controls.util.ElementType;
import com.controls.util.PipeElement;

/**
 *
 * @author marcoisaac
 */
public class CurveElement implements PipeElement {

    private CurveDirection direction;
    private final ElementType type = ElementType.CURVE;
    private String key;

    public CurveElement(CurveDirection direction) {
        this.direction = direction;
    }

    public CurveElement(CurveDirection direction, String key) {
        this.direction = direction;
        this.key = key;
    }

    @Override
    public CurveDirection getDirection() {
        return direction;
    }

    @Override
    public ElementType getTypeElement() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
