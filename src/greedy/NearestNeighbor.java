package greedy;

import java.util.Arrays;

import tspUtil.PathCheck;
import tspUtil.Sorting;
import tspUtil.TSPAlgorithm;

public class NearestNeighbor extends TSPAlgorithm {

	public NearestNeighbor() {
		super();
	}

	/*
	 * 시작점으로부터 시작해서 방문되지 않은 가장 가까운 노드로 방문
	 */
	@Override
	public int[] calculatePath(int startPoint) {
		// TODO Auto-generated method stub
		int[] path = new int[this.numOfCity + 1];
		path[0] = startPoint;
		path[this.numOfCity] = startPoint;

		path = this.calculatePath(path);


		int[] path_new = new int[this.numOfCity + 1];

		path_new[0] = startPoint;
		path_new[this.numOfCity] = startPoint;

		path_new = this.calculatePath_new(path_new);
		//System.out.println("origin : " + PathCheck.getPathCost(path) + ", new : " + PathCheck.getPathCost(path_new));

		return path;
	}

	@Override
	public int[] calculatePath(int[] path) {
		boolean[] visited = new boolean[this.numOfCity];
		Arrays.fill(visited, false);

		//이부분이 잘못 되었던듯
		visited[path[0]] = true;

		for (int i = 0; i < this.numOfCity; i++) {

			//인풋으로 들어온 arr 의 int 순서가 엉망일때
			//작은 순으로 정렬하고
			//가장 작은 값의 인덱스를 [0]에 저장한다.
			//그 후 큰 순서대로 다음 배열공간에 저장한다.
			//
			//각 배열의 인덱스는 가장 작은 값을 저장한 순서를 의미하고
			//각 배열의 값은 가장 작은 값의 인덱스를 말한다.
			//
			//시작번호가 121 이라면 121에서부터 다른 모든 점들사이 거리 map 배열을 가져오고
			//가장 가까이 있는 점의 인덱스가 [0]번의 값으로 들어있다
			int[] indexOfSortedArr = Sorting.getIndexOfSortedArray(this.map[path[i]]);

			for (int j = 0; j < this.numOfCity; j++) {
				//가장 패스가 짧은 것부터 찾아보는데
				//그 패스를 방문 했는지 검사한다
				//
				//방문하지 않았다면
				if (!visited[ indexOfSortedArr[j] ]) {
					path[i + 1] = indexOfSortedArr[j];
					visited[indexOfSortedArr[j]] = true;
					break;
				}
			}
		}
		return path;
	}

	public int[] calculatePath_new(int[] path) {
		// TODO Auto-generated method stub
		boolean[] visited = new boolean[this.numOfCity + 1];
		Arrays.fill(visited, false);

		//이부분이 잘못 되었던듯
		visited[path[0]] = true;

		for (int i = 0; i < this.numOfCity; i++) {

			int[] indexOfSortedArr = Sorting.getIndexOfSortedArray(this.map[path[i]]);

			boolean find = false;

			int p1 = -1;

			for (p1 = 0; p1 < this.numOfCity; p1++) {
				if (!visited[indexOfSortedArr[p1]]) {
					path[i + 1] = indexOfSortedArr[p1];
					visited[indexOfSortedArr[p1]] = true;
					break;
				}
			}

				//i 는 3부터!!!
			if(4 < i){
				int origin = map[path[i-4]][path[i-3]] + map[path[i-3]][path[i-2]] + map[path[i-2]][path[i-1]] + map[path[i-1]][path[i]];
				int n0 = map[path[i-4]][path[i-3]] + map[path[i-3]][path[i-1]] + map[path[i-1]][path[i-2]] + map[path[i-2]][path[i]];
				int n1 = map[path[i-4]][path[i-2]] + map[path[i-2]][path[i-3]] + map[path[i-3]][path[i-1]] + map[path[i-1]][path[i]];
				int n2 = map[path[i-4]][path[i-2]] + map[path[i-2]][path[i-1]] + map[path[i-1]][path[i-3]] + map[path[i-3]][path[i]];
				int n3 = map[path[i-4]][path[i-1]] + map[path[i-1]][path[i-3]] + map[path[i-3]][path[i-2]] + map[path[i-2]][path[i]];
				int n4 = map[path[i-4]][path[i-1]] + map[path[i-1]][path[i-2]] + map[path[i-2]][path[i-3]] + map[path[i-3]][path[i]];

				int min = min(origin, min(n0, min(n1, min(n2, min(n3, n4)))));
				if(min == n0){
					int temp = path[i-2];
					path[i-2] = path[i-1];
					path[i-1] = temp;
				} else if(min == n1){
					int temp = path[i-2];
					path[i-2] = path[i-3];
					path[i-3] = temp;
				} else if(min == n2){
					int temp = path[i-3];
					path[i-3] = path[i-2];
					path[i-2] = path[i-1];
					path[i-1] = temp;
				} else if(min == n3){
					int temp = path[i-3];
					path[i-3] = path[i-1];
					path[i-1] = path[i-2];
					path[i-2] = temp;
				} else if(min == n4){
					int temp = path[i-3];
					path[i-3] = path[i-1];
					path[i-1] = temp;
				}
			}

		}
		return path;
	}

	int min(int a, int b){
		return a < b? a : b;
	}
}
