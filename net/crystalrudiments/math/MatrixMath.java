

package net.crystalrudiments.math;
import net.crystalrudiments.io.InOutHelp;
import net.crystalrudiments.lang.AdamObject;

public class MatrixMath extends AdamObject {

    /** bDEBUG is true if debugging. */
    private static final boolean bDEBUG = true;

    InOutHelp ioh;

    /**
     * Init Method. First thing called when app is run.
     */
    public void init() {
        ioh = new InOutHelp();
    }

    public MatrixMath() {
        init();
    }

    public static double[] getCol(int j, double[][] m) {
        double[] ret = new double[m.length];
        for (int i = 0; i < m.length; i++)
            ret[i] = m[i][j];
        return ret;
    }

    public static double[] getRow(int i, double[][] m) {
        double[] ret = new double[m[i].length];
        for (int j = 0; j < m[i].length; j++)
            ret[j] = m[i][j];
        return ret;
    }

    public static void print(double[] v) {
        print("{");
        for (int i = 0; i < v.length; i++)
            print(v[i] + ",\t");
        print("}");
    }

    public static void print(double[][] v) {
        print("{\n");
        for (int i = 0; i < v.length; i++) {
            print(v[i]);
            print("\n");
        }
        print("}\n");
    }

    /**
     * Prints the array to float precision.
     */
    public static void printf(double[] v) {
        print("{");
        for (int i = 0; i < v.length; i++)
            print((float) v[i] + ",\t");
        print("}");
    }

    public static void printf(double[][] v) {
        print("{\n");
        for (int i = 0; i < v.length; i++) {
            printf(v[i]);
            print("\n");
        }
        print("}\n");
    }

    public static double[][] expanduut(double[] u) {
        double[][] a = new double[u.length][u.length];
        for (int i = 0; i < u.length; i++)
            for (int j = 0; j < u.length; j++)
                a[i][j] = u[i] * u[j];
        return a;
    }

    /*
     * Calculates the norm of the vector.
     */
    public static double norm(double[] a) {
        return (sqrt(sqAllElements(a)));
    }

    /*
     * Returns the sum of all values of the vector squared.
     */
    public static double sqAllElements(double[] a) {
        double d = 0d;
        for (int i = 0; i < a.length; i++)
            d += sq(a[i]);
        return d;
    }

    public static double sq(double d) {
        return d * d;
    }

    public static double sqrt(double d) {
        return Math.sqrt(d);
    }

    /*
     * Returns "a" after multiplied by a scalar "c". Affects the origianl
     * matrix.
     */
    public static double[][] scalarmult(double c, double[][] a) {
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[i].length; j++)
                a[i][j] = c * a[i][j];
        return a;
    }

    public static double[][] scalardiv(double c, double[][] a) {
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[i].length; j++)
                a[i][j] = a[i][j] / c;
        return a;
    }

    /*
     * Returns the identity matrix of size n*n.
     */
    public static double[][] identity(int n) {
        double[][] a = new double[n][n];
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[i].length; j++) {
                if (i == j)
                    a[i][j] = 1;
                else
                    a[i][j] = 0;
            }
        return a;
    }

    /*
     * Returns the result of mutliplying the two matrices together. The original
     * matrices are never scratched.
     */
    public static double[][] matrixmult(double[][] a, double[][] b) {
        if (a[0].length != b.length) {
            error("Improper dimensions.");
            return null;
        }
        double[][] ret = new double[a.length][b[0].length];
        for (int i = 0; i < ret.length; i++)
            for (int j = 0; j < ret[0].length; j++) {
                ret[i][j] = 0;
                for (int x = 0; x < a[0].length; x++)
                    ret[i][j] += a[i][x] * b[x][j];
            }
        return ret;
    }

    public static double[][] matrixsub(double[][] a, double[][] b) {
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[i].length; j++)
                a[i][j] = a[i][j] - b[i][j];
        return a;
    }

    public static double[][] threeMatrixMult(double[][] a, double[][] b, double[][] c) {
        return matrixmult(a, matrixmult(b, c));
    }

    /*
     * Calculates the QR-factorization of "a" using the house-holder reflections
     * method.
     */
    public static void householder(double[][] a, double[][] Q, double[][] R) {
        int n;
        double[][] A = copymatrix(a);
        double[][] tmpQ = new double[a.length][a.length];
        double[] u;
        double[][][] H = new double[a[0].length][][];

        H[0] = copymatrix(a);

        for (n = 1; n < (a[0].length); n++) {
            u = littleasubn(n, H[n - 1]);
            u[0] += norm(u);
            H[n] = expanduut(u);
            H[n] = scalarmult(2d, H[n]);
            H[n] = scalardiv(sqAllElements(u), H[n]); //divide by norm(~u)^2
            H[n] = matrixsub(identity(H[n].length), H[n]); //I - (H[n])...

            H[n] = householder(H[n], a.length); //fix-up H[n].
            A = matrixmult(H[n], A); //multiply A by H on left-side.

            if (bDEBUG) {
                print("householder:n=" + n + "\n");
                print("H[" + n + "]=");
                printf(H[n]);
            }
        }

        tmpQ = copymatrix(H[a[0].length - 1]);
        for (n = a[0].length - 2; n >= 0; n--) {
            tmpQ = matrixmult(H[n], tmpQ);
        }
        /* Assign Q and R cell-wise */
        for (int i = 0; i < Q.length; i++)
            for (int j = 0; j < Q[i].length; j++) {
                Q[i][j] = tmpQ[i][j];
            }
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[i].length; j++) {
                R[i][j] = A[i][j];
            }
    }

    /*
     * Returns little a sub n.
     */
    public static double[] littleasubn(int n, double[][] a) {
        double[] ret = new double[a.length - n];
        for (int i = n; i < a.length; i++) {
            ret[i - n] = a[i][n];
        }
        return ret;
    }

    /*
     * This makes the actual householder H(sub n) by increasing its size by 1 in
     * both dimensions like so, <PRE> {1,0,-,0} {0,*,-,*} {|,|,\,*} {0,*,-,*}
     * </PRE>
     */
    public static double[][] householder(double[][] a, int size) {
        double[][] ret = new double[size][size];
        int offset = (size - a.length);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i < offset || j < offset) {
                    if (i == j)
                        ret[i][j] = 1; //{1,0,-,0}
                    else { //{0,*,-,*}
                        ret[i][j] = 0; //{|,|,\,|}
                    } //{0,*,-,*}
                } else
                    ret[i][j] = a[i - offset][j - offset];
            }
        }
        return ret;
    }

    /*
     * Returns a copy of the matrix.
     */
    public static double[][] copymatrix(double[][] a) {
        double[][] b = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[i].length; j++) {
                b[i][j] = a[i][j];
            }
        return b;
    }

    public static void main(String[] args) {
        //Hw4 h = new Hw4();
        print("Input Matrix>");
        double[][] A = { { 1, 2, -1, 0}, { 3, 1, 2, 1}, { 1, -2, 3, -1},
                { 0, 2, -2, 0}};
        double[][] Q, R;
        R = new double[A.length][A.length];
        Q = new double[A.length][A.length];

        householder(A, Q, R);

        print("A = ");
        printf(A);
        print("Q = ");
        if (Q == null)
            error("Q==Null");
        else
            printf(Q);
        print("R = ");
        if (R == null)
            error("R==Null");
        else
            printf(R);
    }
}