package ga;

import sa.SASearch;
import tspUtil.GetRandomNumber;
import tspUtil.RandomPath;
import tspUtil.SwapCity;

public class SAMutation implements Mutation {

	private double mutationRate;

	int temperature;
	float deltaTemperature;
	int trial;
	int coolingFunction;

	public SAMutation(double mutationRate, int temperature, float deltaTemperature, int trial, int coolingFunction) {
		super();
		this.mutationRate = mutationRate;
		this.temperature = temperature;
		this.deltaTemperature = deltaTemperature;
		this.trial = trial;
		this.coolingFunction = coolingFunction;
	}

	@Override
	public void doMutation(GAElement[] populationList) {
		// TODO Auto-generated method stub
		int populationSize = populationList.length;

		int mutationSize = (int) (populationSize * mutationRate);


		for (int i = 1; i <= mutationSize; i++) {
			SASearch sa = new SASearch(this.temperature, this.deltaTemperature, trial, coolingFunction);
			populationList[populationSize - i].init(sa.calculatePath(populationList[populationSize - i].getPath()));
		}
	}
}
