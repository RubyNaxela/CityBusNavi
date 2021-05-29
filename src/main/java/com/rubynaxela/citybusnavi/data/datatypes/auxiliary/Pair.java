package com.rubynaxela.citybusnavi.data.datatypes.auxiliary;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * This is a convenience class to represent key-value pairs
 *
 * @param <K> the class of the pair's key
 * @param <V> the class of the pair's value
 */
public class Pair<K, V> implements Comparable<Pair<K, V>>, Serializable {

    private final K key;
    private final V value;

    /**
     * Creates a new key-value pair
     *
     * @param key   the key of the pair
     * @param value the value of the pair's key
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @return the pair's key (first) value
     */
    public K getKey() {
        return key;
    }

    /**
     * @return the key's (pair's second) value
     */
    public V getValue() {
        return value;
    }

    /**
     * Tries to compare the pairs in terms of the key using the {@link java.lang.Comparable#compareTo}
     * method. If the keys cannot be compared, this method returns 0
     *
     * @param other the object to be compared
     * @return result of key comparison or {@code 0} on failure
     */
    @Override
    @SuppressWarnings("unchecked")
    public int compareTo(@NotNull Pair<K, V> other) {
        try {
            return ((Comparable<K>) key).compareTo(other.key);
        } catch (ClassCastException ignored) {
            return 0;
        }
    }

    /**
     * Returns a {@link String} representation of this {@code Pair}. {@code ": "} is used as the name/value delimiter
     *
     * @return a {@link String} representation of this {@code Pair}
     */
    @Override
    public String toString() {
        return key + ": " + value;
    }

    /**
     * Tests this {@code Pair} for equality with another {@link Object}. If the Object to be tested is not a Pair or is null,
     * then this method returns false. Two {@code Pair}s are considered equal only if both the keys and values are equal
     *
     * @param other the {@link Object} to test for equality with this {@code Pair}
     * @return {@code true} if the given Object is equal to this Pair else {@code false}
     */
    @Override
    @SuppressWarnings("rawtypes")
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other instanceof Pair) {
            Pair pair = (Pair) other;
            return Objects.equals(key, pair.key) && Objects.equals(value, pair.value);
        }
        return false;
    }
}
