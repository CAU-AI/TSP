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

		//초기 패스를 복사한다.
		int [] bestPath = Arrays.copyOf(path, path.length);

		//시도값은 0
		int trial = 0;

		//시도할 맥스는 limitTrial
		while(trial < this.limitTrial){

			//두 랜덤 넘버를 가져온다
			int[] rand = GetRandomNumber.getTwoRandomNumberReal();

			//시험 패스를 가져온다.
			int [] trialPath = Arrays.copyOf(bestPath, bestPath.length);

			//바꿔본다
			trialPath = insert(trialPath, rand[0], rand[1]);

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
