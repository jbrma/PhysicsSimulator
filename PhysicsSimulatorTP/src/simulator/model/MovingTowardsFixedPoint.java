package simulator.model;

import java.util.List;

import simulator.misc.*;

public class MovingTowardsFixedPoint implements ForceLaws{

	private double g;
	private Vector2D c;
	
	public MovingTowardsFixedPoint(Vector2D _c, double _g) {
		
		if(_c == null) {
			throw new IllegalArgumentException("c value is null");
		}
		if(_g <= 0) {
			throw new IllegalArgumentException("g value is non-positive");
		}
		this.c = _c;
		this.g = _g;
	}
	
	@Override
	public void apply(List<Body> bs) {
		
		Vector2D dir = new Vector2D().direction(); // Direccion auxiliar c - pi
		
		
		for(Body b : bs) {

			 dir = c.minus(b.getPosition().direction());
			 b.addForce(dir.scale(b.getMass() * this.g));
		}
	}
	
	public String toString() {
		
		return "Moving towards " + c + " with constant acceleration " + g;
	}
}
