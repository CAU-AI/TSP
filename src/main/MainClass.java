package main;

import java.io.File;
import java.io.InputStream;
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

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		int mapType= 0, openType=0;
		System.out.print("Open type (1. New File, 2. Load Template) : ");
		openType = scan.nextInt();

		String fileName = null;
		if(openType == MapInfo.OPEN_TYPE_NEW_FILE) {
			System.out.print("Enter Map Type (1. Square, 2. Triangle) : ");
			mapType = scan.nextInt();

			System.out.print("Enter Map name (Usually Sample.txt): ");
			fileName = scan.next();
		}else{
			System.out.print("Choose Template (");
			File file = new File("./map");
			for(int i=0; i<file.list().length;i++){
				System.out.print(i+1+"."+file.list()[i]);
				if(i<file.list().length-1)
					System.out.print(", ");
			}
			System.out.print("): ");
			String mapName = file.list()[scan.nextInt()-1];
			fileName = ".\\map\\" + mapName + "\\Sample_" + mapName + ".txt";
			mapType = MapInfo.MAP_TYPE_SQUARE;
		}

		MapInfo.setMapInfoInstance(fileName, mapType);
		System.out.print("Select Alg (1. Nearest Neighbor, 2. Two-Opt, 3. SA, 4. GA) : ");

		int input = scan.nextInt();
		scan.close();
		switch(input){
		case 1:
			NearestNeighbor nearestNeighbor = new NearestNeighbor();
			int [] path = nearestNeighbor.calculatePath(0);
			System.out.println("Nearest Neighbor: " + PathCheck.getPathCost(path));
			break;
		case 2:
			TwoOptSearch twoOptSearch = new TwoOptSearch(1000000);
			int [] path2 = twoOptSearch.calculatePath(0);
			System.out.println("two opt search: " + PathCheck.getPathCost(path2));
			break;
		case 3:
			double [] temperatureTrial = {10, 20, 30, 50, 100, 1000};

			SASearch saSearch = new SASearch(temperatureTrial[3],0.8, 100000, 1);
			int [] path3 = saSearch.calculatePath(0);
			System.out.println("SA search: " + PathCheck.getPathCost(path3));
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
		System.out.println("Experiment End");
	}
}
