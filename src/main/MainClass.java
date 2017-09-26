package main;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

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

		int minIndex = 121;

		// 1. 맵 인스턴스 생성
		MapInfo.setMapInfoInstance(fileName, mapType);

		makeTimeThread(minIndex);

		// 2. SASearch 오브젝트 생성
		SASearch saSearch = makeSASearch(temperatureTrial[3], 0.8, 100000, 3);

		int[] path3 = saSearch.calculatePath(minIndex);
		minCost = PathCheck.getPathCost(path3);
		minPath = Arrays.copyOf(path3, path3.length);

		// 3. SA서치 수행
		playSASearchLoop(saSearch, path3);


		//		case 4:
		//			int populationSize = 100;
		//			int generationSize = 10000;
		//
		//			//Initialize by SA
		//			Initializer saInitializer = new SAInitalizer(30, 0.8, 10000, 5);
		//			//Random Initialize
		//			Initializer randInitializer = new RandomInitializer();
		//
		//			Selection ptSelection = new PseudoTournamentSelection(populationSize, 10);
		//
		//			Mutation swapMutation = new SwapMutation(0.3);
		//			//Mutation nscMutation = new NSCMutation(0.3, 4);
		//
		//
		//			Crossover pmxCrossover = new PMXCrossover();
		//
		//			MyGASearch myGASearch = new MyGASearch(populationSize , generationSize);
		//
		//			myGASearch.setProcess(saInitializer, pmxCrossover, ptSelection, swapMutation);
		//			//myGASearch.setProcess(randInitializer, pmxCrossover, ptSelection, swapMutation);
		//
		//			int [] path4 = myGASearch.calculatePath(0);
		//			for(int i = 0; i< myGASearch.generationScore.length;i++){
		//				System.out.println(myGASearch.generationScore[i]);
		//			}
		//
		//			System.out.println("GA: " + PathCheck.getPathCost(path4));
		//			break;
		//		}


	}

	public static void playSASearchLoop(SASearch saSearch, int[] path3){
		for(int i = 0 ; i < 100 ; i ++) {
			float delta = (20 - i) * 0.03f;
			if (delta < 0.4)
				delta = 0.4f;
			saSearch.setTemperature(temperatureTrial[2]);
			path3 = saSearch.calculatePath(minPath);
			int currCost = PathCheck.getPathCost(path3);
			if (currCost < minCost) {
				minCost = currCost;
				minPath = path3;
			}
			System.out.println("SA search: " + delta + ", cost : " + minCost);
		}
		Date endDate = new Date();
		long diff = endDate.getTime() - beginDate.getTime();

		long milsec = diff % 1000;
		long sec = diff / 1000;

		System.out.println("Experiment End : " + sec + "." + milsec + "s");
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

		System.out.println("Start point : " + minIndex);
	}
}
