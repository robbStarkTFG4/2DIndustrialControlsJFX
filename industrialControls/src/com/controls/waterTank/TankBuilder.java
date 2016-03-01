/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.waterTank;

import java.util.HashMap;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Dimension2D;
import javafx.scene.paint.Color;

/**
 *
 * @author marcoisaac
 */
public class TankBuilder<B extends TankBuilder<B>> {

    private final HashMap<String, Property> properties = new HashMap<>();

    protected TankBuilder() {
    }

    public final static TankBuilder create() {
        return new TankBuilder();
    }

    public final TankBuilder styleClass(final String STYLE_CLASS) {
        properties.put("styleClass", new SimpleStringProperty(STYLE_CLASS));
        return this;
    }

    public final TankBuilder waterColor(final Color WATER_COLOR) {
        properties.put("waterColor", new SimpleObjectProperty<>(WATER_COLOR));
        return this;
    }

    public final TankBuilder tankBorderColor(final Color BORDER_COLOR) {
        properties.put("borderColor", new SimpleObjectProperty<>(BORDER_COLOR));
        return this;
    }

    public final B showMarkers(final boolean SHOW) {
        properties.put("markers", new SimpleBooleanProperty(SHOW));
        return (B) this;
    }

    public final B hasAlarm(final boolean SHOW) {
        properties.put("hasAlarm", new SimpleBooleanProperty(SHOW));
        return (B) this;
    }

    public final B showScale(final boolean SHOW) {
        properties.put("hasScale", new SimpleBooleanProperty(SHOW));
        return (B) this;
    }

    public final B prefSize(final double WIDTH, final double HEIGHT) {
        properties.put("prefSize", new SimpleObjectProperty<>(new Dimension2D(WIDTH, HEIGHT)));
        return (B) this;
    }

    public final B prefWidth(final double PREF_WIDTH) {
        properties.put("prefWidth", new SimpleDoubleProperty(PREF_WIDTH));
        return (B) this;
    }

    public final B prefHeight(final double PREF_HEIGHT) {
        properties.put("prefHeight", new SimpleDoubleProperty(PREF_HEIGHT));
        return (B) this;
    }

    public final B translateX(final double TRANSLATE_X) {
        properties.put("translateX", new SimpleDoubleProperty(TRANSLATE_X));
        return (B) this;
    }

    public final B translateY(final double TRANSLATE_Y) {
        properties.put("translateY", new SimpleDoubleProperty(TRANSLATE_Y));
        return (B) this;
    }

    public final B alarm(final double ALARM) {
        properties.put("alarm", new SimpleDoubleProperty(ALARM));
        return (B) this;
    }

    public final B alarmValue(final double VALUE) {
        properties.put("alarmVal", new SimpleDoubleProperty(VALUE));
        return (B) this;
    }

    public final Tank build() {
        final Tank CONTROL = new Tank();
        properties.keySet().stream().forEach((key) -> {
            if (null != key) {
                switch (key) {
                    case "prefSize":
                        Dimension2D dim = ((ObjectProperty<Dimension2D>) properties.get(key)).get();
                        CONTROL.setTankSize(dim.getWidth(), dim.getHeight());
                        break;
                    case "prefWidth":
                        CONTROL.setPrefWidth(((DoubleProperty) properties.get(key)).get());
                        break;
                    case "prefHeight":
                        CONTROL.setPrefHeight(((DoubleProperty) properties.get(key)).get());
                        break;
                    case "translateX":
                        CONTROL.setTranslateX(((DoubleProperty) properties.get(key)).get());
                        break;
                    case "translateY":
                        CONTROL.setTranslateY(((DoubleProperty) properties.get(key)).get());
                        break;

                    case "waterColor":
                        CONTROL.setWaterColor(((ObjectProperty<Color>) properties.get(key)).get());
                        break;
                    case "borderColor":
                        CONTROL.setTankBorderColor(((ObjectProperty<Color>) properties.get(key)).get());
                        break;
                    case "markers":
                        CONTROL.setShowMarkers(((SimpleBooleanProperty) properties.get(key)).get());
                        break;
                    case "alarmVal":
                        CONTROL.setAlarmValue(((DoubleProperty) properties.get(key)).get());
                        break;
                    case "hasAlarm":
                        CONTROL.setHasAlarm(((SimpleBooleanProperty) properties.get(key)).get());
                        break;
                    case "hasScale":
                        CONTROL.setShowScale(((SimpleBooleanProperty) properties.get(key)).get());
                        break;
                }
            }
        });
        return CONTROL;
    }
}
