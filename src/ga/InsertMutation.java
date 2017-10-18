package ga;

import tspUtil.GetRandomNumber;
import tspUtil.PathCheck;
import tspUtil.SwapCity;

public class InsertMutation implements Mutation{

    private double mutationRate;
    public InsertMutation(double mutationRate) {
        super();
        this.mutationRate = mutationRate;
    }

    @Override
    public void doMutation(GAElement[] populationList){

        int populationSize = populationList.length;

        int mutationSize = (int) (populationSize * mutationRate);

        for (int i = 1; i <= mutationSize; i++) {


            int[] twoRandNumber = GetRandomNumber.getTwoRandomNumber();

            for (int j = twoRandNumber[1] - 1; j > twoRandNumber[0]; j--) {
                int temp2 = populationList[populationSize - i].path[j + 1];
                populationList[populationSize - i].path[j + 1] = populationList[populationSize - i].path[j];
                populationList[populationSize - i].path[j] = temp2;
            }

            populationList[populationSize - i].cost = PathCheck.getPathCost(populationList[populationSize - i].path);
        }
    }
}
