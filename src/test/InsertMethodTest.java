package test;

import greedy.InsertSearch;
import org.junit.Test;
import sa.SASearch;
import tspUtil.GetRandomNumber;
import tspUtil.MapInfo;
import tspUtil.PathCheck;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class InsertMethodTest {
	static int minCost = 100000000;
	@Test
	public void test(){
		String fileName = ".\\map\\xqf131\\Sample_xqf131.txt";
		// 1. �� �ν��Ͻ� ����
		MapInfo.setMapInfoInstance(fileName, MapInfo.MAP_TYPE_SQUARE);

		SASearch saSearch = makeSASearch(50, 0.8, 100000, 3);
		InsertSearch insertSearch = new InsertSearch(10000);

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


		int equalCount = 0;
		for(int i=0;i<bestPath.length;i++){
			for(int j=0;j<bestPath.length;j++){
				if(i!=j) {
					if(bestPath[i] == bestPath[j]){
						equalCount++;
					}
				}
			}
		}
		assertThat(equalCount, is(2));

		//�õ��� �ƽ��� limitTrial
		while(trial < 100000){
			//�� ���� �ѹ��� �����´�
			int[] twoRandArr = GetRandomNumber.getTwoRandomNumberReal();
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

		equalCount = 0;
		for(int i=0;i<bestPath.length;i++){
			for(int j=0;j<bestPath.length;j++){
				if(i!=j) {
					if (bestPath[i] == bestPath[j]) {
						equalCount++;
					}
				}
			}
		}
		assertThat(equalCount, is(2));
	}

	public static SASearch makeSASearch( double temperatureTrial, double deltaTemperature, int limitTrial, int numOfNextHop){
		SASearch saSearch = new SASearch(temperatureTrial, deltaTemperature, limitTrial, 3);
		System.out.println("SA search: " + minCost);
		return saSearch;
	}
}
