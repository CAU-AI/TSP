package main;

import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import ga.Crossover;
import ga.Initializer;
import ga.Mutation;
import ga.MyGASearch;
import ga.NSCMutation;
import ga.PMXCrossover;
import ga.PseudoTournamentSelection;
import ga.RandomInitializer;
import ga.SAInitalizer;
import ga.Selection;
import ga.SwapMutation;
import greedy.NearestNeighbor;
import greedy.TwoOptSearch;
import sa.SASearch;
import tspUtil.MapInfo;
import tspUtil.PathCheck;

public class MainClass {

	static int minCost = 100000000;
	static int[] minPath;

	static Date beginDate;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.print("Enter Map name (Usually Sample.txt): ");
		//Scanner scan = new Scanner(System.in);
		//String mapName = scan.next();
		String mapName = "Sample_380.txt";
		MapInfo.setMapInfoInstance(mapName);
		//System.out.print("Select Alg (1. Nearest Neighbor, 2. Two-Opt, 3. SA, 4. GA) : ");

		//int input = scan.nextInt();



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

		int input = 3;
		switch(input){
		case 1: {
			NearestNeighbor nearestNeighbor = new NearestNeighbor();
			int min = 1000000;
			int minIndex = 0;
			int size = MapInfo.getInstance().getNumOfCity();
			for (int i = 0; i < size; i++) {
				int[] path = nearestNeighbor.calculatePath(i);
				int length = PathCheck.getPathCost(path);
				if (length < min) {
					min = length;
					minIndex = i;
				}
			}
			System.out.println("Nearest Neighbor MIN: " + minIndex);

		}
			break;
		case 2:
			TwoOptSearch twoOptSearch = new TwoOptSearch(1000000);
			int [] path2 = twoOptSearch.calculatePath(0);
			System.out.println("two opt search: " + PathCheck.getPathCost(path2));
			break;
		case 3:
			double [] temperatureTrial = {10, 20, 30, 50, 100, 1000};
			int minIndex = 121;
			/*
			NearestNeighbor nearestNeighbor = new NearestNeighbor();
			int min = 1000000;

			int size = MapInfo.getInstance().getNumOfCity();
			for(int i = 0 ; i < size ; i ++) {
				int[] path = nearestNeighbor.calculatePath(i);
				int length = PathCheck.getPathCost(path);
				if(length < min){
					min = length;
					minIndex = i;
				}
			}
			*/
			System.out.println("Start point : " + minIndex);

			SASearch saSearch = new SASearch(temperatureTrial[3], 0.8, 100000, 3);
			int[] path3 = saSearch.calculatePath(minIndex);
			minCost = PathCheck.getPathCost(path3);
			minPath = Arrays.copyOf(path3, path3.length);
			System.out.println("SA search: " + minCost);

			for(int i = 0 ; i < 100 ; i ++) {
				float delta = (20 - i) * 0.03f;
				if(delta < 0.4) delta = 0.4f;
				saSearch = new SASearch(temperatureTrial[2], delta, 100000, 3);
				path3 = saSearch.calculatePath(minPath);
				int currCost = PathCheck.getPathCost(path3);
				if(currCost < minCost) {
					minCost = currCost;
					minPath = path3;
				}
				System.out.println("SA search: " + delta + ", cost : " + minCost);
			}
			break;
		case 4:
			int populationSize = 100;
			int generationSize = 10000;

			//Initialize by SA
			Initializer saInitializer = new SAInitalizer(30, 0.8, 10000, 5);
			//Random Initialize
			Initializer randInitializer = new RandomInitializer();

			Selection ptSelection = new PseudoTournamentSelection(populationSize, 10);

			Mutation swapMutation = new SwapMutation(0.3);
			//Mutation nscMutation = new NSCMutation(0.3, 4);


			Crossover pmxCrossover = new PMXCrossover();

			MyGASearch myGASearch = new MyGASearch(populationSize , generationSize);

			myGASearch.setProcess(saInitializer, pmxCrossover, ptSelection, swapMutation);
			//myGASearch.setProcess(randInitializer, pmxCrossover, ptSelection, swapMutation);

			int [] path4 = myGASearch.calculatePath(0);
			for(int i = 0; i< myGASearch.generationScore.length;i++){
				System.out.println(myGASearch.generationScore[i]);
			}

			System.out.println("GA: " + PathCheck.getPathCost(path4));
			break;
		}


		Date endDate = new Date();
		long diff = endDate.getTime() - beginDate.getTime();

		long milsec = diff % 1000;
		long sec = diff / 1000;

		System.out.println("Experiment End : " + sec + "." + milsec + "s");
	}
}
