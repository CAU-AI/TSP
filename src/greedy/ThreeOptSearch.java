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
			int[] threeRandomArr = GetRandomNumber.getThreeRandomNumberReal();
			int firstPoint = threeRandomArr[0];
			int secondPoint = threeRandomArr[1];
			int thirdPoint = threeRandomArr[2];

			//시험 패스를 가져온다.
			int [] trialPath = Arrays.copyOf(bestPath, bestPath.length);

			swapPath(trialPath, firstPoint, secondPoint, thirdPoint);
			//두 랜덤 넘버에 해당하는 패스 순서를 바꾼다

			//시험 패스의 점수를 계산한다.
			int trialScore = PathCheck.getPathCost(trialPath);

			//이전 경로보다 현재 경로가 더 짧다면
			//현재경로를 가장 좋은 경로로 저장해둔다.
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
