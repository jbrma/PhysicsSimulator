package simulator.model;

import java.util.*;

public class CompareVehicles implements Comparator<Vehicle> {

	public CompareVehicles() {
		
	}
	
	@Override
	public int compare(Vehicle o1, Vehicle o2) {
		// TODO Auto-generated method stub
		
		if(o1.getLocation() > o2.getLocation())
			return -1;
		if(o1.getLocation() < o2.getLocation())
			return 1;
		
		
		return 0;
	}

}
