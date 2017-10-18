package ga;

import java.util.Comparator;

public class GAElementComparator implements Comparator<GAElement>{

	@Override
	public int compare(GAElement o1, GAElement o2) {
		// TODO Auto-generated method stub
		
		return o1.getCost() < o2.getCost() ? -1 :(o1.getCost() == o2.getCost() ? 0: 1);
	}
	
}
