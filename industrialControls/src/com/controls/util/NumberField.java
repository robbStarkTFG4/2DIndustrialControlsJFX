/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.util;

import javafx.scene.control.TextField;

/**
 *
 * @author marcoisaac
 */
public class NumberField extends TextField {

    public NumberField(String text) {
        super(text);
    }

    public NumberField() {
    }

    @Override
    public void replaceText(int start, int end, String text) {

        if (text.matches("[(?:\\d*\\.)?\\d+]*")) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String replacement) {
        super.replaceSelection(replacement);
    }

}
