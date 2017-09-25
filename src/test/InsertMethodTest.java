package test;

import greedy.InsertSearch;
import org.junit.Test;
import sa.SASearch;
import tspUtil.GetTwoRandomNumber;
import tspUtil.MapInfo;
import tspUtil.PathCheck;

import java.util.Arrays;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class InsertMethodTest {
	static int minCost = 100000000;
	@Test
	public void test(){
		String fileName = ".\\map\\bcl380\\Sample_bcl380.txt";
		// 1. 맵 인스턴스 생성
		MapInfo.setMapInfoInstance(fileName, MapInfo.MAP_TYPE_SQUARE);

		SASearch saSearch = makeSASearch(50, 0.8, 100000, 3);
		InsertSearch insertSearch = new InsertSearch();

		int minIndex = 121;
		int[] path = saSearch.calculatePath(minIndex);
		minCost = PathCheck.getPathCost(path);
		int[] minPath = Arrays.copyOf(path, path.length);

		// TODO Auto-generated method stub
		int bestScore = PathCheck.getPathCost(path);

		//초기 패스를 복사한다.
		int [] bestPath = Arrays.copyOf(path, path.length);

		//시도값은 0
		int trial = 0;

		for(int i=0;i<bestPath.length;i++){
			for(int j=0;j<bestPath.length;i++){
				if(i!=j) {
				}
			}
		}

		//시도할 맥스는 limitTrial
		while(trial < 100000){
			//두 랜덤 넘버를 가져온다
			int[] twoRandArr = GetTwoRandomNumber.getTwoRandomNumberReal();
			int firstPoint = twoRandArr[0];
			int secondPoint = twoRandArr[1];

			//시험 패스를 가져온다.
			int [] trialPath = Arrays.copyOf(bestPath, bestPath.length);

			trialPath = insertSearch.insert(trialPath, firstPoint, secondPoint);
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

		for(int i=0;i<bestPath.length;i++){
			for(int j=0;j<bestPath.length;i++){
				if(i!=j) {
					if (bestPath[i] == bestPath[j]) {
						System.out.println("wow");
					}
				}
			}
		}
	}

	public static SASearch makeSASearch( double temperatureTrial, double deltaTemperature, int limitTrial, int numOfNextHop){
		SASearch saSearch = new SASearch(temperatureTrial, deltaTemperature, limitTrial, 3);
		System.out.println("SA search: " + minCost);
		return saSearch;
	}
}
