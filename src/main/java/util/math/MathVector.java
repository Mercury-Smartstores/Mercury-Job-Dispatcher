package util.math;

import java.util.Arrays;

public class MathVector {

    private final double[] v;

    public MathVector(double... v) {
        this.v = new double[v.length];
        System.arraycopy(v, 0, this.v, 0, v.length);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.v);
    }

    public int length() {
        return this.v.length;
    }

    public double get(int position) {
        return this.v[position];
    }

    public MathVector add(MathVector u) {
        return MathVector.sum(this, u);
    }

    public static boolean compareLength(MathVector u, MathVector v) {
        return (u.length() == v.length());
    }

    public static void checkLengths(MathVector u, MathVector v) {
        if (!compareLength(u, v)) {
            throw new IllegalArgumentException("Vectors are different lengths");
        }
    }

    public static MathVector sum(MathVector u, MathVector v) {
        checkLengths(u, v);
        double[] sums = new double[u.length()];
        for (int i = 0; i < sums.length; i++) {
            sums[i] = u.get(i) + v.get(i);
        }
        return new MathVector(sums);
    }

    public MathVector multiply(double scalar) {
        return MathVector.product(this, scalar);
    }

    public static MathVector product(MathVector u, double scalar) {
        double[] products = new double[u.length()];
        for (int i = 0; i < products.length; i++) {
            products[i] = scalar * u.get(i);
        }
        return new MathVector(products);
    }

    public double dot(MathVector u) {
        return MathVector.dotProduct(this, u);
    }

    public static double dotProduct(MathVector u, MathVector v) {
        checkLengths(u, v);
        double sum = 0;
        for (int i = 0; i < u.length(); i++) {
            sum += (u.get(i) * v.get(i));
        }
        return sum;
    }

    public MathVector cross(MathVector u) {
        return MathVector.crossProduct(this, u);
    }


    public static MathVector crossProduct(MathVector u, MathVector v) {
        if (u.length() != 3) {
            throw new IllegalArgumentException("Invalid vector length (first vector)");
        } else if (u.length() != 3) {
            throw new IllegalArgumentException("Invalid vector length (second vector)");
        }
        double[] entries = new double[]{
                u.v[1] * v.v[2] - u.v[2] * v.v[1],
                u.v[2] * v.v[0] - u.v[0] * v.v[2],
                u.v[0] * v.v[1] - u.v[1] * v.v[0]};
        return new MathVector(entries);
    }

    public double magnitude() {
        return MathVector.pnorm(this, 2);
    }

    public double pnorm(double p) {
        return MathVector.pnorm(this, p);
    }

    public static double pnorm(MathVector u, double p) {
        if (p < 1) {
            throw new IllegalArgumentException("p must be >= 1");
        }
        double sum = 0;
        for (int i = 0; i < u.length(); i++) {
            sum += Math.pow(Math.abs(u.get(i)), p);
        }
        return Math.pow(sum, 1 / p);
    }

    public double angle(MathVector u) {
        return MathVector.angle(this, u);
    }

    public static double angle(MathVector u, MathVector v) {
        MathVector.checkLengths(u, v);
        return Math.acos(MathVector.dotProduct(u, v) /
                (u.magnitude() * v.magnitude()));
    }
}
