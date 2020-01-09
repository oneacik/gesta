package com.ksidelta.library.gesta.vault;

import com.ksidelta.library.gesta.matcher.ClosestPointsForwardMatcher;
import com.ksidelta.library.gesta.matcher.PatternMatcher;
import com.ksidelta.library.gesta.processor.pattern.PatternProcessorFactory;
import com.ksidelta.library.gesta.shapes.pattern.NamedPattern;

import java.util.LinkedList;
import java.util.List;

import static com.ksidelta.library.gesta.shapes.pattern.PatternsFactory.createAngleLine;
import static com.ksidelta.library.gesta.shapes.pattern.PatternsFactory.createHorizontalLine;
import static com.ksidelta.library.gesta.shapes.pattern.PatternsFactory.createRunicP;
import static com.ksidelta.library.gesta.shapes.pattern.PatternsFactory.createVerticalLine;

/**
 * Example like PatternVault creator.
 */
public class PatternVaultFactory {

    static public PatternVault createEmptyPatternVault() {
        PatternMatcher patternMatcher = new ClosestPointsForwardMatcher(5);
        return new PatternVault(patternMatcher, PatternProcessorFactory.createStandardChain());
    }


    static public PatternVault createPreFilledPatternVault() {
        List<NamedPattern> namedPatterns = new LinkedList<>();
        namedPatterns.add(new NamedPattern("horizontal", createHorizontalLine()));
        namedPatterns.add(new NamedPattern("vertical", createVerticalLine()));
        namedPatterns.add(new NamedPattern("45* angle", createAngleLine(Math.PI * 1 / 4)));
        namedPatterns.add(new NamedPattern("Py!", createRunicP()));

        PatternMatcher patternMatcher = new ClosestPointsForwardMatcher(5);
        return new PatternVault(patternMatcher, PatternProcessorFactory.createStandardChain(), namedPatterns);
    }

}
