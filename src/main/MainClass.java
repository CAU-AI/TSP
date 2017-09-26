package main;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import greedy.NearestNeighbor;
import sa.SASearch;
import tspUtil.MapInfo;
import tspUtil.PathCheck;

public class MainClass {
	static int minCost = 100000000;
	static int[] minPath;
	static double[] temperatureTrial = {10, 20, 30, 50, 100, 1000};
	static Date beginDate;


	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		int mapType = 0, openType = 0;
		System.out.print("Open type (1. New File, 2. Load Template) : ");
		openType = scan.nextInt();

		String fileName = null;
		if (openType == MapInfo.OPEN_TYPE_NEW_FILE) {
			System.out.print("Enter Map Type (1. Square, 2. Triangle) : ");
			mapType = scan.nextInt();
			System.out.print("Enter Map name (Usually Sample.txt): ");
			fileName = scan.next();
		} else {
			System.out.print("Choose Template (");
			File file = new File("./map");
			for (int i = 0; i < file.list().length; i++) {
				System.out.print(i + 1 + "." + file.list()[i]);
				if (i < file.list().length - 1)
					System.out.print(", ");
			}
			System.out.print("): ");
			String mapName = file.list()[scan.nextInt() - 1];
			fileName = ".\\map\\" + mapName + "\\Sample_" + mapName + ".txt";
			mapType = MapInfo.MAP_TYPE_SQUARE;
		}


		// 1. 맵 인스턴스 생성
		MapInfo.setMapInfoInstance(fileName, mapType);

		int num = MapInfo.getInstance().getNumOfCity();
		int bestScore = 1000000000;
		int bestIndex = -1;
		for(int i = 0 ; i < num ; i ++){
			NearestNeighbor simpleGreedy = new NearestNeighbor();
			int [] path = simpleGreedy.calculatePath(i);
			int score = PathCheck.getPathCost(path);
			if(bestScore > score){
				bestScore = score;
				bestIndex = i;
			}
		}

		System.out.println("Best point : " + bestIndex);


		makeTimeThread(0);

		for(int i = 0 ; i < 100; i ++) {
			int startIndex = (int)((Math.random() * 100000) % 380);

			System.out.println("Start point : " + startIndex);

			// 2. SASearch 오브젝트 생성
			SASearch saSearch = makeSASearch(temperatureTrial[2], 0.8, 100000, 3);

			int[] path3 = saSearch.calculatePath(startIndex);

			minCost = PathCheck.getPathCost(path3);
			minPath = Arrays.copyOf(path3, path3.length);

			// 3. SA서치 수행
			playSASearchLoop(saSearch, path3);

		}
	}

	public static void playSASearchLoop(SASearch saSearch, int[] path3){

		saSearch.setTemperature(temperatureTrial[2]);
		path3 = saSearch.calculatePath(minPath);
		int currCost = PathCheck.getPathCost(path3);
		if (currCost < minCost) {
			minCost = currCost;
			minPath = path3;
		}

		pathCheck(path3);

		System.out.println("");
		System.out.println("SA search cost : " + minCost);



		Date endDate = new Date();
		long diff = endDate.getTime() - beginDate.getTime();

		long milsec = diff % 1000;
		long sec = diff / 1000;

		System.out.println("Experiment End : " + sec + "." + milsec + "s");
	}

	public static void pathCheck(int[] path3){
		boolean[] pp = new boolean[path3.length];

		System.out.println("");
		System.out.println("///////////////////////////////////////////////////////////");
		for(int index = 0 ; index < path3.length ; index ++) {
			if(pp[path3[index]]){
				System.out.println("여기 두개 : " + path3[index]);
			}
			pp[path3[index]] = true;
		}

		for(int index = 0 ; index < path3.length; index ++) {
			if(!pp[index]){
				System.out.println("없는 번호 : " + index);
			}
		}

		System.out.print(path3[0]);
		for(int index = 1 ; index < path3.length ; index ++) {
			if(index % 20 == 0)
				System.out.print("\n");
			else
				System.out.print(", ");
			System.out.print(path3[index]);
		}
		System.out.print("\n");
		System.out.println("///////////////////////////////////////////////////////////");
	}

	public static SASearch makeSASearch( double temperatureTrial, double deltaTemperature, int limitTrial, int numOfNextHop){
		SASearch saSearch = new SASearch(temperatureTrial, 0.8, 100000, 3);
		System.out.println("SA search: " + minCost);
		return saSearch;
	}

	public static void makeTimeThread(int minIndex){

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					beginDate = new Date();
					Thread.sleep(20000);
					System.out.println("Result : " + minCost);

					Date endDate = new Date();
					long diff = endDate.getTime() - beginDate.getTime();

					long milsec = diff % 1000;
					long sec = diff / 1000;

					System.out.println("Experiment End : " + sec + "." + milsec + "s");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}
}
