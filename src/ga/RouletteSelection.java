package ga;
import java.util.Random;

public class RouletteSelection implements Selection {


    private int firstParent;
    private int secondParent;
    private double sum = 0;
    protected GAElement[] inputList;

	public void setInputList(GAElement[] inputList){
	    this.inputList = inputList;
        getSum();
    }

    public void getSum(){
	    sum = 0;
        for(int i = 0; i < inputList.length; i ++){
            sum += inputList[i].getFitness();
        }
    }

	@Override
	public int getFirstParent() {
		return firstParent;
	}
	@Override
	public int getSecondParent() {
	    return secondParent;
	}

    @Override
	public void setParentList() {

        getSum();

	    Random random = new Random();
        double rate = random.nextDouble() * sum;
        double r = 0;
        int i = 0;
        for (; r < rate && i < inputList.length; i++) {
            r += inputList[i].getFitness();
        }
        firstParent = i - 1;
        secondParent = firstParent;

        while(secondParent == firstParent) {
            rate = random.nextDouble() * sum;
            r = 0;
            i = 0;
            for (; r < rate && i < inputList.length; i++) {
                r += inputList[i].getFitness();
            }
            secondParent = i - 1;
        }

        if(secondParent < firstParent){
            int temp = firstParent;
            firstParent = secondParent;
            secondParent = temp;
        }
	}
}
