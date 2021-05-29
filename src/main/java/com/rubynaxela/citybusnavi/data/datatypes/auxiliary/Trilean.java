package com.rubynaxela.citybusnavi.data.datatypes.auxiliary;

import java.io.Serializable;

/**
 * This enum is a simple implementation of the trinary logic value, which,
 * unlike the binary logic value, can be either positive, negative or neutral
 */
public enum Trilean implements Serializable {
    POSITIVE, NEUTRAL, NEGATIVE;

    /**
     * Returns a trilean value corresponding to the integer value in 012 model, that is:
     * <ul>
     *     <li>{@code POSITIVE} for {@code 0}</li>
     *     <li>{@code NEGATIVE} for {@code 1}</li>
     *     <li>{@code NEUTRAL} for {@code 2}</li>
     * </ul>
     *
     * @param value integer trilean value in 012 model
     * @return a corresponding trilean value
     */
    public static Trilean from012Model(int value) {
        return value == 2 ? NEUTRAL : (value == 0 ? POSITIVE : NEGATIVE);
    }
}
