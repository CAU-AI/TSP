package main;

import java.io.File;
import java.util.Date;
import java.util.Scanner;

import ga.*;
import sa.BestIndexSearch;
import sa.SASearch;
import tspUtil.MapInfo;
import tspUtil.PathCheck;
import tspUtil.RandomPath;

public class MainClass {
	static Date beginDate;


	public static int[] minPath;
	public static int bestCost;

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


		System.out.print("Choose Search Method (1.SA , 2.GA) : ");
		int searchMethod = scan.nextInt();

		// 1. 타임 스레드생성
		makeTimeThread(0);

		// 2. 맵 인스턴스 생성
		MapInfo.setMapInfoInstance(fileName, mapType);

		System.out.println("맵 생성완료");


		switch(searchMethod){
		case 1: {
			int numOfCity = MapInfo.getInstance().getNumOfCity();

			int trial = (int)((5000000f)/(numOfCity-200));

			System.out.println("SA trial : " + trial);

			//int bIndex = BestIndexSearch.makeBestIndex();
			//System.out.println("BestIndexSearch Over");
			bestCost = Integer.MAX_VALUE;
			while (true) {
				SASearch saSearch = new SASearch(30, 0.8f, trial, 1);
				int[] path = saSearch.calculatePath(RandomPath.getRandomPath(0));
				int cost = PathCheck.getPathCost(path);
				System.out.println("SA Cost : " + cost);

				if(bestCost > cost){
					minPath = path;
					bestCost = cost;
				}
			}
		}
			case 2: {
				int populationSize = 4;
				int generationSize = 5000000;

				int numOfCity = MapInfo.getInstance().getNumOfCity();

				int trial = (int)((100000f)/(numOfCity-500));

				System.out.println("SA trial : " + trial);

				//Initialize by SA
				Initializer saInitializer = new SAInitalizer(30, 0.8, 1, 1);

				//Selection ptSelection = new PseudoTournamentSelection(populationSize, 10);
				Selection ptSelection = new RouletteSelection();


				Crossover orderedCrossover = new OrderedCrossover();
				Crossover pmxCrossover = new PMXCrossover();

				MyGASearch myGASearch = new MyGASearch(populationSize, generationSize);

				myGASearch.setProcess(saInitializer, orderedCrossover, ptSelection, MutationType.RAND);

				int bIndex = BestIndexSearch.makeBestIndex();
				System.out.println("BestIndexSearch Over");
				int[] path = myGASearch.calculatePath(bIndex);

				System.out.println("");
				System.out.println("GA: " + PathCheck.getPathCost(path));

				return;
			}
		}

	}


	public static void makeTimeThread(int minIndex){

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					beginDate = new Date();
					Thread.sleep(120000); //여기를 조절해주세요

					System.out.println("Final Best Cost : ");

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
