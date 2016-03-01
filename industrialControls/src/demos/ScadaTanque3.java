/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demos;

import com.controls.pipes.CurveElement;
import com.controls.pipes.DeviceElement;
import com.controls.pipes.PipePath;
import com.controls.pipes.RectElement;
import com.controls.pipes.curves.Curve;
import com.controls.pipes.devices.compuerta.Compuerta;
import com.controls.pipes.devices.digital.Digital;
import com.controls.pipes.devices.pump1.Pump1;
import com.controls.pipes.devices.watermeter.WaterMeter;
import com.controls.pipes.rect.Rect;
import com.controls.util.CurveDirection;
import com.controls.util.DeviceType;
import com.controls.util.RectDirection;
import com.controls.waterTank.Tank;
import com.controls.waterTank.TankBuilder;
import eu.hansolo.enzo.canvasled.Led;
import eu.hansolo.enzo.canvasled.LedBuilder;
import eu.hansolo.enzo.fonts.Fonts;
import eu.hansolo.enzo.lcd.Lcd;
import eu.hansolo.enzo.lcd.LcdBuilder;
import eu.hansolo.enzo.onoffswitch.OnOffSwitch;
import java.util.Random;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author marcoisaac
 */
public class ScadaTanque3 extends Application {
    
    private OnOffSwitch iconSwitchSymbol;
    private OnOffSwitch iconSwitchSymbol2;
    private OnOffSwitch iconSwitchSymbol3;
    private OnOffSwitch iconSwitchSymbol4;
    private OnOffSwitch iconSwitchSymbol5;
    
    private Led led1, led2, led3, led4, led5, led6, led7;
    private Lcd control;
    private Led led8;
    private Compuerta com1, com2, com3, com4;
    private Pump1 pump;
    private WaterMeter flow;
    private int ctrl = 1;
    private Tank levaduraTank;
    private Timeline stable;
    private Timeline unStable;
    private Timeline waterAnim;
    private Led led9;
    
    private boolean show = false;
    private Timeline pipeAnim;
    private Digital nivelPipa;
    
