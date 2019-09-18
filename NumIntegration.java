public class NumIntegration {
    public static void main(String[] args){
        double upBound = 6.0;
        double lowBound = 4.0;
        double panels = 500.0;

        double h = (upBound - lowBound)/panels;
        System.out.println("Step: " + h);
        double I = rectRule(lowBound,h,panels,'M');
        System.out.println("Definite integral of the function is: " + I);
        System.out.println();

        double h1 = h;
        double h2 = (upBound - lowBound)/(panels*2);
        System.out.println("Step: " + h2);
        double I1 = I;
        double I2 = rectRule(lowBound, h2, panels * 2,'M');
        System.out.println("Definite integral of the function is: " + I2);
        System.out.println();
        System.out.println("Order of accuracy is: " + GeneralMethods.detAccuracy(h1,I1,h2,I2,781.73006742585328977));

        // ORDER OF ACCURACY METHOD TESTING

        //  System.out.println("Order of accuracy is: " + GeneralMethods.detAccuracy(0.2, 780.005, 0.1, 781.2984, 781.73));
        //  System.out.println("Order of accuracy is: " + GeneralMethods.detAccuracy(0.4, 774.8648028178796, 0.1, 781.2984995698428, 781.73));
        //  System.out.println("Order of accuracy is: " + GeneralMethods.detAccuracy(0.04, 781.6609941456502, 0.01, 781.7257500954682, 781.73));
        //  System.out.println("Order of accuracy is: " + GeneralMethods.detAccuracy(0.004, 781.7293766506183, 0.002, 781.7298947318646, 781.73006742585328977));
    }
    private static double mathFunc(double x){
        return Math.pow(10.0, 0.5*x);
    }
    private static double rectRule(double lowBound, double h, double panels, char type){
        double x = 0.0;
        switch (type){
            case 'L': x = lowBound;        // rectangular rule: lower rectangles
                      break;
            case 'U': x = lowBound + h;    // rectangular rule: upper rectangles
                      break;
            case 'M': x = lowBound + h/2;  // rectangular rule: midpoint
                      break;
        }
        double sumOfFunc = 0.0;
        while (panels > 0.0){
            sumOfFunc += mathFunc(x);
            x = x + h;
            panels=panels-1;
        }
        double I = h*sumOfFunc;
        return I;
    }
    private static double trapezRule(double lowBound,  double h, double panels){
        double x = lowBound + h;
        double sumOfFunc = 0.0;
        while (panels > 1.0){
            sumOfFunc += mathFunc(x);
            x = x + h;
            panels=panels-1;
        }
        sumOfFunc = sumOfFunc + 0.5*mathFunc(lowBound) + 0.5*mathFunc(x); // or take f(upperBound) instead of f(x)
        double I = h*sumOfFunc;
        return I;
    }
}
