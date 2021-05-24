package util.math;

import java.awt.*;

public final class Geometry {

    private Geometry(){}


    public static boolean collinear(Point p1, Point p2, Point p3){
        return ((p3.y - p2.y) * (p2.x - p1.x) == (p2.y - p1.y) * (p3.x - p2.x));
    }

    public static Point intersectionTwoLines(Point p1, Point p2, Point p3, Point p4){
        // Line p2-p1 represented as a2 * x + b2 * y = c2
        double a1 = p1.y - p2.y;
        double b1 = p2.x - p1.y;
        double c1 = a1*(p2.x) + b1*(p2.y);
        // Line p4-p3 represented as a2 * x + b2 * y = c2
        double a2 = p3.y - p4.y;
        double b2 = p4.x - p3.y;
        double c2 = a2*(p4.x) + b2*(p4.y);
        double determinant = a1*b2 - a2*b1;
        if (determinant == 0)
        {
            return new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        else
        {
            double x = (b2*c1 - b1*c2)/determinant;
            double y = (a1*c2 - a2*c1)/determinant;
            return new Point((int) x, (int) y);
        }
    }

    public static boolean intersect(Point p1, Point p2, Point p3, Point p4){
        Point intersectionPoint = intersectionTwoLines(p1, p2, p3, p4);
        return intersectionPoint.x != Integer.MAX_VALUE&& intersectionPoint.y != Integer.MAX_VALUE;
    }

    public static MathVector computeVector(Point p1, Point p2) {
        return new MathVector(p2.x - p1.x, p2.y - p1.y);
    }

}
