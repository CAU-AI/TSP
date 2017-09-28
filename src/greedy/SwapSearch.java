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
			trialPath = swap(trialPath, rand[0], rand[1]);

			//시험 패스의 점수를 계산한다.
			int trialScore = PathCheck.getPathCost(trialPath);

			//랜덤넘버를 한번 더 뽑는다
			rand = GetRandomNumber.getTwoRandomNumberReal();

			//시험 패스를 또 가져온다.
			int [] trialPath2 = Arrays.copyOf(trialPath, bestPath.length);

			//한번 더 바꿔본다
			trialPath2 = swap(trialPath2, rand[0], rand[1]);

			//시험 패스의 점수를 계산한다.
			int trialScore2 = PathCheck.getPathCost(trialPath2);

			//이전 경로보다 현재 경로가 더 짧다면
			//현재경로를 가장 좋은 경로로 저장해둔다.
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
