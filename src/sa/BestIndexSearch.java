package sa;

import greedy.NearestNeighbor;
import tspUtil.MapInfo;
import tspUtil.PathCheck;

public class BestIndexSearch {
	public static int makeBestIndex(){
		int num = MapInfo.getInstance().getNumOfCity();
		boolean[] bNum = new boolean[num];
		int bestScore = 1000000000;
		int bestIndex = -1;
		int count = (int)((float)10000/(float)num);
		if(count > num)
			count = num-1;
		for(int i = 0 ; i < count ; i ++){
			int rand = (int)(Math.random() * num);
			if(bNum[rand]){
				i -- ;
			}else {
				bNum[rand] = true;
				NearestNeighbor simpleGreedy = new NearestNeighbor();
				int[] path = simpleGreedy.calculatePath(rand);
				int score = PathCheck.getPathCost(path);
				if (bestScore > score) {
					bestScore = score;
					bestIndex = rand;
				}
			}
		}
		if(bestIndex < 0)
			bestIndex =0;
		return bestIndex;
	}
}
