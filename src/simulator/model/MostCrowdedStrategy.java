package simulator.model;

import java.util.*;

public class MostCrowdedStrategy implements LightSwitchingStrategy {

	private int ticks;
	
	public MostCrowdedStrategy(int timeSlot) {
		
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
			
			int pos = 0;
			
			for(int i = 0; i < qs.size(); i++) {
				
				if(qs.get(i).size() >= qs.get(i + 1).size()) {
					
					pos = i;
				}
				else {
					pos = i + 1;
				}
			}
			
			return pos;
		}
		
		else if(currTime - lastSwitchingTime < ticks) {
			
			return currGreen;
		}
		
		else {
			
			int pos = 0;
			int busca = (currGreen + 1)%qs.size();
			
			for(int i = 0; i < qs.size(); i++) {
				
				if(qs.get(busca).size() >= qs.get(busca + 1).size()) {
					
					pos = busca;
				}
				else {
					pos = busca + 1;
				}
				
				busca++;
				
				if(busca == qs.size())
					busca = 0;
			}
			
			return pos;
		}
	}
}
