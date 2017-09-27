package sa;

import java.util.Arrays;

import greedy.*;
import tspUtil.GetRandomNumber;
import tspUtil.PathCheck;
import tspUtil.TSPAlgorithm;

public class SASearch extends TSPAlgorithm{
	private double temperature;
	private double deltaTemperature;
	private int numOfNextHop;
	
	private OriginalTwoOptSearch twoOptSearch;
	
	public SASearch(double temperature, double deltaTemperature, int limitTrial, int numOfNextHop) {
		this.twoOptSearch = new OriginalTwoOptSearch(limitTrial);
		this.setSAParameter(temperature, deltaTemperature);
		this.numOfNextHop = numOfNextHop;
	}

	public void setTemperature(double temperature){
		this.temperature = temperature;
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

		return path;
	}


	@Override
	public int[] calculatePath(int[] path) {

		int[] bestPath = Arrays.copyOf(path, path.length);

		int bestScore = PathCheck.getPathCost(bestPath);

		float anCount=0;

		double trialTemperature = this.temperature;
		while (trialTemperature > 1) {
			int[] insertTrialPath = Arrays.copyOf(bestPath, bestPath.length);
			int[] inverseTrialPath = Arrays.copyOf(bestPath, bestPath.length);
			int[] swapTrialPath= Arrays.copyOf(bestPath, bestPath.length);
			int[] threeOptTrialPath = Arrays.copyOf(bestPath,bestPath.length);

			int[] trialPath = Arrays.copyOf(bestPath, bestPath.length);


			InsertSearch insertSearch = new InsertSearch(100000);
			insertTrialPath = insertSearch.calculatePath(trialPath);

			InverseSearch inverseSearch = new InverseSearch(10000);
			inverseTrialPath = inverseSearch.calculatePath(trialPath);

			SwapSearch swapSearch = new SwapSearch(10000);
			swapTrialPath = swapSearch.calculatePath(trialPath);

			ThreeOptSearch threeOptSearch = new ThreeOptSearch(10000);
			threeOptTrialPath = threeOptSearch.calculatePath(trialPath);

			int insertTrialScore = PathCheck.getPathCost(insertTrialPath);
			int inverseTrialScore = PathCheck.getPathCost(inverseTrialPath);
			int swapTrialScore = PathCheck.getPathCost(swapTrialPath);
			int threeOptTrialScore = PathCheck.getPathCost(threeOptTrialPath);
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
			if(threeOptTrialScore < trialScore){
				trialPath = threeOptTrialPath;
				trialScore = threeOptTrialScore;
			}

			double prob = this.getAcceptProbability(bestScore, trialScore);
			double random = Math.random();
			if (random < prob) {
				bestPath = Arrays.copyOf(trialPath, trialPath.length);

				bestScore = trialScore;

			}


			// normal function
			// trialTemperature *= deltaTemperature;

			trialTemperature = ((1/(1+Math.pow(Math.E, (8*(anCount-0.5)))))) * temperature;
			anCount+= 0.07f;
			System.out.println("temperature : " + trialTemperature);
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
