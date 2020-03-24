package my.model;

import javafx.geometry.Point2D;

/**
 *
 */
public abstract class ABaseAndHeightFigure extends Polygon {

    /**
     *
     */
    protected double height;

    /**
     * Default constructor
     */
    public ABaseAndHeightFigure(Point2D reference, int sideNumber) {
        super(reference, sideNumber);
        height = 0;
    }

    /**
     * @param pos
     * @param point
     */
    @Override
    public void setPointAt(int pos, Point2D point) {
        super.setPointAt(pos, point);
        if (pos == 2) {
            height = points[1].distance(points[2]) *
                    Math.sin(angle(points[0].subtract(points[1]), points[2].subtract(points[1])));
        }
    }

    private double angle(Point2D v1, Point2D v2) {
        return Math.atan2(v1.getY(), v1.getX()) - Math.atan2(v2.getY(), v2.getX());
    }

    /**
     * @return
     */
    @Override
    public int numberOfPoints() {
        return 3;
    }
}