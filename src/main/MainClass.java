package main;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import ga.*;
import greedy.NearestNeighbor;
import sa.BestIndexSearch;
import sa.SASearch;
import tspUtil.MapInfo;
import tspUtil.PathCheck;

public class MainClass {
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


		// 1. �� �ν��Ͻ� ����
		MapInfo.setMapInfoInstance(fileName, mapType);

		// 2. Ÿ�� ���������
		makeTimeThread(0);


		GAElement[] ga = new GAElement[4];

		// 3. SASearch �� GA 4�� ã��
		for(int i = 0 ; i < 4; i ++) {
			int startIndex = 0;

			// 2. SASearch ������Ʈ ����
			int limitTrial = 1000;
			limitTrial *= 1083/MapInfo.dimension;
			SASearch saSearch = new SASearch(30, 0.8f, limitTrial, 0);

			// 3. SA��ġ ����
			ga[i] = playSASearch(saSearch, startIndex);
		}
	}

	public static GAElement playSASearch(SASearch saSearch, int startIndex){
		GAElement ret = new GAElement();

		//two-opt greedy path ����
		ret.path = saSearch.calculatePath(startIndex);
		ret.path = saSearch.calculatePath(ret.path);
		ret.cost = PathCheck.getPathCost(ret.path);

		return ret;
	}

	public static void makeTimeThread(int minIndex){

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					beginDate = new Date();
					Thread.sleep(30000); //���⸦ �������ּ���

					System.out.println("Final Best Cost : ");

					Date endDate = new Date();
					long diff = endDate.getTime() - beginDate.getTime();

					long milsec = diff % 1000;
					long sec = diff / 1000;

					System.out.println("Experiment End : " + sec + "." + milsec + "s");


					// 5. ��� ���� ����
					//MapInfo.makeResultFile(bestCost, minPath);

					System.exit(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}
}
