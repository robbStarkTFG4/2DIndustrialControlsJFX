/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.waterTank;

import com.sun.javafx.css.converters.PaintConverter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 *
 * @author marcoisaac
 */
public class Tank extends Control {

    public final SimpleDoubleProperty levelHeight = new SimpleDoubleProperty(0);

    public final SimpleDoubleProperty target = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty currentValue = new SimpleDoubleProperty(0);

    public final SimpleDoubleProperty alarmControlLevel = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty alarmValue = new SimpleDoubleProperty(0);
    private final DoubleProperty minAlarmValue = new SimpleDoubleProperty(10);

    private final DoubleProperty heightProperty = new SimpleDoubleProperty(0); // esto determina los calculos
    private final DoubleProperty widthProperty = new SimpleDoubleProperty(0);

    public static final Color DEFAULT_TANK_COLOR = Color.BLUE;
    public static final Color DEFAULT_TANK_BORDER_COLOR = Color.BLACK;
    public static final Color DEFAULT_WATER_COLOR = Color.YELLOWGREEN;

    private ObjectProperty<Paint> tankColorProperty;
    private ObjectProperty<Paint> tankBorderColorProperty;
    private ObjectProperty<Paint> waterColorProperty;

    private TankSkin skin;

    private boolean showMarkers = false;
    private boolean hasAlarm = false;
    private boolean showScale = true;

    // Range properties
    private final DoubleProperty maxValueProperty = new SimpleDoubleProperty(100);

    public Tank() {
    }

    public Double getMaxTankMaxValue() {
        return maxValueProperty.get();
    }

    public void setMaxValueProperty(int value) {
        maxValueProperty.set(value);
    }

    public DoubleProperty getMaxValueProperty() {
        return maxValueProperty;
    }

    //tank border color
    public Paint getTankBorderColor() {
        return null == tankBorderColorProperty ? DEFAULT_TANK_BORDER_COLOR : tankBorderColorProperty.get();
    }

    public void setTankBorderColor(Paint value) {
        tankBorderColorProperty().set(value);
    }

    public ObjectProperty tankBorderColorProperty() {
        if (tankBorderColorProperty == null) {
            tankBorderColorProperty = new StyleableObjectProperty<Paint>() {
                @Override
                public Object getBean() {
                    return Tank.this;
                }

                @Override
                public String getName() {
                    return "tankBorderColor";
                }

                @Override
                public CssMetaData<? extends Styleable, Paint> getCssMetaData() {
                    return StyleableProperties.TANK_BORDER_COLOR;
                }
            };
        }
        return tankBorderColorProperty;
    }

    public void setWaterLevel(double val) {
        double valor = skin.transitive(val);
        //if (valor >= 0) {
        target.set(valor);
        skin.startWaterAnimation();
        //}

    }
    //end tank border color

    ////// EVENT HANDLING
    public final ObjectProperty<EventHandler<MarkerEvent>> onMarkerExceededProperty() {
        return onMarkerExceeded;
    }

    public final EventHandler<MarkerEvent> getOnMarkerExceeded() {
        return onMarkerExceededProperty().get();
    }
    private final ObjectProperty<EventHandler<MarkerEvent>> onMarkerExceeded = new ObjectPropertyBase<EventHandler<MarkerEvent>>() {
        @Override
        public Object getBean() {
            return this;
        }

        @Override
        public String getName() {
            return "onMarkerExceeded";
        }
    };

    public final void setOnMarkerExceeded(EventHandler<MarkerEvent> value) {
        onMarkerExceededProperty().set(value);
    }

    //***
    public final ObjectProperty<EventHandler<MarkerEvent>> onMarkerUnderProperty() {
        return onMarkerUnder;
    }

    public final EventHandler<MarkerEvent> getOnMarkerUnder() {
        return onMarkerUnderProperty().get();
    }
    private final ObjectProperty<EventHandler<MarkerEvent>> onMarkerUnder = new ObjectPropertyBase<EventHandler<MarkerEvent>>() {
        @Override
        public Object getBean() {
            return this;
        }

        @Override
        public String getName() {
            return "onMarkerUnder";
        }
    };

