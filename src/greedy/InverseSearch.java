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
			int[] rand = GetRandomNumber.getTwoRandomNumberReal();

			//시험 패스를 가져온다.
			int [] trialPath = Arrays.copyOf(bestPath, bestPath.length);

			//바꿔본다
			trialPath = inverse(trialPath, rand[0], rand[1]);

			PathCheck.checkChangeNearNodesLocal(trialPath, rand[0]);
			PathCheck.checkChangeNearNodesLocal(trialPath, rand[1]);
			PathCheck.checkChangeNearNodes(trialPath, rand[0]);
			PathCheck.checkChangeNearNodes(trialPath, rand[1]);

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
