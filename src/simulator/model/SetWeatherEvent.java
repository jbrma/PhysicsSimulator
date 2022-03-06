package simulator.model;

import java.util.*;

import simulator.misc.*;

public class SetWeatherEvent extends Event {

	private int time;
	private List<Pair<String,Weather>> listaTiempo;
	
	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		super(time);
		// ...
		
		if(ws == null) throw new IllegalArgumentException("La no puede tener valor null.");
		
		this.time = time;
		listaTiempo = ws;
		
		}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		
		for(Pair<String,Weather> w: listaTiempo) {
			
			if(map.getRoad(w.getFirst()) == null) throw new IllegalArgumentException("La carretera no existe en el mapa de carreteras");
			
			map.getRoad(w.getFirst()).setWeather(w.getSecond());
			
		}
	}

}
