package test;

import org.junit.Test;
import tspUtil.MapInfo;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class MapInfoTest {
	@Test
	public void testConvertFileToTriangleType(){
		// �־��� ���� ����Ʈ��ŭ ��ȸ
		File file = new File("./map");
		for(int i=0; i<file.list().length;i++){
			String mapName = file.list()[i];
			String fileName = ".\\map\\" + mapName + "\\Sample_" + mapName + ".txt";
			System.out.println(fileName);
			MapInfo.setMapInfoInstance(fileName, MapInfo.MAP_TYPE_SQUARE); //SQUARE TYPE Map Tri Ÿ������ ��ȯ

			//����
			BufferedReader reader1, reader2 = null;
			StringBuffer sb1 = new StringBuffer(fileName);
			sb1.insert(fileName.length() - 4, "_tri");
			StringBuffer sb2 = new StringBuffer(fileName);
			sb2.insert(fileName.length()-4, "_triorig");
			boolean isTrue = true;
			try {
				reader1 = new BufferedReader(new FileReader(sb1.toString())); // ������ tri Ÿ��
				reader2 = new BufferedReader(new FileReader(sb2.toString())); // �� ��� ������(��������)
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
