package simulator.factories;

import java.util.*;

import org.json.*;

import simulator.misc.*;
import simulator.model.*;

public class SetWeatherEventBuilder extends Builder<Event> {

	private int time;
	private List<Pair<String, Weather>> listaClima;
	
	public SetWeatherEventBuilder() {
		super("set_weather");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		time = data.getInt("time");
		
		JSONArray array = data.getJSONArray("info");
		listaClima = new ArrayList<Pair<String, Weather>>();
		
		for (int i = 0; i < array.length(); i++)
			listaClima.add(new Pair<String, Weather>(array.getJSONObject(i).getString("road"), Weather.valueOf(array.getJSONObject(i).getString("weather"))));
		
		return new SetWeatherEvent(time, listaClima);
	}

}
