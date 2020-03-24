package my.model;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 */
public class Ray extends Segment {
    /**
     *
     */
    public static Integer[] OPTIONS = new Integer[]{2};

    /**
     * Default constructor
     */
    public Ray(Point2D reference, int sideNumber) {
        super(reference, sideNumber);
    }

    /**
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        gc.strokeLine(direction.getX(), direction.getY(), direction.getX() + 10000 * Math.cos(angle),
                direction.getY() + 10000 * Math.sin(angle));
    }

    /**
     * @param point
     */
    @Override
    public boolean intersects(Point2D point) {
        double dist = Math.max(borderWidth, 10);
        return (Math.max(point.distance(direction), point.distance(reference)) <
                dist + direction.distance(reference) ||
                point.distance(direction) < point.distance(reference)) &&
                direction.distance(point) * Math.sin(Math.toRadians(direction.angle(reference, point))) < dist;
    }
}