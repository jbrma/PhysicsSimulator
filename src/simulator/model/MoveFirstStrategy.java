package simulator.model;

import java.util.*;

public class MoveFirstStrategy implements DequeuingStrategy {

	public MoveFirstStrategy() {
		
	}
	
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		// TODO Auto-generated method stub
		
		List<Vehicle> primero = new ArrayList<Vehicle>();
		
		if(!q.isEmpty()) {
			
			primero.add(q.get(0));
		}
			
		return primero;
	}

}
