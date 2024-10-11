package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class MovingBody extends Body {

	public MovingBody(String _id, String _gid, Vector2D _p, Vector2D _v, double _m) {
		super(_id, _gid, _p, _v, _m);
		
	}

	@Override
	void advance(double dt) {
		
		Vector2D a = new Vector2D();
		
		if (m > 0) {
		
			a = f.scale(1/m);
        }
		
		
        Vector2D newP = p.plus(v.scale(dt)).plus(a.scale(dt*dt*0.5));
        Vector2D newV = v.plus(a.scale(dt));
        p = newP;
        v = newV;
	}
	
   }
