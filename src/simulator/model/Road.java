package simulator.model;

import java.util.*;

import org.json.*;

public abstract class Road extends SimulatedObject{

	private Junction cruceDestino;
	private Junction cruceOrigen;
	private int longitud;
	protected int velocidadMax;
	protected int limiteActual; //limite velocidad
	private int limiteCont;
	private Weather condAmbiental;
	private int contTotal;
	private List<Vehicle> vehiculos;
	private String id;
	

	private CompareVehicles compara;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		
		super(id);
		// TODO Auto-generated constructor stub
		
		if(maxSpeed <= 0) throw new IllegalArgumentException("La velocidad maxima debe ser un valor positivo.");
		if(contLimit < 0) throw new IllegalArgumentException("El limite de contaminación debe ser no negativo.");
		if(length < 0) throw new IllegalArgumentException("La longitud de la carretera debe ser un valor positivo.");
		if(srcJunc == null || destJunc == null || weather == null) throw new IllegalArgumentException ("Los valores del cruce de origen, el cruce de destino o la condición meteorológica no pueden ser nulos.");
		
		this.id = id;
		setCruceOrigen(srcJunc);
		setCruceDestino(destJunc);
		velocidadMax = maxSpeed;
		limiteCont = contLimit;
		longitud = length;
		condAmbiental = weather;
		vehiculos = new ArrayList<Vehicle>();
		
		
	}

	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		reduceTotalContamination();
		updateSpeedLimit();
		
		
		for(Vehicle v: vehiculos) {
			
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
		
		compara = new CompareVehicles();
		vehiculos.sort(compara);
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		
		JSONObject json = new JSONObject();
		
		json.put("id", id);
		json.put("speedlimit", limiteActual);
		json.put("weather", condAmbiental);
		json.put("co2", contTotal);
		
		JSONArray jArray = new JSONArray();
		json.put("vehicles", jArray);
		for (Vehicle vehicle : vehiculos)
		{
			jArray.put(vehicle.getId());
		}
		
		return json;
	}

	/*se utiliza por los vehículos para entrar a la carretera*/
	void enter(Vehicle v) {
		
		if(v.getLocation() != 0) throw new IllegalArgumentException("La localización no puede ser distinta de 0");
		if(v.getSpeed() != 0) throw new IllegalArgumentException("La velocidad no puede ser distinta de 0");
		
		vehiculos.add(v);
	}

	/*lo utiliza un vehículo para abandonar la carretera.*/
	void exit(Vehicle v) {
		
		vehiculos.remove(v);
	}
	
	/* añade c unidades de CO2 al total de la contaminación
	de la carretera.*/
	public void addContamination(int c) {
		
		if(c < 0) throw new IllegalArgumentException("Las unidades de CO2 no pueden ser negativas");
		
		contTotal = contTotal + c;
	}
	
	
	
	abstract void reduceTotalContamination();
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);
	
	
	//GETTERS 
	public Junction getCruceOrigen() {
		return cruceOrigen;
	}
	public Junction getCruceDestino() {
		return cruceDestino;
	}

	public int getLength() {
		return longitud;
	}
	public int getTotalCO2() {
		return contTotal;
	}
	public Weather getWeather() {
		return condAmbiental;
	}
	public int getLimiteCont() {
		return limiteCont;
	}
	public int getVelocidadMax() {	
		return velocidadMax;
	}
	
	public String getId() {
		
		return id;
	}
	public List<Vehicle> getVehiculos() {
		return vehiculos;
	}
	
	
	
	//SETTERS

	public void setCruceOrigen(Junction origen) {
		this.cruceOrigen = origen;
	}
	
	public void setCruceDestino(Junction destino) {
		this.cruceDestino = destino;
	}
	
	public void setTotalCO2(int total) {
		this.contTotal = total;
	}
	
	public void setWeather(Weather w) {
		
		if(w == null) throw new IllegalArgumentException("La condición meteorológica no puede ser un valor nulo.");
		
		condAmbiental = w;
	}
	
	public void setLimiteCont(int limiteCont) {
		
		this.limiteCont = limiteCont;
	}
	
	public void setLimiteActual(int limiteActual) {
		
		this.limiteActual = limiteActual;
	}
	
	public void setVelocMaxima(int velocidadMaxima) {
		
		this.velocidadMax = velocidadMaxima;
	}
	
	public void setId(String id) {
		
		this.id = id;
	}
	public void setVehiculos(List<Vehicle> vehiculos) {
		
		this.vehiculos = vehiculos;
	}
	
}
