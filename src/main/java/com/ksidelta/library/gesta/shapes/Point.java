package com.ksidelta.library.gesta.shapes;

/**
 * TODO: Document this class / interface here
 *
 * @since v7.4
 */
public class Point implements PositionTransformable<Point> {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point translate(double x, double y) {
        return new Point(this.x + x, this.y + y);
    }

    public Point scale(double x, double y) {
        return new Point(this.x * x, this.y * y);
    }

    public double squareDistance(Point p) {
        return Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2);
    }

    public double distance(Point p) {
        return Math.sqrt(squareDistance(p));
    }

    public Point minus(Point point) {
        return new Point(this.getX() - point.getX(), this.getY() - point.getY());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
