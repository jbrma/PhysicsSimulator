package simulator.model;

import java.util.*;
import org.json.*;
import simulator.misc.*;

public class TrafficSimulator {

	private RoadMap mapaCarreteras;
	private List<Event> listaEventos;
	private int tiempoSim;
	
	public TrafficSimulator() {
		
		listaEventos = new SortedArrayList<Event>();
		mapaCarreteras = new RoadMap();
		tiempoSim = 0;
	}
	
	/*añade el evento e a la lista de eventos.*/
	public void addEvent(Event e) {
		
		listaEventos.add(e);
	}
	
	/* avanza el estado de la simulación*/
	public void advance() {
		
		tiempoSim += 1;
		
		while(listaEventos.size()> 0 && listaEventos.get(0).getTime() == tiempoSim)
			listaEventos.remove(0).execute(mapaCarreteras);
		
		for(Junction cruce : mapaCarreteras.getJunctions())
			cruce.advance(tiempoSim);
		
		for(Road carretera : mapaCarreteras.getRoads())
			carretera.advance(tiempoSim);
	}
	
	/* limpia el mapa de carreteras y la lista de eventos, y pone el tiempo
	de la simulación a 0*/
	
	public void reset() {
		
		mapaCarreteras.reset();
		listaEventos.clear();
		tiempoSim = 0;
	}
	
	public JSONObject report() {
		
		JSONObject j = new JSONObject();
		
		j.put("time", tiempoSim);
		j.put("state", mapaCarreteras.report());
		
		return j;
	}
}
