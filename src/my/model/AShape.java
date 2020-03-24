package my.model;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/**
 *
 */
public abstract class AShape extends AFigure {
    /**
     * @param point
     */
    @Override
    public boolean intersects(Point2D point) {
        return false;
    }

    /**
     *
     */
    boolean filled = true;
    /**
     *
     */
    Color fillColor = Color.WHITE;

    /**
     * Default constructor
     */
    public AShape(Point2D reference, int sideNumber) {
        super(reference, sideNumber);
    }

    /**
     * @return
     */
    @Override
    public boolean getFilled() {
        return filled;
    }

    /**
     * @param f
     */
    @Override
    public void setFilled(boolean f) {
        filled = f;
    }

    /**
     * @return
     */
    @Override
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * @param fillColor
     */
    @Override
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }


}