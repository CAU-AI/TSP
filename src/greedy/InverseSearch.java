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

		//초기 패스를 복사한다.
		int [] bestPath = Arrays.copyOf(path, path.length);

		//시도값은 0
		int trial = 0;

		//시도할 맥스는 limitTrial
		while(trial < this.limitTrial){
			//두 랜덤 넘버를 가져온다
			int[] twoRandArr = GetRandomNumber.getTwoRandomNumberReal();
			int firstPoint = twoRandArr[0];
			int secondPoint = twoRandArr[1];

			//시험 패스를 가져온다.
			int [] trialPath = Arrays.copyOf(bestPath, bestPath.length);

			inverse(trialPath, firstPoint, secondPoint);
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
