import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;


public class Secant {
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

                double root = calcRoot(oldx1,oldx2);
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
        return x*x*Math.exp((-1*x)/2)-1;
    }
    private static double secDif(double xNext, double xPrev){
        double res = 0;
        res  = ((matFunc(xNext)-matFunc(xPrev))/(xNext - xPrev));
        return res;
    }
    private static boolean haveOppSigns(double num1, double num2) {
        if ((num1 > 0 && num2 > 0) || (num1 < 0 && num2 < 0) || (num1 == num2)) return false;
        return true;
    }
    private static double calcRoot(double par1, double par2){
        double xPrev = par1;
        double xNext = par2;
        double temp = 0;
        double yPrev = matFunc(par1);
        double yNext = matFunc(par2);
        int i = 0;
        while (i<2000){
            temp = xNext;
            System.out.println("x: " + xNext);
            xNext = calcOnce(xNext, xPrev);
            System.out.println("x+1: " + xNext);
            System.out.println("x-1: " + xPrev);
            System.out.println();
            yNext = matFunc(xNext);
            xPrev = temp;

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
        System.out.println();
        return xNext;
    }
    private static double calcOnce(double xNext, double xPrev) {
        return (xNext - (matFunc(xNext)/secDif(xNext, xPrev)));
    }
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
