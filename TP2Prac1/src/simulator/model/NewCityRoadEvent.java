package simulator.model;

public class NewCityRoadEvent extends Event {
	
	private int time;
	private String id;
	private String origen;
	private String destino;
	private int longitud;
	private int CO2limite;
	private int velocidadMax;
	private Weather clima;

	NewCityRoadEvent(int time, String id, String srcJunc, String
			destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		// TODO Auto-generated constructor stub
		
		if(time < 0) throw new IllegalArgumentException("El tiempo debe ser un valor positivo.");
		if(maxSpeed <= 0) throw new IllegalArgumentException("La velocidad maxima debe ser un valor positivo.");
		if(co2Limit < 0) throw new IllegalArgumentException("El limite de contaminación debe ser positivo.");
		if(length < 0) throw new IllegalArgumentException("La longitud de la carretera debe ser un valor positivo.");
		if(srcJunc == null || destJunc == null || weather == null) throw new IllegalArgumentException ("Los valores del cruce de origen, el cruce de destino o la condición meteorológica no pueden ser nulos.");
		
		this.time = time;
		this.id = id;
		origen = srcJunc;
		destino = destJunc;
		longitud = length;
		CO2limite = co2Limit;
		velocidadMax = maxSpeed;
		clima = weather;
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		Road r = new CityRoad(id, map.getJunction(origen), map.getJunction(destino), velocidadMax, CO2limite, longitud, clima );
		map.addRoad(r);
	}

}
