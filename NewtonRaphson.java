import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class NewtonRaphson {
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

                double root = calcRoot(oldx1);
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
    private static double matFunc(double x){
      //  return (Math.pow(x,2.0) + 6*x + 6);
        return (1.0/4.0) * (x + 4) * (x + 1) * (x - 2);
    }
    private static double numDiffFD(double x){
        double res = 0;
        double h = 0.001;
        res  = (matFunc(x+h) - matFunc(x)) / h;
        return res;
    }
    private static boolean haveOppSigns(double num1, double num2) {
        if ((num1 > 0 && num2 > 0) || (num1 < 0 && num2 < 0) || (num1 == num2)) return false;
        return true;
    }
    private static double calcRoot(double par){
        double xPrev = par;
        double xNext = 0;
        double yPrev = matFunc(par);
        double yNext = 1;
        int i = 0;
        while (i<2000){
            xNext = calcOnce(xPrev);
            //System.out.println(xNext);
            yNext = matFunc(xNext);
            xPrev = xNext;
            if (round(yNext,10) == round(yPrev,10)) {
                System.out.println("Converged");
                break;
            }
            else {
                yPrev = yNext;
            }
            i++;
        }
        System.out.println("Iterations: " + i);
        return xNext;
    }
    private static double calcOnce(double x) {
        return (x - (matFunc(x)/numDiffFD(x)));
    }
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
