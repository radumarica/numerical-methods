
import java.util.Arrays;

//LOSS OF PRECISION WITH FEW POINTS. CHECK IT OUT!  TRY USING ITERATIVE SOLVER INSTEAD OF GE

public class Spline {
    public static void main(String[] args){
        double[] xs = {3, 4, 5, 8, 9, 10, 12};                     //remember ascending order!
        double[] ys = {6, 13, 22, 61, 78, 97, 141};
        double x = 7.0;
        double[] hs = calcDiff(xs).clone();
        double[] ms = calcMs(ys, hs).clone();
        System.out.println(ms.length);
        int loc = locateX(x, xs);
        double[] as = calcCoeff(ys[loc], ys[loc+1], ms[loc], ms[loc+1], hs[loc]).clone();
        double y = calcOnePoly(xs[loc],as,x);
        System.out.println("Answer: "+y);

    }
    private static double[] calcCoeff(double yCur, double yNext, double mCur, double mNext, double h){
        double[] res = new double[4];
        res[0] = yCur;
        res[1] = ((yNext-yCur)/h) - ((h*mCur)/2.0) - ((h/6.0)*(mNext - mCur));
        res[2] = mCur/2.0;
        res[3] = (mNext - mCur)/(6.0*h);
        return res;
    }
    private static double calcOnePoly(double xj, double[] aj, double x){
        double qj;
        qj = aj[0] + aj[1]*(x-xj) + aj[2]*Math.pow((x-xj),2.0) + aj[3]*Math.pow((x-xj),3.0);
        return qj;
    }
    private static int locateX(double x, double[] xs){
        int after = 0;
        for (int i = 0; i<xs.length; i++){
            if (x > xs[i]) after = i;
            else break;
        }
        return after;
    }
    private static double[] calcDiff(double[] xs){
        double[] res = new double[xs.length-1];
        for (int i = 0; i<xs.length-1; i++){
            res[i] = xs[i+1] - xs[i];
        }
        return res;
    }
    private static double[] calcMs (double[] ys, double[] hs){          //Natural Spline
        int n = ys.length;
        int m = n+1;
        double[][] equations = new double[n][m];
        equations[0][0] = 1;
        equations[n-1][m-2] = 1;
        for (int i = 1; i<n-1; i++){
            equations[i][i-1] = hs[i-1];
            equations[i][i] = 2*(hs[i-1]+hs[i]);
            equations[i][i+1] = hs[i];
            equations[i][n] = 6.0*(((ys[i+1]-ys[i])/hs[i]) - ((ys[i]-ys[i-1])/hs[i-1]));
        }
        System.out.println(Arrays.deepToString(equations));
        return GaussElim.gaussianElimination(GeneralMethods.rowToObj(equations));
    }
}
