package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {

	protected double G;
	
	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Newton's law of Universal Gravitation");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		ForceLaws f;
		
		if(data.has("G")) {
			
			this.G = data.getDouble("G");
			
		    f = new NewtonUniversalGravitation(G);
		    
		}
		else {
			f = new NewtonUniversalGravitation(6.67E-11);
		}
	
		return f;
	}


	@Override
	public JSONObject getInfoData() {
		JSONObject j = new JSONObject();
		j.put("G", "the gravitational constant (a number)");
		return j;
	}
}
