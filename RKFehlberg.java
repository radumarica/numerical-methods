public class RKFehlberg {
    public static void main(String[] args){
        solveODE(0.0,1.0/1024.0,0.25,0.6,0.000005);
    }

    private static double solveODE(double xCur, double yCur, double h, double xUpto, double absTol){
        double yNext = 0.0;
        double error = 0.0;
        while (xCur < xUpto){
            Tuple<Double,Double> predCor = calcOnce(xCur,yCur,h);
            error = Math.abs(predCor.get1() - predCor.get2());
            //System.out.println(0.25*h*absTol + " " + error + " " + h*absTol);
            if ((error >= 0.25*h*absTol) && (error <= h*absTol)) {
                yNext = predCor.get2();
                yCur = yNext;
                xCur = xCur + h;
            }
            else if (error > h*absTol) h = 0.5*h;
            else if (error < 0.25*4*absTol) {
                yNext = predCor.get2();
                yCur = yNext;
                h = 2.0*h;
                xCur = xCur + h;
            }
            System.out.println(xCur + " : " + yCur + " : " + h);
        }
        return yNext;
    }

    private static Tuple<Double,Double> calcOnce(double xCur, double yCur, double h){
        double k1 = h*funcODE(xCur,yCur);
        double k2 = h*funcODE(xCur+0.25*h,yCur+0.25*k1);
        double k3 = h*funcODE(xCur+3.0/8.0*h,yCur+3.0/32.0*k1+9.0/32.0*k2);
        double k4 = h*funcODE(xCur+12.0/13.0*h,yCur+1932.0/2197.0*k1-7200.0/2197.0*k2+7296.0/2197.0*k3);
        double k5 = h*funcODE(xCur+h,yCur+439.0/216.0*k1-8.0*k2+3680.0/513.0*k3-845.0/4104.0*k4);
        double k6 = h*funcODE(xCur+0.5*h,yCur-8.0/27.0*k1+2.0*k2-3544.0/2565.0*k3+1859.0/4104.0*k4+11.0/40.0*k5);
        double rk5 = yCur + (16.0/135.0*k1 + 6656.0/12825.0*k3+28561.0/56430.0*k4-9.0/50.0*k5+2.0/55.0*k6);
        double rk4 = yCur + (25.0/216.0*k1 + 1408.0/2565.0*k3 + 2197.0/4104.0*k4-0.2*k5);
        return new Tuple(new Double(rk5),new Double(rk4));
    }

    private static double funcODE(double x, double y){
        //return y-x;
        return 16*y*(1-y);
    }

}