    @Override
    public void start(Stage stage) throws Exception {
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        Group root = new Group();
        
        Font font2 = Fonts.robotoBold(12);
        
        Text levaduraTag = new Text("Tanque Levadura");
        levaduraTag.setTranslateX(140);
        levaduraTag.setTranslateY(550);
        levaduraTag.setFont(font2);
        
        levaduraTank = TankBuilder.create()
                .prefSize(180, 210)
                .translateX(100)
                .translateY(450)
                .showMarkers(true)
                .hasAlarm(true)
                .alarmValue(80)
                .build();
        
        Digital nivelBajo = new Digital(285, 580, 60, 20);
        
        Digital nivelAlto = new Digital(285, 500, 60, 20);
        
        levaduraTank.setOnMarkerExceeded(c -> {
            nivelAlto.activaded();
            led5.setOn(true);
        });
        
        levaduraTank.setOnMarkerUnder(w -> {
            nivelBajo.activaded();
            led6.setOn(true);
            if (show) {
                notification(Pos.CENTER, "Tanque vacio!!!! , bomba detenida");
            }
            detenerBomba();
        });
        
        levaduraTank.setTurOffProperty(c -> {
            nivelAlto.deactivated();
            nivelBajo.deactivated();
            led5.setOn(false);
            led6.setOn(false);
        });
        
        Text aguaTag = new Text("Tanque Agua");
        aguaTag.setTranslateX(140);
        aguaTag.setTranslateY(160);
        aguaTag.setFont(font2);
        
        Tank waterTank = TankBuilder.create()
                .prefSize(100, 100)
                .translateX(130)
                .translateY(120)
                .showMarkers(false)
                .hasAlarm(false)
                .showScale(false)
                .build();
        
        nivelPipa = new Digital(365, 150, 60, 20);
        
        Image image = new Image(ScadaTanque3.class.getResourceAsStream("pipa.png"));
        ImageView img = new ImageView();
        img.setFitHeight(150);
        img.setFitWidth(150);
        img.setImage(image);
        img.setTranslateX(240);
        img.setTranslateY(130);
        
        Text lev2Tag = new Text("Tanque Secundario");
        lev2Tag.setTranslateX(640);
        lev2Tag.setTranslateY(140);
        lev2Tag.setFont(font2);
        
        Tank levadura2Tanque = TankBuilder.create()
                .prefSize(150, 120)
                .translateX(620)
                .translateY(100)
                .showMarkers(false)
                .hasAlarm(false)
                .showScale(false)
                .build();
        
        double pivotX = 380;
        double pivotY = 620;
        
        Rect rect = new Rect(RectDirection.LEFT_RIGHT, 280, 620, 1, 100, 100, 20, 1);
        rect.setW(86);
        
        Text com1Tag = new Text("Comp 1");
        com1Tag.setTranslateX(400);
        com1Tag.setTranslateY(560);
        com1Tag.setFont(font2);
        
        com1 = new Compuerta(RectDirection.DOWN_TOP, pivotX, pivotY, 100, 100, 20, 1);
        com1.getTransforms().add(new Rotate(90, pivotX, pivotY));
        
        PipePath path = new PipePath(root, 460, 620);
        path.setH(60);
        path.add(new RectElement(RectDirection.LEFT_RIGHT, 90));
        path.add(new CurveElement(CurveDirection.RIGHT_UP));
        path.add(new CurveElement(CurveDirection.UP_LEFT, "boro"));
        path.add(new CurveElement(CurveDirection.LEFT_UP_2, "neck"));
        path.add(new DeviceElement(RectDirection.DOWN_TOP, DeviceType.WATER_METER, "w1"));
        
        flow = (WaterMeter) path.getNodeMap().get("w1");
        
        path.add(new RectElement(RectDirection.DOWN_TOP, 7.5));
        path.add(new DeviceElement(RectDirection.DOWN_TOP, DeviceType.COMPUERTA, "comp2"));
        
        Text com2Tag = new Text("Comp 2");
        com2Tag.setTranslateX(595);
        com2Tag.setTranslateY(310);
        com2Tag.setFont(font2);
        
        com2 = (Compuerta) path.getNodeMap().get("comp2");
        
        path.add(new RectElement(RectDirection.DOWN_TOP, 2));
        path.add(new CurveElement(CurveDirection.LEFT_DOWN, "touchi"));
        
        PipePath path2 = new PipePath(root, 541, 360);
        path2.setH(60);
        path2.add(new CurveElement(CurveDirection.LEFT_UP_2, "fist"));
        
        Curve curve0 = (Curve) path2.getNodeMap().get("fist");
        curve0.setW(85);
        
        path2.add(new DeviceElement(RectDirection.DOWN_TOP, DeviceType.COMPUERTA, "comp3"));
        
        Text com3Tag = new Text("Comp 3");
        com3Tag.setTranslateX(340);
        com3Tag.setTranslateY(293);
        com3Tag.setFont(font2);
        
        com3 = (Compuerta) path2.getNodeMap().get("comp3");
        
        path2.add(new RectElement(RectDirection.DOWN_TOP, 22.5));
        path2.add(new CurveElement(CurveDirection.UP_LEFT, "left1"));
        path2.add(new CurveElement(CurveDirection.LEFT_DOWN_2, "down1"));
        
        PipePath path3 = new PipePath(root, 360, 619);
        path3.setH(60);
        path3.add(new RectElement(RectDirection.DOWN_TOP, 90));
        path3.add(new CurveElement(CurveDirection.UP_LEFT));
        path3.add(new RectElement(RectDirection.RIGHT_LEFT, 121));
        path3.add(new CurveElement(CurveDirection.LEFT_UP_2));
        path3.add(new DeviceElement(RectDirection.DOWN_TOP, DeviceType.COMPUERTA, "comp4"));

        ///  Text com1Tag = new Text("Comp 1");
        Text com4Tag = new Text("Comp 4");
        com4Tag.setTranslateX(110);
        com4Tag.setTranslateY(325);
        com4Tag.setFont(font2);
        
        com4 = (Compuerta) path3.getNodeMap().get("comp4");
        
        path3.add(new RectElement(RectDirection.DOWN_TOP, 1));
        path3.add(new CurveElement(CurveDirection.LEFT_DOWN, "llegada"));
        
        Curve arrive = (Curve) path3.getNodeMap().get("llegada");
        arrive.setW(60);
        
        WaterMeter water = (WaterMeter) path.getNodeMap().get("w1");
        
        iconSwitchSymbol = new OnOffSwitch();
        iconSwitchSymbol.setThumbColor(Color.web("#455A64"));
        iconSwitchSymbol.setSwitchColor(Color.web("#455A64"));
        iconSwitchSymbol.setTextColorOn(Color.web("#455A64"));
        iconSwitchSymbol.setTextColorOff(Color.web("#455A64"));
        iconSwitchSymbol.setSelected(false);
        iconSwitchSymbol.setOnSelect(D -> {
            led1.setOn(true);
            com1.open();
        });
        iconSwitchSymbol.setOnDeselect(D -> {
            led1.setOn(false);
            com1.close();
        });
        
        iconSwitchSymbol2 = new OnOffSwitch();
        iconSwitchSymbol2.setSelected(false);
        iconSwitchSymbol2.setThumbColor(Color.web("#455A64"));
        iconSwitchSymbol2.setSwitchColor(Color.web("#455A64"));
        iconSwitchSymbol2.setTextColorOn(Color.web("#455A64"));
        iconSwitchSymbol2.setTextColorOff(Color.web("#455A64"));
        iconSwitchSymbol2.setOnSelect(D -> {
            led2.setOn(true);
            com2.open();
        });
        iconSwitchSymbol2.setOnDeselect(D -> {
            led2.setOn(false);
            com2.close();
        });
        
        iconSwitchSymbol3 = new OnOffSwitch();  // SWITCH COMPUERTA 3
        iconSwitchSymbol3.setSelected(false);
        iconSwitchSymbol3.setThumbColor(Color.web("#455A64"));
        iconSwitchSymbol3.setSwitchColor(Color.web("#455A64"));
        iconSwitchSymbol3.setTextColorOn(Color.web("#455A64"));
        iconSwitchSymbol3.setTextColorOff(Color.web("#455A64"));
        iconSwitchSymbol3.setOnSelect(D -> {
            led3.setOn(true);
            com3.open();
            pipeAnimation();
        });
        iconSwitchSymbol3.setOnDeselect(D -> {
            led3.setOn(false);
            com3.close();
            pipeAnim.stop();
            nivelPipa.deactivated();
            led7.setOn(false);
        });
        
        iconSwitchSymbol4 = new OnOffSwitch();
        iconSwitchSymbol4.setSelected(false);
        iconSwitchSymbol4.setThumbColor(Color.web("#455A64"));
        iconSwitchSymbol4.setSwitchColor(Color.web("#455A64"));
        iconSwitchSymbol4.setTextColorOn(Color.web("#455A64"));
        iconSwitchSymbol4.setTextColorOff(Color.web("#455A64"));
        iconSwitchSymbol4.setOnSelect(D -> {
            led4.setOn(true);
            com4.open();
        });
        iconSwitchSymbol4.setOnDeselect(D -> {
            led4.setOn(false);
            com4.close();
        });
        
        iconSwitchSymbol5 = new OnOffSwitch();
        iconSwitchSymbol5.setSelected(false);
        iconSwitchSymbol5.setThumbColor(Color.web("#455A64"));
        iconSwitchSymbol5.setSwitchColor(Color.web("#455A64"));
        iconSwitchSymbol5.setTextColorOn(Color.web("#455A64"));
        iconSwitchSymbol5.setTextColorOff(Color.web("#455A64"));
        iconSwitchSymbol5.setOnSelect(D -> {
            if (com1.isOpen() && (com3.isOpen() || com2.isOpen())) {
                pump.startPump();
                flow.fadeFlowInSign();
                led8.setOn(true);
                led9.setOn(true);
                show = true;
                ampAnimation();
            } else {
                iconSwitchSymbol5.setSelected(false);
                notification(Pos.CENTER, "Active compuerta 1 y compuerta 2 o 3");
            }
            
        });
        iconSwitchSymbol5.setOnDeselect(D -> {
            if (pump.isRunning()) {
                pump.stopPump();
                flow.fadeFlowSign();
                led8.setOn(false);
                led9.setOn(false);
            }
            
            if (stable != null) {
                Status status = stable.statusProperty().get();
                if (status == Status.RUNNING) {
                    stable.stop();
                    waterAnim.stop();
                }
            }
            
            control.setValue(0);
        });
        
        GridPane pane = new GridPane();
        pane.setPrefWidth(screen.getWidth());
        pane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setVgap(5);
        pane.setHgap(7);       
        pane.setPadding(new Insets(7, 0, 10, 80));
        
        Font font = Fonts.robotoBold(20);
        
        Text txt1 = new Text("Compuerta 1");
        txt1.setFont(font);
        
        pane.add(txt1, 0, 0);
        pane.add(iconSwitchSymbol, 0, 1);
        
        Text txt2 = new Text("Compuerta 2");
        txt2.setFont(font);
        
        pane.add(txt2, 1, 0);
        pane.add(iconSwitchSymbol2, 1, 1);
        
        Text txt3 = new Text("Compuerta 3");
        txt3.setFont(font);
        
        pane.add(txt3, 2, 0);
        pane.add(iconSwitchSymbol3, 2, 1);
        
        Text txt4 = new Text("Compuerta 4");
        txt4.setFont(font);
        
        pane.add(txt4, 3, 0);
        pane.add(iconSwitchSymbol4, 3, 1);
        
        Text txt5 = new Text("Bomba");
        txt5.setFont(font);
        
        pane.add(txt5, 4, 0);
        pane.add(iconSwitchSymbol5, 4, 1);
        
        Rectangle marco = new Rectangle(450, 280);
        marco.setFill(Color.web("#607D8B"));
        marco.setTranslateX(800);
        marco.setTranslateY(100);
        
        Rectangle interior = new Rectangle(420, 260);
        interior.setFill(Color.web("#F5F5F5"));
        interior.setStroke(Color.web("#727272"));
        interior.setTranslateX(815);
        interior.setTranslateY(110);
        interior.setArcWidth(20);
        interior.setArcHeight(20);
        
        GridPane indiPane = tablero();
        
        pump = new Pump1(RectDirection.DOWN_TOP, 710, 575, 200, 200, 20, 1);
        
        indicadorBomba();
        
        root.getChildren().addAll(levaduraTank, img, waterTank, levadura2Tanque, com1, rect, nivelBajo, nivelAlto, nivelPipa, marco,
                interior, indiPane, control, pump, levaduraTag, aguaTag, lev2Tag, com1Tag, com4Tag, com3Tag, com2Tag, pane);
        
        Scene scene = new Scene(root, screen.getWidth(), screen.getHeight());
        scene.setFill(Color.web("#BBDEFB"));
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                show = false;
                levaduraTank.setWaterLevel(85);
            } else if (key.getCode() == KeyCode.SPACE) {
                levaduraTank.setWaterLevel(5);
            }
        });
        stage.setScene(scene);
        stage.show();
        
    }
    
    private void detenerBomba() {
        if (pump.isRunning()) {
            iconSwitchSymbol5.setSelected(false);
            pump.stopPump();
            led8.setOn(false);
            led9.setOn(false);
            flow.fadeFlowSign();
        }
    }
    
    private void indicadorBomba() {
        control = LcdBuilder.create()
                //.minSize(128, 40)
                //.maxSize(1280, 400)
                .prefWidth(450)
                .prefHeight(220)
                .keepAspect(true)
                .lcdDesign(Lcd.LcdDesign.GRAY)
                .foregroundShadowVisible(true)
                .crystalOverlayVisible(true)
                .title("Amperaje Bomba")
                .batteryVisible(false)
                .signalVisible(false)
                .alarmVisible(false)
                .unit("A")
                .unitVisible(true)
                .decimals(3)
                .animationDurationInMs(1500)
                .minMeasuredValueDecimals(1)
                .minMeasuredValueVisible(true)
                .maxMeasuredValueDecimals(3)
                .maxMeasuredValueVisible(true)
                .formerValueVisible(true)
                //.threshold(26)
                .thresholdVisible(false)
                .trendVisible(false)
                .numberSystemVisible(false)
                .lowerRightTextVisible(false)
                //.lowerRightText("Info")
                .minValue(0)
                .maxValue(150)
                //.valueFont(Lcd.LcdFont.ELEKTRA)
                .valueFont(Lcd.LcdFont.LCD)
                .animated(true)
                //.value(30)
                .translateX(800)
                .translateY(420)
                .build();
        //control.setValue(65);
        //control.setValue(99);
    }
    
    private GridPane tablero() {
        GridPane indiPane = new GridPane();
        indiPane.setPrefSize(400, 280);
        indiPane.setTranslateX(825);
        indiPane.setTranslateY(117);
        indiPane.setHgap(15);
        indiPane.setVgap(7);
        indiPane.setPadding(new Insets(15, 0, 0, 25));
        
        Font font = Fonts.dinFun(15);
        
        Text indiTxt1 = new Text("Comp 1");
        indiTxt1.setFont(font);
        
        indiPane.add(indiTxt1, 0, 0);
        
        led1 = LedBuilder.create()
                .ledColor(Color.web("7FFF00"))
                .prefWidth(60)
                .prefHeight(40)
                .build();
        
        indiPane.add(led1, 0, 1);

        ///
        Text indiTxt2 = new Text("Comp 2");
        indiTxt2.setFont(font);
        
        indiPane.add(indiTxt2, 1, 0);
        
        led2 = LedBuilder.create()
                .ledColor(Color.web("7FFF00"))
                .prefWidth(60)
                .prefHeight(40)
                .build();
        
        indiPane.add(led2, 1, 1);

        //
        Text indiTxt3 = new Text("Comp 3");
        indiTxt3.setFont(font);
        
        indiPane.add(indiTxt3, 2, 0);
        
        led3 = LedBuilder.create()
                .ledColor(Color.web("7FFF00"))
                .prefWidth(60)
                .prefHeight(40)
                .build();
        
        indiPane.add(led3, 2, 1);

        //
        Text indiTxt4 = new Text("Comp 4");
        indiTxt4.setFont(font);
        
        indiPane.add(indiTxt4, 3, 0);
        
        led4 = LedBuilder.create()
                .ledColor(Color.web("7FFF00"))
                .prefWidth(60)
                .prefHeight(40)
                .build();
        
        indiPane.add(led4, 3, 1);

        //
        Text indiTxt5 = new Text("Nivel alto");
        indiTxt5.setFont(font);
        
        indiPane.add(indiTxt5, 0, 3);
        
        led5 = LedBuilder.create()
                .ledColor(Color.web("7FFF00"))
                .prefWidth(60)
                .prefHeight(40)
                .build();
        
        indiPane.add(led5, 0, 4);

        //
        Text indiTxt6 = new Text("Nivel bajo");
        indiTxt6.setFont(font);
        
        indiPane.add(indiTxt6, 1, 3);
        
        led6 = LedBuilder.create()
                .ledColor(Color.web("7FFF00"))
                .prefWidth(60)
                .prefHeight(40)
                .build();
        
        indiPane.add(led6, 1, 4);

        //
        Text indiTxt7 = new Text("Pipa llena");
        indiTxt7.setFont(font);
        
        indiPane.add(indiTxt7, 2, 3);
        
        led7 = LedBuilder.create()
                .ledColor(Color.web("7FFF00"))
                .prefWidth(60)
                .prefHeight(40)
                .build();
        
        indiPane.add(led7, 2, 4);

        //
        Text indiTxt8 = new Text("Sensor Flujo");
        indiTxt8.setFont(font);
        
        indiPane.add(indiTxt8, 3, 3);
        
        led8 = LedBuilder.create()
                .ledColor(Color.web("7FFF00"))
                .prefWidth(60)
                .prefHeight(40)
                .build();
        
        indiPane.add(led8, 3, 4);

        //
        Text indiTxt9 = new Text("Bomba");
        indiTxt9.setFont(font);
        
        indiPane.add(indiTxt9, 0, 5);
        
        led9 = LedBuilder.create()
                .ledColor(Color.web("7FFF00"))
                .prefWidth(60)
                .prefHeight(40)
                .build();
        
        indiPane.add(led9, 0, 6);

        //
        return indiPane;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    private void ampAnimation() {
        KeyFrame frame = new KeyFrame(Duration.millis(200), r -> {
            control.setValue(35 - randDouble(0, 5));
        });
        
        stable = new Timeline(frame);
        stable.setAutoReverse(true);
        stable.setCycleCount(Timeline.INDEFINITE);
        //stable.play();

        KeyFrame frame2 = new KeyFrame(Duration.millis(100), r -> {
            control.setValue(80 - randDouble(0, 12));
        });
        
        unStable = new Timeline(frame2);
        unStable.setAutoReverse(true);
        unStable.setCycleCount(4);
        unStable.play();
        unStable.setOnFinished(r -> {
            stable.play();
        });
        
        KeyFrame frame3 = new KeyFrame(Duration.millis(31.25), r -> {
            levaduraTank.setWaterLevel(levaduraTank.getCurrentValue() - 1);
            //System.out.println("DAFUQQQQ!!!!!");
        });
        
        waterAnim = new Timeline(frame3);
        waterAnim.setAutoReverse(true);
        waterAnim.setCycleCount(Timeline.INDEFINITE);
        waterAnim.play();
        
    }
    
    public double randDouble(int min, int max) {
        
        Random rand = new Random();
        
        int randomNum = rand.nextInt((max - min) + 1) + min;
        
        return randomNum;
    }
    
    private void notification(Pos pos, String mensaje) {

        //Node graphic = null;
        Notifications notificationBuilder = Notifications.create()
                .title("Info")
                .text(mensaje)
                //.graphic(graphic)
                .hideAfter(Duration.seconds(5))
                .position(pos);
        
        notificationBuilder.showWarning();

        //notificationBuilder.owner(stage);
        notificationBuilder.hideCloseButton();
        
        notificationBuilder.darkStyle();
        
    }
    
    public void pipeAnimation() {
        KeyFrame frame = new KeyFrame(Duration.seconds(7), r -> {
            notification(Pos.CENTER, "PIPA LLENA");
            nivelPipa.activaded();
            led7.setOn(true);
            detenerBomba();
        });
        
        pipeAnim = new Timeline(frame);
        pipeAnim.setAutoReverse(false);
        pipeAnim.setCycleCount(1);
        pipeAnim.play();
    }
}
