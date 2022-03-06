package simulator.factories;

import java.util.*;

import org.json.*;

import simulator.misc.*;
import simulator.model.*;

public class SetContClassEventBuilder extends Builder<Event> {

	private int time;
	private List<Pair<String, Integer>> listaCont;
	
	public SetContClassEventBuilder() {
	
		super("set_cont_class");
	}


	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		time = data.getInt("time");
		JSONArray array = data.getJSONArray("info");
		listaCont = new ArrayList<Pair<String, Integer>>();
		
		for (int i = 0; i < array.length(); i++)
			listaCont.add(new Pair<String, Integer>(array.getJSONObject(i).getString("vehicle"), array.getJSONObject(i).getInt("class")));
		
		return new NewSetContClassEvent(time, listaCont);
	}
}
