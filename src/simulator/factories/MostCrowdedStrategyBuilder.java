package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {

	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		 MostCrowdedStrategy strat;
		 
		 if(data.has("timeslot"))
			 strat = new MostCrowdedStrategy(data.getInt("timeslot"));
		 else
			 strat = new MostCrowdedStrategy(1);
		 
		 return strat;
		 
	}

}