    public final void setOnMarkerUnder(EventHandler<MarkerEvent> value) {
        onMarkerUnderProperty().set(value);
    }
    //*****

    public final ObjectProperty<EventHandler<MarkerEvent>> turnOffProperty() {
        return turnOffProperty;
    }

    public final EventHandler<MarkerEvent> getturnOffProperty() {
        return turnOffProperty().get();
    }
    private final ObjectProperty<EventHandler<MarkerEvent>> turnOffProperty = new ObjectPropertyBase<EventHandler<MarkerEvent>>() {
        @Override
        public Object getBean() {
            return this;
        }

        @Override
        public String getName() {
            return "turnOff";
        }
    };

    public final void setTurOffProperty(EventHandler<MarkerEvent> value) {
        turnOffProperty().set(value);
    }
    //---

    public static class MarkerEvent extends Event {

        public static final EventType<MarkerEvent> MARKER_EXCEEDED = new EventType(ANY, "markerExceeded");
        public static final EventType<MarkerEvent> MARKER_UNDERRUN = new EventType(ANY, "markerUnderrun");
        public static final EventType<MarkerEvent> TURN_OFF = new EventType(ANY, "turnOff");

        public MarkerEvent(final Object SOURCE, final EventTarget TARGET, EventType<MarkerEvent> TYPE) {
            super(SOURCE, TARGET, TYPE);
        }
    }

    public void fireMarkerEvent(final MarkerEvent EVENT) {
        final EventHandler<MarkerEvent> HANDLER;
        final EventType TYPE = EVENT.getEventType();
        if (MarkerEvent.MARKER_EXCEEDED == TYPE) {
            HANDLER = getOnMarkerExceeded();
        } else if (MarkerEvent.MARKER_UNDERRUN == TYPE) {
            HANDLER = getOnMarkerUnder();
            //HANDLER = null;
        } else if (MarkerEvent.TURN_OFF == TYPE) {
            HANDLER = getturnOffProperty();
            //HANDLER = null;
        } else {
            HANDLER = null;
        }

        if (null == HANDLER) {
            return;
        }

        HANDLER.handle(EVENT);
    }

    //END EVENT HANDLING
    // tank color
    public Paint getTankColor() {
        return null == tankColorProperty ? DEFAULT_TANK_COLOR : tankColorProperty.get();
    }

    public void setTankColor(Paint value) {
        tankColorProperty().set(value);
    }

    public ObjectProperty tankColorProperty() {
        if (tankColorProperty == null) {
            tankColorProperty = new StyleableObjectProperty<Paint>() {
                @Override
                public Object getBean() {
                    return Tank.this;
                }

                @Override
                public String getName() {
                    return "tankColor";
                }

                @Override
                public CssMetaData<? extends Styleable, Paint> getCssMetaData() {
                    return StyleableProperties.TANK_COLOR;
                }
            };
        }
        return tankColorProperty;
    }

    //end tank color
    // tank water color
    public Paint getWaterColor() {
        return null == waterColorProperty ? DEFAULT_WATER_COLOR : waterColorProperty.get();
    }

    public void setWaterColor(Paint value) {

        waterColorProperty().set(value);
    }

    public ObjectProperty waterColorProperty() {
        if (waterColorProperty == null) {
            waterColorProperty = new StyleableObjectProperty<Paint>() {
                @Override
                public Object getBean() {
                    return Tank.this;
                }

                @Override
                public String getName() {
                    return "waterColor";
                }

                @Override
                public CssMetaData<? extends Styleable, Paint> getCssMetaData() {
                    return StyleableProperties.WATER_COLOR;
                }
            };
        }
        return waterColorProperty;
    }

    // END tank water color
    public double getHeightProperty() {
        return heightProperty.get();
    }

