package my.model;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 */
public class Ellipse extends Circle {
    /**
     *
     */
    public static Integer[] OPTIONS = new Integer[]{2};

    /**
     * Default constructor
     */
    public Ellipse(Point2D reference, int sideNumber) {
        super(reference, sideNumber);
    }

    /**
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        double r1 = Math.abs(anchor.getX());
        double r2 = Math.abs(anchor.getY());
        double offsetX = Math.min(r1 * Math.signum(anchor.getX()), 0);
        double offsetY = Math.min(r2 * Math.signum(anchor.getY()), 0);
        if (filled) {
            gc.setFill(fillColor);
            gc.fillOval(reference.getX() + offsetX, reference.getY() + offsetY, r1, r2);
        }
        gc.setStroke(borderColor);
        gc.setLineWidth(borderWidth);
        gc.strokeOval(reference.getX() + offsetX, reference.getY() + offsetY, r1, r2);
    }

    /**
     * @param point
     */
    @Override
    public boolean intersects(Point2D point) {
        double a = Math.max(Math.abs(anchor.getX()), Math.abs(anchor.getY())) / 2;
        double b = Math.min(Math.abs(anchor.getX()), Math.abs(anchor.getY())) / 2;
        double c = Math.sqrt(a * a - b * b);
        Point2D center = reference.add(anchor.getX() / 2, anchor.getY() / 2), f1, f2;
        if (Math.abs(anchor.getX()) > Math.abs(anchor.getY())) {
            f1 = center.add(c, 0);
            f2 = center.subtract(c, 0);
        } else {
            f1 = center.add(0, c);
            f2 = center.subtract(0, c);
        }
        if (filled) {
            return point.distance(f1) + point.distance(f2) < 2 * (Math.max(borderWidth, 10) + a);
        }
        return Math.abs(point.distance(f1) + point.distance(f2) - 2 * a) < 2 * Math.max(borderWidth, 10);
    }
}