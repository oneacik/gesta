package com.ksidelta.library.gesta.shapes.pattern;

import com.ksidelta.library.gesta.shapes.Point;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This pattern factory create patterns with shitload of points
 * You should use correct value for {@link com.ksidelta.library.gesta.processor.pattern.DistanceSimilarRemoverProcessor}
 * and correct forwardLookValue for {@link com.ksidelta.library.gesta.matcher.ClosestPointsForwardMatcher}
 * <p>
 * Or just fuck it and use random PatternVault from PatternVaultFactory
 *
 * @since v7.4
 */
public class PatternsFactory {
    static final int MANY_TIMES = 1000;

    static public Pattern createHorizontalLine() {
        return new Pattern(lineTo(new Point(-1, 0), new Point(1, 0), MANY_TIMES));
    }

    static public Pattern createVerticalLine() {
        return new Pattern(lineTo(new Point(0, -1), new Point(0, 1), MANY_TIMES));
    }

    static public Pattern createAngleLine(double angle) {
        return new Pattern(
                lineTo(
                        new Point(-Math.cos(angle), -Math.sin(angle)),
                        new Point(Math.cos(angle), Math.sin(angle)),
                        MANY_TIMES
                )
        );
    }

    static public Pattern createRunicP() {
        List<Point> points = new LinkedList<>();
        points.addAll(lineTo(new Point(-1, 0), new Point(1, 0), MANY_TIMES));
        points.addAll(lineTo(new Point(1, 0), new Point(0, 1), MANY_TIMES));
        points.addAll(lineTo(new Point(0, 1), new Point(0, -1), MANY_TIMES));
        return new Pattern(points);
    }

    static public List<Point> lineTo(Point from, Point to, int times) {
        Function<Integer, Double> fraction = x -> x / (double) times;
        Function<Integer, Double> reverseFraction = x -> 1.0 - x / (double) times;

        return IntStream.rangeClosed(0, times).mapToObj(
                x -> new Point(
                        reverseFraction.apply(x) * from.getX() + fraction.apply(x) * to.getX(),
                        reverseFraction.apply(x) * from.getY() + fraction.apply(x) * to.getY()
                )
        ).collect(Collectors.toList());
    }


}
