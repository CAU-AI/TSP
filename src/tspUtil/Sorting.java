package tspUtil;

import java.util.Arrays;

public class Sorting {

	//인풋으로 들어온 arr 의 int 순서가 엉망일때
	//작은 순으로 정렬하고
	//가장 작은 값의 인덱스를 [0]에 저장한다.
	//그 후 큰 순서대로 다음 배열공간에 저장한다.
	public static int[] getIndexOfSortedArray(int [] arr){

		//리턴할 배열을 만든다
		int [] retArr = new int[arr.length];

		//방문했는지 검사할 배열을 만든다
		//나중에 포문을 돌 때 인풋으로 들어온 arr을 방문했는지를 의미한다.
		boolean [] visited = new boolean[arr.length];

		//소팅된 배열을 만든다.
		//인풋으로 들어온 배열을 복사한다.
		int [] sortedArr = Arrays.copyOf(arr, arr.length);

		//복사한 배열을 소팅한다.
		Arrays.sort(sortedArr);

		//2차 배열을 돈다.
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
