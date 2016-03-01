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
public class TElement implements PipeElement {

    private final ElementType type = ElementType.T_ELEMENT;

    public TElement() {

    }

    @Override
    public CurveDirection getDirection() {
        return null;
    }

    @Override
    public ElementType getTypeElement() {
        return type;
    }

}
