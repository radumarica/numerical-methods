import java.util.Arrays;

public class LUfact {

    //I am sorry for all these global variables, the code is written for an algorithmic purpose, OOP principles ignored

    private static  int n = 4; //input size
    private static double[][] A = {{1,-3,2,1}, {2,-6,1,4}, {-1,2,3,4}, {-1,1,4,5}};
    private static double[] b = {-4,1,12,12};
    private static double[][] L = new double[n][n];
    private static double[][] U = new double[n][n];

    public static void main(String[] args){
        initKnown();
        printBoth();
        LUcoeff();
        printBoth();
       // calcSol();

    }
    private static void initKnown(){
        for (int i = 0; i<n; i++){
            U[i][i] = 1;
        }
        for (int i = 0; i<n; i++){
            L[i][0] = A[i][0];
        }
    }
    private static void LUcoeff(){
        for (int i = 0; i<n; i++){
            for (int j = 1; j<n; j++){
                double sum = 0;
                for (int k = 0; k<n; k++){
                    sum = sum + (L[i][k]*U[k][j]);
                    // the variable is not omitted in the equation because it is initialized to 0 (before its actual
                    // value is found), so adding a 0 (multiplied by something) will not affect the result
                }
                if (i<j) {
                    if(L[i][i] != 0)  U[i][j] = (A[i][j] - sum) / L[i][i];
                   // else U[i][j] = 1;
                }
                else {
                    L[i][j] = A[i][j] - sum;
                }
            }
        }
    }

    private static double[] calcSol(){
        double[] y = new double[n];
        for (int i = 0; i<n; i++){        // forward substitution to find vector y
            double sum = 0;
            for (int j = 0; j<i; j++){
                sum = sum + L[i][j] * y[j];
            }
            y[i] = (b[i] - sum)/L[i][i];
        }

        System.out.println(Arrays.toString(y));

        double[] x = new double[n];
        for (int i = n-1; i>=0; i--){     // backward substitution to find vector x
            double sum = 0;
            for (int j = i+1; j<n; j++){
                sum = sum + U[i][j] * x[j];
            }
            x[i] = (y[i] - sum)/U[i][i];
        }
        System.out.println(Arrays.toString(x));
        return x;
    }

    private static void printBoth(){
        for (int i = 0; i<n; i++){
            for (int j = 0; j<n; j++){
                System.out.print(L[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i<n; i++){
            for (int j = 0; j<n; j++){
                System.out.print(U[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
