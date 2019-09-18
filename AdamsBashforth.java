import java.util.LinkedList;
import java.util.Arrays;
public class AdamsBashforth {
    public static void main(String[] args){
        solveODE(0.0,0.0,1.0,9.0);
    }

    private static double solveODE(double xCur, double yCur, double h, double xUpto){

        // taking four values from RK, make it more clear, ALSO CHANGE funcODE in RK class
        double[] yVals = RungeKutta.solveODE(xCur,yCur,h,xCur+4*h);
        yCur = yVals[3];
        LinkedList<Double> primes = new LinkedList<>();

        for (int i = 0; i<4; i++){
            primes.addFirst(funcODE(xCur,yVals[i]));
            xCur = xCur + h;
        }
        xCur = xCur - h;
        System.out.println(Arrays.toString(primes.toArray()));

        int iter = 0;
        int until = (int)(Math.abs(xUpto - xCur)/Math.abs(h)+0.0001);   //crutch instead of proper rounding, sorry
        System.out.println();
        System.out.println("Iterations to be done: " + until);
        double yNext = 0.0;
        while (iter < until) {
            yNext = yCur + h/24.0 * (55*primes.get(0) - 59*primes.get(1) + 37*primes.get(2) - 9*primes.get(3));

            xCur = xCur + h;

           // double primePred = funcODE(xCur, yNext);         // Uncomment these two lines for Adams-Moulton
           // yNext = yCur + h/24.0 * (9*primePred + 19*primes.get(0) - 5*primes.get(1) + primes.get(2));

            yCur = yNext;

            System.out.println(xCur + " : " + yCur);
            primes.removeLast();
            primes.addFirst(funcODE(xCur, yCur));
            iter++;
        }
        return 0;
    }

    private static double funcODE(double x, double y){
        return 4*Math.pow(x,3.0);
    }
}
