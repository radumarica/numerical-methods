import java.util.Arrays;
public class Row {
    private double value[];
    private int zeros = 0;
    private double pivot = 0.0;
    public Row(double[] value) {
        this.value = value.clone();
    }
    public String toString(){
        return Arrays.toString(this.value);
    }
    public double[] getValue(){
        return this.value;
    }
    public void setValue(double[] newValue){
        for (int i=0; i<newValue.length; i++){
            this.value[i] = newValue[i];
        }
    }
    public int getZeros(){
        return this.zeros;
    }
    public void resetZeros() { this.zeros = 0; }
    public void incrZeros(){
        this.zeros++;
    }
    public double getPivot() {
        return (-1)*Math.abs(this.pivot); // *-1 to sort in a descending order
    }
    public void setPivot(double newPivot){
        this.pivot = newPivot;
    }
}