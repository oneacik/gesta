package com.ksidelta.library.gesta.matcher;

class ClosestPointsForwardMatcherTest extends AbstractPatternVaultSimpleMatchingTest {

    @Override
    PatternMatcher getMatcher() {
        return new ClosestPointsForwardMatcher(7);
    }
}
