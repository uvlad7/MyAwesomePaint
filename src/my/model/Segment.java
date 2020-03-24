package my.model;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;


/**
 *
 */
public class Segment extends AFigure {
    /**
     *
     */
    public static Integer[] OPTIONS = new Integer[]{1};

    /**
     *
     */
    Point2D direction;
    double angle;

    /**
     * Default constructor
     */
    public Segment(Point2D reference, int sideNumber) {
        super(reference, sideNumber);
        angle = 0;
        direction = reference;
    }

    /**
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(borderColor);
        gc.setLineWidth(borderWidth);
        gc.strokeLine(reference.getX(), reference.getY(), direction.getX(), direction.getY());
    }

    /**
     * @param pos
     * @param point
     */
    @Override
    public void setPointAt(int pos, Point2D point) {
        if (pos == 1) {
            direction = point;
            angle = Math.atan2(direction.getY() - reference.getY(), direction.getX() - reference.getX());
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
            return direction;
        }
        throw new IllegalArgumentException("Illegal position: " + pos);
    }

    /**
     * @param point
     */
    @Override
    public void move(Point2D point) {
        direction = direction.add(point.getX() - reference.getX(), point.getY() - reference.getY());
        super.move(point);
    }

    /**
     * @param point
     */
    @Override
    public boolean intersects(Point2D point) {
        return nearLine(reference, direction, point,  Math.max(borderWidth, 10));
    }

    boolean nearLine(Point2D start, Point2D end, Point2D point, double dist) {
        return Math.max(point.distance(end), point.distance(start)) <
                dist + end.distance(reference) &&
                end.distance(point) * Math.sin(Math.toRadians(end.angle(reference, point))) < dist;
    }

    /**
     * @return
     */
    @Override
    public int numberOfPoints() {
        return 2;
    }
}