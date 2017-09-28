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
			int[] rand = GetRandomNumber.getTwoRandomNumberReal();

			//���� �н��� �����´�.
			int [] trialPath = Arrays.copyOf(bestPath, bestPath.length);

			//�ٲ㺻��
			trialPath = inverse(trialPath, rand[0], rand[1]);

			PathCheck.checkChangeNearNodesLocal(trialPath, rand[0]);
			PathCheck.checkChangeNearNodesLocal(trialPath, rand[1]);
			PathCheck.checkChangeNearNodes(trialPath, rand[0]);
			PathCheck.checkChangeNearNodes(trialPath, rand[1]);

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
		int first = firstPoint;
		int second = secondPoint;
		if(secondPoint < firstPoint) {
			first = secondPoint;
			second = firstPoint;
		}

		for (int i = 0; i < (secondPoint - firstPoint) / 2; i++) {
			int temp = arr[secondPoint - i];
			arr[secondPoint - i] = arr[firstPoint + i];
			arr[firstPoint + i] = temp;
		}

		return arr;
	}
}
