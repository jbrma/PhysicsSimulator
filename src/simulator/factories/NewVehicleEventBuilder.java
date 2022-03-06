package simulator.factories;

import java.util.*;

import org.json.*;

import simulator.model.*;

public class NewVehicleEventBuilder extends Builder<Event>{

	private int time;
	private String id;
	private int grContaminacion;
	private int velocidadMax;
	private List<String> itinerario;
	
	
	public NewVehicleEventBuilder() {
		super("new_vehicle");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub

		time = data.getInt("time");
		id = data.getString("id");
		velocidadMax = data.getInt("maxspeed");
		grContaminacion = data.getInt("class");
		itinerario = new ArrayList<String>();
		
		JSONArray array = data.getJSONArray("itinerary");
		
		for (int i = 0; i < array.length(); i++)
			itinerario.add(array.getString(i));
		
		return new NewVehicleEvent(time, id, velocidadMax, grContaminacion, itinerario);
	}
}
