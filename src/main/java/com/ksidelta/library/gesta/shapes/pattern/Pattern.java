package com.ksidelta.library.gesta.shapes.pattern;

import com.google.common.collect.Streams;
import com.ksidelta.library.gesta.shapes.Point;
import com.ksidelta.library.gesta.shapes.PositionTransformable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Integer.min;

/**
 * TODO: Document this class / interface here
 *
 * @since v7.4
 */
public class Pattern implements PositionTransformable<Pattern> {
    private List<Point> points;

    public Pattern() {
        this.points = new LinkedList<Point>();
    }

    public Pattern(List<Point> points) {
        this.points = points;
    }

    /**
     * NON FUNCTIONAL!!!
     * <p>
     * This one doesn't recreate whole Pattern
     */
    public Pattern addPoint(Point p) {
        this.points.add(p);
        return this;
    }

    public List<Point> getPoints() {
        return points;
    }

    public Pattern setPoints(List<Point> points) {
        this.points = points;
        return this;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Pattern(new LinkedList<Point>(this.points));
    }

    public Pattern translate(double x, double y) {
        return new Pattern(
                this.points.stream()
                        .map(point -> point.translate(x, y))
                        .collect(Collectors.toList())
        );
    }

    public Pattern scale(double x, double y) {
        return new Pattern(
                this.points.stream()
                        .map(point -> point.scale(x, y))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public double getX() {
        return getMin(Point::getX);
    }

    @Override
    public double getY() {
        return getMin(Point::getY);
    }

    public double getWidth() {
        return getMinMax(Point::getX);
    }

    public double getHeight() {
        return getMinMax(Point::getY);
    }

    public double getLength() {
        class PointWithSum {
            Point point;
            double sum;

            public PointWithSum(Point point, double sum) {
                this.point = point;
                this.sum = sum;
            }

            public Point point() {
                return point;
            }

            public double sum() {
                return sum;
            }
        }

        return points.stream().reduce(
                new PointWithSum(points.get(0), 0.0),
                (a, b) -> new PointWithSum(b, a.point().distance(b) + a.sum()),
                (a, b) -> {
                    throw new RuntimeException("This shouldn't be ever used");
                }
        ).sum();
    }

    public Point minus(Pattern pattern) {
        class Pa {
            Point a;
            Point b;

            public Pa(Point a, Point b) {
                this.a = a;
                this.b = b;
            }

            public Point minus() {
                return a.minus(b);
            }
        }


        return Streams.zip(this.getPoints().stream(), pattern.getPoints().stream(), (a, b) -> new Pa(a, b))
                .map(x -> x.minus())
                .reduce((a, b) -> a.translate(b.getX(), b.getY()))
                .map(point ->
                        new Point(
                                point.getX() / min(getPoints().size(), pattern.getPoints().size()),
                                point.getY() / min(getPoints().size(), pattern.getPoints().size())
                        )
                )
                .orElseThrow(() -> new IllegalStateException("Need more points"));
    }


    private double getMin(Function<Point, Double> func) {
        return points.stream().map(func).min(Double::compareTo).orElse(0.0);
    }

    private double getMinMax(Function<Point, Double> func) {
        return points.stream().map(func).max(Double::compareTo).orElse(0.0)
                - points.stream().map(func).min(Double::compareTo).orElse(0.0);
    }

    public Optional<Pattern> toOptional() {
        return Optional.of(this);
    }
}
