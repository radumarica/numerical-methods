import java.lang.Math;
import java.util.ArrayList;


public class FixedPoint {

    private static ArrayList<Double> data = new ArrayList<>();

    public static void main(String args[]) {
        calc(2.0);
        System.out.println(data);
        System.out.println(numDiffFD(2,2.618033988749895));
        System.out.println(numDiffCD(2,2.618033988749895));
        System.out.println(1/(2.618033988749895*2.618033988749895));
    }
    private static void calc(double x){
        System.out.println(g2(x));
        double xNext = g2(x);
        data.add(xNext);
        if (xNext != x) calc(xNext);
    }

    private static double matFunc(double x){
        return (Math.pow(x,2.0) - 3*x + 1);
    }

    private static double g1(double x){
        return (0.333 * (Math.pow(x, 2.0) + 1));
    }

    private static double g2(double x){
        return (3 - (1/x));
    }

    private static double numDiffFD(int choice, double x){
        double res = 0;
        double h = 0.001;
        if (choice == 1) res  = (g1(x+h) - g1(x)) / h;
        if (choice == 2) res  = (g2(x+h) - g2(x)) / h;
        return res;
    }
    private static double numDiffCD(int choice, double x){
        double res = 0;
        double h = 0.001;
        if (choice == 1) res  = (g1(x+h) - g1(x-h)) / (2*h);
        if (choice == 2) res  = (g2(x+h) - g2(x-h)) / (2*h);
        return res;
    }
}