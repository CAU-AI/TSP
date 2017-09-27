package greedy;

import tspUtil.GetRandomNumber;
import tspUtil.PathCheck;

import java.util.Arrays;

public class InverseSearch extends TwoOptSearch {

	public InverseSearch(int limitTrial){
		this.setTwoOptSearchParameter(limitTrial);
	}

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
			int[] twoRandArr = GetRandomNumber.getTwoRandomNumberReal();
			int firstPoint = twoRandArr[0];
			int secondPoint = twoRandArr[1];

			//���� �н��� �����´�.
			int [] trialPath = Arrays.copyOf(bestPath, bestPath.length);

			inverse(trialPath, firstPoint, secondPoint);
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

	public int[] inverse(int [] arr, int firstPoint, int secondPoint){
		if(firstPoint < secondPoint) {
			for (int i = 0; i < (secondPoint - firstPoint) / 2; i++) {
				int temp = arr[secondPoint - i];
				arr[secondPoint - i] = arr[firstPoint + i];
				arr[firstPoint + i] = temp;
			}
		} else {
			for (int i = 0; i < (firstPoint - secondPoint) / 2; i++) {
				int sec = secondPoint + i;
				if(arr.length <= sec){
					sec -= arr.length;
				}
				int first = firstPoint - i;
				if(first < 0){
					first += arr.length;
				}
				int temp = arr[sec];
				arr[sec] = arr[first];
				arr[first] = temp;
			}
		}
		return arr;
	}
}
