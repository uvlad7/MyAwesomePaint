package my.model;

import javafx.geometry.Point2D;

/**
 *
 */
public class RegularPolygon extends Polygon {
    /**
     *
     */
    public static Integer[] OPTIONS = new Integer[]{3, 4, 5, 6, 7, 8, 9, 10};

    /**
     * Default constructor
     */
    public RegularPolygon(Point2D reference, int sideNumber) {
        super(reference, sideNumber);
    }

    /**
     * @param pos
     * @param point
     */
    @Override
    public void setPointAt(int pos, Point2D point) {
        if (pos == 1) {
            double angle = Math.toRadians(360.0 / sideNumber);
            Point2D vector = point.subtract(reference);
            points[0] = point;
            for (int i = 1; i < sideNumber; i++) {
                points[i] = reference.add(vector.getX() * Math.cos(angle * i) - vector.getY() * Math.sin(angle * i),
                        vector.getX() * Math.sin(angle * i) + vector.getY() * Math.cos(angle * i));
            }

        } else {
            throw new IllegalArgumentException("Illegal position: " + pos);
        }
    }

    /**
     * @return
     */
    @Override
    public int numberOfPoints() {
        return 2;
    }
}