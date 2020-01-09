package com.ksidelta.library.gesta.matcher;

import com.google.common.collect.Streams;
import com.ksidelta.library.gesta.shapes.Point;
import com.ksidelta.library.gesta.shapes.pattern.Pattern;

/**
 * Matcher normalizing linearly distance between {@link Point}s.
 * It counts square avarage of distance between two {@link Pattern}s.
 */
public class NormalizedSquareDistanceMatcher implements PatternMatcher {
    public final Integer normalizedNumberOfPoints;

    NormalizedSquareDistanceMatcher() {
        this.normalizedNumberOfPoints = 100;
    }

    NormalizedSquareDistanceMatcher(Integer normalizedNumberOfPoints) {
        this.normalizedNumberOfPoints = normalizedNumberOfPoints;
    }

    @Override
    public double match(Pattern first, Pattern second) {
        first = normalize(first);
        second = normalize(second);

        first = moveTooClose(first, second);

        return calculate(first, second);
    }

    public double calculate(Pattern first, Pattern second) {
        return Streams.zip(
                first.getPoints().stream(),
                second.getPoints().stream(),
                Point::squareDistance
        )
                .mapToDouble(d -> d)
                .sum();
    }

    public Pattern moveTooClose(Pattern toBeMoved, Pattern toStay) {
        Point mover = toStay.minus(toBeMoved);
        return toBeMoved.translate(mover.getX(), mover.getY());
    }

    public Pattern normalize(Pattern pattern) {
        Double length = pattern.getLength();
        Double normalizedLength = length / (normalizedNumberOfPoints - 1);
        return _normalize(head(pattern), tail(pattern), normalizedLength, normalizedLength, new Pattern());
    }

    public Pattern _normalize(
            Point before,
            Pattern restOfPattern,
            Double remainingLength,
            Double normalizedLength,
            Pattern normalizedPattern
    ) {
        if (restOfPattern.getPoints().isEmpty())
            return normalizedPattern.addPoint(before);

        Point after = head(restOfPattern);
        Double distance = before.distance(after);

        if (distance > remainingLength) {
            Double ratio = remainingLength / distance;
            Double antyRatio = 1.0 - remainingLength / distance;

            Point newPoint = new Point(
                    before.getX() * antyRatio + head(restOfPattern).getX() * ratio,
                    before.getY() * antyRatio + head(restOfPattern).getY() * ratio
            );

            return _normalize(newPoint, restOfPattern, normalizedLength, normalizedLength, normalizedPattern.addPoint(newPoint));
        }

        return _normalize(
                head(restOfPattern),
                tail(restOfPattern),
                remainingLength - distance,
                normalizedLength,
                normalizedPattern
        );
    }

    public static Point head(Pattern pattern) {
        return pattern.getPoints().get(0);
    }

    public static Pattern tail(Pattern pattern) {
        return new Pattern(pattern.getPoints().subList(1, pattern.getPoints().size()));
    }


}
