package ga;

import tspUtil.PathCheck;

import java.util.Arrays;

public class MyGASearch extends GASearch{
	
	public int [] generationScore;
	public MyGASearch(int populationSize, int generationSize) {
		// TODO Auto-generated constructor stub
		super(populationSize, generationSize);
		this.generationSize = generationSize;
	}
	
	@Override
	public int[] calculatePath(int startPoint) {
		// TODO Auto-generated method stub
		
		
		this.generationScore = new int[this.generationSize];
		this.populationList = this.initializer.initializePopulation(populationSize, startPoint);

		System.out.println("Init complete");
		GAElementComparator gaCom = new GAElementComparator();

		//오직 룰렛을 위해서 존재하는 함수
		this.selection.setInputList(this.populationList);

		for(int i = 0; i < this.generationSize; i++){

			//Arrays.sort(this.populationList, gaCom);

			this.selection.setParentList();

			int firstParent = this.selection.getFirstParent();
			int secondParent = this.selection.getSecondParent();
			GAElement[] child = this.crossover.crossover(this.populationList[firstParent], this.populationList[secondParent]);


			populationList[populationList.length - 2] = child[0];
			populationList[populationList.length - 1] = child[1];

			mutation.doMutation(populationList);


			Arrays.sort(this.populationList, gaCom);

			System.out.println("/////////////////////////////////////");
			for(int k = 0 ; k < this.populationList.length; k ++){
				//System.out.println("cost[" + k + "] : " + PathCheck.getPathCost(this.populationList[k].getPath()));
			}

			this.generationScore[i] = this.populationList[0].getCost();
		}
		Arrays.sort(this.populationList, gaCom);





		return populationList[0].getPath();
	}

	@Override
	public int[] calculatePath(int[] path) {
		// TODO Auto-generated method stub
		//do nothing
		System.err.println("nothing implemented...use calculatePath(int startPoint)");
		return null;
	}
	private int[] getCheck(){
		int arr[] = new int[this.populationSize];
		for(int i = 0; i < arr.length;i++){
			arr[i] = this.populationList[i].getCost();
		}
		return arr;
	}
	private GAElement [] getCopyOfPopulationList(GAElement [] populationList){
		GAElement [] copy = new GAElement[this.populationSize];
		for(int i = 0; i < this.populationSize; i++){
			try {
				copy[i] = (GAElement)populationList[i].clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				System.err.println("Clone Error");
			}
		}
		return copy;
	}
	
}
