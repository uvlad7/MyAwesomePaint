package my.model;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 */
public class Circle extends AShape {
    /**
     *
     */
    public static Integer[] OPTIONS = new Integer[]{2};
    /**
     *
     */
    Point2D anchor;

    /**
     * Default constructor
     */
    public Circle(Point2D reference, int sideNumber) {
        super(reference, sideNumber);
        anchor = Point2D.ZERO;
    }

    /**
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        double r = Math.min(Math.abs(anchor.getX()), Math.abs(anchor.getY()));
        double offsetX = Math.min(r * Math.signum(anchor.getX()), 0);
        double offsetY = Math.min(r * Math.signum(anchor.getY()), 0);
        if (filled) {
            gc.setFill(fillColor);
            gc.fillOval(reference.getX() + offsetX, reference.getY() + offsetY, r, r);
        }
        gc.setStroke(borderColor);
        gc.setLineWidth(borderWidth);
        gc.strokeOval(reference.getX() + offsetX, reference.getY() + offsetY, r, r);
    }

    /**
     * @param point
     */
    @Override
    public boolean intersects(Point2D point) {
        double r = Math.min(Math.abs(anchor.getX()), Math.abs(anchor.getY()));
        double d = point.distance(reference.getX() + Math.min(r * Math.signum(anchor.getX()), 0) + r / 2,
                reference.getY() + Math.min(r * Math.signum(anchor.getY()), 0) + r / 2);
        if (filled) {
            return d < Math.max(borderWidth, 10) + r / 2;
        }
        return Math.abs(d - r / 2) < Math.max(borderWidth, 10);
    }

    /**
     * @param pos
     * @param point
     */
    @Override
    public void setPointAt(int pos, Point2D point) {
        if (pos == 1) {
            anchor = point.subtract(reference);
        } else {
            throw new IllegalArgumentException("Illegal position: " + pos);
        }
    }

    /**
     * @param pos
     * @return
     */
    @Override
    public Point2D getPointAt(int pos) {
        if (pos == 1) {
            return anchor.add(reference);
        }
        throw new IllegalArgumentException("Illegal position: " + pos);
    }

    /**
     * @return
     */
    @Override
    public int numberOfPoints() {
        return 2;
    }
}