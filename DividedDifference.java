//COMMENT THIS CODE PROPERLY!

import java.util.ArrayList;
import java.util.Arrays;
public class DividedDifference {
    public static void main(String[] args){
//        double[] xs = {2,3,4,5,8,9};
//        Double[] ys = {new Double(1), new Double(6), new Double(13), new Double(22), new Double(61), new Double(78)};
//        double[] xs = {-1,0,3};
//        Double[] ys = {new Double(8), new Double(-2), new Double(4)};
//        double[] xs = {8,9,9.5,11};
//        Double[] ys = {new Double(2.079442), new Double(2.197225), new Double(2.251292), new Double(2.397895)};
//        double[] xs = {1,2,4,6};
//        Double[] ys = {new Double(7), new Double(39.597979746446661366447284277872), new Double(224), new Double(617.27141518136088074571558682588)};
        double[] xs = {2,3,5,7,8,10};
        Double[] ys = {new Double(16), new Double(81), new Double(625), new Double(2401), new Double(4096), new Double(10000)};
        double x = 4 ;
        interpolate(xs, ys, x);

    }
    private static void interpolate(double[] xs, Double[] ys, double x){
        ArrayList<Double> pols = new ArrayList<>();
        pols.add(new Double(ys[0]));
        ArrayList<ArrayList<Double>> funcOf = new ArrayList<>();
        ArrayList<Double> tempList = new ArrayList<Double>(Arrays.asList(ys));
        funcOf.add(tempList);

        funcOf.add(new ArrayList<Double>());

        for(ArrayList<Double> item1 : funcOf) {
            for(Double item2: item1){
               System.out.print(item2 + " ");
            }
            System.out.println();
        }
        for (int i = 1; i<xs.length; i++){
            int j = i;
            funcOf.add(new ArrayList<Double>());
            funcOf.get(i).add((funcOf.get(i - 1).get(1) - funcOf.get(i - 1).get(0)) / (xs[j] - xs[j - i]));
      //    System.out.println(i+","+(funcOf.get(i).size()-1));         // to see the order of ArrayList filling
            System.out.println(funcOf.get(i).get(0));
            pols.add(calcPoly(xs, pols, funcOf.get(i).get(0), x));
            int last = pols.size()-1;
            if ((converged(pols.get(last-1),pols.get(last))) || (i+1 == xs.length)) break;
            j++;
            for(int k = 1; k<i+1; k++){
                Double temp = (funcOf.get(k-1).get(j-k+1) - funcOf.get(k-1).get(j-k))/(xs[j]-xs[j-k]);
                funcOf.get(k).add(temp);
      //         System.out.println(k+","+(funcOf.get(k).size()-1));   // to see the order of ArrayList filling
            }
        }
        System.out.println();
        for (Double item: pols){
            System.out.print(item+" ");
        }
    }
    private static Double calcPoly(double[] xs, ArrayList<Double> pols, double func, double x){
        double mult = 1;
        for (int i=0; i<pols.size(); i++){
            mult *= (x-xs[i]);
        }
        mult *= func;
        return new Double(pols.get(pols.size()-1)+mult);
    }
    private static boolean converged ( double prevX, double nextX){
        if ((Math.abs(nextX-prevX)/Math.abs(prevX)) > 0.0025) return false;  //tune precision here
        return true;
    }

}
