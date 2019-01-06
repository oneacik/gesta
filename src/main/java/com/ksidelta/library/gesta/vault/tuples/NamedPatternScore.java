package com.ksidelta.library.gesta.vault.tuples;

import com.ksidelta.library.gesta.shapes.pattern.NamedPattern;

/**
 * TODO: Document this class / interface here
 *
 * @since v7.4
 */
public class NamedPatternScore {
    private NamedPattern namedPattern;
    private double score;

    public NamedPatternScore(NamedPattern namedPattern, double score) {
        this.namedPattern = namedPattern;
        this.score = score;
    }

    public NamedPattern getNamedPattern() {
        return namedPattern;
    }

    public double getScore() {
        return score;
    }
}
