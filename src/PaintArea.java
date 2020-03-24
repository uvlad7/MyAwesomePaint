import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import my.model.AFigure;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaintArea extends StackPane {
    private Canvas mainCanvas;
    private Canvas tempCanvas;
    //private Canvas backCanvas;
    private Class<? extends AFigure> selectedItem;
    private Color borderColor;
    private Color fillColor;
    private double borderWidth;
    private boolean filled;
    private int sideNumber;
    private int pointPos;
    private List<AFigure> figures;
    private AFigure current;
    private Point2D offset;

    public PaintArea(double width, double height, Class<? extends AFigure> selectedItem, Color borderColor, Color fillColor, double borderWidth, boolean filled, int sideNumber) {
        super.setWidth(width);
        super.setHeight(height);
        this.selectedItem = selectedItem;
        this.borderColor = borderColor;
        this.fillColor = fillColor;
        this.borderWidth = borderWidth;
        this.filled = filled;
        this.sideNumber = sideNumber;
        mainCanvas = new Canvas(width, height);
        mainCanvas.getGraphicsContext2D().setLineCap(StrokeLineCap.ROUND);
        mainCanvas.getGraphicsContext2D().setLineJoin(StrokeLineJoin.ROUND);
        tempCanvas = new Canvas(width, height);
        tempCanvas.getGraphicsContext2D().setLineCap(StrokeLineCap.ROUND);
        tempCanvas.getGraphicsContext2D().setLineJoin(StrokeLineJoin.ROUND);
        /*backCanvas = new Canvas(width, height);*/
        getChildren().addAll(/*backCanvas, */mainCanvas, tempCanvas);
        pointPos = 0;
        figures = new ArrayList<>();
        setOnMouseClicked(this::mouseClicked);
        setOnMouseMoved(this::mouseMoved);
        setOnMouseDragged(this::mouseDragged);
        setOnDragDetected(this::dragDetected);
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setBorderWidth(double borderWidth) {
        this.borderWidth = borderWidth;
    }

    public void setSelectedItem(Class<? extends AFigure> selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public int getSideNumber() {
        return sideNumber;
    }

    public void setSideNumber(int sideNumber) {
        this.sideNumber = sideNumber;
    }

    /*@Override
    public void setWidth(double width) {
        //TODO
        super.setWidth(width);
    }

    @Override
    public void setHeight(double height) {
        //TODO
        super.setHeight(height);
    }*/

    @Override
    public boolean isResizable() {
        return false;
    }

    public void mouseClicked(MouseEvent e) {
        switch (pointPos) {
            case 0:
                try {
                    current = selectedItem.getConstructor(Point2D.class, int.class).newInstance(new Point2D(e.getX(), e.getY()), sideNumber);
                    current.setBorderColor(borderColor);
                    current.setBorderWidth(borderWidth);
                    current.setFillColor(fillColor);
                    current.setFilled(filled);
                    current.draw(tempCanvas.getGraphicsContext2D());
                    pointPos = 1;
                } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }
                break;
            case -1:
                pointPos = 0;
                break;
            default:
                updateCreated(e.getX(), e.getY());
                pointPos++;
        }
        if (pointPos == current.numberOfPoints()) {
            pointPos = 0;
            figures.add(current);
            tempCanvas.getGraphicsContext2D().clearRect(0, 0, tempCanvas.getWidth(), tempCanvas.getHeight());
            current.draw(mainCanvas.getGraphicsContext2D());
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (pointPos > 0) {
            updateCreated(e.getX(), e.getY());
        } else if (pointPos == -1) {
            updateMoved(e.getX(), e.getY());
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (pointPos > 0) {
            updateCreated(e.getX(), e.getY());
        }
    }


    public void dragDetected(MouseEvent e) {
        if (pointPos == 0) {
            Point2D point = new Point2D(e.getX(), e.getY());
            for (int i = figures.size() - 1; i >= 0; i--) {
                if (figures.get(i).intersects(point)) {
                    current = figures.get(i);
                    pointPos = -1;
                    offset = current.getReference().subtract(e.getX(), e.getY());
                    break;
                }
            }
        }
    }

    private void updateCreated(double x, double y) {
        tempCanvas.getGraphicsContext2D().clearRect(0, 0, tempCanvas.getWidth(), tempCanvas.getHeight());
        current.setPointAt(pointPos, new Point2D(x, y));
        current.draw(tempCanvas.getGraphicsContext2D());
    }

    private void updateMoved(double x, double y) {
        mainCanvas.getGraphicsContext2D().clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        current.move(offset.add(x, y));
        for (AFigure figure : figures) {
            figure.draw(mainCanvas.getGraphicsContext2D());
        }
    }
}

