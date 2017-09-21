package test;

import org.junit.Test;
import tspUtil.MapInfo;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class MapInfoTest {
	@Test
	public void testConvertFileToTriangleType(){
		// 주어진 샘플 리스트만큼 순회
		File file = new File("./map");
		for(int i=0; i<file.list().length;i++){
			String mapName = file.list()[i];
			String fileName = ".\\map\\" + mapName + "\\Sample_" + mapName + ".txt";
			System.out.println(fileName);
			MapInfo.setMapInfoInstance(fileName, MapInfo.MAP_TYPE_SQUARE); //SQUARE TYPE Map Tri 타입으로 변환

			//검증
			BufferedReader reader1, reader2 = null;
			StringBuffer sb1 = new StringBuffer(fileName);
			sb1.insert(fileName.length() - 4, "_tri");
			StringBuffer sb2 = new StringBuffer(fileName);
			sb2.insert(fileName.length()-4, "_triorig");
			boolean isTrue = true;
			try {
				reader1 = new BufferedReader(new FileReader(sb1.toString())); // 생성한 tri 타입
				reader2 = new BufferedReader(new FileReader(sb2.toString())); // 비교 대상 데이터(조교샘플)
				String readLine1= "";
				String readLine2="";
				while((readLine1 = reader1.readLine()) != null){
					readLine2 = reader2.readLine();
					if(!(readLine2).equals(readLine1)){
						System.out.println("rl1 : "+readLine1);
						System.out.println("rl2 : "+readLine2);
						isTrue =false;
						break;
					}
				}
			} catch (FileNotFoundException e) {
				System.err.println("setDistanceMap - File not found");
				System.exit(1);
			}catch(IOException e){
				System.err.println(e);
			}
			assertTrue(isTrue);
		}
	}
}
