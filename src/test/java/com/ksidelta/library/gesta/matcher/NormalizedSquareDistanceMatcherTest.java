package com.ksidelta.library.gesta.matcher;

import static org.junit.jupiter.api.Assertions.*;

class NormalizedSquareDistanceMatcherTest extends AbstractPatternVaultSimpleMatchingTest{

    @Override
    PatternMatcher getMatcher() {
        return new NormalizedSquareDistanceMatcher(100);
    }
}
