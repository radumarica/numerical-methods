import java.util.Arrays;
public class EllipticDirichlet {
    public static void main(String[] args){
        int n = 5;
        int m = 6;
        int p = (n-2)*(m-2);
        double[][] domain = new double[n][m];
        double[][] matrixU = new double[p][p+1];   // b in Au=b is 0, because we are solving Laplace equation
        includeBC(domain, 0.0, 100.0, 0.0, 50.0);
        setUpMatrix(domain,matrixU);
        for(int i=0; i<p; i++){
            System.out.println(Arrays.toString(matrixU[i]));
        }
        Jacobi.solveSys(p,matrixU);
    }
    private static void includeBC(double[][] domain,double lbc, double rbc, double tbc, double bbc){
        int n = domain.length;
        int m = domain[0].length;
        for (int i = 1; i<n-1; i++){
            domain[i][0] = lbc;
            domain[i][m-1] = rbc;
        }
        for (int i = 1; i<m-1; i++){
            domain[0][i] = tbc;
            domain[n-1][i] = bbc;
        }
    }
    private static void setUpMatrix(double[][] domain, double[][] matrixU){
        int n = domain.length;
        int m = domain[0].length;
        int p = matrixU.length;
        int q = matrixU[0].length;
        for (int i = 1; i<n-1; i++){
            for (int j = 1; j<m-1; j++){
                int ind = ind1D(i-1,j-1,m-2);
                matrixU[ind][ind] = -4.0;
                if(j-1 == 0) matrixU[ind][q-1]=matrixU[ind][q-1]-domain[i][j-1];    //introduce a more smart branching here
                else  matrixU[ind][ind1D(i-1,j-2,m-2)] = 1;

                if (j+1 == m-1) matrixU[ind][q-1]=matrixU[ind][q-1]-domain[i][j+1];
                else matrixU[ind][ind1D(i-1,j,m-2)] = 1;

                if(i-1 == 0) matrixU[ind][q-1]=matrixU[ind][q-1]-domain[i-1][j];
                else matrixU[ind][ind1D(i-2,j-1,m-2)] = 1;

                if (i+1 == n-1) matrixU[ind][q-1]=matrixU[ind][q-1]-domain[i+1][j];
                else matrixU[ind][ind1D(i,j-1,m-2)] = 1;

            }
        }
    }
    private static int ind1D(int i, int j, int m){
        return i*m+j;
    }
}
