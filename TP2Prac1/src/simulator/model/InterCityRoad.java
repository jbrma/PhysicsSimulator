package simulator.model;

public class InterCityRoad extends Road {

	public InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	void reduceTotalContamination() {
		// TODO Auto-generated method stub
		int tc = getTotalCO2(), x = condAtmNum();
		double total = 0;

		total = ((100.0 - x) / (100.0) * tc);
		
		setTotalCO2((int)(total));
	}

	@Override
	void updateSpeedLimit() {
		// TODO Auto-generated method stub
		
		if(getTotalCO2() > getLimiteCont()) {
			setLimiteActual((int)(getVelocidadMax()*0.5));
		}
		else {
			setLimiteActual(getVelocidadMax());
		}
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		// TODO Auto-generated method stub	
		
		if(getWeather() == Weather.STORM ) {
			
			return (int) (limiteActual*0.8);
		}
		else {
			return limiteActual;
		}
	}
	
	public int condAtmNum() {
		
		int num = 0;
		
		
		if(getWeather() == Weather.SUNNY)
			num = 2;
		else if (getWeather() == Weather.CLOUDY)
			num = 3;
		else if(getWeather() == Weather.RAINY)
			num = 10;
		else if(getWeather() == Weather.WINDY)
			num = 15;
		else if(getWeather() == Weather.STORM)
			num = 20;
		
			
		return num;
		
	}
}
