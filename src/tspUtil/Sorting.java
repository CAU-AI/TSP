package tspUtil;

import java.util.Arrays;

public class Sorting {

	//��ǲ���� ���� arr �� int ������ �����϶�
	//���� ������ �����ϰ�
	//���� ���� ���� �ε����� [0]�� �����Ѵ�.
	//�� �� ū ������� ���� �迭������ �����Ѵ�.
	public static int[] getIndexOfSortedArray(int [] arr){

		//������ �迭�� �����
		int [] retArr = new int[arr.length];

		//�湮�ߴ��� �˻��� �迭�� �����
		//���߿� ������ �� �� ��ǲ���� ���� arr�� �湮�ߴ����� �ǹ��Ѵ�.
		boolean [] visited = new boolean[arr.length];

		//���õ� �迭�� �����.
		//��ǲ���� ���� �迭�� �����Ѵ�.
		int [] sortedArr = Arrays.copyOf(arr, arr.length);

		//������ �迭�� �����Ѵ�.
		Arrays.sort(sortedArr);

		//2�� �迭�� ����.
		for(int i = 0; i < retArr.length; i++){
			for(int j = 0; j < arr.length; j++){
				//
				if(arr[j] == sortedArr[i] && visited[j] != true) {
					retArr[i] = j;
					visited[j] = true;
					break;
				}
			}
		}
		return retArr;
	}
}
