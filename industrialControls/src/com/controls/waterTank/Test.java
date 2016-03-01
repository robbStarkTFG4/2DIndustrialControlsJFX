/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.waterTank;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author marcoisaac
 */
public class Test extends Application {

    private static int noOfNodes = 0;
    private boolean llena = true;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();

        /* Tank tank = new Tank();
         tank.setTankSize(60, 300);
         tank.setTranslateX(200);
         tank.setTranslateY(120);
         tank.setWaterColor(Color.RED);
         tank.setTankColor(Color.BLACK);
         tank.setTankBorderColor(Color.WHITESMOKE);
         tank.setOnMarkerExceeded(c -> {
         System.out.println("ALARMAAAAAAA!!!!");
         });*/
        Tank tank = TankBuilder.create()
                .prefSize(60, 300)
                .translateX(200)
                .translateY(120)
                //.waterColor(Color.RED)
                //.tankBorderColor(Color.WHITESMOKE)
                .showMarkers(true)
                .hasAlarm(true)
                .alarmValue(80)
                .build();

        tank.setOnMarkerExceeded(c -> {
            System.out.println("ALARMAAAAAAA!!!!");
        });

        tank.setOnMarkerUnder(w -> {
            System.out.println("so tired!!!!");
        });

        tank.setTurOffProperty(d -> {
            System.out.println("APAGA SENSORES");
        });

        Button btn = new Button("accion");

        btn.setOnMouseClicked(c -> {
            if (llena) {
                tank.setWaterLevel(85);
                llena = false;
            } else {
                tank.setWaterLevel(8);
                llena = true;
            }

        });
        root.getChildren().addAll(tank, btn);

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        calcNoOfNodes(scene.getRoot());
        System.out.println("number of nodes: " + noOfNodes);

    }

    public static void main(String[] args) {
        launch(args);
    }

    // ******************** Misc **********************************************
    private static void calcNoOfNodes(Node node) {
        if (node instanceof Parent) {
            if (((Parent) node).getChildrenUnmodifiable().size() != 0) {
                ObservableList<Node> tempChildren = ((Parent) node).getChildrenUnmodifiable();
                noOfNodes += tempChildren.size();
                tempChildren.stream().forEach((n) -> {
                    calcNoOfNodes(n);
                });
            }
        }
    }
}
