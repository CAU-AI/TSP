package greedy;

import tspUtil.GetRandomNumber;
import tspUtil.PathCheck;

import java.util.Arrays;

public class InsertSearch extends TwoOptSearch {

	public InsertSearch(int limitTrial){
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
			trialPath = insert(trialPath, rand[0], rand[1]);

			//���� �н��� ������ ����Ѵ�.
			int trialScore = PathCheck.getPathCost(trialPath);

			//�����ѹ��� �ѹ� �� �̴´�
			rand = GetRandomNumber.getTwoRandomNumberReal();

			//���� �н��� �� �����´�.
			int [] trialPath2 = Arrays.copyOf(trialPath, bestPath.length);

			//�ѹ� �� �ٲ㺻��
			trialPath2 = insert(trialPath2, rand[0], rand[1]);

			//���� �н��� ������ ����Ѵ�.
			int trialScore2 = PathCheck.getPathCost(trialPath2);

			//���� ��κ��� ���� ��ΰ� �� ª�ٸ�
			//�����θ� ���� ���� ��η� �����صд�.
			if(trialScore < bestScore){
				if(trialScore2 < bestScore){
					bestPath = Arrays.copyOf(trialPath2, trialPath2.length);
					bestScore = trialScore2;
				}else {
					bestPath = Arrays.copyOf(trialPath, trialPath.length);
					bestScore = trialScore;
				}
			} else if(trialScore2 < bestScore){
				bestPath = Arrays.copyOf(trialPath2, trialPath2.length);
				bestScore = trialScore2;
			}



			trial++;
		}
		return bestPath;
	}

	public int[] insert(int[] arr, int firstPoint, int secondPoint){
		if(firstPoint < secondPoint){
			int temp = arr[firstPoint];
			for(int i=firstPoint; i<secondPoint;i++){
				arr[i] = arr[i+1];
			}
			arr[secondPoint] = temp;
		}else if(firstPoint > secondPoint){
			int temp = arr[firstPoint];
			for(int i = firstPoint; secondPoint < i; i -- ) {
				arr[i] = arr[i - 1];
			}
			arr[secondPoint] = temp;
		}
		return arr;
	}

}
