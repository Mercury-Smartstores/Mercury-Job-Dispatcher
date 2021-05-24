package util.math;


import lombok.Getter;

import java.awt.*;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

public class BoundingQuadrilateral {

    private final static double TOLERANCE = 0.01;

    @Getter
    private final Point upperLeftPoint;
    @Getter
    private final Point upperRightPoint;
    @Getter
    private final Point lowerRightPoint;
    @Getter
    private final Point lowerLeftPoint;

    @Getter
    private final Point diagonalIntersectionPoint;

    public BoundingQuadrilateral(Point p1, Point p2, Point p3, Point p4) throws UnexpectedException {
        if (Geometry.collinear(p1, p2, p3) || Geometry.collinear(p1, p2, p4) ||
                Geometry.collinear(p1, p3, p4) || Geometry.collinear(p2, p3, p4) ||
                !(Geometry.intersectSegment(p1, p2, p3, p4) ||
                        Geometry.intersectSegment(p1, p3, p2, p4) || Geometry.intersectSegment(p1, p2, p4, p3))) {
            throw new IllegalArgumentException("The points received do not define any convex quadrilateral");
        }

        ArrayList<Integer> coordinatesY = new ArrayList<>(Arrays.asList(p1.y, p2.y, p3.y, p4.y));
        ArrayList<Integer> coordinatesX = new ArrayList<>(Arrays.asList(p1.x, p2.x, p3.x, p4.x));
        Point[] points = {p1, p2, p3, p4};

        Collections.sort(coordinatesY);
        Collections.sort(coordinatesX);

        Function<Point, Boolean> isUpper = p -> (p.y == coordinatesY.get(3) || p.y == coordinatesY.get(2));
        Function<Point, Boolean> isRight = p -> (p.x == coordinatesX.get(2) || p.x == coordinatesX.get(3));
        Function<Point, Boolean> isLower = p -> (p.y == coordinatesY.get(0) || p.y == coordinatesY.get(1));
        Function<Point, Boolean> isLeft = p -> (p.x == coordinatesX.get(0) || p.x == coordinatesX.get(1));

        Optional<Point> optionalUpperRight = Arrays.stream(points)
                .filter(p -> isUpper.apply(p) && isRight.apply(p))
                .findFirst();
        Optional<Point> optionalUpperLeft = Arrays.stream(points)
                .filter(p -> isUpper.apply(p) && isLeft.apply(p))
                .findFirst();
        Optional<Point> optionalLowerRight = Arrays.stream(points)
                .filter(p -> isLower.apply(p) && isRight.apply(p))
                .findFirst();
        Optional<Point> optionalLowerLeft = Arrays.stream(points)
                .filter(p -> isLower.apply(p) && isLeft.apply(p))
                .findFirst();

        if (optionalUpperRight.isPresent() && optionalUpperLeft.isPresent() &&
                optionalLowerRight.isPresent() && optionalLowerLeft.isPresent()) {
            this.upperRightPoint = optionalUpperRight.get();
            this.upperLeftPoint = optionalUpperLeft.get();
            this.lowerLeftPoint = optionalLowerLeft.get();
            this.lowerRightPoint = optionalLowerRight.get();
        } else {
            throw new UnexpectedException("One of the points for the quadrilateral cannot be located");
        }

        this.diagonalIntersectionPoint = Geometry.intersectionTwoLines(this.upperRightPoint, this.lowerLeftPoint,
                this.upperLeftPoint, this.lowerRightPoint);
    }


    public boolean pointInside(Point p) {
        MathVector v1 = Geometry.computeVector(p, upperLeftPoint);
        MathVector v2 = Geometry.computeVector(p, upperRightPoint);
        MathVector v3 = Geometry.computeVector(p, lowerRightPoint);
        MathVector v4 = Geometry.computeVector(p, lowerLeftPoint);
        double totalAngle = v1.angle(v2) + v2.angle(v3) + v3.angle(v4) + v4.angle(v1);
        double expectedAngle = Math.PI * 2;
        return totalAngle >= expectedAngle - TOLERANCE && totalAngle <= expectedAngle + TOLERANCE;
    }

    public double distance(Point p) {
        return Geometry.distance(p, this.diagonalIntersectionPoint);
    }

    public String toString() {
        return String.format("Quadrilateral ( UpperRight: %s, UpperLeft: %s, LowerRight: %s, LowerLeft: %s)",
                this.upperRightPoint, this.upperLeftPoint, this.lowerRightPoint, this.lowerLeftPoint);
    }

}
