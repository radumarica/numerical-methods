import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

//SIMPLIFY THE WHOLE PROCESS

public class GaussElim {

    static private Comparator <Row> compZeros;
    static private Comparator <Row> compPivots;
    static {
        compZeros = new Comparator<Row>() {
            @Override
            public int compare(Row r1, Row r2) {
                return Integer.valueOf(r1.getZeros()).compareTo(Integer.valueOf(r2.getZeros()));
            }
        };
        compPivots = new Comparator<Row>() {
            @Override
            public int compare(Row r1, Row r2) {
                return Double.valueOf(r1.getPivot()).compareTo(Double.valueOf(r2.getPivot()));
            }
        };
    }

    public static void main(String args[]){

        int n = 3;
        Row[] matrix = new Row[n];
//        matrix[0] = new Row(new double[]{1,1,-2,1,3,-1,4});
//        matrix[1] = new Row(new double[]{2,-1,1,2,1,-3,20});
//        matrix[2] = new Row(new double[]{1,3,-3,-1,2,1,-15});
//        matrix[3] = new Row(new double[]{5,2,-1,-1,2,1,-3});
//        matrix[4] = new Row(new double[]{-3,-1,2,3,1,3,16});
//        matrix[5] = new Row(new double[]{4,3,1,-6,-3,-2,-27});

//        matrix[0] = new Row(new double[]{7,5,-3,16});
//        matrix[1] = new Row(new double[]{3,-5,2,-8});
//        matrix[2] = new Row(new double[]{5,3,-7,0});

//        matrix[0] = new Row(new double[]{1,0,0,0,0});
//        matrix[3] = new Row(new double[]{1.5,8,2.5,0,9.6});
//        matrix[2] = new Row(new double[]{0,2.5,9,2,-9.6});
//        matrix[1] = new Row(new double[]{0,0,0,1,0});

//        matrix[0] = new Row(new double[]{2,1,3,1});
//        matrix[1] = new Row(new double[]{2,6,8,3});
//        matrix[2] = new Row(new double[]{6,8,18,5});
        matrix[0] = new Row(new double[]{10,-7,0,7});
        matrix[1] = new Row(new double[]{-3,2.099,6,3.901});
        matrix[2] = new Row(new double[]{5,-1,5,6});

        gaussianElimination(matrix);
    }

    public static double[] gaussianElimination (Row[] matrix){
        countZeros(matrix);
        Arrays.sort(matrix, compZeros);

        for (int from = 0; from<matrix.length-1; from++){
            int to = from+1;
            for (int k=from+1; k<matrix.length; k++){
                if (matrix[k].getValue()[from] !=0) to++;
                else break;
            }
            // System.out.println(from + " to " + to);
            Arrays.sort(matrix, from, to, compPivots);
            for (int j = from + 1; j<matrix.length; j++){
                double fraction = (-1.0)*(matrix[j].getValue()[from]/matrix[from].getValue()[from]);
                double[] temp = multRow(matrix[from].getValue(), fraction);
                temp = GeneralMethods.addRows(temp,matrix[j].getValue());
                matrix[j].setValue(temp);
            }
            countZeros(matrix);
            Arrays.sort(matrix, compZeros);
        }

        System.out.println();
        for (Row arr : matrix) {
            System.out.println(arr);
        }
        System.out.println();
        System.out.println(Arrays.toString(backwardSubstitution(matrix)));
          System.out.println();
        return backwardSubstitution(matrix);
    }


    private static void countZeros(Row[] matrix){ // count zeros in the beginning of every row (needed for sorting)
        int rows = matrix.length;
        int columns = matrix[0].getValue().length;
        for (int h=0; h<rows; h++){
            matrix[h].resetZeros();
        }
        for (int i=0; i<rows; i++){
            double[] temp = matrix[i].getValue();
            for (int j=0; j<columns; j++){
                if (temp[j] == 0) matrix[i].incrZeros();
                else {
                    matrix[i].setPivot(temp[j]);
                    break;
                }
            }
        }
    }


    private static double[] multRow(double[] row, double constant){ // a method for multiplying a row by a constant
        double[] res = new double[row.length];
        for (int i=0; i<row.length; i++){
            res[i] = row[i] * constant;
        }
        return res;
    }
    private static double[] backwardSubstitution (Row[] matrix){
        int size = matrix.length;
        double[] solution = new double[size];
        for (int i = size-1; i>=0; i--){
            double sum = 0;
            for (int j = i+1; j<matrix[0].getValue().length-1; j++){
                sum = sum + matrix[i].getValue()[j]*solution[j];
            }
            solution[i] = (matrix[i].getValue()[matrix[0].getValue().length-1] - sum) / matrix[i].getValue()[i];
        }
        return solution;
    }
}
