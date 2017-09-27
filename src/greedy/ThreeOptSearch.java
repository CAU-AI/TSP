package greedy;

import tspUtil.GetRandomNumber;
import tspUtil.PathCheck;
import tspUtil.TSPAlgorithm;

import java.util.Arrays;

public class ThreeOptSearch extends TSPAlgorithm{

	protected int limitTrial;

	public ThreeOptSearch(){
		this.limitTrial = 10000;
	}

	public ThreeOptSearch(int limitTrial){
		this.setTwoOptSearchParameter(limitTrial);
	}
	
	public void setTwoOptSearchParameter(int limitTrial){
		this.limitTrial = limitTrial;
	}


	//�� �����ѹ��� �̾Ƽ�
	//�� �����ѹ� ������ ���ڵ��� ���������� ���� �����ϴ� �Լ���
	//limitTrial �� �õ��Ѵ�.
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
			int[] threeRandomArr = GetRandomNumber.getThreeRandomNumberReal();
			int firstPoint = threeRandomArr[0];
			int secondPoint = threeRandomArr[1];
			int thirdPoint = threeRandomArr[2];

			//���� �н��� �����´�.
			int [] trialPath = Arrays.copyOf(bestPath, bestPath.length);

			swapPath(trialPath, firstPoint, secondPoint, thirdPoint);
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


	@Override
	public int[] calculatePath(int startPoint) {
		// TODO Auto-generated method stub
		NearestNeighbor simpleGreedy = new NearestNeighbor();
		int [] path = simpleGreedy.calculatePath(startPoint);
		
		path = this.calculatePath(path);
		return path;
	}

	public int[] swapPath(int [] arr, int firstPoint, int secondPoint, int thirdPoint){
		if(firstPoint < 1 || secondPoint > arr.length-1){
			System.err.println("2opt.. index error in swapPath func");
			System.exit(1);
		}

		int temp = arr[firstPoint];

		arr[firstPoint] = arr[secondPoint];
		arr[secondPoint] = arr[thirdPoint];
		arr[thirdPoint] = temp;

		return arr;
	}
}
