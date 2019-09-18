import java.lang.Math;
import java.util.*;


public class Bisection {

    public static void main(String args[]) {

        int rootsNum = 6;
        double step = 0.1;
        double xr1 = 1.0; // guess a reference point

        double xl1 = xr1;
        double xr2 = xr1 + step;
        double xl2 = 0;
        double y1Temp = matFunc(xr1);
        double y2Temp = matFunc(xr2);
        char i = 'l';
        double oldx1 = xr1;
        double oldx2 = xr2;

        ArrayList<Double> roots = new ArrayList<>();

        while (rootsNum > 0){
            if(haveOppSigns(y1Temp,y2Temp)){
                System.out.println("Root found");

                if (oldx1 > oldx2){
                    double tempX = oldx1;
                    oldx1 = oldx2;
                    oldx2 = tempX;
                }

                double root = calcRoot(oldx1,oldx2,0);
                if (!roots.contains(root)){
                    roots.add(root);
                    rootsNum -= 1;
                }
            }
            if (i == 'l'){
                y1Temp = matFunc(xl1);
                xl2 = xl1 - step;
                y2Temp = matFunc(xl2);
                oldx1 = xl1;
                oldx2 = xl2;
                xl1 = xl2;
                i = 'r';
            }
            else if (i == 'r'){
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
        System.out.println(roots);



    }
    private static double matFunc(double x){
       // return 10*Math.pow(x,2.0) - 20*x - 30;
       // return (1.0/4.0) * (x + 4) * (x + 1) * (x - 2);
       // return (x - 3)*(x - 2)*(x - 1)*(x)*(x + 1) * (x + 2) * (x + 3);

        return (1.0/100.0) * (x*x*x*x*x*x - 2*x*x*x*x*x - 26*x*x*x*x + 28*x*x*x + 145*x*x - 26*x - 80);

    }
    private static boolean haveOppSigns(double num1, double num2) {
        if ((num1 > 0 && num2 > 0) || (num1 < 0 && num2 < 0) || (num1 == num2)) return false;
        return true;
    }
    private static double calcRoot(double x1, double x2, int iter) {
        //System.out.println("CALLED");
        iter++;
        if (matFunc(x1)==0) return x1;
        if (matFunc(x2)==0) return x2;

        double c = (x1 + x2) / 2;
        if (matFunc(c)==0) return c;

        double a = x1;
        double b = x2;

        if (iter < 5000) {
            if (haveOppSigns(matFunc(a), matFunc(c))) calcRoot(a, c, iter);
            else if (haveOppSigns(matFunc(c), matFunc(b))) calcRoot(c, b, iter);
            return c;
        }
        else return c;
    }

}