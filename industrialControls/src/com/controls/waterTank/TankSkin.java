/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.waterTank;

import com.controls.util.NumberField;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author marcoisaac
 */
public class TankSkin extends SkinBase<Tank> implements Skin<Tank> {

    private static final double DEFAULT_HEIGHT = 100.0;
    private static final double DEFAULT_WIDTH = 75.0;

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    static double startX = 80;
    static double startY = 70;
    static double width = 180;
    static double arcHeight = 12;
    static double levelSeparation = 22;
    static double offset = 1;

    //static double minValue = 0;
    //static double maxValue = 100000;
    private Path path2;

    private DoubleBinding binding; // alarm for max value
    private DoubleBinding binding2; // alarm for min value
    private DoubleBinding AlarmControlBinding;
    private Timeline tl2;
    private Timeline tl;

    private boolean showMenus = false;
    private boolean affect = false;

    private Path pointer;

    public TankSkin(Tank control) {
        super(control);
        init();
        initGraphics();
        //registerListeners();
    }

    private void init() {

        /*if (Double.compare(getSkinnable().getPrefWidth(), 0.0) <= 0 || Double.compare(getSkinnable().getPrefHeight(), 0.0) <= 0
         || Double.compare(getSkinnable().getWidth(), 0.0) <= 0 || Double.compare(getSkinnable().getHeight(), 0.0) <= 0) {
         if (getSkinnable().getPrefWidth() > 0 && getSkinnable().getPrefHeight() > 0) {
         System.out.println("HOLAAAA!!!!!!!!!!!");
         getSkinnable().setPrefSize(getSkinnable().getPrefWidth(), getSkinnable().getPrefHeight());
         } else {
         System.out.println("DIMENSIONES DEFAULT");
         getSkinnable().setPrefSize(defaultWidth, defaultHeight);
         }
         }*/

 /* if (Double.compare(getSkinnable().getMinWidth(), 0.0) <= 0 || Double.compare(getSkinnable().getMinHeight(), 0.0) <= 0) {
         System.out.println("HOLAAAA!!!!!!!!!!!");
         getSkinnable().setMinSize(defaultWidth / 2, defaultHeight / 2);
         }

         if (Double.compare(getSkinnable().getMaxWidth(), 0.0) <= 0 || Double.compare(getSkinnable().getMaxHeight(), 0.0) <= 0) {
         System.out.println("HOLAAAA!!!!!!!!!!!");
         getSkinnable().setMaxSize(defaultWidth, defaultHeight);
         }*/
    }

    private void initGraphics() {

        Pane root = new Pane();

        startX = 0;
        startY = 0;

        if (Double.compare(getSkinnable().getHeightProperty(), 0.0) <= 0 || Double.compare(getSkinnable().getWidthProperty(), 0.0) <= 0) {

            getSkinnable().getHeightProp().set(DEFAULT_HEIGHT);
            getSkinnable().widthPropertyProperty().set(DEFAULT_WIDTH);
        }

        System.out.println("altura asignada: " + getSkinnable().getHeightProperty());

        width = getSkinnable().getWidthProperty();
        double levelStartPointY = startY;
        double levelEndPointY = startY + getSkinnable().getHeightProperty();

        double levelHeightR = levelEndPointY - levelStartPointY;

        final Path path = new Path();
        path.setStrokeWidth(1.5);
        path.getElements().add(new MoveTo(startX, startY));
        path.getElements().add(new LineTo(startX, startY + getSkinnable().getHeightProperty()));
        path.getElements().add(new CubicCurveTo(startX + width / 4, startY + getSkinnable().getHeightProperty() + arcHeight, startX + (3 * width / 4), startY + getSkinnable().getHeightProperty() + arcHeight, startX + width, startY + getSkinnable().getHeightProperty()));

        path.getElements().add(new LineTo(startX + width, startY));

        path.getElements().add(new CubicCurveTo(startX + width - width / 4, startY - arcHeight * 0.4, startX + width - (3 * width / 4), startY - arcHeight * 0.4, startX, startY));

        List<Stop> stops = new ArrayList<>();
        stops.add(new Stop(0, Color.WHITESMOKE));
        stops.add(new Stop(1, Color.web("#607D8B")));
        LinearGradient linear = new LinearGradient(startX, startY, startX + width, startY, false, CycleMethod.REFLECT, stops);

        //path.setFill(getSkinnable().getTankColor());
        path.setFill(linear);
        path.setStroke(null);///////////

        DropShadow shadow = new DropShadow(11, Color.web("#212121"));
        path.setEffect(shadow);

        //CubicCurve topCurve = new CubicCurve(startX, startY, startX + (width / 4), startY + (levelHeightR / 32), startX + (3 * width / 4), startY + (levelHeightR / 32), startX + width, startY);
        //CubicCurve topCurve = new CubicCurve(startX, startY, startX + (width / 4), startY + (levelHeightR / 18), startX + (3 * width / 4), startY + (levelHeightR / 18), startX + width, startY);
        //topCurve.setStrokeWidth(1.5);
        //topCurve.setFill(getSkinnable().getTankColor());
        //topCurve.setStrokeLineCap(StrokeLineCap.ROUND);
        //topCurve.setStroke(Color.web("#727272"));
        //topCurve.setStroke(Color.BLACK);
        double midX = startX + (((startX + width) - startX) / 2);
        double midY = startY + ((startY + (levelHeightR / 18)) - (startY * 0.85));

        RadialGradient gradient = new RadialGradient(60,
                (((startX + width) - startX) / 2) * .75,
                midX,
                midY,
                (((startX + width) - startX) / 2) * 0.60,
                false,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.BLACK),
                new Stop(1, Color.GREY));

