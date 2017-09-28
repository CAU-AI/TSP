package tspUtil;

import java.util.Arrays;

public class PathCheck {
	/*
	 * 모든 경로의 합 계산
	 * 경로 형식은 만약 5개의 도시면
	 * int형의 배열에
	 * 0 1 2 3 4 0
	 * 또는
	 * 0 2 4 1 3 0
	 * 식의 형태를 띄어야함(출발지와 도착지가 같아야하며 모든 도시의 index가 한번씩 있어야함)
	 */
	public static int getPathCost(int [] path){
		
		int totalCost = 0;
		int [][] map = MapInfo.getInstance().getDistanceMap();
		
		for(int i = 0; i < path.length - 1;i++){
			totalCost += map[path[i]][path[i+1]];
		}

		if(path[0] != path[path.length -1])
			totalCost += map[path[path.length - 1]][path[0]];
		return totalCost;
	}
	/*
	 * 경로가 적합한 경로인지 확인
	 * 경로 형식은 만약 5개의 도시면
	 * int형의 배열에
	 * 0 1 2 3 4 0
	 * 또는
	 * 0 2 4 1 3 0
	 * 식의 형태를 띄어야함(출발지와 도착지가 같아야하며 모든 도시의 index가 한번씩 있어야함)
	 */
	public static boolean isPathDuplicated(int [] path){
		boolean [] visited = new boolean[MapInfo.getInstance().getNumOfCity()];
		
		Arrays.fill(visited, false);
		
		//출발지와 도착지가 같은지 확인
		if(path[0] != path[path.length-1]) return true;
		
		for(int i = 0; i < path.length-1;i++){
			if(visited[i]) return true;
			else visited[i] = true;
		}
		return false;
	}
	
	public static void printPath(int [] path){
		for(int i = 0; i < path.length; i++){
			System.out.print(path[i]+ " ");
		}
		System.out.println();
	}

	public static void checkChangeNearNodes(int[] path, int i){
		int [][] map = MapInfo.getInstance().getDistanceMap();

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

		if(i < path.length - 4){


			/*
			int[] n = new int[6];

			int cnt = 0;
			for(int o0 = 1 ; o0 <= 3 ; o0 ++){
				for(int o1 = o0+1 ; o1 <= 3 ; o1 ++){
					for(int o2 = o1+1 ; o2 <= 3 ; o2 ++){
						n[] = map[path[i+4]][path[i+o2]] + map[path[i+o2]][path[i+2]] + map[path[i+2]][path[i+1]] + map[path[i+1]][path[i]];
					}
				}
			}
			*/


			int origin = map[path[i+4]][path[i+3]] + map[path[i+3]][path[i+2]] + map[path[i+2]][path[i+1]] + map[path[i+1]][path[i]];
			int n0 = map[path[i+4]][path[i+3]] + map[path[i+3]][path[i+1]] + map[path[i+1]][path[i+2]] + map[path[i+2]][path[i]];
			int n1 = map[path[i+4]][path[i+2]] + map[path[i+2]][path[i+3]] + map[path[i+3]][path[i+1]] + map[path[i+1]][path[i]];
			int n2 = map[path[i+4]][path[i+2]] + map[path[i+2]][path[i+1]] + map[path[i+1]][path[i+3]] + map[path[i+3]][path[i]];
			int n3 = map[path[i+4]][path[i+1]] + map[path[i+1]][path[i+3]] + map[path[i+3]][path[i+2]] + map[path[i+2]][path[i]];
			int n4 = map[path[i+4]][path[i+1]] + map[path[i+1]][path[i+2]] + map[path[i+2]][path[i+3]] + map[path[i+3]][path[i]];

			int min = min(origin, min(n0, min(n1, min(n2, min(n3, n4)))));
			if(min == n0){
				int temp = path[i+2];
				path[i+2] = path[i+1];
				path[i+1] = temp;
			} else if(min == n1){
				int temp = path[i+2];
				path[i+2] = path[i+3];
				path[i+3] = temp;
			} else if(min == n2){
				int temp = path[i+3];
				path[i+3] = path[i+2];
				path[i+2] = path[i+1];
				path[i+1] = temp;
			} else if(min == n3){
				int temp = path[i+3];
				path[i+3] = path[i+1];
				path[i+1] = path[i+2];
				path[i+2] = temp;
			} else if(min == n4){
				int temp = path[i+3];
				path[i+3] = path[i+1];
				path[i+1] = temp;
			}
		}
	}

	public static void checkChangeNearNodesLocal(int[] path, int index){
		int [][] map = MapInfo.getInstance().getDistanceMap();

		if(2 < index && index < path.length - 2){
			int i = index - 2;
			int origin = map[path[i+4]][path[i+3]] + map[path[i+3]][path[i+2]] + map[path[i+2]][path[i+1]] + map[path[i+1]][path[i]];
			int n0 = map[path[i+4]][path[i+3]] + map[path[i+3]][path[i+1]] + map[path[i+1]][path[i+2]] + map[path[i+2]][path[i]];
			int n1 = map[path[i+4]][path[i+2]] + map[path[i+2]][path[i+3]] + map[path[i+3]][path[i+1]] + map[path[i+1]][path[i]];
			int n2 = map[path[i+4]][path[i+2]] + map[path[i+2]][path[i+1]] + map[path[i+1]][path[i+3]] + map[path[i+3]][path[i]];
			int n3 = map[path[i+4]][path[i+1]] + map[path[i+1]][path[i+3]] + map[path[i+3]][path[i+2]] + map[path[i+2]][path[i]];
			int n4 = map[path[i+4]][path[i+1]] + map[path[i+1]][path[i+2]] + map[path[i+2]][path[i+3]] + map[path[i+3]][path[i]];

			int min = min(origin, min(n0, min(n1, min(n2, min(n3, n4)))));
			if(min == n0){
				int temp = path[i+2];
				path[i+2] = path[i+1];
				path[i+1] = temp;
			} else if(min == n1){
				int temp = path[i+2];
				path[i+2] = path[i+3];
				path[i+3] = temp;
			} else if(min == n2){
				int temp = path[i+3];
				path[i+3] = path[i+2];
				path[i+2] = path[i+1];
				path[i+1] = temp;
			} else if(min == n3){
				int temp = path[i+3];
				path[i+3] = path[i+1];
				path[i+1] = path[i+2];
				path[i+2] = temp;
			} else if(min == n4){
				int temp = path[i+3];
				path[i+3] = path[i+1];
				path[i+1] = temp;
			}

		}
	}


	private static int min(int a, int b){
		return a < b? a : b;
	}
}
