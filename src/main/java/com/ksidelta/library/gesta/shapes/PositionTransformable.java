package com.ksidelta.library.gesta.shapes;

/**
 * TODO: Document this class / interface here
 *
 * @since v7.4
 */
public interface PositionTransformable<T> {
    public T translate(double x, double y);
    public T scale(double x, double y);
    public double getX();
    public double getY();
}
