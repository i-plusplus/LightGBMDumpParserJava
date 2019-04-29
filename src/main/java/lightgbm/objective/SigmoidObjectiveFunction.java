package lightgbm.objective;

public class SigmoidObjectiveFunction implements ObjectiveFunction {
    public double apply(double d){
        return Math.exp(d)/(1+Math.exp(d));
    }
}
