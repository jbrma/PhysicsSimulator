package simulator.model;

import simulator.misc.Vector2D;

public class StationaryBody extends Body{

	
	public StationaryBody(String _id, String _gid, Vector2D _p, Double _m) {
		super(_id, _gid, _p, new Vector2D(), _m);
	}

	@Override
	void advance(double dt) {
		// No hace nada
	}
}
