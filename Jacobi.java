import java.util.Arrays;

public class Jacobi {
    public static void main(String[] args){
        int n = 3;
        double[][] augM =  {{6,-2,1,11}, {-2,7,2,5},{1,2,-5,-1}};         //Diagonal dominance is necessary!
        solveSys(n,augM);
    }
    public static double[] solveSys(int n, double[][] augM){     // to be used in FDM
        double[] prevX = new double[n];
        double[] nextX = new double[n];

        int iter = 0;
        while (iter < 2000){
            nextX = calcSol(augM,n,nextX).clone();
            //System.out.println(Arrays.toString(nextX));
            if (allConverged(prevX,nextX)) break;
            else {
                prevX = nextX.clone();
                iter++;
            }
        }

        System.out.println("Solution: " + Arrays.toString(nextX));
        System.out.println("Iterations: " + iter);
        return nextX;
    }

    private static double[] calcSol(double[][] augM, int n, double[] x){
        double[] curX = x.clone();
        for (int i = 0; i<n; i++){
            double sum = 0;
            for (int j = 0; j<i; j++) sum = sum + augM[i][j]*curX[j];
            for (int k=i+1; k<n; k++) sum = sum + augM[i][k]*curX[k];
            x[i] = (augM[i][n] - sum) / augM[i][i];
            curX = x.clone();                                         //Gauss-Seidel if uncommented
        }
        return x;
    }
    private static boolean allConverged (double[] prevX, double[] nextX){
        for (int i = 0; i<prevX.length; i++){
            if ((Math.abs(nextX[i]-prevX[i])/Math.abs(prevX[i])) > 0.00025) return false;  //tune precision here
        }
        return true;
    }
}
