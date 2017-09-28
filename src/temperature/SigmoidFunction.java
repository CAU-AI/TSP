package temperature;

public class SigmoidFunction {
	public static double updateTrialTemperature(double origTemperature, double coefficient, int count){
		return ((1/(1+Math.pow(Math.E, (8*(count*coefficient-0.5)))))) * origTemperature;
	}
}
