package test;

import ga.*;
import org.junit.Test;
import tspUtil.MapInfo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CrossoverTest {
	@Test
	public void noDuplicationTest(){
		String fileName = ".\\map\\xqf131\\Sample_xqf131.txt";
		// 1. 맵 인스턴스 생성
		MapInfo.setMapInfoInstance(fileName, MapInfo.MAP_TYPE_SQUARE);

		Initializer saInitializer = new SAInitalizer(30, 0.8, 1000, 1);
		GAElement[] populationList = saInitializer.initializePopulation(20, 100);

		Crossover orderedCrossover = new OrderedCrossover();
		GAElement[] gaChild = orderedCrossover.crossover(populationList[0], populationList[1]);
		int equalCount = 0;
		for(int i=0; i<gaChild[0].getPath().length;i++){
			for(int j=0;j<gaChild[0].getPath().length;j++){
				if(i!=j) {
					if (gaChild[0].getPath()[i] == gaChild[0].getPath()[j]) {
						equalCount++;
					}
				}
			}
		}
		assertThat(equalCount, is(2));
	}
}
