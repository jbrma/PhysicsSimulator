package simulator.model;

import java.util.*;

public class NewVehicleEvent extends Event {

	private int time;
	private String id;
	private int velocidadMax;
	private int grContaminacion;
	private List<String> itinerario;
	
	public NewVehicleEvent(int time, String id, int maxSpeed, 
			int contClass, List<String> itinerary) {
			super(time);
			// ...
		
		if(time < 0) throw new IllegalArgumentException("El tiempo debe ser un valor positivo.");
		if (maxSpeed <= 0) throw new IllegalArgumentException("La velocidad maxima debe ser un valor positivo.");
		if (contClass < 0 || contClass > 10) throw new IllegalArgumentException("El grado de contaminacion debe ser un valor entre 0 y 10.");
		if (itinerary.size() < 2) throw new IllegalArgumentException("El itinerario debe tener un valor de al menos 2 cruces.");
		
		
		this.time = time;
		this.id = id;
		velocidadMax = maxSpeed;
		grContaminacion = contClass;
		itinerario = itinerary;
		
		}
	

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		
		List<Junction> listaAux = new ArrayList<Junction>();
		
		for(String s : itinerario)
			listaAux.add(map.getJunction(s));
		
		Vehicle v = new Vehicle(id, velocidadMax, grContaminacion, listaAux);
		map.addVehicle(v);
		v.moveToNextRoad();
	}

}
