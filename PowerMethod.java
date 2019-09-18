import java.util.Arrays;
public class PowerMethod {
    public static void main(String[] args){
        double[][] A = {{2,0,1,-3}, {0,2,10,4},{0,0,2,0}, {0,0,0,3}};
       // double[][] A = {{1,2,0}, {-2,1,2},{1,3,1}};
        double[] u = new double[A.length];
        u = fillU(u);
        Tuple<Double,double[]> res  =  calcEigen(A,u,u.length);
        System.out.println(res.get1());
        System.out.println(Arrays.toString(res.get2()));
    }
    private static double[] fillU (double[] u){
        for (int i=0; i<u.length; i++){
            u[i] = 1;
        }
        return u;
    }
    private static Tuple<Double,double[]> calcOnce(double[][] A, double[] u, int n){
        double [] res = new double[n];
        res = multMbyV(A,u,n);
        System.out.println("Multiplied: " + Arrays.toString(res));
        double nfactor = res[findMax(res,res.length)];
        for (int i = 0; i<n; i++){
            res[i] = res[i]/nfactor;
        }
        return new Tuple<>(new Double(nfactor),res);
    }

    // finding maximum eigenvalue and a corresponding eigenvector
    private static Tuple<Double,double[]> calcEigen(double[][] A, double[] u, int n){
        double prevVal = 0;
        double nextVal = 0;
        int iter = 0;
        while (iter < 2000){
            Tuple<Double,double[]> temp = calcOnce(A,u,n);
            nextVal = temp.get1();
            System.out.println("Factor: " + nextVal);
            System.out.println("Vector: " + Arrays.toString(temp.get2()));
            System.out.println();
            if (converged(prevVal,nextVal) && allConverged(u,temp.get2())) {
                u = temp.get2().clone();
                break;
            }
            else{
                u = temp.get2().clone();
                prevVal = nextVal;
                iter++;
            }
        }
        System.out.println("Iterations: " + iter);
        return new Tuple(new Double(nextVal),u);
    }
    private static double[] multMbyV(double[][]A, double[] u, int n){
        double res[] = new double[n];
        for (int i=0; i<n; i++) {
           double sum = 0;
            for (int j=0; j<n; j++){
                sum += (A[i][j]*u[j]);
            }
            res[i] = sum;
        }
        return res;
    }
    private static int findMax(double[] u, int n){
        int max = 0;
        for(int i=1; i<n; i++){
            if (Math.abs(u[i])>=Math.abs(u[max])) max = i;
        }
        return max;
    }
    private static boolean converged ( double prevX, double nextX){
        if ((Math.abs(nextX-prevX)/Math.abs(prevX)) > 0.00025) return false;  //tune precision here
        return true;
    }
    private static boolean allConverged (double[] prevX, double[] nextX){
        for (int i = 0; i<prevX.length; i++){
            if ((Math.abs(nextX[i]-prevX[i])/Math.abs(prevX[i])) > 0.5) return false;  //tune precision here
        }
        return true;
    }
}
