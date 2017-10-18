package ga;

import tspUtil.GetRandomNumber;
import tspUtil.SwapCity;

public class SwapMutation implements Mutation {

	private double mutationRate;
	public SwapMutation(double mutationRate) {
		super();
		this.mutationRate = mutationRate;
	}

	@Override
	public void doMutation(GAElement[] populationList) {
		// TODO Auto-generated method stub
		int populationSize = populationList.length;

		int mutationSize = (int) (populationSize * mutationRate);

		for (int i = 1; i <= mutationSize; i++) {

			int [] twoRandNumber = GetRandomNumber.getTwoRandomNumber();
			
			SwapCity.swapCity(populationList[populationSize - i].getPath(), twoRandNumber[0], twoRandNumber[1]);

			populationList[populationSize - i].init();

		}
	}
}
