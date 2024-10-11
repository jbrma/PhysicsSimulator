package simulator.model;

import java.util.List;
import simulator.misc.*;

public class NewtonUniversalGravitation implements ForceLaws {

	private double G;
	
	public NewtonUniversalGravitation(double _G) {
		if(_G <= 0) {
			throw new IllegalArgumentException("G value is non-positive");
		}
		this.G = _G;
	}
	
	@Override
	public void apply(List<Body> bs) {
		
		Vector2D dir = new Vector2D(); // Direccion auxiliar
		double f;
		
		for(int i = 0; i < bs.size(); i++) {
			
			if(bs.get(i).getMass() != 0){
			
			for(int j = 0; j < bs.size(); j++) {
				
				if(bs.get(i) != bs.get(j)) { // Comprueba que no es el mismo cuerpo
					
					if(bs.get(j).getPosition().distanceTo(bs.get(i).getPosition()) == 0) {
						
						bs.get(i).addForce(dir.direction().scale(0));
					}
					else {
					 f = (G * bs.get(i).getMass() * bs.get(j).getMass() / Math.pow(bs.get(i).getPosition().distanceTo(bs.get(j).getPosition()), 2));
					 dir = bs.get(j).getPosition().minus(bs.get(i).getPosition());
					 bs.get(i).addForce(dir.direction().scale(f));
					}
					
					}
			 	}
			}
		}
	}
	
	public String toString() {
		
		return "Newton’s Universal Gravitation with G=" + G;
	}
}
