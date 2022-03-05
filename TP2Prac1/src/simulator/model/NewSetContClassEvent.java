package simulator.model;

import java.util.List;

import simulator.misc.*;

public class NewSetContClassEvent extends Event {

	private int time;
	private List<Pair<String,Integer>> listaCont;
	
	public NewSetContClassEvent(int time, List<Pair<String,Integer>> cs) {
		super(time);
		// TODO Auto-generated constructor stub
		
		if(cs == null) throw new IllegalArgumentException("La lista no puede estar vacía.");
		
		this.time = time;
		listaCont = cs;
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		
		for(Pair<String,Integer> c: listaCont){
			
			if(map.getVehicle(c.getFirst()) == null) throw new IllegalArgumentException ("El vehiculo no existe en el mapa de carreteras.");
			
			map.getVehicle(c.getFirst()).setContaminationClass(c.getSecond());
		}
	}
	

}
