package test;

import ga.*;
import org.junit.Before;
import org.junit.Test;
import tspUtil.MapInfo;
import tspUtil.PathCheck;

import java.util.Date;

public class MutationTest {
	private static int limitTrial;
	private static Date beginTime;
	private static int sum = 0;
	private static int bestCost=0;
	private static int loopCount = 100;


	private void makeMapInfo(){
		makeFqm5087();
	}

	private static void makeFqm5087(){
		String fileName = ".\\map\\fqm5087\\Sample_fqm5087.txt";
		MapInfo.setMapInfoInstance(fileName, MapInfo.MAP_TYPE_SQUARE);
		System.out.println("data name : " +fileName + "-----------------------------");
	}

	private static void makeDhb3386(){
		String fileName = ".\\map\\dhb3386\\Sample_dhb3386.txt";
		MapInfo.setMapInfoInstance(fileName, MapInfo.MAP_TYPE_SQUARE);
		System.out.println("data name : " +fileName + "-----------------------------");
	}

	private static void makeLap7454(){
		String fileName = ".\\map\\lap7454\\Sample_lap7454.txt";
		MapInfo.setMapInfoInstance(fileName, MapInfo.MAP_TYPE_SQUARE);
		System.out.println("data name : " +fileName + "-----------------------------");
	}

	private static void makeXit1083(){
		String fileName = ".\\map\\xit1083\\Sample_xit1083.txt";
		MapInfo.setMapInfoInstance(fileName, MapInfo.MAP_TYPE_SQUARE);
		System.out.println("data name : " +fileName + "-----------------------------");
	}



	@Before
	public void setUp(){
		initialize();
	}

	public void initialize(){
		limitTrial = 15000;
		beginTime = new Date();
		sum=0;
		bestCost = 100000000;
	}

	@Test
	public void testMutationRand(){
		beginTime = new Date();
		makeMapInfo();
		int populationSize = 10;
		int generationSize = loopCount;

		int numOfCity = MapInfo.getInstance().getNumOfCity();

		//Initialize by SA
		Initializer saInitializer = new SAInitalizer(30, 0.8, 30, 1);

		Selection ptSelection = new RouletteSelection();

		Crossover orderedCrossover = new OrderedCrossover();

		MyGASearch myGASearch = new MyGASearch(populationSize, generationSize);

		myGASearch.setProcess(saInitializer, orderedCrossover, ptSelection, MutationType.RAND);

		int[] path = myGASearch.calculatePath(0);

		int cost = PathCheck.getPathCost(path);
		bestCost = Math.min(cost, bestCost);
		printResult("mutationRAND");
	}

	@Test
	public void testMutationSA(){
		beginTime = new Date();
		makeMapInfo();
		int populationSize = 10;
		int generationSize = loopCount;

		int numOfCity = MapInfo.getInstance().getNumOfCity();

		//Initialize by SA
		Initializer saInitializer = new SAInitalizer(30, 0.8, 30, 1);

		Selection ptSelection = new RouletteSelection();

		Crossover orderedCrossover = new OrderedCrossover();

		MyGASearch myGASearch = new MyGASearch(populationSize, generationSize);

		myGASearch.setProcess(saInitializer, orderedCrossover, ptSelection, MutationType.SA);

		int[] path = myGASearch.calculatePath(0);
		int cost = PathCheck.getPathCost(path);
		bestCost = Math.min(cost, bestCost);
		printResult("mutationSA");
	}

	private void printResult(String message){
		System.out.println("final best score : " +bestCost);
		Date endTime = new Date();
		long diff = (endTime.getTime() - beginTime.getTime())/loopCount;
		long milsec = diff % 1000;
		long sec = diff / 1000;
		System.out.println("everage time : " + sec + "." + milsec + "s \n");
		System.out.println();
	}
}
