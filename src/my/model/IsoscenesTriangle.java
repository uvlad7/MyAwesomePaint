package my.model;

import javafx.geometry.Point2D;

/**
 *
 */
public class IsoscenesTriangle extends ABaseAndHeightFigure {
    /**
     *
     */
    public static Integer[] OPTIONS = new Integer[]{3};

    /**
     * Default constructor
     */
    public IsoscenesTriangle(Point2D reference, int sideNumber) {
        super(reference, sideNumber);
    }

    /**
     * @param pos
     * @param point
     */
    public void setPointAt(int pos, Point2D point) {
        super.setPointAt(pos, point);
        if (pos == 2) {
            Point2D vector = points[1].subtract(points[0]).normalize().multiply(height);
            double angle = Math.toRadians(90);
            super.setPointAt(2, points[1].midpoint(points[0]).add(
                    vector.getX() * Math.cos(angle) - vector.getY() * Math.sin(angle),
                    vector.getX() * Math.sin(angle) + vector.getY() * Math.cos(angle)));
        }
    }
}