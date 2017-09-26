package tspUtil;

import java.io.*;

public class MapInfo {
	//Singleton pattern
	public static final int MAP_TYPE_SQUARE = 1;
	public static final int OPEN_TYPE_NEW_FILE = 1;
	public static final int OPEN_TYPE_LOAD_TEMPLATE = 2;
	public static final int MAP_TYPE_TRIANGLE = 2;
	private static MapInfo instance = null;
	private int numOfCity; // 도시 숫자
	private int mapType; // Sample 파일의 타입, 1. Square, 2.Triangle
	private int distanceMap[][]; // 두 도시간의 거리 저장, symmetric matrix
	private Point points[];
	public static int dimension;

	public static void setMapInfoInstance(String filename, int mapType){
		instance = new MapInfo(filename, mapType);
	}
	
	public static MapInfo getInstance(){
		return instance;
	}
	
	private MapInfo(String filename,int mapType) {
		this.setMapType(mapType, filename);
		this.setNumOfCity(filename);
		this.setDistanceMap(filename);
	}

	public void setMapType(int mapType, String filename){
		this.mapType = mapType;
		if(mapType == MAP_TYPE_SQUARE){
			convertFileToTriangleType(filename);
		}
	}

	private void convertFileToTriangleType(String fileName){
		BufferedReader reader = null;
		FileWriter fw = null;
		BufferedWriter writer = null;

		try {
			reader = new BufferedReader(new FileReader(fileName));
			StringBuffer sb = new StringBuffer(fileName);
			sb.insert(fileName.length() - 4, "_tri");
			fw = new FileWriter(sb.toString(), false);
			writer = new BufferedWriter(fw);
		} catch (FileNotFoundException e) {
			System.err.println("convertFileToTriangleType - File not found");
			System.exit(1);
		} catch (IOException E){
			System.err.println("IO Exception");
			System.exit(1);
		}

		String str = null;
		dimension = 0;
		for(int i=0;i<8;i++){
			try {
				str = reader.readLine();
			} catch (IOException e) {
				System.err.println("File read error");
				System.exit(1);
			}
			if (str == null || str.equals(""))
				break;
			if(i==5){ //set dimension
				dimension = Integer.parseInt(str.substring(12, str.length()));
			}
		}

		points = new Point[dimension];
		for(int i=0;i<dimension;i++){
			try {
				str = reader.readLine();
				String[] splitedStr = str.split(" ");
				points[i] = new Point(Integer.parseInt(splitedStr[1]), Integer.parseInt(splitedStr[2]));
				for(int j=0; j<i;j++){
					int distance = (int)Math.hypot(points[j].x - points[i].x, points[j].y - points[i].y); // 점 사이의 거리
					writer.write(String.valueOf(distance));
					writer.write(" ");
				}
				writer.write(String.valueOf(0) + " "); //자기 자신과의 거리
				writer.newLine();
			} catch (IOException e) {
				System.err.println("File read error");
				e.printStackTrace();
				System.exit(1);
			}
		}

		//스트림 닫기
		if(reader!=null){
			try{
				reader.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		if(writer!=null){
			try {
				writer.close();
			}catch (IOException e){

			}
		}
		if(fw!=null){
			try{
				fw.close();
			}catch(IOException e){

			}
		}
	}
	
	public int getNumOfCity() {
		return numOfCity;
	}
	
	public int[][] getDistanceMap() {
		return distanceMap;
	}

	public int getTwoCityDistance(int firstCityIndex, int secondCityIndex){
		return this.distanceMap[firstCityIndex][secondCityIndex];
	}
	
	//지도 정보 초기화
	//각 도시간의 거리를 나타내는 2차원 행렬로써 symmetric한 지도라고 가정
	
	private void setDistanceMap(String filename) {
		this.distanceMap = new int[this.numOfCity][this.numOfCity];

		if(mapType == 1){
			StringBuffer sb = new StringBuffer(filename);
			filename = sb.insert(filename.length()-4, "_tri").toString(); //TRI용 변환 파일 사용
		}

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			System.err.println("setDistanceMap - File not found");
			System.exit(1);
		}

		for (int i = 0; i < numOfCity; i++) {
			try {
				String str = reader.readLine();

				String[] splitedStr = str.split(" ");

				for (int j = 0; j < splitedStr.length; j++) {
					this.distanceMap[i][j] = Integer.parseInt(splitedStr[j]);
				}

			} catch (IOException e) {
				System.err.println("File read error");
				System.exit(1);
			}
		}
		try {
			reader.close();
		} catch (IOException e) {
			System.err.println("Reader close error");
			System.exit(1);
		}
		for(int i = 0; i < numOfCity; i++){
			for(int j = i + 1; j < numOfCity; j++){
				this.distanceMap[i][j] = this.distanceMap[j][i];
			}
		}
	}
	// 파일에서 도시의 숫자를 초기화
	// 도시 개수 = 파일의 줄 수
	private void setNumOfCity(String filename) {
		this.numOfCity = 0;

		LineNumberReader reader = null;
		if(mapType == 1){
			StringBuffer sb = new StringBuffer(filename);
			filename = sb.insert(filename.length()-4, "_tri").toString(); //TRI용 변환 파일 사용
		}

		try {
			reader = new LineNumberReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			System.err.println("setNumOfCity - File not found");
			System.exit(1);
		}
		String str = null;
		while (true) {
			try {
				str = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("File read error");
				System.exit(1);
			}
			if (str == null || str.equals(""))
				break;

			this.numOfCity++;
		}
		try {
			reader.close();
		} catch (IOException e) {
			System.err.println("Reader close error");
			System.exit(1);
		}
	}

	static class Point{
		int x,y;
		Point(int x, int y){
			this.x=x;
			this.y=y;
		}
	}
}
