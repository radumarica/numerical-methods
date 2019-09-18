
//FIND OUT ABOUT VERY SLOW CONVERGENCE FOR PI EXAMPLE

import java.util.Arrays;
public class NonLinSysNR {
    public static void main(String[] args){
        System.out.println();
        double[] xs = {1,1};
        solveNonLinSys(xs,functions,derivatives);
    }

    interface Function {
       double func(double[] xs);
    }

//    private static Function[] functions = new Function[] {
//            new Function() { public double func(double[] xs) { double x1 = xs[0]; double x2 = xs[1]; return (x1*x1 + x2*x2 - 1); } },
//            new Function() { public double func(double[] xs) { double x1 = xs[0]; double x2 = xs[1]; return (Math.sin(Math.PI*x1/2)+(x2*x2*x2)); } },
//    };
//    private static Function[][] derivatives = new Function[][] {          // possibly replace by a 1D array to avoid confusion
//            {  new Function() { public double func(double[] xs) {  double x1 = xs[0]; double x2 = xs[1]; return 2*x1; } },
//                    new Function() { public double func(double[] xs) {  double x1 = xs[0]; double x2 = xs[1]; return 2*x2; } }  }, // ROW 1
//            {  new Function() { public double func(double[] xs) {  double x1 = xs[0]; double x2 = xs[1]; return (Math.PI/2)*Math.cos(Math.PI/2)*x1; } },
//                    new Function() { public double func(double[] xs) {  double x1 = xs[0]; double x2 = xs[1]; return 3*x2*x2; } }  } //ROW 2
//    };


    private static Function[] functions = new Function[] {
            new Function() { public double func(double[] xs) { double x1 = xs[0]; double x2 = xs[1]; return (x1*x1 + 3*x1 - x2 + 2); } },
            new Function() { public double func(double[] xs) { double x1 = xs[0]; double x2 = xs[1]; return (2*x1 - x2 + 3); } },
    };
    private static Function[][] derivatives = new Function[][] {          // possibly replace by a 1D array to avoid confusion
            {  new Function() { public double func(double[] xs) {  double x1 = xs[0]; double x2 = xs[1]; return 2*x1 + 3; } },
                    new Function() { public double func(double[] xs) {  double x1 = xs[0]; double x2 = xs[1]; return -1; } }  }, // ROW 1
            {  new Function() { public double func(double[] xs) {  double x1 = xs[0]; double x2 = xs[1]; return 2; } },
                    new Function() { public double func(double[] xs) {  double x1 = xs[0]; double x2 = xs[1]; return -1; } }  } //ROW 2
    };


    private static double[] solveNonLinSys (double[] xs, Function[] functions, Function[][] derivatives){
        double[] prevX = xs.clone();
        double[] nextX = new double[functions.length];
        int iter = 0;
        while (iter < 2000){
            nextX = calcOnce(prevX,functions,derivatives).clone();
            System.out.println(Arrays.toString(nextX));
            if (GeneralMethods.allConverged(prevX,nextX,0.0005)) {
                System.out.println("The solution converged to: " + Arrays.toString(nextX));
                System.out.println("Iterations: " + iter);
                return nextX;
            }
            else {
                prevX = nextX.clone();
                iter++;
            }
        }
        return nextX;
    }

    private static double[] calcOnce(double[] xs, Function[] functions, Function[][] derivatives){
        int n = derivatives.length;
        double[][] jacobianAug = calcJacobian(xs,functions,derivatives).clone();
        double sol[] = GaussElim.gaussianElimination(GeneralMethods.rowToObj(jacobianAug));
        return GeneralMethods.addRows(xs,sol);
    }
    private static double[][] calcJacobian (double[] xs, Function[] functions, Function[][] derivatives){
        int n = xs.length;
        double[][] jacobianAug = new double[n][n+1];
        for (int i = 0; i<n; i++){
            for (int j = 0; j<n; j++){
                jacobianAug[i][j] = derivatives[i][j].func(xs);
            }
            jacobianAug[i][n] = -1*functions[i].func(xs);
        }
        return jacobianAug;
    }

}
