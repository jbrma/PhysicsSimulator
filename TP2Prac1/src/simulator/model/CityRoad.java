package simulator.model;

public class CityRoad extends Road {

	public CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	void reduceTotalContamination() {
		// TODO Auto-generated method stub
		
		int x = condAtmNum(), tc = getTotalCO2();
		
		
		if(tc - x >= 0) {
			
			setTotalCO2(tc - x);
			
		}
		else {
			
			setTotalCO2(0);
		}
		
	}

	@Override
	void updateSpeedLimit() {
		// TODO Auto-generated method stub
		
		limiteActual = velocidadMax;
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		// TODO Auto-generated method stub
		
		int speed = 0;
		
		int f = v.getGradoCont(), s = limiteActual;
		
		if(v.getStatus() == VehicleStatus.TRAVELING) {
			
			speed = (int)(((11-f)*s)/11);
		}
		
		return speed;
	}
	
public int condAtmNum() {
		
		int num = 0;
			
		if(getWeather() == Weather.WINDY ||getWeather() == Weather.STORM)
			num = 10;
		else
			num = 2;
				
		return num;
		
	}
}
