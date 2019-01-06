package com.ksidelta.library.gesta.shapes.pattern;

/**
 * TODO: Document this class / interface here
 *
 * @since v7.4
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
