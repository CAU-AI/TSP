package sa;

import java.util.Arrays;

import greedy.TwoOptSearch;
import tspUtil.GetTwoRandomNumber;
import tspUtil.PathCheck;
import tspUtil.TSPAlgorithm;

public class SASearch extends TSPAlgorithm{
	private double temperature;
	private double deltaTemperature;
	private int numOfNextHop;
	
	private TwoOptSearch twoOptSearch;
	
	public SASearch(double temperature, double deltaTemperature, int limitTrial, int numOfNextHop) {
		this.twoOptSearch = new TwoOptSearch(limitTrial);
		this.setSAParameter(temperature, deltaTemperature);
		this.numOfNextHop = numOfNextHop;
	}

	public void setTemperature(double temperature){
		this.temperature = temperature;
	}

	public void setSAParameter(double temperature, double deltaTemperature) {
		if (temperature <= 0) {
			System.err.println("Temperature must be bigger than 0");
			System.exit(1);
		}
		this.temperature = temperature;
		if (!(0 < deltaTemperature && deltaTemperature < 1)) {
			System.err.println("delta value of temperature must be between 0 to 1");
			System.exit(1);
		}
		this.deltaTemperature = deltaTemperature;
	}

	@Override
	public int[] calculatePath(int startPoint) {
		// TODO Auto-generated method stub

		//먼저 투옵서치로 패스를 만든다.
		int[] path = this.twoOptSearch.calculatePath(startPoint);

		//그 후 SA서치로 패스를 만든다.
		path = this.calculatePath(path);
		return path;
	}

	@Override
	public int[] calculatePath(int[] path) {

		//인수로 받은 순서를 복사한다.
		int[] bestPath = Arrays.copyOf(path, path.length);

		//초기값으로 들어온 패스의 길이를 저장해둔다
		int bestScore = PathCheck.getPathCost(bestPath);

		//온도가 1보다 크다면 계속 돈다
		//온도가 1과 같거나 작아지면 멈춘다
		while (this.temperature > 1) {


			//복사한 인수의 순서를 한번 더 복사한다.
			int[] trialPath_insert = Arrays.copyOf(bestPath, bestPath.length);
			int[] trialPath_inverse = Arrays.copyOf(bestPath, bestPath.length);
			int[] trialPath_swap= Arrays.copyOf(bestPath, bestPath.length);
			int[] trialPath = Arrays.copyOf(bestPath, bestPath.length);


			//순서를 바꿔본다
			/*
			for(int i = 0; i < this.numOfNextHop ; i++){
				int[] twoRandNumber = GetTwoRandomNumber.getTwoRandomNumber();
				int firstPoint = twoRandNumber[0];
				int secondPoint = twoRandNumber[1];
				trialPath = this.twoOptSearch.swapPath(trialPath, firstPoint, secondPoint);
				//trialPath = this.twoOptSearch.swap(trialPath, firstPoint, secondPoint);
			}*/

			//순서를 바꿔본다
			//for(int i = 0; i < this.numOfNextHop ; i++){
				int[] twoRandNumber = GetTwoRandomNumber.getTwoRandomNumberReal();
				int firstPoint = twoRandNumber[0];
				int secondPoint = twoRandNumber[1];
				this.twoOptSearch.setSearchMethod(TwoOptSearch.METHOD_INSERT);
				trialPath_insert = this.twoOptSearch.calculatePath(trialPath);

				this.twoOptSearch.setSearchMethod(TwoOptSearch.METHOD_INVERSE);
				trialPath_inverse = this.twoOptSearch.calculatePath(trialPath);

				this.twoOptSearch.setSearchMethod(TwoOptSearch.METHOD_SWAP);
				trialPath_swap = this.twoOptSearch.calculatePath(trialPath);
			//}

			//패스의 점수를 저장한다.
			int trialScore_insert = PathCheck.getPathCost(trialPath_insert);
			int trialScore_inverse = PathCheck.getPathCost(trialPath_inverse);
			int trialScore_swap = PathCheck.getPathCost(trialPath_swap);

			if(trialScore_insert < trialScore_inverse){
				trialPath = trialPath_insert;
				System.out.println("insert is best");
			}else{
				trialPath = trialPath_inverse;
				System.out.println("inverse is best");

			}
			int trialScore = Math.min(trialScore_insert, trialScore_inverse);

			//어떤 랜덤값보다 확률이 크다면
			double prob = this.getAcceptProbability(bestScore, trialScore);
			double random = Math.random();
			if (random < prob) {
				//시험삼아 복사한 패스를 현재 패스로 저장해 놓는다.
				bestPath = Arrays.copyOf(trialPath, trialPath.length);

				//시험삼아 계산한 점수를 현재 점수로 저장해 놓는다.
				bestScore = trialScore;

				//System.out.println("bestScore : " + bestScore + ", prob : " + (float)prob);
			}

			// normal function
			 this.temperature *= deltaTemperature;

			// sigmoid function
			// this.temperature = ((1/(1+Math.pow(Math.E, (-1*this.temperature)))));
			// System.out.println("temperature : " +temperature);
		}
		return bestPath;
	}

	//확률을 구한다
	private double getAcceptProbability(int bestScore, int trialScore) {

		//현재 스코어가 더 크다면 1을 리턴
		if (bestScore > trialScore)
			return 1;
		else {
			//return 0;
			//그렇지 않다면 확률 p 를 식에 대입하여 리턴한다.
			return Math.pow(Math.E, -(trialScore - bestScore) / this.temperature);
		}
	}
	
}
