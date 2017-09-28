package test;

import greedy.InsertSearch;
import main.MainClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import sa.BestIndexSearch;
import sa.SASearch;
import temperature.TemperatureFunction;
import tspUtil.MapInfo;
import tspUtil.PathCheck;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TemperatureFunctionTest {
	static String normal08 = "normal08";
	static String normal07 = "normal07";
	static int sum = 0;
	static int limitTrial = 15000;
	static int trialCost = 100000000;
	static int bestCost = 10000000;
	static int[] trialPath, minPath;
	static double[] temperatureTrial = {10, 20, 30, 50, 100, 1000};
	static Date beginTime;
	static long diff;
	static final int loopCount=1;

	private void makeMapInfo(){
		makeXit1083();
	}

	@Before
	public void setUp(){

		initialize();
	}

	public void initialize(){
		sum=0;
		bestCost = 1000000;
	}


	private static void makeBcl380(){
		String fileName = ".\\map\\bcl380\\Sample_bcl380.txt";
		MapInfo.setMapInfoInstance(fileName, MapInfo.MAP_TYPE_SQUARE);
		System.out.println("data name : " +fileName + "-----------------------------");
	}

	private static void makeXql662(){
		String fileName = ".\\map\\xql662\\Sample_xql662.txt";
		MapInfo.setMapInfoInstance(fileName, MapInfo.MAP_TYPE_SQUARE);
		System.out.println("data name : " +fileName + "-----------------------------");
	}

	private static void makeXit1083(){
		String fileName = ".\\map\\xit1083\\Sample_xit1083.txt";
		MapInfo.setMapInfoInstance(fileName, MapInfo.MAP_TYPE_SQUARE);
		System.out.println("data name : " +fileName + "-----------------------------");
	}


	@Test
	public void testNormal09(){
		makeMapInfo();

		testNormal(loopCount, 0.9f);
	}

	@Test
	public void testNormal08(){
		beginTime = new Date();
		makeMapInfo();
		testNormal(loopCount, 0.8f);
	}

	@Test
	public void testNormal07(){
		beginTime = new Date();
		makeMapInfo();
		testNormal(loopCount,0.7f);
	}

	@Test
	public void testSigmoid(){
		beginTime = new Date();
		makeMapInfo();
//		for(int i = 0 ; i < loopCount; i ++) {
			int startIndex =BestIndexSearch.makeBestIndex();

			startIndex = (int) (Math.random() * MapInfo.dimension - 1);
			//if(i==0)
			//startIndex = bestIndex;


			// 2. SASearch 오브젝트 생성
			SASearch saSearch =  new SASearch(30, 0.9, limitTrial, TemperatureFunction.SIGMOID);
			saSearch.setIsTest(true);

			int[] path3 = saSearch.calculatePath(startIndex); //two-opt greedy path 생성
			trialPath = Arrays.copyOf(path3, path3.length);
			trialCost = PathCheck.getPathCost(path3);
			// 3. SA서치 수행
			playSASearch(saSearch, path3);
//		}
		printResult("sigmoid()");

	}

	public void testNormal(int loopCount, double deltaTemperature){
//		for(int i = 0 ; i < loopCount; i ++) {
			int startIndex = BestIndexSearch.makeBestIndex();

			startIndex = (int) (Math.random() * MapInfo.dimension - 1);
			//if(i==0)
			//startIndex = bestIndex;

			// 2. SASearch 오브젝트 생성
			SASearch saSearch = new SASearch(30, deltaTemperature, limitTrial, TemperatureFunction.NORMAL);
			saSearch.setIsTest(true);

			int[] path3 = saSearch.calculatePath(startIndex); //two-opt greedy path 생성
			trialPath = Arrays.copyOf(path3, path3.length);
			trialCost = PathCheck.getPathCost(path3);
			// 3. SA서치 수행
			playSASearch(saSearch, path3);
//		}
		printResult("normal("+deltaTemperature+")");
	}


	private static void playSASearch(SASearch saSearch, int[] path3){

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
		sum += trialCost;
	}

	private void printResult(String message){
		System.out.println("final best score : " +bestCost);
		System.out.println("average of "+message+" : "+sum/loopCount);
		Date endTime = new Date();
		long diff = (endTime.getTime() - beginTime.getTime())/loopCount;
		long milsec = diff % 1000;
		long sec = diff / 1000;
		System.out.println("everage time : " + sec + "." + milsec + "s \n");
		System.out.println();
	}
}
