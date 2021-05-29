package com.rubynaxela.citybusnavi.gui.components.std;

import com.rubynaxela.citybusnavi.gui.GUIManager;
import org.intellij.lang.annotations.MagicConstant;

import javax.swing.*;
import java.awt.*;

public class DefaultJButton extends JButton {

    private int fontSize = 13, fontStyle = Font.PLAIN;

    /**
     * Creates a button with no set text or icon
     *
     * @see JButton#JButton()
     */
    public DefaultJButton() {
        super();
    }

    /**
     * Creates a button with text
     *
     * @param text the text of the button
     * @see JButton#JButton(String)
     */
    public DefaultJButton(String text) {
        super(text);
        updateFont();
    }

    private void updateFont() {
        setFont(GUIManager.getGlobalFont(fontSize, fontStyle));
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int size) {
        fontSize = size;
        updateFont();
    }

    public int getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(@MagicConstant(flags = {Font.PLAIN, Font.BOLD, Font.ITALIC}) int style) {
        fontStyle = style;
        updateFont();
    }

    public void setFontSizeAndStyle(int size,
                                    @MagicConstant(flags = {Font.PLAIN, Font.BOLD, Font.ITALIC}) int style) {
        fontSize = size;
        fontStyle = style;
        updateFont();
    }
}
