package ga;

import tspUtil.PathCheck;

import java.util.Arrays;
import java.util.Random;

public class GAElement {
	private int [] path;
	private int cost;
	private float fitness;

	public void init(int[] path){
		this.path = path;
		this.cost = PathCheck.getPathCost(path);
		this.fitness = (1.0f / cost) * 10;
	}

	public void init(){
		this.cost = PathCheck.getPathCost(path);
		this.fitness = (1.0f / cost) * 10;
	}

	public int[] getPath(){
		return path;
	}

	public int getCost(){
		return cost;
	}

	public float getFitness(){
		return fitness;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		GAElement cloneObj = new GAElement();
		cloneObj.cost = this.cost;
		cloneObj.path = Arrays.copyOf(this.path, path.length);
		
		return cloneObj;
	}
	
}
