package my.model;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 */
public class Rhombus extends IsoscenesTriangle {
    /**
     *
     */
    public static Integer[] OPTIONS = new Integer[]{4};

    /**
     * Default constructor
     */
    public Rhombus(Point2D reference, int sideNumber) {
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
            super.setPointAt(3, points[1].midpoint(points[0]).subtract(
                    vector.getX() * Math.cos(angle) - vector.getY() * Math.sin(angle),
                    vector.getX() * Math.sin(angle) + vector.getY() * Math.cos(angle)));
        } else if (pos == 3) {
            throw new IllegalArgumentException("Illegal position");
        }
    }

    /**
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        double[][] xy = new double[][]{{points[0].getX(), points[2].getX(), points[1].getX(), points[3].getX()},
                {points[0].getY(), points[2].getY(), points[1].getY(), points[3].getY()}};
        if (filled) {
            gc.setFill(fillColor);
            gc.fillPolygon(xy[0], xy[1], sideNumber);
        }
        gc.setStroke(borderColor);
        gc.setLineWidth(borderWidth);
        gc.strokePolygon(xy[0], xy[1], sideNumber);
    }

    /**
     * @param point
     */
    @Override
    public boolean intersects(Point2D point) {
        double dist = Math.max(borderWidth, 10);
        return nearLine(points[0], points[2], point, dist) ||
                nearLine(points[2], points[1], point, dist) ||
                nearLine(points[1], points[3], point, dist) ||
                nearLine(points[3], points[0], point, dist) ||
                (filled && isInside(new Point2D[]{points[0], points[2], points[1], points[3]}, point));
    }
}