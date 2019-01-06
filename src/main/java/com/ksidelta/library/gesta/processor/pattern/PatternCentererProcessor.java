package com.ksidelta.library.gesta.processor.pattern;

import com.ksidelta.library.gesta.shapes.pattern.Pattern;

import java.util.Optional;

/**
 * It will center the Pattern so it fits into (-1,1)/(-1,1) plane.
 */
public class PatternCentererProcessor implements PatternProcessor {
    @Override
    public Optional<Pattern> process(Pattern pattern) {
        if (pattern.getPoints().size() < 2) {
            return Optional.empty();
        }

        boolean byWidth = pattern.getWidth() > pattern.getHeight();
        double scale = byWidth ? 2.0 / pattern.getWidth() : 2.0 / pattern.getHeight();
        return pattern
                .translate(-pattern.getX() - pattern.getWidth() / 2.0, -pattern.getY() - pattern.getHeight() / 2.0)
                .scale(scale, scale)
                .toOptional();
    }
}
