package my.model;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 */
public abstract class AFigure {
    /**
     *
     */
    Point2D reference;
    /**
     *
     */
    Color borderColor = Color.BLACK;
    /**
     *
     */
    double borderWidth = 1;
    public AFigure(Point2D reference, int sideNumber) {
        this.reference = reference;
    }

    /**
     * @param gc
     */
    public abstract void draw(GraphicsContext gc);

    /**
     * @param point
     */
    public abstract boolean intersects(Point2D point);

    /**
     * @param point
     */
    public void move(Point2D point) {
        this.reference = point;
    }

    /**
     * @return
     */
    public Point2D getReference() {
        return reference;
    }

    /**
     * @return
     */
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * @param color
     */
    public void setBorderColor(Color color) {
        this.borderColor = color;
    }

    /**
     * @return
     */
    public double getBorderWidth() {
        return borderWidth;
    }

    /**
     * @param w
     */
    public void setBorderWidth(double w) {
        this.borderWidth = w;
    }

    /**
     * @param pos
     * @param point
     */
    public void setPointAt(int pos, Point2D point) {

    }

    /**
     * @param pos
     * @return
     */
    public Point2D getPointAt(int pos) {
        return null;
    }

    /**
     * @return
     */
    public boolean getFilled() {
        return false;
    }

    /**
     * @param f
     */
    public void setFilled(boolean f) {
    }

    /**
     * @return
     */
    public Color getFillColor() {
        return null;
    }

    /**
     * @param fillColor
     */
    public void setFillColor(Color fillColor) {
    }

    /**
     * @return
     */
    public int numberOfPoints() {
        return 1;
    }
}