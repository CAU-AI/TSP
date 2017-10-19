package ga;

import sa.SASearch;
import tspUtil.PathCheck;
import tspUtil.RandomPath;

import java.util.Random;

public class SAInitalizer implements Initializer{

	private double temperature;
	private double deltaTemperature;
	private int limitTrial;
	private int coolingFunction;
	
	
	public SAInitalizer(double temperature, double deltaTemperature, int limitTrial, int coolingFunction) {
		this.temperature = temperature;
		this.deltaTemperature = deltaTemperature;
		this.limitTrial = limitTrial;
		this.coolingFunction = coolingFunction;
	}


	@Override
	public GAElement[] initializePopulation(int populationSize, int startCity) {
		// TODO Auto-generated method stub
		Random gen = new Random();
		GAElement[] populationList = new GAElement[populationSize];
		for(int i = 0; i < populationSize; i++){
			int randTrail = (int)( this.limitTrial * (gen.nextFloat() * 0.5f + 0.5f));
			SASearch sa = new SASearch(this.temperature, this.deltaTemperature, randTrail, coolingFunction);
			populationList[i] = new GAElement();
			populationList[i].init(sa.calculatePath(RandomPath.getRandomPath(startCity)));
			System.out.println("cost : " + populationList[i].getCost());
		}


		return populationList;
	}
	
}
