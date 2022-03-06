package simulator.control;

import java.io.*;

import org.json.*;

import simulator.factories.*;
import simulator.model.*;

public class Controller {
	
	private TrafficSimulator ts;
	private Factory<Event> eventos;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory){
	// TODO complete
		
		if (sim == null) {throw new IllegalArgumentException("El valor de Traffic Simulator no puede ser null.");}
		if (eventsFactory == null) {throw new IllegalArgumentException("El valor de Events Factory no puede ser null.");}
		
		this.ts = sim;
		this.eventos = eventsFactory;
	}
	
	public void loadEvents(InputStream in) {
		
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray arr = jo.getJSONArray("events");
		
		
		if(!jo.has("events")) throw new IllegalArgumentException ("La entrada JSON no encaja.");
		
		for(int i = 0; i < arr.length(); i++)
			ts.addEvent(eventos.createInstance(arr.getJSONObject(i)));
	}
	
	public void run(int n, OutputStream out) {
		
		JSONObject outObject = new JSONObject();
		JSONArray arr = new JSONArray();
		
		for (int i = 0; i < n; i++) 
		{
			ts.advance();
			arr.put(ts.report());
		}
		
		outObject.put("states", arr);
		
		PrintStream p = new PrintStream(out);
		p.println(outObject.toString());
	}
	
	public void reset() {
		
		ts.reset();
	}
	
}
