package sa;

import java.util.Arrays;

import greedy.*;
import temperature.NormalFunction;
import temperature.SigmoidFunction;
import temperature.TemperatureFunction;
import tspUtil.GetRandomNumber;
import tspUtil.PathCheck;
import tspUtil.TSPAlgorithm;

public class SASearch extends TSPAlgorithm{
	private double temperature;
	private double deltaTemperature;
	private int temperatureFunction;
	private boolean isTest = false;
	private OriginalTwoOptSearch twoOptSearch;
	
	public SASearch(double temperature, double deltaTemperature, int limitTrial, int temperatureFunction) {
		this.twoOptSearch = new OriginalTwoOptSearch(limitTrial);
		this.setSAParameter(temperature, deltaTemperature);
		this.temperatureFunction = temperatureFunction;
	}

	public void setIsTest(boolean isTest){
		this.isTest = isTest;
	}


	public void setSAParameter(double temperature, double deltaTemperature) {
		if (temperature <= 0) {
			System.err.println("Temperature must be bigger than 0");
			System.exit(1);
		}
		this.temperature = temperature;
		if (!(0 < deltaTemperature && deltaTemperature < 1)) {
			System.err.println("delta value of temperature must be between 0 to 1");
			System.exit(1);
		}
		this.deltaTemperature = deltaTemperature;
	}

	@Override
	public int[] calculatePath(int startPoint) {
		// TODO Auto-generated method stub
		int[] path = this.twoOptSearch.calculatePath(startPoint);
		path = this.calculatePath(path);
		return path;
	}


	@Override
	public int[] calculatePath(int[] path) {

		int[] bestPath = Arrays.copyOf(path, path.length);

		int bestScore = PathCheck.getPathCost(bestPath);

		float anCount=0;

		double trialTemperature = this.temperature;
		int count =0;


		while (trialTemperature > 1) {
			int[] insertTrialPath;
			int[] inverseTrialPath;
			int[] swapTrialPath;
			//int[] threeOptTrialPath = Arrays.copyOf(bestPath,bestPath.length);

			int[] trialPath = Arrays.copyOf(bestPath, bestPath.length);


			InsertSearch insertSearch = new InsertSearch(twoOptSearch.limitTrial * 6);
			insertTrialPath = insertSearch.calculatePath(trialPath);

			InverseSearch inverseSearch = new InverseSearch(twoOptSearch.limitTrial * 4);
			inverseTrialPath = inverseSearch.calculatePath(trialPath);

			SwapSearch swapSearch = new SwapSearch(twoOptSearch.limitTrial * 1);
			swapTrialPath = swapSearch.calculatePath(trialPath);

			//ThreeOptSearch threeOptSearch = new ThreeOptSearch(twoOptSearch.limitTrial * 3);
			//threeOptTrialPath = threeOptSearch.calculatePath(trialPath);

			int insertTrialScore = PathCheck.getPathCost(insertTrialPath);
			int inverseTrialScore = PathCheck.getPathCost(inverseTrialPath);
			int swapTrialScore = PathCheck.getPathCost(swapTrialPath);
			//int threeOptTrialScore = PathCheck.getPathCost(threeOptTrialPath);
			int trialScore = 0;
			if(swapTrialScore < inverseTrialScore){
				if(insertTrialScore < swapTrialScore){
					trialPath = insertTrialPath;
					trialScore = insertTrialScore;
				}else{
					trialPath = swapTrialPath;
					trialScore = swapTrialScore;
				}
			}else{
				if(insertTrialScore < inverseTrialScore){
					trialPath = insertTrialPath;
					trialScore = insertTrialScore;
				}else{
					trialPath = inverseTrialPath;
					trialScore = inverseTrialScore;
				}
			}

			double prob = this.getAcceptProbability(bestScore, trialScore);
			double random = Math.random();
			if (random < prob) {
				bestPath = Arrays.copyOf(trialPath, trialPath.length);
				bestScore = trialScore;

			}

			if(this.temperatureFunction == TemperatureFunction.NORMAL){
				trialTemperature = NormalFunction.updateTrialTemperature(trialTemperature, deltaTemperature);
			}else if(temperatureFunction == TemperatureFunction.SIGMOID){
				trialTemperature = SigmoidFunction.updateTrialTemperature(temperature, 0.07f, count++);
			}

			//if(!isTest)
				//System.out.println("temperature : " + trialTemperature);
			// sigmoid function
			// System.out.println("temperature : " +temperature);
		}
		return bestPath;
	}

	private double getAcceptProbability(int bestScore, int trialScore) {

		if (bestScore > trialScore)
			return 1;
		else {
			return Math.pow(Math.E, -(trialScore - bestScore) / this.temperature);
		}
	}
	
}
