package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.FollowNext;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class FollowNextBuilder extends Builder<ForceLaws>{

	protected Vector2D c;
	protected double g;
	protected double d;
	
	public FollowNextBuilder() {
		super("follow_next", "Follow Next");
		
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		
		ForceLaws f;
		
		
		if(data.has("c")) {
			
			double v1 = data.getJSONArray("c").getDouble(0);
			double v2 = data.getJSONArray("c").getDouble(1);

			if(data.getJSONArray("c").length() > 2) {
				
				throw new IllegalArgumentException("c must be a 2D");
			}
			
			this.c = new Vector2D(v1 , v2);	
			    
		}
		else {
			this.c = new Vector2D();
		}
		     
		this.g = data.has("g") ? data.getDouble("g") : 10.0;
		this.d = data.has("d") ? data.getDouble("d") : 1.5E10;
	
		if(this.g < 0) {
			throw new IllegalArgumentException("g must be positive");
		}
		
		if(this.d < 0) {
			throw new IllegalArgumentException("d must be positive");
		}
		
		f = new FollowNext(this.g, this.d, this.c);
		
		return f;
	}
	
	@Override
	public JSONObject getInfoData() {
		JSONObject j = new JSONObject();
		j.put("c", "the point towards which bodies move (e.g., [100.0,50.0]");
		j.put("g", "the length of the acceleration vector (a number)");
		j.put("d", "the distance with the next body");
		return j;
	}

}
