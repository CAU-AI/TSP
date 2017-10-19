package test;

import org.junit.Before;
import org.junit.Test;
import tspUtil.MapInfo;

import java.util.Date;

public class MutationTest {
	private static int limitTrial;
	private static Date beginTime;
	private static int sum = 0;
	private static int bestCost=0;
	private static int loopCount = 1;


	private void makeMapInfo(){
		makeFqm5087();
	}

	private static void makeFqm5087(){
		String fileName = ".\\map\\xqf131\\Sample_fqm5087.txt";
		MapInfo.setMapInfoInstance(fileName, MapInfo.MAP_TYPE_SQUARE);
		System.out.println("data name : " +fileName + "-----------------------------");
	}

	private static void makeDhb3386(){
		String fileName = ".\\map\\bcl380\\Sample_dhb3386.txt";
		MapInfo.setMapInfoInstance(fileName, MapInfo.MAP_TYPE_SQUARE);
		System.out.println("data name : " +fileName + "-----------------------------");
	}

	private static void makeLap7454(){
		String fileName = ".\\map\\xql662\\Sample_lap7454.txt";
		MapInfo.setMapInfoInstance(fileName, MapInfo.MAP_TYPE_SQUARE);
		System.out.println("data name : " +fileName + "-----------------------------");
	}

	private static void makeXit1083(){
		String fileName = ".\\map\\xit1083\\Sample_xit1083.txt";
		MapInfo.setMapInfoInstance(fileName, MapInfo.MAP_TYPE_SQUARE);
		System.out.println("data name : " +fileName + "-----------------------------");
	}

	@Test
	public void testNormal08_temp50(){
		beginTime = new Date();
		makeMapInfo();
		//testNormal(loopCount, 0.8f, 100);
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
