package simulator.model;

import org.json.JSONObject;
import java.util.*;

public class Vehicle extends SimulatedObject {
	
	private List<Junction> itinerario;
	private int velocidadMax;
	private int velocidadActual;
	private VehicleStatus estado;
	private Road carretera;
	private int localizacion = 0;
	private int gradoContaminacion;
	private int totalContaminacion;
	private int totalDistancia;
	private String id;
	
	private int indCruce;
	private Junction origenJunction = null;
	private Junction destinoJunction = null;
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		// TODO Auto-generated constructor stub
		
		if (maxSpeed <= 0) throw new IllegalArgumentException("La velocidad maxima debe ser un valor positivo.");
		if (contClass < 0 || contClass > 10) throw new IllegalArgumentException("El grado de contaminacion debe ser un valor entre 0 y 10.");
		if (itinerary.size() < 2) throw new IllegalArgumentException("El itinerario debe tener un valor de al menos 2 cruces.");
		
		
		this.id = id;
		velocidadMax = maxSpeed;
		gradoContaminacion = contClass;
		itinerario = Collections.unmodifiableList(new ArrayList<>(itinerary));
		
		
	}

	/* Pone la velocidad actual al valor mínimo entre s y la velocidad
	máxima del vehículo. Lanza una excepción si s es negativo.
	 */	
	void setSpeed(int s) {
		
		if(s < 0) throw new IllegalArgumentException("La velocidad debe ser un valor positivo.");
		
		if(s <= velocidadMax) {
			velocidadActual = s;
		}
		else {
			velocidadActual = velocidadMax;
		}
	}
	
	/*pone el valor de contaminación del vehículo a c.
	Lanza una excepción si c no es un valor entre 0 y 10 (ambos incluidos).
	 */
	void setContaminationClass(int c) {
		
		if(c < 0 || c > 10) throw new IllegalArgumentException(" El grado de contaminacion debe ser un valor entre 0 y 10.");
		
		else {
			
			gradoContaminacion = c;
		}
		
	}
	
	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		
		int locAux = localizacion + velocidadActual;
		int c = 0; //Contaminacion producida
		
		if(estado == VehicleStatus.TRAVELING) {
			
			if(locAux > carretera.getLength()) {
				
				locAux = carretera.getLength();
			}
			
			c = (locAux - localizacion) * gradoContaminacion;
			totalDistancia += (locAux - localizacion);
			totalContaminacion += c;
			carretera.addContamination(c);
			localizacion = locAux;
			
			if(locAux >= carretera.getLength()) {
				carretera.getCruceDestino().enter(this);
				estado =  VehicleStatus.WAITING;
				velocidadActual = 0; 
				indCruce++;
			}
			
		}
		else {
			
			velocidadActual = 0;		
		}
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		
		json.put("id", id);
		json.put("speed", velocidadActual);
		json.put("distance", totalDistancia);
		json.put("co2", totalContaminacion);
		json.put("class", gradoContaminacion);
		json.put("status", estado);
		
		if (estado != VehicleStatus.PENDING && estado != VehicleStatus.ARRIVED)
		{
			json.put("road", carretera);
			json.put("location", localizacion);
		}		
		
		return json;
	}
	
	
	
	/* Mueve el vehículo a la siguiente carretera. Este proceso
	se hace saliendo de la carretera actual y entrando a la siguiente carretera de su
	itinerario, en la localización 0.
	*/
	void moveToNextRoad() {

		if(estado != VehicleStatus.WAITING && estado != VehicleStatus.PENDING) throw new IllegalArgumentException("No se puede pasar a la siguiente carretera con este estado");
		
		if(carretera != null) {
			carretera.exit(this);
		}
		
		if(indCruce == itinerario.size() - 1) {
			
			localizacion = 0;
			carretera = null;
			estado = VehicleStatus.ARRIVED;
		}
		else {
			origenJunction = itinerario.get(indCruce);
			destinoJunction = itinerario.get(indCruce + 1);
			localizacion = 0;
			Road siguiente = origenJunction.roadTo(destinoJunction);
			siguiente.enter(this);
			carretera = siguiente;
			estado = VehicleStatus.TRAVELING;
			
		}
	}

	
	
	//GETTERS
	
	public int getLocation() {
		return localizacion;	
	}
	
	public int getSpeed() {
		return velocidadActual;
	}
	
	public int getMaxSpeed() {
		return velocidadMax;
	}
	
	public int getGradoCont() {
		return gradoContaminacion;
	}

	public VehicleStatus getStatus() {
		return estado;
	}

	public int getTotalCO2() {
		return totalContaminacion;
	}

	public List<Junction> getItinerary() {
		return itinerario;
	}

	public Road getRoad() {
		return carretera;	
	}
	
	
	
}
