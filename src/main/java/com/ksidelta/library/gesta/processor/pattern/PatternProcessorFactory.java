package com.ksidelta.library.gesta.processor.pattern;

import java.util.LinkedList;
import java.util.List;

/**
 * TODO: Document this class / interface here
 *
 * @since v7.4
 */
public class PatternProcessorFactory {

    static public List<PatternProcessor> createStandardChain() {
        List<PatternProcessor> patternProcessors = new LinkedList<>();
        patternProcessors.add(new PatternCentererProcessor());
        patternProcessors.add(new DistanceSimilarRemoverProcessor(0.1));
        return patternProcessors;
    }

}
