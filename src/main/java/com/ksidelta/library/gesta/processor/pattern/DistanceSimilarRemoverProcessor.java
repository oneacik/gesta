package com.ksidelta.library.gesta.processor.pattern;

import com.ksidelta.library.gesta.shapes.pattern.Pattern;
import com.ksidelta.library.gesta.shapes.Point;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Processor used to minimize number of points to trace.
 * Also removes dense groups that may fuck up matching process.
 */
public class DistanceSimilarRemoverProcessor implements PatternProcessor {
    private final double minDistance;

    /**
     * Remember that normalized Patterns are in range (-1,1)/(-1,1)
     * therefore 0.02 is equivalent of one percent of size
     *
     * @param minDistance
     */
    public DistanceSimilarRemoverProcessor(double minDistance) {
        this.minDistance = minDistance;
    }

    @Override
    public Optional<Pattern> process(Pattern pattern) {
        List<Point> points = pattern.getPoints();
        List<Point> filtered = new LinkedList<>();
        Optional<Point> previousPoint = Optional.empty();
        //fucking unholy abomination - why functional in java is too retarded!?
        for (Point p : points) {
            if (previousPoint.map(x -> p.distance(x) > minDistance).orElse(true)) {
                previousPoint = Optional.of(p);
                filtered.add(p);
            }
        }
        return Optional.of(pattern.setPoints(filtered));
    }
}
