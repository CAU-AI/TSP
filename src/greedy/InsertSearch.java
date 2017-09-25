package greedy;

import tspUtil.GetTwoRandomNumber;
import tspUtil.PathCheck;

import java.util.Arrays;

public class InsertSearch extends TwoOptSearch {
	@Override
	public int[] calculatePath(int [] path) {
		// TODO Auto-generated method stub
		int bestScore = PathCheck.getPathCost(path);

		//�ʱ� �н��� �����Ѵ�.
		int [] bestPath = Arrays.copyOf(path, path.length);

		//�õ����� 0
		int trial = 0;

		//�õ��� �ƽ��� limitTrial
		while(trial < this.limitTrial){

			//�� ���� �ѹ��� �����´�
			int[] twoRandArr = GetTwoRandomNumber.getTwoRandomNumberReal();
			int firstPoint = twoRandArr[0];
			int secondPoint = twoRandArr[1];

			//���� �н��� �����´�.
			int [] trialPath = Arrays.copyOf(bestPath, bestPath.length);

			insert(trialPath, firstPoint, secondPoint);
			//�� ���� �ѹ��� �ش��ϴ� �н� ������ �ٲ۴�

			//���� �н��� ������ ����Ѵ�.
			int trialScore = PathCheck.getPathCost(trialPath);

			//���� ��κ��� ���� ��ΰ� �� ª�ٸ�
			//�����θ� ���� ���� ��η� �����صд�.
			if(trialScore < bestScore){
				bestPath = Arrays.copyOf(trialPath, trialPath.length);
				bestScore = trialScore;
			}
			trial++;
		}
		return bestPath;
	}

	public int[] insert(int[] arr, int firstPoint, int secondPoint){
		if(firstPoint<secondPoint){
			int temp = arr[firstPoint];
			for(int i=firstPoint; i<secondPoint-1;i++){
				arr[i] = arr[i+1];
			}
			arr[secondPoint] = temp;
		}else if(firstPoint> secondPoint){
			int temp = arr[firstPoint];
			for(int i=firstPoint; i<arr.length-1; i++){
				arr[i] = arr[i+1];
			}
			arr[arr.length-1] = arr[0];
			for(int i=0; i<secondPoint-1;i++){
				arr[i] = arr[i+1];
			}
			arr[secondPoint] = temp;
		}
		return arr;
	}

}
