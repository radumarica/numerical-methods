public class Euler {
    public static void main(String[] args){
        //solveODE(1.0,1.0,-0.01,0.3);
        solveODE(0.0,2.0,0.01,1.0);
    }
    //parameters: initial x, initial y(x), step(can be negative), x to continue until
    private static double solveODE(double xCur, double yCur, double h, double xUpto){
        int iter = 0;
        int until = (int)(Math.abs(xUpto - xCur)/Math.abs(h));
        System.out.println("Iterations to be done: " + until);
        double yNext = 0.0;
        while (iter < until){
            double yPrime = funcODE(xCur, yCur);
            yNext = GeneralMethods.onceEuler(yCur, yPrime, h);
            xCur = xCur + h;

            //double yPrimeCor = funcODE(xCur, yNext);            // uncomment these two lines for Heun's method
            //yNext = yCur + 1.0/2.0 * h * (yPrime + yPrimeCor);

            yCur = yNext;

            System.out.println(xCur + " : " + yCur);
            iter ++;
        }
        return yNext;
    }
    private static double funcODE(double x, double y){
        return y-x;
    }
}
