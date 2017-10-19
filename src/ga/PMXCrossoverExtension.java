package ga;

import tspUtil.GetRandomNumber;
import tspUtil.SwapCity;

import java.util.Arrays;

public class PMXCrossoverExtension implements Crossover {
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
		if(firstPoint > secondPoint){
			int temp = firstPoint;
			firstPoint = secondPoint;
			secondPoint = temp;
		}

		int[] firstPathMiddle = Arrays.copyOfRange(firstPath, firstPoint, secondPoint+1);
		int[] secondPathMiddle = Arrays.copyOfRange(secondPath, firstPoint, secondPoint+1);

		int len = firstPath.length;
		for(int i=secondPoint+1; i < len; i++){
			if(hasPathInMiddle(firstPathMiddle, secondParent.getPath()[i])){
				int idx1 = this.getIndexOfCity(firstPath, secondParent.getPath()[i]);
				SwapCity.swapCity(firstPath, i, idx1);
			}else{
				firstPath[i] = secondPath[i];
			}
		}
		for(int i=0; i<firstPoint; i++){
			if(hasPathInMiddle(firstPathMiddle, secondParent.getPath()[i])){
				int idx1 = this.getIndexOfCity(firstPath, secondParent.getPath()[i]);
				SwapCity.swapCity(firstPath, i, idx1);
			}else{
				firstPath[i] = secondPath[i];
			}
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

	private boolean hasPathInMiddle(int[] middleArr, int city){
		for(int i=0; i<middleArr.length;i++){
			if(middleArr[i] == city)
				return true;
		}
		return false;
	}
}
