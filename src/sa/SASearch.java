package sa;

import java.util.Arrays;

import greedy.*;
import tspUtil.GetTwoRandomNumber;
import tspUtil.PathCheck;
import tspUtil.TSPAlgorithm;

public class SASearch extends TSPAlgorithm{
	private double temperature;
	private double deltaTemperature;
	private int numOfNextHop;
	
	private OriginalTwoOptSearch twoOptSearch;
	
	public SASearch(double temperature, double deltaTemperature, int limitTrial, int numOfNextHop) {
		this.twoOptSearch = new OriginalTwoOptSearch(limitTrial);
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

		//���� ���ɼ�ġ�� �н��� �����.
		int[] path = this.twoOptSearch.calculatePath(startPoint);

		return path;
	}


	@Override
	public int[] calculatePath(int[] path) {

		//�μ��� ���� ������ �����Ѵ�.
		int[] bestPath = Arrays.copyOf(path, path.length);

		//�ʱⰪ���� ���� �н��� ���̸� �����صд�
		int bestScore = PathCheck.getPathCost(bestPath);

		//�µ��� 1���� ũ�ٸ� ��� ����
		//�µ��� 1�� ���ų� �۾����� �����
		float anCount=0;

		double trialTemperature = this.temperature;
		while (trialTemperature > 1) {
			//������ �μ��� ������ �ѹ� �� �����Ѵ�.
			int[] insertTrialPath = Arrays.copyOf(bestPath, bestPath.length);
			int[] inverseTrialPath = Arrays.copyOf(bestPath, bestPath.length);
			int[] swapTrialPath= Arrays.copyOf(bestPath, bestPath.length);
			int[] trialPath = Arrays.copyOf(bestPath, bestPath.length);


			int[] twoRandNumber = GetTwoRandomNumber.getTwoRandomNumberReal();
			int firstPoint = twoRandNumber[0];
			int secondPoint = twoRandNumber[1];

			InsertSearch insertSearch = new InsertSearch();
			insertTrialPath = insertSearch.calculatePath(trialPath);

			InverseSearch inverseSearch = new InverseSearch();
			inverseTrialPath = inverseSearch.calculatePath(trialPath);

			SwapSearch swapSearch = new SwapSearch();
			swapTrialPath = swapSearch.calculatePath(trialPath);

			//�н��� ������ �����Ѵ�.
			int insertTrialScore = PathCheck.getPathCost(insertTrialPath);
			int inverseTrialScore = PathCheck.getPathCost(inverseTrialPath);
			int swapTrialScore = PathCheck.getPathCost(swapTrialPath);
			int trialScore = 0;
			if(swapTrialScore < inverseTrialScore){
				if(insertTrialScore < swapTrialScore){
					trialPath = insertTrialPath;
					trialScore = insertTrialScore;
				}else{
					trialPath = swapTrialPath;
					trialScore = swapTrialScore;
				}
			}else{
				if(insertTrialScore < inverseTrialScore){
					trialPath = insertTrialPath;
					trialScore = insertTrialScore;
				}else{
					trialPath = inverseTrialPath;
					trialScore = inverseTrialScore;
				}
			}

			//� ���������� Ȯ���� ũ�ٸ�
			double prob = this.getAcceptProbability(bestScore, trialScore);
			double random = Math.random();
			if (random < prob) {
				//������ ������ �н��� ���� �н��� ������ ���´�.
				bestPath = Arrays.copyOf(trialPath, trialPath.length);

				//������ ����� ������ ���� ������ ������ ���´�.
				bestScore = trialScore;

				//System.out.println("bestScore : " + bestScore + ", prob : " + (float)prob);
			}

			// normal function
			 //this.temperature *= deltaTemperature;

			trialTemperature = ((1/(1+Math.pow(Math.E, (8*(anCount-0.5)))))) * temperature;
			anCount+= 0.07f;
			System.out.println("anCount : " + anCount);
			System.out.println("temperature : " + trialTemperature);
			// sigmoid function
			// System.out.println("temperature : " +temperature);
		}
		return bestPath;
	}


	//Ȯ���� ���Ѵ�
	private double getAcceptProbability(int bestScore, int trialScore) {

		//���� ���ھ �� ũ�ٸ� 1�� ����
		if (bestScore > trialScore)
			return 1;
		else {
			//return 0;
			//�׷��� �ʴٸ� Ȯ�� p �� �Ŀ� �����Ͽ� �����Ѵ�.
			return Math.pow(Math.E, -(trialScore - bestScore) / this.temperature);
		}
	}
	
}
