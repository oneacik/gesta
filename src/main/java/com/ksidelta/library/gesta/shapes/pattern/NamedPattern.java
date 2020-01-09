package com.ksidelta.library.gesta.shapes.pattern;

/**
 * Named Pattern is a Pattern with its name.
 */
public class NamedPattern {
    private final Pattern pattern;
    private final String name;

    public NamedPattern(String name, Pattern pattern) {
        this.pattern = pattern;
        this.name = name;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getName() {
        return name;
    }
}
