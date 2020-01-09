package com.ksidelta.library.gesta.matcher;

class NormalizedSquareDistanceMatcherTest extends AbstractPatternVaultSimpleMatchingTest {

    @Override
    PatternMatcher getMatcher() {
        return new NormalizedSquareDistanceMatcher(100);
    }
}