        //topCurve.setFill(gradient);
        //----------------- new path
        Path cover = new Path();
        cover.getElements().add(new MoveTo(startX, startY));
        cover.getElements().add(new CubicCurveTo(startX + width - (3 * width / 4), startY - arcHeight * 0.4, startX + width - width / 4, startY - arcHeight * 0.4, startX + width, startY));
        cover.getElements().add(new CubicCurveTo(startX + (3 * width / 4), startY + (levelHeightR / 18), startX + (width / 4), startY + (levelHeightR / 18), startX, startY));
        cover.setFill(gradient);
        cover.setStroke(Color.web("#727272"));
        //-------------- end new path
        if (getSkinnable().isShowScale()) {
            Line level = new Line(startX - levelSeparation, startY, startX - levelSeparation, startY + getSkinnable().getHeightProperty());
            root.getChildren().add(level);

            switch (findScale(0, getSkinnable().getMaxTankMaxValue())) {
                case 1:// 100
                    int skip = 0;
                    for (int i = 0; i <= 20; i++) {
                        if (i != 0) {
                            double y = (-1 * ((i * 100) / 20) + 100);
                            double amount = (i * levelHeightR) / 20;

                            skip++;
                            if ((y % 2) == 0) {

                                if (skip == 4) {
                                    Text indi;
                                    if (y != 0) {
                                        indi = new Text(String.valueOf((int) y));
                                        indi.setTranslateX(startX - levelSeparation - (2 * levelSeparation / 4) - (0.8 * levelSeparation));
                                        indi.setTranslateY(startY + amount + 4);
                                        Line lv = new Line(startX - levelSeparation - (2 * levelSeparation / 4), startY + amount,
                                                startX - levelSeparation + (2 * levelSeparation / 4), startY + amount);
                                        root.getChildren().add(lv);
                                        root.getChildren().add(indi);
                                    } else {
                                        indi = new Text(String.valueOf((int) y));
                                        indi.setTranslateX(startX - levelSeparation - (2 * levelSeparation / 4) - (0.5 * levelSeparation));
                                        indi.setTranslateY(startY + amount + 4);
                                        Line lv = new Line(startX - levelSeparation - (2 * levelSeparation / 4), startY + amount,
                                                startX - levelSeparation + (2 * levelSeparation / 4), startY + amount);
                                        root.getChildren().add(lv);
                                        root.getChildren().add(indi);
                                    }

                                    skip = 0;
                                } else {
                                    Line lv = new Line(startX - levelSeparation - (levelSeparation / 4), startY + amount,
                                            startX - levelSeparation + (levelSeparation / 4), startY + amount);
                                    root.getChildren().add(lv);
                                }

                            } else {
                                Line lv = new Line(startX - levelSeparation - (levelSeparation / 4), startY + amount,
                                        startX - levelSeparation + (levelSeparation / 4), startY + amount);
                                root.getChildren().add(lv);
                            }

                        } else {

                            Text indi = new Text(String.valueOf("100"));
                            indi.setTranslateX(startX - levelSeparation - (2 * levelSeparation / 4) - (1.1 * levelSeparation));
                            indi.setTranslateY(startY + 4);
                            Line lv = new Line(startX - levelSeparation - (2 * levelSeparation / 4), startY,
                                    startX - levelSeparation + (2 * levelSeparation / 4), startY);
                            root.getChildren().add(lv);
                            root.getChildren().add(indi);
                        }

                    }
                    break;
                case 2:// 1000
                    break;
                case 3://10000
                    break;
                case 4://100000
                    break;
                case 5:
                    break;
                case 6:
                    break;
            }
        }
        path2 = new Path();
        path2.getElements().add(new MoveTo(startX + offset, startY - offset + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight())));
        path2.getElements().add(new LineTo(startX + offset, startY + getSkinnable().getLevelHeight() - offset + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight())));
        path2.getElements().add(new CubicCurveTo(startX + width / 4, startY + getSkinnable().getLevelHeight() + arcHeight + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight()),
                startX + (3 * width / 4), startY + getSkinnable().getLevelHeight() + arcHeight + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight()), startX + width - offset,
                startY + getSkinnable().getLevelHeight() - offset + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight())));
        path2.getElements().add(new LineTo(startX + width - offset, startY + offset + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight())));
        path2.getElements().add(new LineTo(startX - offset + width, startY - offset + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight())));
        path2.setStroke(null);

        final List<Node> element = new ArrayList<>();

        alarmSetUp(element, root);

        // END ALARMS CODE
        tl = new Timeline();
        tl.setCycleCount(Timeline.INDEFINITE);

        Circle dot = new Circle(7, Color.YELLOW);

        KeyFrame changeLevel = new KeyFrame(Duration.millis(10), (ActionEvent event) -> {
            getSkinnable().getLevelHeightProperty().set(getSkinnable().getLevelHeight() + 1);

            if (getSkinnable().getLevelHeight() <= getSkinnable().getTargetProperty().doubleValue()) {
                //getSkinnable().getLevelHeightProperty().set(getSkinnable().getLevelHeight() + 1);
                //checkWaterLevel(topCurve);
                width = 180;
                path2.getElements().clear();
                path2.getElements().add(new MoveTo(startX + offset, startY - offset + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight())));
                path2.getElements().add(new LineTo(startX + offset, startY + getSkinnable().getLevelHeight() - offset + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight())));
                path2.getElements().add(new CubicCurveTo(startX + width / 4, startY + getSkinnable().getLevelHeight() + arcHeight + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight()),
                        startX + (3 * width / 4), startY + getSkinnable().getLevelHeight() + arcHeight + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight()), startX + width - offset,
                        startY + getSkinnable().getLevelHeight() - offset + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight())));
                path2.getElements().add(new LineTo(startX + width - offset, startY + offset + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight())));
                path2.getElements().add(new LineTo(startX - offset + width, startY - offset + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight())));
                LinearGradient gradient2 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, new Stop(0, Color.WHITE), new Stop(1, Color.web("#926239")));
                //path2.setFill(getSkinnable().getWaterColor());
                path2.setFill(gradient2);
                dot.setTranslateX(startX + width - offset);
                dot.setTranslateY(startY + offset + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight()));
                System.out.println("VALOR WIDTH: " + (width));
            } else {
                tl.stop();
            }

        });

        tl.getKeyFrames().add(changeLevel);

        tl2 = new Timeline();
        tl2.setCycleCount(Timeline.INDEFINITE);
        KeyFrame reduceLevel = new KeyFrame(Duration.millis(10), (ActionEvent event) -> {
            getSkinnable().getLevelHeightProperty().set(getSkinnable().getLevelHeight() - 1);

            if (getSkinnable().getLevelHeight() >= getSkinnable().getTargetProperty().doubleValue()) {
                //checkWaterLevel(topCurve);
                path2.getElements().clear();
                path2.getElements().add(new MoveTo(startX + offset, startY - offset + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight())));
                path2.getElements().add(new LineTo(startX + offset, startY + getSkinnable().getLevelHeight() - offset + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight())));
                path2.getElements().add(new CubicCurveTo(startX + width / 4,
                        startY + getSkinnable().getLevelHeight() + arcHeight + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight()),
                        startX + (3 * width / 4), startY + getSkinnable().getLevelHeight() + arcHeight + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight()),
                        startX + width - offset, startY + getSkinnable().getLevelHeight() - offset + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight())));
                path2.getElements().add(new LineTo(startX + width - offset, startY + offset + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight())));
                path2.getElements().add(new LineTo(startX - offset + width, startY - offset + (getSkinnable().getHeightProperty() - getSkinnable().getLevelHeight())));
            } else {
                tl2.stop();
            }

        });

        tl2.getKeyFrames().add(reduceLevel);

        if (getSkinnable().isShowScale()) {
            TextField indicator = new TextField();
            indicator.setTranslateX(startX + width + 7);
            indicator.setTranslateY(startY);
            indicator.setEditable(false);
            indicator.prefWidthProperty().set(3 * width / 8);

            root.getChildren().add(indicator);

            binding = Bindings.multiply((100.0 / getSkinnable().getHeightProperty()), getSkinnable().getLevelHeightProperty());
            binding.addListener(new ChangeListener<Number>() {

                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (oldValue.intValue() > 0) {
                        indicator.setText(oldValue.toString());
                        getSkinnable().currentValue.set(Math.ceil(oldValue.doubleValue()));
                    } else {
                        indicator.setText("0");
                    }

                    if (getSkinnable().isHasAlarm()) {
                        if (oldValue.doubleValue() > getSkinnable().alarmValue.doubleValue()) {
                            getSkinnable().fireMarkerEvent(new Tank.MarkerEvent(this, null, Tank.MarkerEvent.MARKER_EXCEEDED));
                        }

                        if (oldValue.doubleValue() < getSkinnable().minAlarmValueProperty().doubleValue()) {
                            getSkinnable().fireMarkerEvent(new Tank.MarkerEvent(this, null, Tank.MarkerEvent.MARKER_UNDERRUN));
                        }

                        if ((oldValue.doubleValue() < getSkinnable().alarmValue.doubleValue()) && (oldValue.doubleValue() > getSkinnable().minAlarmValueProperty().doubleValue())) {
                            getSkinnable().fireMarkerEvent(new Tank.MarkerEvent(this, null, Tank.MarkerEvent.TURN_OFF));
                        }
                    }

                }

            });

            /*binding2 = Bindings.multiply((100.0 / getSkinnable().getHeightProperty()), getSkinnable().getLevelHeightProperty());
            binding2.addListener(new ChangeListener<Number>() {

                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    indicator.setText(oldValue.toString());
                    if (getSkinnable().isHasAlarm()) {
                        if (oldValue.doubleValue() < getSkinnable().minAlarmValueProperty().doubleValue()) {
                            getSkinnable().fireMarkerEvent(new Tank.MarkerEvent(this, null, Tank.MarkerEvent.MARKER_UNDERRUN));
                        }
                    }

                }

            });*/
        }

        root.getChildren().addAll(path, path2, cover);

        getChildren().add(root);

        if (getSkinnable().isHasAlarm()) {

        }
    }

    private void alarmSetUp(final List<Node> element, Pane root) {
        // BEGIN ALARM CODE
        if (getSkinnable().isShowMarkers()) {

            final Button btn = new Button();
            GlyphsDude.setIcon(btn, MaterialDesignIcon.PLUS, "1.4em");
            btn.setTranslateX(startX);
            btn.setTranslateY(startY + getSkinnable().getHeightProperty() + 27);

            btn.setOnMouseClicked(c -> {

                if (!showMenus) {
                    GlyphsDude.setIcon(btn, MaterialDesignIcon.CLOSE, "1.4em");
                    showMenus = true;
                } else {
                    showMenus = false;
                    GlyphsDude.setIcon(btn, MaterialDesignIcon.PLUS, "1.4em");
                }

                if (showMenus) {
                    fadeInMenus(element);
                } else {
                    fadeOutMenus(element);
                }

            });

            ObservableList<String> options
                    = FXCollections.observableArrayList(
                            "Max",
                            "Min"
                    );

            final ComboBox select = new ComboBox(options);
            select.setTranslateX(startX + 40);
            select.setTranslateY(startY + getSkinnable().getHeightProperty() + 27);
            select.setPrefWidth(65);
            select.setValue(options.get(0));
            select.setOpacity(0);

            element.add(select);

            final NumberField targetField = new NumberField();
            targetField.setTranslateX(startX + 110);
            targetField.setTranslateY(startY + getSkinnable().getHeightProperty() + 27);
            targetField.prefWidthProperty().set(2 * width / 8);
            targetField.setOpacity(0);
            targetField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                Platform.runLater(() -> {
                    if (targetField.isFocused() && !targetField.getText().isEmpty()) {
                        targetField.selectAll();
                    }
                });
            });

            targetField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (!affect) {
                    try {
                        Double val = Double.parseDouble(newValue);
                        if (val <= getSkinnable().getMaxTankMaxValue()) {
                            System.out.println("OLD LEVEL: " + getSkinnable().getAlarmControlLevel().get());
                            double translate = ((-1 * getSkinnable().getHeightProperty() * val) / 100);
                            System.out.println("NEW LEVEL: " + translate);

                            //getSkinnable().getAlarmControlLevel().set(translate);
                            pointer.setTranslateY(translate);
                        }
                    } catch (Exception e) {
                        targetField.setText("0.0");
                    }

                }
            });

            element.add(targetField);

            /*Button valBtn = new Button();
             GlyphsDude.setIcon(valBtn, MaterialDesignIcon.ALARM_CHECK, "1.4em");
             valBtn.setTranslateX(startX + 55 + 100);
             valBtn.setTranslateY(startY + getSkinnable().getHeightProperty() + 27);
             valBtn.setOpacity(0);
            
             element.add(valBtn);*/
            pointerSetUp(targetField);
            root.getChildren().addAll(select, targetField, btn);

            // alarm pointer
            double pointerStart = startX - levelSeparation;
            pointer = new Path();
            pointer.getElements().add(new MoveTo(pointerStart, startY + getSkinnable().getHeightProperty()));
            pointer.getElements().add(new LineTo(pointerStart + 10, startY + getSkinnable().getHeightProperty() - 7));
            pointer.getElements().add(new MoveTo(pointerStart, startY + getSkinnable().getHeightProperty()));
            pointer.getElements().add(new LineTo(pointerStart + 10, startY + getSkinnable().getHeightProperty() + 7));
            pointer.getElements().add(new LineTo(pointerStart + 10, startY + getSkinnable().getHeightProperty() - 7));

            pointer.setFill(Color.web("#1976D2"));
            pointer.setStroke(null);
            pointer.setOnMousePressed(t -> {
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
                //orgTranslateX = ((Path) (t.getSource())).getTranslateX();
                orgTranslateY = ((Path) (t.getSource())).getTranslateY();
                pointer.setFill(Color.GREEN);
            });

            pointer.setOnMouseReleased(z -> {
                pointer.setFill(Color.web("#1976D2"));
            });

            pointer.setOnMouseDragged(t -> {
                double offsetY = t.getSceneY() - orgSceneY;
                double newTranslateY = orgTranslateY + offsetY;
                if (((-1 * newTranslateY) >= 0) && ((getSkinnable().getHeightProperty()) >= (-1 * newTranslateY))) {
                    getSkinnable().getAlarmControlLevel().set(newTranslateY);
                    ((Path) (t.getSource())).setTranslateY(newTranslateY);
                }
            });

            root.getChildren().add(pointer);
        }
    }

    public void startWaterAnimation() {
        System.out.println("valor de accion: " + getSkinnable().getTargetProperty().getValue());
        if (getSkinnable().getTargetProperty().getValue() > getSkinnable().getLevelHeight()) {
            tl.play();
            tl2.stop();
        } else {
            System.out.println("play second animation");
            tl2.play();
            tl.stop();
        }
    }

    private void registerListeners() {

    }

    private int findScale(double minValue, double maxValue) {
        if (maxValue <= 100) {
            return 1;
        } else if (maxValue <= 1000) {
            return 2;
        } else if (maxValue <= 10000) {
            return 3;
        } else if (maxValue <= 100000) {
            return 4;
        }
        return 1;
    }

    public Double transitive(Double val) {
        return (getSkinnable().getHeightProperty() / 100) * val;
    }

    private void checkWaterLevel(CubicCurve topCurve) {
        if (getSkinnable().getLevelHeightProperty().isEqualTo(getSkinnable().getHeightProperty(), 0.2).getValue()) {
            //topCurve.setFill(getSkinnable().getWaterColor());
        } else {
            //topCurve.setFill(getSkinnable().getTankColor());
        }
    }

    private void pointerSetUp(TextField text) {

        AlarmControlBinding = Bindings.multiply((100.0 / getSkinnable().getHeightProperty()), getSkinnable().getAlarmControlLevel().multiply(-1));
        AlarmControlBinding.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {

            affect = true;

            text.setText(newValue.toString());
            getSkinnable().getAlarmValue().set(newValue.doubleValue());
            affect = false;
        });
    }

    private void fadeInMenus(List<Node> element) {
        for (Node el : element) {
            FadeTransition ft = new FadeTransition(Duration.millis(500), el);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.setCycleCount(1);
            ft.play();
        }
    }

    private void fadeOutMenus(List<Node> element) {
        for (Node el : element) {
            FadeTransition ft = new FadeTransition(Duration.millis(500), el);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setCycleCount(1);
            ft.play();
        }
    }
}
