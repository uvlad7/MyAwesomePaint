package my.model;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 */
public class Straight extends Ray {
    /**
     *
     */
    public static Integer[] OPTIONS = new Integer[]{1};

    /**
     * Default constructor
     */
    public Straight(Point2D reference, int sideNumber) {
        super(reference, sideNumber);
    }

    /**
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        gc.strokeLine(reference.getX(), reference.getY(), reference.getX() + 10000 * Math.cos(angle + Math.PI),
                reference.getY() + 10000 * Math.sin(angle + Math.PI));
    }

    /**
     * @param point
     */
    @Override
    public boolean intersects(Point2D point) {
        return direction.distance(point) *
                Math.sin(Math.toRadians(direction.angle(reference, point))) < Math.max(borderWidth, 10);
    }

}