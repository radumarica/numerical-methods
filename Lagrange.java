public class Lagrange {
    public static void main(String[] args){
      //  double[] xs = {-1,0,3};
      //  double[] ys = {8,-2,4};
        double[] xs = {1.0,2.0,6.0};
        double[] ys = {11.0,46.0,7.0};
        double x = 0;
        System.out.println(interpolate(xs,ys,x));
    }
    private static double interpolate(double[] xs, double ys[], double x){
        double res = 0;
        for (int i=0; i<ys.length; i++){
            double numer = 1;
            double denom = 1;
            for (int j = 0; j<i; j++){
                numer *= (x-xs[j]);
                denom *= (xs[i]-xs[j]);
            }
            for (int k = i+1; k<xs.length; k++){
                numer *= (x-xs[k]);
                denom *= (xs[i]-xs[k]);
            }
            res += (numer/denom)*ys[i];
        }
        return res;
    }
}
