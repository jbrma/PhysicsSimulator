package simulator.factories;

import org.json.JSONObject;

import simulator.model.*;

public class NewJunctionEventBuilder extends Builder<Event> {

	private Factory<LightSwitchingStrategy> lightStrat;
	private Factory<DequeuingStrategy> dequeueStrat;
	private int time;
	private String id;
	private int xCoor;
	private int yCoor;
	private LightSwitchingStrategy ls_strategy;
	private DequeuingStrategy dq_strategy;
	
	
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, 
									Factory<DequeuingStrategy> dqsFactory) {
		super("new_junction");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		time = data.getInt("time");
		id = data.getString("id");
		xCoor = data.getJSONArray("coor").getInt(0);
		yCoor = data.getJSONArray("coor").getInt(1);	
		
		JSONObject lStrat = data.getJSONObject("ls_strategy");
		JSONObject dStrat = data.getJSONObject("dq_strategy");
			
		ls_strategy = lightStrat.createInstance(lStrat);
		dq_strategy = dequeueStrat.createInstance(dStrat);
		
		return new NewJunctionEvent(time, id, ls_strategy, dq_strategy, xCoor, yCoor);
	}

}
