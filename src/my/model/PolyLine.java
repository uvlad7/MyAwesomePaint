package my.model;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 */
public class PolyLine extends AFigure {
    /**
     *
     */
    public static Integer[] OPTIONS = new Integer[]{2, 3, 4, 5, 6, 7, 8, 9, 10};
    /**
     *
     */
    Segment[] segments;
    /**
     *
     */
    int sideNumber;

    /**
     * Default constructor
     */
    public PolyLine(Point2D reference, int sideNumber) {
        super(reference, sideNumber);
        this.sideNumber = sideNumber;
        segments = new Segment[sideNumber];
        for (int i = 0; i < sideNumber; i++) {
            segments[i] = new Segment(reference, 0);
        }
    }

    /**
     * @param pos
     * @param point
     */
    @Override
    public void setPointAt(int pos, Point2D point) {
        if (pos > 0 && pos <= sideNumber) {
            segments[pos - 1].setPointAt(1, point);
            for (int i = pos; i < sideNumber; i++) {
                segments[i].move(point);
            }
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
        if (pos > 0 && pos <= sideNumber) {
            return segments[sideNumber - 1].getPointAt(1);
        }
        throw new IllegalArgumentException("Illegal position: " + pos);
    }

    /**
     * @param point
     */
    @Override
    public void move(Point2D point) {
        super.move(point);
        segments[0].move(point);
        for (int i = 1; i < sideNumber; i++) {
            segments[i].move(segments[i - 1].direction);
        }
    }

    /**
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        for (int i = 0; i < sideNumber; i++) {
            segments[i].draw(gc);
        }
    }

    /**
     * @param point
     */
    @Override
    public boolean intersects(Point2D point) {
        for (int i = 0; i < sideNumber; i++) {
            if (segments[i].intersects(point)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return
     */
    @Override
    public int numberOfPoints() {
        return sideNumber + 1;
    }

    @Override
    public void setBorderColor(Color color) {
        super.setBorderColor(color);
        for (int i = 0; i < sideNumber; i++) {
            segments[i].setBorderColor(color);
        }
    }

    @Override
    public void setBorderWidth(double w) {
        super.setBorderWidth(w);
        for (int i = 0; i < sideNumber; i++) {
            segments[i].setBorderWidth(w);
        }
    }
}