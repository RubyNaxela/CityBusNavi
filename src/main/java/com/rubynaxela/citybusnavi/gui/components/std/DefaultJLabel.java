package com.rubynaxela.citybusnavi.gui.components.std;

import com.rubynaxela.citybusnavi.gui.GUIManager;
import org.intellij.lang.annotations.MagicConstant;

import javax.swing.*;
import java.awt.*;

public class DefaultJLabel extends JLabel {

    private int fontSize = 13, fontStyle = Font.PLAIN;

    /**
     * Creates a JLabel instance with no image and with an empty string for the title.
     * The label is centered vertically in its display area. The label's contents,
     * once set, will be displayed on the leading edge of the label's display area
     *
     * @see JLabel#JLabel()
     */
    public DefaultJLabel() {
        super();
    }

    /**
     * Creates a JLabel instance with the specified text. The label is aligned
     * against the leading edge of its display area, and centered vertically
     *
     * @param text the text to be displayed by the label
     * @see JLabel#JLabel(String)
     */
    public DefaultJLabel(String text) {
        super(text);
        updateFont();
    }

    /**
     * Creates a JLabel instance with the specified text and horizontal
     * alignment. The label is centered vertically in its display area.
     *
     * @param text                the text to be displayed by the label
     * @param horizontalAlignment one of the following constants defined in {@link javax.swing.SwingConstants}:
     *                            LEFT, CENTER, RIGHT, LEADING or TRAILING.
     * @see JLabel#JLabel(String, int)
     */
    public DefaultJLabel(String text, @MagicConstant(intValues = {SwingConstants.LEFT, SwingConstants.CENTER,
            SwingConstants.RIGHT, SwingConstants.LEADING, SwingConstants.TRAILING}) int horizontalAlignment) {
        super(text, null, horizontalAlignment);
    }

    private void updateFont() {
        setFont(GUIManager.getGlobalFont(fontSize, fontStyle));
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        updateFont();
    }

    public int getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(@MagicConstant(intValues = {Font.PLAIN, Font.BOLD, Font.ITALIC}) int fontStyle) {
        this.fontStyle = fontStyle;
        updateFont();
    }

    public void setFontSizeAndStyle(int fontSize,
                                    @MagicConstant(flags = {Font.PLAIN, Font.BOLD, Font.ITALIC}) int fontStyle) {
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        updateFont();
    }
}