    public DoubleProperty heightPropertyProperty() {
        return heightProperty;
    }

    public double getWidthProperty() {
        return widthProperty.get();
    }

    public DoubleProperty widthPropertyProperty() {
        return widthProperty;
    }

    public DoubleProperty getHeightProp() {
        return heightProperty;
    }

    public void setTankHeight(double value) {
        heightProperty.set(value);
    }

    public void setTankWidth(double value) {
        widthProperty.set(value);
    }

    public void setTankSize(double width, double height) {
        heightProperty.set(height);
        widthProperty.set(width);
    }

    public double getLevelHeight() {
        return levelHeight.get();
    }

    public SimpleDoubleProperty getLevelHeightProperty() {
        return levelHeight;
    }

    public SimpleDoubleProperty getTargetProperty() {
        return target;
    }

    public double getTarget() {
        return target.get();
    }

    public double getCurrentValue() {
        return currentValue.get();
    }

    public SimpleDoubleProperty getAlarmControlLevel() {
        return alarmControlLevel;
    }

    public boolean isShowMarkers() {
        return showMarkers;
    }

    public void setShowMarkers(boolean showMarkers) {
        this.showMarkers = showMarkers;
    }

    public boolean isHasAlarm() {
        return hasAlarm;
    }

    public void setHasAlarm(boolean hasAlarm) {
        this.hasAlarm = hasAlarm;
    }

    public SimpleDoubleProperty getAlarmValue() {
        return alarmValue;
    }

    public void setAlarmValue(double value) {
        alarmValue.set(value);
    }

    public boolean isShowScale() {
        return showScale;
    }

    public void setShowScale(boolean showScale) {
        this.showScale = showScale;
    }

    public double getMinAlarmValue() {
        return minAlarmValue.get();
    }

    public void setMinAlarmValue(double value) {
        minAlarmValue.set(value);
    }

    public DoubleProperty minAlarmValueProperty() {
        return minAlarmValue;
    }

    //css properties
    @Override
    protected Skin<?> createDefaultSkin() {
        return (skin = new TankSkin(Tank.this));
    }

    private static class StyleableProperties {

        private static final CssMetaData<Tank, Paint> TANK_COLOR
                = new CssMetaData<Tank, Paint>("-tank-color", PaintConverter.getInstance(), DEFAULT_TANK_COLOR) {

            @Override
            public boolean isSettable(Tank tank) {
                return null == tank.tankColorProperty || !tank.tankColorProperty.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(Tank tank) {
                return (StyleableProperty) tank.tankColorProperty();
            }

            @Override
            public Color getInitialValue(Tank tank) {
                return (Color) tank.getTankColor();
            }
        };

        private static final CssMetaData<Tank, Paint> TANK_BORDER_COLOR
                = new CssMetaData<Tank, Paint>("-tank-border-color", PaintConverter.getInstance(), DEFAULT_TANK_BORDER_COLOR) {

            @Override
            public boolean isSettable(Tank tank) {
                return null == tank.tankBorderColorProperty || !tank.tankBorderColorProperty.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(Tank tank) {
                return (StyleableProperty) tank.tankBorderColorProperty();
            }

            @Override
            public Color getInitialValue(Tank tank) {
                return (Color) tank.getTankBorderColor();
            }
        };

        private static final CssMetaData<Tank, Paint> WATER_COLOR
                = new CssMetaData<Tank, Paint>("-water-color", PaintConverter.getInstance(), DEFAULT_WATER_COLOR) {

            @Override
            public boolean isSettable(Tank tank) {
                return null == tank.waterColorProperty || !tank.waterColorProperty.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(Tank tank) {
                return (StyleableProperty) tank.waterColorProperty();
            }

            @Override
            public Color getInitialValue(Tank tank) {
                return (Color) tank.getWaterColor();
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(Control.getClassCssMetaData());
            Collections.addAll(styleables,
                    TANK_COLOR,
                    null
            );
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }
}
