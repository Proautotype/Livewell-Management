/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uiObjects;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class admin_deptMemberList extends HBox {

    private String user_id, user_name, user_status;
    private Circle imageCircle;
    private VBox details;

    public admin_deptMemberList() {

    }

    public admin_deptMemberList(String user_id, String user_name, String user_status, Image imager) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_status = user_status;

        imageCircle = new Circle(20);
        imageCircle.setFill(new ImagePattern(imager));
        details = new VBox();
        details.setSpacing(3);
        details.setAlignment(Pos.CENTER);
        details.getChildren().addAll(new Label(user_name), new Label(user_status));

        setSpacing(20);
        this.getChildren().addAll(imageCircle, details);
        
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

}
