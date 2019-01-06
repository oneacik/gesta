package com.ksidelta.library.gesta.shapes.pattern;

import com.ksidelta.library.gesta.shapes.Point;
import com.ksidelta.library.gesta.shapes.PositionTransformable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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
        this.points.forEach(point -> point.translate(x, y));
        return this;
    }

    public Pattern scale(double x, double y) {
        this.points.forEach(point -> point.scale(x, y));
        return this;
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
