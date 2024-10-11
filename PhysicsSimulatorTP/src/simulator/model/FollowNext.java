package simulator.model;

import java.util.List;

import simulator.misc.*;

public class FollowNext implements ForceLaws{

	private double g;
	private Vector2D c;
	private double d;
	
	public FollowNext(double _g, double _d, Vector2D _c) {
		
		this.c = _c;
		this.g = _g;
		this.d = _d;
	}
	
	@Override
	public void apply(List<Body> bs) {
		
		Vector2D dir = new Vector2D().direction(); // Direccion auxiliar c - pi
		
		for(int i = 0; i < bs.size() - 1; i++) {
			
			aplica(bs.get(i), bs.get(i + 1));
		}
		
		//Para añadir la fuerza al ultimo cuerpo con direccion el primero
		aplica(bs.get(bs.size() - 1), bs.get(0));
	}
	
	public void aplica(Body actual, Body sig) {
		
		Vector2D dir = new Vector2D().direction();
		Vector2D posActual = actual.getPosition();
		Vector2D posSig = sig.getPosition();
		
		if(posActual.distanceTo(posSig) > d) {
			
			dir = posSig.minus(posActual.direction());
		
		}
		else {
			dir = c.minus(posActual.direction());
		}
			 
		 actual.addForce(dir.scale(sig.getMass() * this.g));
	
	}
	
	public String toString() {
		
		return "Moving towards " + c + " with constant acceleration " + g;
	}
}
