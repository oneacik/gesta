package com.ksidelta.library.gesta;

import com.ksidelta.library.gesta.processor.pattern.PatternProcessorFactory;
import com.ksidelta.library.gesta.matcher.ClosestPointsForwardMatcher;
import com.ksidelta.library.gesta.matcher.PatternMatcher;
import com.ksidelta.library.gesta.shapes.pattern.NamedPattern;
import com.ksidelta.library.gesta.vault.PatternVault;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static com.ksidelta.library.gesta.shapes.pattern.PatternsFactory.createAngleLine;
import static com.ksidelta.library.gesta.shapes.pattern.PatternsFactory.createHorizontalLine;
import static com.ksidelta.library.gesta.shapes.pattern.PatternsFactory.createVerticalLine;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

;

/**
 * TODO: Document this class / interface here
 *
 * @since v7.4
 */
public class TestPatternVaultSimpleMatching {
    PatternVault patternVault;

    @Before
    public void setUp() {
        PatternMatcher patternMatcher = new ClosestPointsForwardMatcher(5);
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
    public void angledLinesShouldMatch(){
        assertEquals(patternVault.matchPattern(createAngleLine(Math.PI*1/6)).get().getNamedPattern().getName(), "horizontal");
        assertEquals(patternVault.matchPattern(createAngleLine(Math.PI*2/6)).get().getNamedPattern().getName(), "vertical");

    }

    private List<NamedPattern> createSimplePatterns() {
        List<NamedPattern> namedPatterns = new LinkedList<>();
        namedPatterns.add(new NamedPattern("horizontal", createHorizontalLine()));
        namedPatterns.add(new NamedPattern("vertical", createVerticalLine()));
        return namedPatterns;
    }
}
