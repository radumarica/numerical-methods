import java.util.ArrayList;
import java.util.Arrays;

public final class GeneralMethods {

    // converting from 2D matrix to 1D matrix of Row objects
    public static Row[] rowToObj(double[][] matrix){     //Make generic type of matrix here as well as in the Row class
        int n = matrix.length;
        Row[] matrixObj = new Row[n];
        for (int i = 0; i<n; i++){
            matrixObj[i] = new Row(matrix[i]);
        }
        return matrixObj;
    }

    // determining accuracy of a method based on steps and corresponding approximations
    // BE CAREFUL WITH PRECISION! THE SMALLER ERROR THERE IS, THE MORE PRECISE EXACT SOLUTION SHOULD BE
    public static double detAccuracy(double h1, double app1, double h2, double app2, double exactSol){
        double fractStep = h1/h2;
        double err1 = Math.abs(exactSol-app1);// / Math.abs(exactSol);   //unceomment both for relative error
        double err2 = Math.abs(exactSol-app2);// / Math.abs(exactSol);
        double fractErr = err1/err2;
     //   System.out.println(fractStep);
     //   System.out.println(err1);
     //   System.out.println(err2);
     //   System.out.println(fractErr);
     //   return Math.log(fractErr)/Math.log(fractStep);
        return Math.round(Math.log(fractErr)/Math.log(fractStep));
    }

    public static double[] addRows(double[] row1, double[] row2){       // a method for adding two rows or vectors
        double[] res = new double[row1.length];
        for (int i=0; i<row1.length;i++){
            res[i] = row1[i]+row2[i];
        }
        return res;
    }

    public static boolean allConverged (double[] prevX, double[] nextX, double tolErr){
        for (int i = 0; i<prevX.length; i++){
            if ((Math.abs(nextX[i]-prevX[i])/Math.abs(prevX[i])) > tolErr) return false;
        }
        return true;
    }


    public static double rowSumNorm (double[][] matrix){                      //a method for calculating row sum norm
        int n = matrix.length;
        int m = matrix[0].length;
        double max = 0;
        for (int i = 0; i<n; i++){
            double sum = 0;
            for (int j = 0; j<m; j++){
                sum+=Math.abs(matrix[i][j]);
            }
            if (sum>max) max = sum;
        }
        return max;
    }


    public static boolean converged ( double prevX, double nextX, double tolErr){
        if ((Math.abs(nextX-prevX)/Math.abs(prevX)) > tolErr) return false;
        return true;
    }

    //a complementary technique for getting better estimates - Richardson Extrapolation
    //difStepRes should contain approximations for halved steps, order of accuracy of the method should be known
    public static double richardson(Double[] difStepRes, double ordAcc){
        ArrayList<ArrayList<Double>> rEstim = new ArrayList<>();
        ArrayList<Double> tempList = new ArrayList<Double>(Arrays.asList(difStepRes));
        rEstim.add(tempList);
        for (int i = 1; i<difStepRes.length; i++){
            int j = i+1;
            double mult = Math.pow(2.0, (double) i * ordAcc);
            rEstim.add(new ArrayList<Double>());
            rEstim.get(i).add((mult*rEstim.get(i - 1).get(1) - rEstim.get(i - 1).get(0)) / (mult - 1));

            if ((converged(rEstim.get(i-1).get(0),rEstim.get(i).get(0),0.0025)) || (i+1 >= difStepRes.length)) {
                for (int p = 0; p<rEstim.size(); p++){
                    for (int q = 0; q<rEstim.get(p).size(); q++){
                        System.out.println(rEstim.get(p).get(q));
                    }
                    System.out.println();
                }
                return rEstim.get(i).get(0);
            }

            for(int k = 1; k<i+1; k++){
                mult = Math.pow(2.0, (double) k * ordAcc);
                Double temp = (mult*rEstim.get(k-1).get(j-k+1) - rEstim.get(k-1).get(j-k))/(mult-1);
                rEstim.get(k).add(temp);
            }
        }
        return 0.0;
    }

    public static double onceEuler(double y, double yPrime, double h){
        return y + h*yPrime;
    }

}
