package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MovingBody;

public class MovingBodyBuilder extends Builder<Body> {

	protected String id;
	protected String gid;
	protected Vector2D v;
	protected Vector2D p;
	protected double m;
	
	
	public MovingBodyBuilder() {
		super("mv_body", "Moving body");
		
	}

	@Override
	protected Body createInstance(JSONObject data) {
		
		Body b;
		
		if(data.has("id") && data.has("gid") && data.has("p") && data.has("v") && data.has("m")) {
			
		    
			double v1 = data.getJSONArray("p").getDouble(0);
			double v2 = data.getJSONArray("p").getDouble(1);
			
			double v3 = data.getJSONArray("v").getDouble(0);
			double v4 = data.getJSONArray("v").getDouble(1);
			
			if(data.getJSONArray("p").length() > 2 || data.getJSONArray("v").length() > 2) {
				
				throw new IllegalArgumentException("P or/and V must be a 2D");
			}
			
		     this.id = data.getString("id");
		     this.gid = data.getString("gid");
		     this.p = new Vector2D(v1, v2);
		     this.v = new Vector2D(v3, v4);
		     this.m = data.getDouble("m");
		     
		     b = new MovingBody(this.id, this.gid, this.p, this.v, this.m);
		    
		}
		else {
			throw new IllegalArgumentException("Must have all data");
		}
	
		return b;
	}

	@Override
	public JSONObject getInfoData() {
		// TODO Auto-generated method stub
		return null;
	}

}
