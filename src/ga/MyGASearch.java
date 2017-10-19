package ga;

import java.util.Arrays;
import java.util.Random;

public class MyGASearch extends GASearch{
	protected Mutation[] mutations;
	public int [] generationScore;
	public MyGASearch(int populationSize, int generationSize) {
		// TODO Auto-generated constructor stub
		super(populationSize, generationSize);
		this.generationSize = generationSize;

		mutations = new Mutation[4];
		Mutation swapMutation = new SwapMutation(0.3);
		Mutation inverseMutation = new InverseMutation(0.3);
		Mutation insertMutation = new InsertMutation(0.3);
		Mutation saMutation = new SAMutation(0.3f, 30, 0.8f, 1, 1);

		mutations[0] = swapMutation;
		mutations[1] = inverseMutation;
		mutations[2] = insertMutation;
		mutations[3] = saMutation;
	}

	private static int count = 0;
	
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

			switch(this.mutationType){
			case RAND:
				int rand = (int)(new Random().nextFloat() * 100) % (mutations.length);
				mutations[rand].doMutation(populationList);
				break;
			case SWAP:
				mutations[0].doMutation(populationList);
				break;
			case INVERSE:
				mutations[1].doMutation(populationList);
				break;
			case INSERT:
				mutations[2].doMutation(populationList);
				break;
			case SA:
				mutations[3].doMutation(populationList);
				break;
			}


			/*
			GAElement[][] mutationList = new GAElement[3][populationList.length];
			int mutationMin[] = new int[3];

			for(int m = 0 ; m < 3 ; m++){
				for(int ll = 0 ; ll < populationList.length; ll++){
					mutationList[m][ll] = populationList[ll];
				}
				mutation[m].doMutation(mutationList[m]);

				//최소값 찾기
				mutationMin[m] = 1999999999;
				for(int ll = 0 ; ll < populationList.length; ll++){
					if(mutationList[m][ll].getCost() < mutationMin[m]) {
						mutationMin[m] = mutationList[m][ll].getCost();
					}
				}
			}

			int mutaitionListMin = 0;
			if(mutationMin[0] < mutationMin[1]){
				if(mutationMin[0] > mutationMin[2]){
					mutaitionListMin = 2;
				}
			}else{
				if(mutationMin[1] < mutationMin[2]){
					mutaitionListMin = 1;
				}else{
					mutaitionListMin = 2;
				}
			}

			this.populationList = mutationList[mutaitionListMin];
			*/

			Arrays.sort(this.populationList, gaCom);


			/*
			System.out.println("/");
			for(int k = 0 ; k < this.populationList.length; k ++){
				System.out.println("cost[" + k + "] : " + this.populationList[k].getCost());
			}
			*/


			if(100 < count) {
				System.out.println("cost : " + this.populationList[0].getCost());
				count = 0;
			}
			count ++;

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
