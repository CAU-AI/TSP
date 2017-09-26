package greedy;

import java.util.Arrays;

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
		return path;
	}

	@Override
	public int[] calculatePath(int[] path) {
		// TODO Auto-generated method stub
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
}
