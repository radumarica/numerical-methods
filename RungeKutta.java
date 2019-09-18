
public class RungeKutta {
    public static void main(String[] args){
        //solveODE(0.0,2.0,0.1,1.0);
        solveODE(0.0,0.0,1.0,9.0);
    }
    public static double[] solveODE(double xCur, double yCur, double h, double xUpto){
        double[] fourValues = new double[4];
        int iter = 0;
        int until = (int)(Math.abs(xUpto - xCur)/Math.abs(h));
        System.out.println("Iterations to be done: " + until);
        double yNext = 0.0;
        double k1 = 0.0;
        double k2 = 0.0;
        double k3 = 0.0;
        double k4 = 0.0;
        while (iter < until) {
            if(iter<4) fourValues[iter] = yCur;         //special thing for multistep, must be improved!
            yNext = calcOnce(xCur, yCur, h);
            xCur = xCur + h;
            yCur = yNext;
            System.out.println(xCur + " : " + yCur);
            iter ++;
        }
        System.out.println("The value of y at x = "+ xCur + " is " + yNext);
        return fourValues;
    }
    public static double calcOnce(double xCur, double yCur, double h){ // is public for Fehlberg method
        double k1 = h*funcODE(xCur, yCur);
        double k2 = h*funcODE(xCur + 0.5*h, yCur + 0.5*k1);
        double k3 = h*funcODE(xCur + 0.5*h, yCur + 0.5*k2);
        double k4 = h*funcODE(xCur + h, yCur + k3);
        double yNext = yCur + 1.0/6.0 * (k1 + 2*k2 + 2*k3 + k4);
        return yNext;
    }
    private static double funcODE(double x, double y){
        //return y-x;
        return 4*Math.pow(x,3.0);
    }
}
