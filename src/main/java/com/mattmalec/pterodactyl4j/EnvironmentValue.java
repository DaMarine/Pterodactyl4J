package com.mattmalec.pterodactyl4j;

import java.util.Optional;

/**
 * Represents a value used in communication with an {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
 */
public class EnvironmentValue<T> {

    private final T value;

    private EnvironmentValue(T value) {
        this.value = value;
    }

    /**
     * Resolves any type
     *
     * @return {@link java.util.Optional} with a possible value
     */
    public Optional<T> get() {
        return Optional.of(value);
    }

    /**
     * Resolves a {@link java.lang.String}
     *
     * @return {@link java.util.Optional} with a possible String value
     */
    public Optional<String> getAsString() {
        if(value instanceof String)
            return Optional.of((String) value);
        return get().map(String::valueOf);
    }

    /**
     * Resolves an {@link java.lang.Integer}
     *
     * @return {@link java.util.Optional} with a possible Integer value
     */
    public Optional<Integer> getAsInteger() {
        if(value instanceof Integer)
            return Optional.of((Integer) value);
        return Optional.empty();
    }

    /**
     * Loads any type into an EnvironmentValue
     *
     * @param  value
     *         The value to load
     *
     * @return An EnvironmentValue instance for the provided object
     */
    public static EnvironmentValue<?> of(Object value) {
        return new EnvironmentValue<>(value);
    }

    /**
     * Loads a {@link java.lang.String} into an EnvironmentValue
     *
     * @param  value
     *         The value to load
     *
     * @return An EnvironmentValue instance for the provided String
     */
    public static EnvironmentValue<String> ofString(String value) {
        return new EnvironmentValue<>(value);
    }

    /**
     * Loads an {@link java.lang.Integer} into an EnvironmentValue
     *
     * @param  value
     *         The value to load
     *
     * @return An EnvironmentValue instance for the provided Integer
     */
    public static EnvironmentValue<Integer> ofInteger(int value) {
        return new EnvironmentValue<>(value);
    }

    @Override
    public String toString() {
        return get().toString();
    }
}
