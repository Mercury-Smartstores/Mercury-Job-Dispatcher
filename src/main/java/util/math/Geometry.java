package util.math;

import java.awt.*;

public final class Geometry {

    public static final Integer ORIENTATION_CLOCKWISE = 1;
    public static final Integer ORIENTATION_COUNTERCLOCKWISE = -1;

    private Geometry() {
    }


    public static boolean collinear(Point p1, Point p2, Point p3) {
        return ((p3.y - p2.y) * (p2.x - p1.x) == (p2.y - p1.y) * (p3.x - p2.x));
    }

    public static Point intersectionTwoLines(Point p1, Point p2, Point p3, Point p4) {
        // Line p2-p1 represented as a2 * x + b2 * y = c2
        double a1 = p1.y - p2.y;
        double b1 = p2.x - p1.y;
        double c1 = a1 * (p2.x) + b1 * (p2.y);
        // Line p4-p3 represented as a2 * x + b2 * y = c2
        double a2 = p3.y - p4.y;
        double b2 = p4.x - p3.y;
        double c2 = a2 * (p4.x) + b2 * (p4.y);
        double determinant = a1 * b2 - a2 * b1;
        if (determinant == 0) {
            return new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        } else {
            double x = (b2 * c1 - b1 * c2) / determinant;
            double y = (a1 * c2 - a2 * c1) / determinant;
            return new Point((int) x, (int) y);
        }
    }

    public static boolean intersectLines(Point p1, Point p2, Point p3, Point p4) {
        Point intersectionPoint = intersectionTwoLines(p1, p2, p3, p4);
        return intersectionPoint.x != Integer.MAX_VALUE && intersectionPoint.y != Integer.MAX_VALUE;
    }

    public static boolean intersectSegment(Point p1, Point q1, Point p2, Point q2) {
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);
        return (o1 != o2 && o3 != o4) || (o1 == 0 && onSegment(p1, p2, q1)) ||
                (o2 == 0 && onSegment(p1, q2, q1)) || (o3 == 0 && onSegment(p2, p1, q2))
                || (o4 == 0 && onSegment(p2, q1, q2));
    }

    public static boolean onSegment(Point p, Point q, Point r) {
        return q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
                q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y);
    }

    public static int orientation(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0) return 0;
        return (val > 0) ? ORIENTATION_CLOCKWISE : ORIENTATION_COUNTERCLOCKWISE;
    }

    public static MathVector computeVector(Point p1, Point p2) {
        return new MathVector(p2.x - p1.x, p2.y - p1.y);
    }

}
