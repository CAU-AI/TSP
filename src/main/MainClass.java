package main;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import greedy.NearestNeighbor;
import sa.BestIndexSearch;
import sa.SASearch;
import tspUtil.MapInfo;
import tspUtil.PathCheck;

public class MainClass {
	static int trialCost = 100000000;
	static int bestCost = 10000000;
	static int[] trialPath, minPath;
	static double[] temperatureTrial = {10, 20, 30, 50, 100, 1000};
	static Date beginDate;


	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		int mapType = 0, openType = 0;
		System.out.print("Open type (1. New File, 2. Load Template) : ");
		openType = scan.nextInt();

		String fileName = null;
		if (openType == MapInfo.OPEN_TYPE_NEW_FILE) {
			System.out.print("Enter Map Type (1. Square, 2. Triangle, 3. TestDemo) : ");
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

		// 2. 타임 스레드생성
		makeTimeThread(0);



		// 3. SASearch 무한 반복
		for(int i = 0 ; i < 100; i ++) {
			int startIndex =0;
			if(i==0)
				startIndex = BestIndexSearch.makeBestIndex();
			else
				startIndex = (int) (Math.random() * MapInfo.dimension - 1);

			// 2. SASearch 오브젝트 생성
			double deltaTemperature = MapInfo.dimension >800 ? 0.7f : 0.8f;
			int limitTrial = 15000;
			limitTrial *= 1083/MapInfo.dimension;
			SASearch saSearch = new SASearch(30, deltaTemperature, limitTrial, 0);

			int[] path3 = saSearch.calculatePath(startIndex); //two-opt greedy path 생성
			trialPath = Arrays.copyOf(path3, path3.length);
			trialCost = PathCheck.getPathCost(path3);
			// 3. SA서치 수행
			playSASearch(saSearch, path3);
		}

		for(int i = 0 ; i < 50; i ++) {
			int startIndex = (int) (Math.random() * MapInfo.dimension - 1);

			// 2. SASearch 오브젝트 생성
			int limitTrial = 15000;
			limitTrial *= 1083/MapInfo.dimension;
			SASearch saSearch = new SASearch(30, 0.9f, limitTrial, 0);

			int[] path3 = saSearch.calculatePath(startIndex); //two-opt greedy path 생성
			trialPath = Arrays.copyOf(path3, path3.length);
			trialCost = PathCheck.getPathCost(path3);
			// 3. SA서치 수행
			playSASearch(saSearch, path3);
		}

	}

	public static void playSASearch(SASearch saSearch, int[] path3){

		saSearch.setTemperature(temperatureTrial[2]);
		path3 = saSearch.calculatePath(trialPath);
		int currCost = PathCheck.getPathCost(path3);
		if (currCost < trialCost) {
			trialCost = currCost;
			trialPath = path3;
		}

		//pathCheck(path3);

		// 신기록 경신시
		if(trialCost < bestCost){
			bestCost = trialCost;
			minPath = trialPath;
		}

		System.out.println("");
		System.out.println("Trial search cost : " + trialCost);
		System.out.println("Best Cost : " + bestCost);


		Date endDate = new Date();
		long diff = endDate.getTime() - beginDate.getTime();

		long milsec = diff % 1000;
		long sec = diff / 1000;

		System.out.println("Experiment End : " + sec + "." + milsec + "s \n");
	}

	public static void makeTimeThread(int minIndex){

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					beginDate = new Date();
					Thread.sleep(30000); //여기를 조절해주세요

					System.out.println("Final Best Cost : " + bestCost);

					Date endDate = new Date();
					long diff = endDate.getTime() - beginDate.getTime();

					long milsec = diff % 1000;
					long sec = diff / 1000;

					System.out.println("Experiment End : " + sec + "." + milsec + "s");


					// 5. 결과 파일 생성
					MapInfo.makeResultFile(bestCost, minPath);

					System.exit(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}
}
