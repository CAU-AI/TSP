package ga;

import tspUtil.GetRandomNumber;
import tspUtil.SwapCity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class OrderedCrossover implements Crossover {
	@Override
	public GAElement[] crossover(GAElement firstParent, GAElement secondParent) {
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


		int idx1 = secondPoint+1, idx2 = secondPoint+1;
		int len = firstPath.length;
		for(int i=0; i < len-secondPoint-1; i++){
			while(hasPathInMiddle(firstPathMiddle, secondParent.getPath()[idx1%len]))
				idx1+=1;
			while(hasPathInMiddle(secondPathMiddle, firstParent.getPath()[idx2%len]))
				idx2+=1;
			firstPath[i + secondPoint] = secondParent.getPath()[idx1%len];
			secondPath[i + secondPoint] = firstParent.getPath()[idx2%len];
			idx1++;
			idx2++;
		}
		for(int i=0; i<firstPoint; i++){
			while(hasPathInMiddle(firstPathMiddle, secondParent.getPath()[idx1%len]))
				idx1+=1;
			while(hasPathInMiddle(secondPathMiddle, firstParent.getPath()[idx2%len]))
				idx2+=1;
			firstPath[i] = secondParent.getPath()[idx1%len];
			secondPath[i] = firstParent.getPath()[idx2%len];
			idx1++;
			idx2++;
		}

		child[0].init(firstPath);
		child[1].init(secondPath);

		return child;
	}

	private boolean hasPathInMiddle(int[] middleArr, int city){
		for(int i=0; i<middleArr.length;i++){
			if(middleArr[i] == city)
				return true;
		}
		return false;
	}
}
