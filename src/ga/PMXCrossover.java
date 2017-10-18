package ga;

import java.util.Arrays;

import tspUtil.GetRandomNumber;
import tspUtil.PathCheck;
import tspUtil.SwapCity;

public class PMXCrossover implements Crossover{
	
	
	
	@Override
	public GAElement[] crossover(GAElement firstParent, GAElement secondParent) {
		// TODO Auto-generated method stub
		GAElement [] child = new GAElement[2];
		child[0] = new GAElement();
		child[1] = new GAElement();
		
		int [] firstPath = Arrays.copyOf(firstParent.getPath(), firstParent.getPath().length);
		int [] secondPath = Arrays.copyOf(secondParent.getPath(), secondParent.getPath().length);
		
		int [] twoRandNumber = GetRandomNumber.getTwoRandomNumber();
		
		int firstPoint = twoRandNumber[0];
		int secondPoint = twoRandNumber[1];
		
		for(int i = firstPoint; i <= secondPoint; i++){
			int idx1 = this.getIndexOfCity(firstPath, secondParent.getPath()[i]);
			SwapCity.swapCity(firstPath, i, idx1);
			
			int idx2 = this.getIndexOfCity(secondPath, firstParent.getPath()[i]);
			SwapCity.swapCity(secondPath, i, idx2);
		}

		child[0].init(firstPath);
		child[1].init(secondPath);
		
		return child;
	}
	
	private int getIndexOfCity(int [] arr, int city){
		for(int i = 0; i < arr.length; i++){
			if(arr[i] == city) return i;
		}
		return -1;
	}
	
}
