package greedy;

import java.util.Arrays;

import tspUtil.GetTwoRandomNumber;
import tspUtil.PathCheck;
import tspUtil.TSPAlgorithm;

public class TwoOptSearch extends TSPAlgorithm{

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


	//두 랜덤넘버를 뽑아서
	//두 랜덤넘버 사이의 숫자들을 순차적으로 순서 변경하는 함수를
	//limitTrial 번 시도한다.
	@Override
	public int[] calculatePath(int [] path) {
		// TODO Auto-generated method stub
		int bestScore = PathCheck.getPathCost(path);

		//초기 패스를 복사한다.
		int [] bestPath = Arrays.copyOf(path, path.length);

		//시도값은 0
		int trial = 0;

		//시도할 맥스는 limitTrial
		while(trial < this.limitTrial){

			//두 랜덤 넘버를 가져온다
			int[] twoRandArr = GetTwoRandomNumber.getTwoRandomNumberReal();
			int firstPoint = twoRandArr[0];
			int secondPoint = twoRandArr[1];

			//시험 패스를 가져온다.
			int [] trialPath = Arrays.copyOf(bestPath, bestPath.length);

			//두 랜덤 넘버에 해당하는 패스 순서를 바꾼다
			swapPathReal(trialPath, firstPoint, secondPoint);
			//swap(trialPath, firstPoint, secondPoint);

			//시험 패스의 점수를 계산한다.
			int trialScroe = PathCheck.getPathCost(trialPath);

			//이전 경로보다 현재 경로가 더 짧다면
			//현재경로를 가장 좋은 경로로 저장해둔다.
			if(trialScroe < bestScore){
				bestPath = Arrays.copyOf(trialPath, trialPath.length);
				bestScore = trialScroe;

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

	public int[] swapPathReal(int [] arr, int firstPoint, int secondPoint){
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
