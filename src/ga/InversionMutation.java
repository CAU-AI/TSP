
package ga;

        import tspUtil.GetRandomNumber;
        import tspUtil.PathCheck;
        import tspUtil.SwapCity;

public class InversionMutation implements Mutation{

    private double mutationRate;
    public InversionMutation(double mutationRate) {
        super();
        this.mutationRate = mutationRate;
    }


    @Override
    public void doMutation(GAElement[] populationList){
        int populationSize = populationList.length;

        int mutationSize = (int) (populationSize * mutationRate);

        for (int i = 1; i <= mutationSize; i++) {
            for (int k = 0; k < 5; k++) {
                int[] twoRandNumber = GetRandomNumber.getTwoRandomNumber();

                int mid = twoRandNumber[0] + ((twoRandNumber[1] + 1) - twoRandNumber[0]) / 2;
                int endCount = twoRandNumber[1];
                for (int j = twoRandNumber[0]; j < mid; j++) {
                    int tmp = populationList[populationSize - i].getPath()[j];
                    populationList[populationSize - i].getPath()[j] = populationList[populationSize - i].getPath()[endCount];
                    populationList[populationSize - i].getPath()[endCount] = tmp;
                    endCount--;
                }
            }

            populationList[populationSize - i].init(populationList[populationSize - i].getPath());
        }

    }

}
