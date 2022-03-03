package simulator.model;

import java.util.*;

public class RoundRobinStrategy implements LightSwitchingStrategy {
	
	private int ticks;
	
	public RoundRobinStrategy(int timeSlot) {
		
		ticks = timeSlot;
	}
	
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		// TODO Auto-generated method stub
		
		if(roads.isEmpty()) {
			
			return -1;
		}
		else if(currGreen == -1) {
			
			return 0;
		}
		else if(currTime - lastSwitchingTime < ticks) {
			
			return currGreen;
		}
		
		return (currGreen + 1)%roads.size();
	}

}
