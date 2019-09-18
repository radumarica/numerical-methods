import java.util.Arrays;

public class NonLinSysFP {

    public static void main(String args[]){
        double[] xs = {-0.9,0.9};
        double[] solution = solveNonLinSys(xs, reformulations);                         //reference copy
        double norm = GeneralMethods.rowSumNorm(calcJacobian(solution,derivativesG));   //checking convergence
        if(norm < 1) System.out.println("Infinity norm of Jacobian of G at the solution is " + norm + ", which is " +
                "smaller than 1, so the results converge");
        else System.out.println("Infinity norm of Jacobian of G at the solution is " + norm + ", which is " +
                "greater than 1, so the results do not converge");
    }

    interface Function {
        double func(double[] xs);
    }
    private static Function[] functions = new Function[] {          //not used in the code, are just shown for the user
            new Function() { public double func(double[] xs) { double x1 = xs[0]; double x2 = xs[1]; return (2*x1 + x2 - 1); } },
            new Function() { public double func(double[] xs) { double x1 = xs[0]; double x2 = xs[1]; return (x1*x1 + x2*x2 - 1); } },
    };
    private static Function[] reformulations = new Function[] {
            new Function() { public double func(double[] xs) { double x1 = xs[0]; double x2 = xs[1]; return ((1-x2)/2.0); } },
            new Function() { public double func(double[] xs) { double x1 = xs[0]; double x2 = xs[1]; return (Math.sqrt(1-x1*x1)); } },
    };
    private static Function[][] derivativesG = new Function[][] {             //partial derivatives of reformulations
            {  new Function() { public double func(double[] xs) {  double x1 = xs[0]; double x2 = xs[1]; return 0; } },
                    new Function() { public double func(double[] xs) {  double x1 = xs[0]; double x2 = xs[1]; return -1.0/2.0; } }  }, // ROW 1
            {  new Function() { public double func(double[] xs) {  double x1 = xs[0]; double x2 = xs[1]; return -1.0*x1/Math.sqrt(1-x1*x1); } },
                    new Function() { public double func(double[] xs) {  double x1 = xs[0]; double x2 = xs[1]; return 0; } }  } //ROW 2
    };

    private static double[] solveNonLinSys(double[] xs, Function[] reformulations){
        double[] prevX = xs.clone();
        double[] nextX = new double[xs.length];
        int iter = 0;
        while (iter<2000){
            nextX = calcOnce(prevX, reformulations).clone();
            if (GeneralMethods.allConverged(prevX,nextX,0.005)) {
                System.out.println("The solution converged to: " + Arrays.toString(nextX));
                System.out.println("Iterations: " + iter);
                return nextX;
            }
            else {
                System.out.println(Arrays.toString(nextX));
                prevX = nextX.clone();
                iter++;
            }
        }
        return nextX;
    }

    private static double[] calcOnce(double[] xs, Function[] reformulations){
        int n = xs.length;
        double[] locxs = xs.clone();              //local copy of xs to change leave the argument unchanged
        double[] res = new double[n];
        for (int i = 0; i<n; i++){
            res[i] = reformulations[i].func(locxs);
            //locxs[i] = res[i];                  //Gauss-Seidel principle if uncommented
        }
        return res;
    }

    private static double[][] calcJacobian (double[] xs, Function[][] derivatives){   //Make it a general method!
        int n = xs.length;
        double[][] jacobian = new double[n][n];
        for (int i = 0; i<n; i++){
            for (int j = 0; j<n; j++){
                jacobian[i][j] = derivatives[i][j].func(xs);
            }
        }
        return jacobian;
    }
}
