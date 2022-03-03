package simulator.model;

import java.util.*;

public class MoveAllStrategy implements DequeuingStrategy{

	public MoveAllStrategy() {
		
	}
	
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		// TODO Auto-generated method stub
		
		List<Vehicle> vehiculos = new ArrayList<Vehicle>();
		
		for(int i = 0; i < q.size(); i++) {
			
			vehiculos.add(q.get(i));
		}	
		
		return vehiculos;
	}

}
