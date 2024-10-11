package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.StationaryBody;

public class StationaryBodyBuilder extends Builder<Body> {

	protected String id;
	protected String gid;
	protected Vector2D p;
	protected double m;
	
	public StationaryBodyBuilder() {
		super("st_body", "Stationary Body Builder");
	}

	@Override
	protected Body createInstance(JSONObject data) {
		Body b;
		
		
		if(data.has("id") && data.has("gid") && data.has("p") && data.has("m")) {
			
			double v1 = data.getJSONArray("p").getDouble(0);
			double v2 = data.getJSONArray("p").getDouble(1);
		    
			if(data.getJSONArray("p").length() > 2) {
				
				throw new IllegalArgumentException("P must be a 2D");
			}
			
		     this.id = data.getString("id");
		     this.gid = data.getString("gid");
		     this.p = new Vector2D(v1, v2);
		     this.m = data.getDouble("m");
		     
		     
		     b = new StationaryBody(this.id, this.gid, this.p, this.m);
		    
		}
		else {
			throw new IllegalArgumentException("Must have all data");
		}
	
		return b;
	}

	@Override
	public JSONObject getInfoData() {
		return null;
	}

}
