package greedy;

import tspUtil.GetRandomNumber;
import tspUtil.PathCheck;

import java.util.Arrays;

public class SwapSearch extends TwoOptSearch {

	public SwapSearch(int limitTrial){
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
			trialPath = swap(trialPath, rand[0], rand[1]);

			//���� �н��� ������ ����Ѵ�.
			int trialScore = PathCheck.getPathCost(trialPath);

			//�����ѹ��� �ѹ� �� �̴´�
			rand = GetRandomNumber.getTwoRandomNumberReal();

			//���� �н��� �� �����´�.
			int [] trialPath2 = Arrays.copyOf(trialPath, bestPath.length);

			//�ѹ� �� �ٲ㺻��
			trialPath2 = swap(trialPath2, rand[0], rand[1]);

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

	public int[] swapTwoPath(int [] arr, int firstPoint, int secondPoint, int thirdPoint){
		if(firstPoint < 1 || secondPoint > arr.length-1 || thirdPoint > arr.length - secondPoint + firstPoint){
			System.err.println("2opt.. index error in swapPath func");
			System.exit(1);
		}

		for(int i = 0; i < (secondPoint - firstPoint)/2; i++) {
			int temp = arr[secondPoint - i];
			arr[secondPoint - i] = arr[firstPoint + i];
			arr[firstPoint + i] = temp;
		}


		return arr;
	}

	public int[] swap(int [] arr, int firstPoint, int secondPoint){
		if(firstPoint < 1 || secondPoint > arr.length-1){
			System.err.println("2opt.. index error in swapPath func");
			System.exit(1);
		}
		int temp = arr[secondPoint];
		arr[secondPoint] = arr[firstPoint];
		arr[firstPoint] = temp;
		return arr;
	}
}
