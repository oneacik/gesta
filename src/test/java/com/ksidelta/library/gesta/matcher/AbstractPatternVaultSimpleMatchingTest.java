package com.ksidelta.library.gesta.matcher;

import com.ksidelta.library.gesta.processor.pattern.PatternProcessorFactory;
import com.ksidelta.library.gesta.shapes.pattern.NamedPattern;
import com.ksidelta.library.gesta.vault.PatternVault;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static com.ksidelta.library.gesta.shapes.pattern.PatternsFactory.createAngleLine;
import static com.ksidelta.library.gesta.shapes.pattern.PatternsFactory.createHorizontalLine;
import static com.ksidelta.library.gesta.shapes.pattern.PatternsFactory.createRunicP;
import static com.ksidelta.library.gesta.shapes.pattern.PatternsFactory.createVerticalLine;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


/**
 * TODO: Document this class / interface here
 *
 * @since v7.4
 */
public abstract class AbstractPatternVaultSimpleMatchingTest {
    PatternVault patternVault;

    abstract PatternMatcher getMatcher();

    @BeforeEach
    public void setUp() {
        PatternMatcher patternMatcher = getMatcher();
        this.patternVault = new PatternVault(patternMatcher, PatternProcessorFactory.createStandardChain(), createSimplePatterns());
    }

    @Test
    public void horizontalAndVerticalMatching() {
        assertEquals(patternVault.matchPattern(createHorizontalLine()).get().getNamedPattern().getName(), "horizontal");
        assertEquals(patternVault.matchPattern(createVerticalLine()).get().getNamedPattern().getName(), "vertical");
    }

    @Test
    public void samePatternsMatchedGiveZeroError() {
        assertEquals(patternVault.matchPattern(createHorizontalLine()).get().getScore(), 0.0, 0.0001);
        assertEquals(patternVault.matchPattern(createVerticalLine()).get().getScore(), 0.0, 0.0001);
    }

    @Test
    public void reversePatternsShouldHaveBigError() {
        assertNotEquals(0.0, patternVault.matchPattern(createHorizontalLine().scale(-1, 1)).get().getScore(), 0.5);
        assertNotEquals(0.0, patternVault.matchPattern(createVerticalLine().scale(1, -1)).get().getScore(), 0.5);
    }

    @Test
    public void angledLinesShouldMatch() {
        assertEquals(patternVault.matchPattern(createAngleLine(Math.PI * 1 / 12)).get().getNamedPattern().getName(), "horizontal");
        assertEquals(patternVault.matchPattern(createAngleLine(Math.PI * 10 / 12)).get().getNamedPattern().getName(), "vertical");

    }

    private List<NamedPattern> createSimplePatterns() {
        List<NamedPattern> namedPatterns = new LinkedList<>();
        namedPatterns.add(new NamedPattern("horizontal", createHorizontalLine()));
        namedPatterns.add(new NamedPattern("vertical", createVerticalLine()));
        namedPatterns.add(new NamedPattern("45* angle", createAngleLine(Math.PI * 1 / 4)));
        namedPatterns.add(new NamedPattern("Py!", createRunicP()));
        return namedPatterns;
    }
}
