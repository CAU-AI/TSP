package greedy;

import java.util.Arrays;

import tspUtil.GetTwoRandomNumber;
import tspUtil.PathCheck;
import tspUtil.TSPAlgorithm;

public class TwoOptSearch extends TSPAlgorithm{
	public static final int METHOD_INSERT = 1;
	public static final int METHOD_INVERSE = 2;
	public static final int METHOD_SWAP = 3;

	protected int limitTrial;
	
	public TwoOptSearch(){
		this.limitTrial = 100000;
	}
	
	public TwoOptSearch(int limitTrial){
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
			int[] twoRandArr = GetTwoRandomNumber.getTwoRandomNumberReal();
			int firstPoint = twoRandArr[0];
			int secondPoint = twoRandArr[1];

			//���� �н��� �����´�.
			int [] trialPath = Arrays.copyOf(bestPath, bestPath.length);

			if(searchMethod == METHOD_INSERT) {
				insert(trialPath, firstPoint, secondPoint);
			}else if(searchMethod ==METHOD_INVERSE){
				inverse(trialPath, firstPoint, secondPoint);
			}else{
				swap(trialPath, firstPoint, secondPoint);
			}
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

	public int[] swapPath(int [] arr, int firstPoint, int secondPoint){
		if(firstPoint < 1 || secondPoint > arr.length-1){
			System.err.println("2opt.. index error in swapPath func");
			System.exit(1);
		}
		for(int i = 0; i < (secondPoint - firstPoint)/2; i++){
			int temp = arr[secondPoint - i];
			arr[secondPoint - i] = arr[firstPoint + i];
			arr[firstPoint + i] = temp;
		}
		return arr;
	}

	public int[] insert(int[] arr, int firstPoint, int secondPoint){
		if(firstPoint<secondPoint){
			int temp = arr[firstPoint];
			for(int i=firstPoint; i<secondPoint-1;i++){
				arr[i] = arr[i+1];
			}
			arr[secondPoint] = temp;
		}else{
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
}
