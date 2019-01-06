package com.ksidelta.library.gesta.vault;

import com.ksidelta.library.gesta.matcher.PatternMatcher;
import com.ksidelta.library.gesta.processor.pattern.PatternProcessor;
import com.ksidelta.library.gesta.shapes.pattern.NamedPattern;
import com.ksidelta.library.gesta.shapes.pattern.Pattern;
import com.ksidelta.library.gesta.vault.tuples.NamedPatternScore;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * TODO: Document this class / interface here
 *
 * @since v7.4
 */
public class PatternVault {
    final private PatternMatcher patternMatcher;
    final private List<NamedPattern> patterns;
    final private List<PatternProcessor> patternProcessors;

    public PatternVault(PatternMatcher patternMatcher, List<PatternProcessor> patternProcessors) {
        this.patternMatcher = patternMatcher;
        this.patternProcessors = patternProcessors;
        this.patterns = new LinkedList<>();
    }

    public PatternVault(PatternMatcher patternMatcher, List<PatternProcessor> patternProcessors, List<NamedPattern> patterns) {
        this.patternMatcher = patternMatcher;
        this.patternProcessors = patternProcessors;
        this.patterns = patterns.stream()
                .map(x -> new NamedPattern(x.getName(),
                        processPattern(x.getPattern())
                                .orElseThrow(() -> new IllegalArgumentException("Pattern Processing Failed"))
                ))
                .collect(Collectors.toList());
    }

    public PatternVault addPattern(Pattern pattern, String name) {
        patterns.add(
                new NamedPattern(name,
                        processPattern(pattern)
                                .orElseThrow(() -> new IllegalArgumentException("Pattern Processing Failed"))
                )
        );
        return this;
    }

    public Optional<NamedPatternScore> matchPattern(Pattern pattern) {
        if (pattern.getPoints().isEmpty()) {
            return Optional.empty();
        }

        return processPattern(pattern).map(pie ->
                patterns.stream()
                        .map(patternToMatch ->
                                new NamedPatternScore(patternToMatch,
                                        patternMatcher.match(patternToMatch.getPattern(), pie)
                                )
                        )
                        .min(Comparator.comparingDouble(NamedPatternScore::getScore))
                        .orElseThrow(() -> new IllegalStateException("Patterns to compare should exist"))
        );

    }

    private Optional<Pattern> processPattern(Pattern pattern) {
        Optional<Pattern> processed = Optional.of(pattern);
        for (PatternProcessor patternProcessor : this.patternProcessors) {
            processed = processed.flatMap(patternProcessor::process);
        }
        return processed;
    }


}
