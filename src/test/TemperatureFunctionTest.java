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
	static int sum = 0;
	static int limitTrial = 15000;
	static int trialCost = 100000000;
	static int bestCost = 10000000;
	static int[] trialPath, minPath;
	static double[] temperatureTrial = {10, 20, 30, 50, 100, 1000};
	static Date beginTime;
	static long diff;
	static final int loopCount=5;

	private void makeMapInfo(){
		makeXqf131();
	}

	@Before
	public void setUp(){
		initialize();
	}

	public void initialize(){
		limitTrial = 15000;
		beginTime = new Date();
		sum=0;
		bestCost = 1000000;
	}

	private static void makeXqf131(){
		String fileName = ".\\map\\xqf131\\Sample_xqf131.txt";
		MapInfo.setMapInfoInstance(fileName, MapInfo.MAP_TYPE_SQUARE);
		System.out.println("data name : " +fileName + "-----------------------------");
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

//	@Test
//	public void testNormal09_temp30(){
//		beginTime = new Date();
//		makeMapInfo();
//		testNormal(loopCount, 0.9f, 30);
//	}
//
//	@Test
//	public void testNormal09_temp50(){
//		beginTime = new Date();
//		makeMapInfo();
//		testNormal(loopCount, 0.9f, 100);
//	}
//
//	@Test
//	public void testNormal08_09(){
//		beginTime =new Date();
//		makeMapInfo();
//		testTwoNormal(loopCount, 0.8f, 0.9f);
//	}

	@Test
	public void testNormal08_temp30(){
		beginTime = new Date();
		makeMapInfo();
		testNormal(loopCount, 0.8f, 30);
	}

	@Test
	public void testNormal08_temp50(){
		beginTime = new Date();
		makeMapInfo();
		testNormal(loopCount, 0.8f, 100);
	}


	@Test
	public void testNormal07_temp30(){
		beginTime = new Date();
		makeMapInfo();
		testNormal(loopCount,0.7f, 30);
	}

	@Test
	public void testNormal07_temp50(){
		beginTime = new Date();
		makeMapInfo();
		testNormal(loopCount,0.7f, 100);
	}

	@Test
	public void testSigmoid_temp30(){
		beginTime = new Date();
		makeMapInfo();
		testSigmoid(30);
	}

	@Test
	public void testSigmoid_temp50(){
		beginTime = new Date();
		makeMapInfo();
		testSigmoid(100);
	}

	private void testSigmoid(double trialTemperature){

		for(int i = 0 ; i < loopCount; i ++) {
			int startIndex = 0;
			if(i==0)
				startIndex =BestIndexSearch.makeBestIndex();
			else
				startIndex = (int) (Math.random() * MapInfo.dimension - 1);


			// 2. SASearch 오브젝트 생성
			SASearch saSearch =  new SASearch(trialTemperature, 0.9, limitTrial, TemperatureFunction.SIGMOID);
			saSearch.setIsTest(true);

			int[] path3 = saSearch.calculatePath(startIndex); //two-opt greedy path 생성
			trialPath = Arrays.copyOf(path3, path3.length);
			trialCost = PathCheck.getPathCost(path3);
			// 3. SA서치 수행
			playSASearch(saSearch, path3);
		}
		printResult("sigmoid()");

	}

	private void testTwoNormal(int loopCount, double deltaTemperature1, double deltaTemperature2){
		for(int i = 0 ; i < 30; i ++) {
			int startIndex = 0;
			if(i==0)
				startIndex =BestIndexSearch.makeBestIndex();
			else
				startIndex = (int) (Math.random() * MapInfo.dimension - 1);
			//if(i==0)
			//startIndex = bestIndex;

			// 2. SASearch 오브젝트 생성
			SASearch saSearch = new SASearch(30, deltaTemperature1, limitTrial, TemperatureFunction.NORMAL);
			saSearch.setIsTest(true);

			int[] path3 = saSearch.calculatePath(startIndex); //two-opt greedy path 생성
			trialPath = Arrays.copyOf(path3, path3.length);
			trialCost = PathCheck.getPathCost(path3);
			// 3. SA서치 수행
			playSASearch(saSearch, path3);
		}

		for(int i = 0 ; i < 10; i ++) {
			int startIndex = (int) (Math.random() * MapInfo.dimension - 1);
			//if(i==0)
			//startIndex = bestIndex;

			// 2. SASearch 오브젝트 생성
			SASearch saSearch = new SASearch(30, deltaTemperature2, limitTrial, TemperatureFunction.NORMAL);
			saSearch.setIsTest(true);

			trialCost = PathCheck.getPathCost(trialPath);
			// 3. SA서치 수행
			playSASearch(saSearch, minPath);
		}

		printResult("normal("+deltaTemperature1+", " + deltaTemperature2 + ")");
	}

	private void testNormal(int loopCount, double deltaTemperature, double trialTemperature){
		for(int i = 0 ; i < loopCount; i ++) {
			int startIndex = 0;
			if(i==0)
				startIndex =BestIndexSearch.makeBestIndex();
			else
				startIndex = (int) (Math.random() * MapInfo.dimension - 1);
			//if(i==0)
			//startIndex = bestIndex;

			// 2. SASearch 오브젝트 생성
			SASearch saSearch = new SASearch(trialTemperature, deltaTemperature, limitTrial, TemperatureFunction.NORMAL);
			saSearch.setIsTest(true);

			int[] path3 = saSearch.calculatePath(startIndex); //two-opt greedy path 생성
			trialPath = Arrays.copyOf(path3, path3.length);
			trialCost = PathCheck.getPathCost(path3);
			// 3. SA서치 수행
			playSASearch(saSearch, path3);
		}
		int equalCount = 0;
		for(int i=0;i<minPath.length;i++){
			for(int j=0;j<minPath.length;j++){
				if(i!=j) {
					if(minPath[i] == minPath[j]){
						equalCount++;
					}
				}
			}
		}
		assertThat(equalCount, is(2));
		System.out.println(minPath.length);
		printResult("normal("+deltaTemperature+")");
	}


	private static void playSASearch(SASearch saSearch, int[] path3){
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
