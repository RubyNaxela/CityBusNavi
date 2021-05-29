package com.rubynaxela.citybusnavi.gui.components;

import com.rubynaxela.citybusnavi.gui.components.std.DefaultJButton;
import org.intellij.lang.annotations.MagicConstant;

import javax.swing.*;

public class ResourceButton<T> extends DefaultJButton {

    private T resource;

    public ResourceButton(String text) {
        super(text);
    }

    public ResourceButton(String text, @MagicConstant(intValues = {SwingConstants.LEFT, SwingConstants.CENTER,
            SwingConstants.RIGHT, SwingConstants.LEADING, SwingConstants.TRAILING}) int alignment) {
        super(text);
        setHorizontalAlignment(alignment);
    }

    public void bindResource(T object) {
        this.resource = object;
    }

    public T getResource() {
        return resource;
    }
}
