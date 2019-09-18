import java.lang.Math;
import java.util.*;


public class RegulaFalsi {

    public static void main(String args[]) {

        int rootsNum = 3;
        double step = 0.5;
        double xr1 = 1.4; // guess a reference point

        double xl1 = xr1;
        double xr2 = xr1 + step;
        double xl2 = 0;
        double y1Temp = matFunc(xr1);
        double y2Temp = matFunc(xr2);
        char i = 'l';
        double oldx1 = xr1;
        double oldx2 = xr2;

        ArrayList<Double> roots = new ArrayList<>();

        while (rootsNum > 0) {
            if (haveOppSigns(y1Temp, y2Temp)) {
                System.out.println("Root located");

                if (oldx1 > oldx2) {
                    double tempX = oldx1;
                    oldx1 = oldx2;
                    oldx2 = tempX;
                }

                double root = calcRoot(oldx1, oldx2);
                if (!roots.contains(root)) {
                    roots.add(root);
                    rootsNum -= 1;
                    if (rootsNum == 0) break;
                }
            }
            if (i == 'l') {
                y1Temp = matFunc(xl1);
                xl2 = xl1 - step;
                y2Temp = matFunc(xl2);
                oldx1 = xl1;
                oldx2 = xl2;
                xl1 = xl2;
                i = 'r';
            } else if (i == 'r') {
                y1Temp = matFunc(xr1);
                xr2 = xr1 + step;
                y2Temp = matFunc(xr2);
                oldx1 = xr1;
                oldx2 = xr2;
                xr1 = xr2;
                i = 'l';
            }
            System.out.println(oldx1 + " " + oldx2);
        }
       // double root = calcRoot(1.0, 2.0);
       // roots.add(root);
        System.out.println(roots);


    }

    private static double matFunc(double x) {
       //  return 10*Math.pow(x,2.0) - 20*x - 30;
         return (1.0/4.0) * (x + 4) * (x + 1) * (x - 2);
       // return (x - 3)*(x - 2)*(x - 1)*(x)*(x + 1) * (x + 2) * (x + 3);

        //return x*x*x + 3*x - 5;
       // return (1.0/100.0) * (x*x*x*x*x*x - 2*x*x*x*x*x - 26*x*x*x*x + 28*x*x*x + 145*x*x - 26*x - 80);

    }

    private static boolean haveOppSigns(double num1, double num2) {
        if ((num1 > 0 && num2 > 0) || (num1 < 0 && num2 < 0) || (num1 == num2)) return false;
        return true;
    }


    private static double calcRoot (double par1, double par2){
        double[] res = new double[3];
        double x1 = par1;
        double x2 = par2;
        int iter = 0;
        for (int i = 0; i<2000; i++) {
            iter = i;
            res = calcOnce(x1,x2);
            if (res[2]==1) {
                System.out.println("Iterations for this root: " + iter);
                System.out.println();
                return res[0];
            }
            else if ((x1 == res[0]) && (x2 == res[1])){
                System.out.println("Converged");
                break;
            }
            else {
                x1 = res[0];
                x2 = res[1];
            }
        }
        System.out.println("Iterations for this root: " + iter);
        System.out.println();
        return res[1];
    }

    private static double[] calcOnce(double x1, double x2) {
        System.out.println(x1 + " " + x2);
        System.out.println(matFunc(x1) + " " + matFunc(x2));

        double res[] = new double[3];
        double c = 0.0;
        if (matFunc(x1) == 0.0) {
           // System.out.println("WE ARE HERE 1");
            res[0] = x1;
            res[1] = x1;
            res[2] = 1;
        }
        else if (matFunc(x2) == 0.0) {
           // System.out.println("WE ARE HERE 2");
            res[0] = x2;
            res[1] = x2;
            res[2] = 1;
        }

        else {

            double a = x1;
            double b = x2;

            double[] coeff = findFunc(x1, x2, matFunc(x1), matFunc(x2));
            c = (-1) * coeff[1] / coeff[0]; // interpolation line crosses the X axis

            if (haveOppSigns(matFunc(a), matFunc(c))) {
                res[0] = a;
                res[1] = c;
            }
            else if (haveOppSigns(matFunc(c), matFunc(b))) {
                res[0] = b;
                res[1] = c;
            }
          //  System.out.println("WE ARE HERE 3");
           // System.out.println(c);
        }

        return res;
    }
    private static double[] findFunc(double x1, double x2, double y1, double y2){
        double[] coeff = new double[2];
        coeff[0] = (y1-y2) / (x1-x2);
        coeff[1] = y2 - coeff[0]*x2;
        return coeff;
    }
}
