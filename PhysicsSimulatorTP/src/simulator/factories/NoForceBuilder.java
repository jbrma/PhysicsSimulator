package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws>{

	private JSONObject data = getInfo();
	
	public NoForceBuilder() {
		super("nf", "No Force");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		
		ForceLaws f;
		
		f = new NoForce();
		
		return f;
	}
	
	

	@Override
	public JSONObject getInfoData() {
		JSONObject j = new JSONObject();
		return j;
	}

}
