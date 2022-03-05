package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		
		RoundRobinStrategy strat;
		
		if(data.has("timeslot"))
			strat = new RoundRobinStrategy(data.getInt("timeslot"));
		else
			strat = new RoundRobinStrategy(1);
		
		return strat;
	}

}
