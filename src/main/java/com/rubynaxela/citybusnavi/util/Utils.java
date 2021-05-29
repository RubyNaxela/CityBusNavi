package com.rubynaxela.citybusnavi.util;

import org.jetbrains.annotations.Contract;

import java.awt.*;
import java.time.LocalDate;

/**
 * This class contains various utility functions.
 */
public class Utils {

    /**
     * Converts a number to {@link String} with a leading zero to fill to two digits
     *
     * @param number an integer
     * @return formatted number as {@link String}
     */
    public static String intToTwoDigits(int number) {
        return (number < 10 ? "0" : "") + number;
    }

    /**
     * Converts a number to hexadecimal {@link String} with a leading zero to fill to two digits
     *
     * @param number an integer
     * @return formatted number as {@link String}
     */
    public static String intToTwoDigitsHex(int number) {
        final String hexNumber = Integer.toString(number, 16);
        return hexNumber.length() == 1 ? "0" + hexNumber : hexNumber;
    }

    /**
     * Converts a {@link java.awt.Color} to KML ABGR notation
     *
     * @param color a color
     * @return ABGR notation as {@link String}
     */
    public static String colorToABGR(Color color) {
        return intToTwoDigitsHex(color.getAlpha()) + intToTwoDigitsHex(color.getBlue()) +
                intToTwoDigitsHex(color.getGreen()) + intToTwoDigitsHex(color.getRed());
    }

    /**
     * Removes quotation marks from the given {@link String}
     *
     * @param text a {@link String}
     * @return the text without quotation marks
     */
    public static String removeQuotes(String text) {
        return text.replace("\"", "");
    }

    /**
     * Parses a {@link String} containing a date formatted as YYYYMMDD (no dashes, points etc.)
     *
     * @param date the date stored as a {@link String}
     * @return a {@link java.time.LocalDate} object
     */
    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8));
    }

    @Contract(pure = true)
    public static GridBagConstraints gridElementSettings(int row, int column, int width, int height, int alignment,
                                                         Insets insets, int fill) {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = column;
        gbc.gridy = row;
        gbc.anchor = alignment;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        return gbc;
    }

    @Contract(pure = true)
    public static GridBagConstraints gridElementSettings(int row, int column, int width, int height, int alignment) {
        return gridElementSettings(row, column, width, height, alignment,
                new Insets(5, 5, 5, 5), GridBagConstraints.BOTH);
    }

    /**
     * Returns a {@link java.awt.GridBagConstraints} object with the following properties:
     * <ul>
     *     <li>{@code row} and {@code column} parameters translate directly into {@link java.awt.GridBagConstraints#gridy}
     *         and {@link java.awt.GridBagConstraints#gridx} fields of the returned object respectively</li>
     *     <li>the {@code width} and {@code height} parameter translates to {@link java.awt.GridBagConstraints#gridwidth}
     *         and fields {@link java.awt.GridBagConstraints#gridheight} fields of the returned object respectively,
     *         which means it will take up space of that much grid columns and rows</li>
     * </ul>
     *
     * @param row    vertical position in the grid
     * @param column horizontal position in the grid
     * @param width  determines the component width (in terms of grid columns)
     * @param height determines the component height (in terms of grid rows)
     * @return a {@link java.awt.GridBagConstraints} instance
     */
    @Contract(pure = true)
    public static GridBagConstraints gridElementSettings(int row, int column, int width, int height) {
        return gridElementSettings(row, column, width, height, GridBagConstraints.CENTER,
                new Insets(5, 5, 5, 5), GridBagConstraints.BOTH);
    }

    @Contract(pure = true)
    public static GridBagConstraints gridElementSettings(int row, int column, int alignment) {
        return gridElementSettings(row, column, 1, 1, alignment,
                new Insets(5, 5, 5, 5), GridBagConstraints.BOTH);
    }

    /**
     * Returns a {@link GridBagConstraints} object where {@code row} and {@code column} parameters translate directly into
     * {@code gridy} and {@code gridx} fields of the returned object respectively
     *
     * @param row    vertical position in the grid
     * @param column horizontal position in the grid
     * @return a {@code GridBagConstraints} instance
     */
    @Contract(pure = true)
    public static GridBagConstraints gridElementSettings(int row, int column) {
        return gridElementSettings(row, column, 1, 1, GridBagConstraints.CENTER,
                new Insets(5, 5, 5, 5), GridBagConstraints.BOTH);
    }
}
