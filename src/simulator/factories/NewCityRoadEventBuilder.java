package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends Builder<Event> {
	
	private int time;
	private String id;
	private String origen;
	private String destino;
	private int longitud;
	private int limiteCO2;
	private int velocidadMax;
	private Weather clima;
	
	public NewCityRoadEventBuilder() {
		super("new_city_road");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		time = data.getInt("time");
		id = data.getString("id");
		origen = data.getString("src");
		destino = data.getString("dest");
		longitud = data.getInt("length");
		limiteCO2 = data.getInt("co2limit");
		velocidadMax = data.getInt("maxspeed");
		clima = Weather.valueOf(data.getString("weather"));
		
		return new NewCityRoadEvent(time, id, origen, destino, longitud, limiteCO2, velocidadMax, clima);
	}

}
