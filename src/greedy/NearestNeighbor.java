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
	 * ���������κ��� �����ؼ� �湮���� ���� ���� ����� ���� �湮
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

		//�̺κ��� �߸� �Ǿ�����
		visited[path[0]] = true;

		for (int i = 0; i < this.numOfCity; i++) {

			//��ǲ���� ���� arr �� int ������ �����϶�
			//���� ������ �����ϰ�
			//���� ���� ���� �ε����� [0]�� �����Ѵ�.
			//�� �� ū ������� ���� �迭������ �����Ѵ�.
			//
			//�� �迭�� �ε����� ���� ���� ���� ������ ������ �ǹ��ϰ�
			//�� �迭�� ���� ���� ���� ���� �ε����� ���Ѵ�.
			//
			//���۹�ȣ�� 121 �̶�� 121�������� �ٸ� ��� ������� �Ÿ� map �迭�� ��������
			//���� ������ �ִ� ���� �ε����� [0]���� ������ ����ִ�
			int[] indexOfSortedArr = Sorting.getIndexOfSortedArray(this.map[path[i]]);

			for (int j = 0; j < this.numOfCity; j++) {
				//���� �н��� ª�� �ͺ��� ã�ƺ��µ�
				//�� �н��� �湮 �ߴ��� �˻��Ѵ�
				//
				//�湮���� �ʾҴٸ�
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

		//�̺κ��� �߸� �Ǿ�����
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

				//i �� 3����!!!
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
