/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes;

import com.controls.util.DeviceType;
import com.controls.util.ElementType;
import com.controls.util.PipeElement;
import com.controls.util.RectDirection;

/**
 *
 * @author marcoisaac
 */
public class DeviceElement implements PipeElement {

    private RectDirection direction;
    private final ElementType type = ElementType.DEVICE;
    private double length = 0;
    private DeviceType deviceType;
    private String key;

    public DeviceElement(RectDirection direction, DeviceType deviceType) {
        this.direction = direction;
        this.deviceType = deviceType;
    }

    public DeviceElement(RectDirection direction, DeviceType deviceType, String key) {
        this.direction = direction;
        this.deviceType = deviceType;
        this.key = key;
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

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public String getKey() {
        return key;
    }

}
