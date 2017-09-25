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
		// 1. �� �ν��Ͻ� ����
		MapInfo.setMapInfoInstance(fileName, MapInfo.MAP_TYPE_SQUARE);

		SASearch saSearch = makeSASearch(50, 0.8, 100000, 3);
		InsertSearch insertSearch = new InsertSearch();

		int minIndex = 121;
		int[] path = saSearch.calculatePath(minIndex);
		minCost = PathCheck.getPathCost(path);
		int[] minPath = Arrays.copyOf(path, path.length);

		// TODO Auto-generated method stub
		int bestScore = PathCheck.getPathCost(path);

		//�ʱ� �н��� �����Ѵ�.
		int [] bestPath = Arrays.copyOf(path, path.length);

		//�õ����� 0
		int trial = 0;

		for(int i=0;i<bestPath.length;i++){
			for(int j=0;j<bestPath.length;i++){
				if(i!=j) {
				}
			}
		}

		//�õ��� �ƽ��� limitTrial
		while(trial < 100000){
			//�� ���� �ѹ��� �����´�
			int[] twoRandArr = GetTwoRandomNumber.getTwoRandomNumberReal();
			int firstPoint = twoRandArr[0];
			int secondPoint = twoRandArr[1];

			//���� �н��� �����´�.
			int [] trialPath = Arrays.copyOf(bestPath, bestPath.length);

			trialPath = insertSearch.insert(trialPath, firstPoint, secondPoint);
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
