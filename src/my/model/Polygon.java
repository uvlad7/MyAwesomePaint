package my.model;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 */
public class Polygon extends AShape {
    /**
     *
     */
    public static Integer[] OPTIONS = new Integer[]{3, 4, 5, 6, 7, 8, 9, 10};

    /**
     *
     */
    int sideNumber;
    /**
     *
     */
    Point2D[] points;
    // A Java program to check if a given point
// lies inside a given polygon
// Refer https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
// for explanation of functions onSegment(),
// orientation() and doIntersect()
// Define Infinite (Using INT_MAX
    // caused overflow problems)
    int INF = 10000;

    /**
     * Default constructor
     */
    public Polygon(Point2D reference, int sideNumber) {
        super(reference, sideNumber);
        this.sideNumber = sideNumber;
        points = new Point2D[sideNumber];
        for (int i = 0; i < sideNumber; i++) {
            points[i] = reference;
        }
    }

    /**
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        double[][] xy = new double[2][sideNumber];
        for (int i = 0; i < sideNumber; i++) {
            xy[0][i] = points[i].getX();
            xy[1][i] = points[i].getY();
        }
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
    public void move(Point2D point) {
        Point2D diff = point.subtract(reference);
        super.move(point);
        for (int i = 0; i < sideNumber; i++) {
            points[i] = points[i].add(diff);
        }
    }

    /**
     * @param point
     */
    @Override
    public boolean intersects(Point2D point) {
        double dist = Math.max(borderWidth, 10);
        for (int i = 1; i < sideNumber; i++){
            if (nearLine(points[i - 1], points[i], point, dist)) {
                return true;
            }
        }
        return nearLine(points[sideNumber - 1], points[0], point, dist) || (filled && isInside(points, point));
    }

    /**
     * @param pos
     * @param point
     */
    @Override
    public void setPointAt(int pos, Point2D point) {
        if (pos > 0 && pos < sideNumber) {
            for (int i = pos; i < sideNumber; i++) {
                points[i] = point;
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
        if (pos >= 0 && pos < sideNumber) {
            return points[sideNumber];
        }
        throw new IllegalArgumentException("Illegal position: " + pos);
    }

    /**
     * @return
     */
    @Override
    public int numberOfPoints() {
        return sideNumber;
    }

    boolean nearLine(Point2D start, Point2D end, Point2D point, double dist) {
        return Math.max(point.distance(end), point.distance(start)) <
                dist + end.distance(reference) &&
                end.distance(point) * Math.sin(Math.toRadians(end.angle(reference, point))) < dist;
    }

    // The function that returns true if
    // line segment 'p1q1' and 'p2q2' intersect.
    boolean doIntersect(Point2D p1, Point2D q1, Point2D p2, Point2D q2) {
        // Find the four orientations needed for
        // general and special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4) {
            return true;
        }

        // Special Cases
        // p1, q1 and p2 are colinear and
        // p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) {
            return true;
        }

        // p1, q1 and p2 are colinear and
        // q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) {
            return true;
        }

        // p2, q2 and p1 are colinear and
        // p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) {
            return true;
        }

        // p2, q2 and q1 are colinear and
        // q1 lies on segment p2q2
        return o4 == 0 && onSegment(p2, q1, q2);

        // Doesn't fall in any of the above cases
    }

    // Returns true if the point p lies
    // inside the polygon[] with n vertices
    boolean isInside(Point2D[] polygon, Point2D p) {
        // Create a point for line segment from p to infinite
        Point2D extreme = new Point2D(INF, p.getY());

        // Count intersections of the above line
        // with sides of polygon
        int count = 0, i = 0;
        do {
            int next = (i + 1) % sideNumber;

            // Check if the line segment from 'p' to
            // 'extreme' intersects with the line
            // segment from 'polygon[i]' to 'polygon[next]'
            if (doIntersect(polygon[i], polygon[next], p, extreme)) {
                // If the point 'p' is colinear with line
                // segment 'i-next', then check if it lies
                // on segment. If it lies, return true, otherwise false
                if (orientation(polygon[i], p, polygon[next]) == 0) {
                    return onSegment(polygon[i], p,
                            polygon[next]);
                }

                count++;
            }
            i = next;
        } while (i != 0);

        // Return true if count is odd, false otherwise
        return (count % 2 == 1); // Same as (count%2 == 1)
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are colinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    int orientation(Point2D p, Point2D q, Point2D r) {
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX())
                - (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (val == 0) {
            return 0; // colinear
        }
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    // Given three colinear points p, q, r,
    // the function checks if point q lies
    // on line segment 'pr'
    boolean onSegment(Point2D p, Point2D q, Point2D r) {
        return q.getX() <= Math.max(p.getX(), r.getX()) &&
                q.getX() >= Math.min(p.getX(), r.getX()) &&
                q.getY() <= Math.max(p.getY(), r.getY()) &&
                q.getY() >= Math.min(p.getY(), r.getY());
    }
// This code is contributed by 29AjayKumar
}