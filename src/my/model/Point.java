package my.model;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 */
public class Point extends AFigure {
    /**
     *
     */
    public static Integer[] OPTIONS = new Integer[]{0};

    /**
     * Default constructor
     */
    public Point(Point2D reference, int sideNumber) {
        super(reference, sideNumber);
    }

    /**
     * @param pos
     * @param point
     */
    @Override
    public void setPointAt(int pos, Point2D point) {
        throw new IllegalArgumentException("Illegal position: " + pos);
    }

    /**
     * @param pos
     * @return
     */
    @Override
    public Point2D getPointAt(int pos) {
        throw new IllegalArgumentException("Illegal position: " + pos);
    }

    /**
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(borderColor);
        gc.setLineWidth(borderWidth);
        gc.strokeLine(reference.getX(), reference.getY(), reference.getX(), reference.getY());
    }

    /**
     * @param point
     */
    @Override
    public boolean intersects(Point2D point) {
        return point.distance(reference) < Math.max(borderWidth, 10);
    }
}