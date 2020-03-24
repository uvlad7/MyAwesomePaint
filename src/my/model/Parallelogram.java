package my.model;

import javafx.geometry.Point2D;

/**
 *
 */
public class Parallelogram extends ABaseAndHeightFigure {
    /**
     *
     */
    public static Integer[] OPTIONS = new Integer[]{4};

    /**
     * Default constructor
     */
    public Parallelogram(Point2D reference, int sideNumber) {
        super(reference, sideNumber);
    }

    /**
     * @param pos
     * @param point
     */
    public void setPointAt(int pos, Point2D point) {
        super.setPointAt(pos, point);
        if (pos == 2) {
            super.setPointAt(3, points[2].add(points[0].subtract(points[1])));
        } else if (pos == 3) {
            throw new IllegalArgumentException("Illegal position");
        }
    }
}