public class MonteCarlo {
    public static void main(String[] args){
        double upBound = 6;                                    // should be greater than lowBound
        double lowBound = 4;
        classicMonteCarlo(upBound, lowBound);
    }

    private static double classicMonteCarlo(double upBound, double lowBound){
        double height = 0;
        if (mathFunc(upBound)>= mathFunc(lowBound)) height = mathFunc(upBound);
        else height = mathFunc(lowBound);
        // f(x) for x between lowBound and upBound should not be greater than height
        // potential optimization
        double A = (upBound - lowBound) * height;
        System.out.println(A);
        double hits = 0;
        double shots = 0;
        while (shots < 10000000){
            double x = Math.random()*(upBound-lowBound) + lowBound;
            double y = Math.random()*height;
            if (y<mathFunc(x)) hits++;
            shots++;
        }
        System.out.println("Hits: " + hits);
        System.out.println("Shots: " + shots);
        double p = hits/shots;
        double I = p*A;
        System.out.println("Definite integral of the function is: " + I);
        return I;
    }

    private static double meanValueMonteCarlo(double upBound, double lowBound, int n){
        double step = (upBound - lowBound)/n;
        double sum = 0.0;
        double x = lowBound;
        while (x<upBound){
            sum += mathFunc(x);
            x += step;
        }
        double mean = sum / n;
        double I = (upBound-lowBound)*mean;
        System.out.println("Definite integral of the function is: " + I);
        return I;
    }

    private static double mathFunc(double x){
        return Math.pow(10.0, 0.5*x);
    }
}
