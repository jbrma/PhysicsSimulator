package simulator.model;

import java.util.*;
import org.json.*;

public class RoadMap {
	
	private List<Junction> listaCruces;
	private List<Road> listaCarreteras;
	private List<Vehicle> listaVehiculos;
	private Map<String,Junction> mapaCruces;
	private Map<String,Road> mapaCarreteras;
	private Map<String,Vehicle> mapaVehiculos;
	
	protected RoadMap() {
		
		listaCruces = new ArrayList<Junction>();
		listaCarreteras = new ArrayList<Road>();
		listaVehiculos = new ArrayList<Vehicle>();
		mapaCruces = new HashMap<String, Junction>();
		mapaCarreteras = new HashMap<String, Road>();
		mapaVehiculos = new HashMap<String, Vehicle>();
	}
	
	/*añade el cruce j al final de la lista de cruces y modifica
	el mapa correspondiente.*/
	void addJunction(Junction j) {
		
		if(!mapaCruces.containsKey(j.getId())) {
			
			mapaCruces.put(j.getId(), j);
			listaCruces.add(j);
		}
		else throw new IllegalArgumentException("Ya existe un cruce con el mismo identificador.");	
	}
	
	/* añade la carretera r al final de la lista de carreteras y modifica
	el mapa correspondiente.*/
	void addRoad(Road r) {
		
		if(listaCarreteras.contains(r.getId()) && mapaCruces.containsValue(r.getCruceOrigen()) && mapaCruces.containsValue(r.getCruceDestino())) throw new IllegalArgumentException ("Ya existe una carrera con el mismo identificador.");
		
		listaCarreteras.add(r);
		mapaCarreteras.put(r.getId(), r);
	}
	
	/*añade el vehículo v al final de la lista de vehículos y
	modifica el mapa de vehículos en concordancia.*/
	void addVehicle(Vehicle v) {
		
		List<Junction> lista = v.getItinerary();
		
		if (listaVehiculos.contains(v))
		{
			throw new IllegalArgumentException("Ya existe un vehiculo con id "+ v.getId() +".");
		}
		
		for (int i = 0; i < lista.size()-1; i++)
		{
			if (lista.get(i).roadTo(lista.get(i+1)) == null)
			{
				throw new IllegalArgumentException("El itineario es nulo.");
			}
		}
		
		listaVehiculos.add(v);
		mapaVehiculos.put(v.getId(), v);
	}
	
	//GETTERS
	
	
	public Junction getJunction(String id) {
		
		return mapaCruces.get(id);
	}
	
	public Road getRoad(String id) {
		
		if(listaCarreteras.contains(mapaCarreteras.get(id)))
		{
			return mapaCarreteras.get(id);
		}
		
		return null;
	}
	
	public Vehicle getVehicle(String id) {
		
		if(listaVehiculos.contains(mapaVehiculos.get(id)))
			return mapaVehiculos.get(id);
		
		return null;
	}
	
	public List<Junction> getJunctions() {
		
		return Collections.unmodifiableList(listaCruces);
	}
	
	public List<Road> getRoads() {
		
		return Collections.unmodifiableList(listaCarreteras);
	}
	
	public List<Vehicle> getVehicles() {
		
		return Collections.unmodifiableList(listaVehiculos);
	}
	
	
	/* Limpia todas las listas y mapas */
	void reset() {
		
		listaCruces.clear();
		listaCarreteras.clear();
		listaVehiculos.clear();
		mapaCruces.clear();
		mapaCarreteras.clear();
		mapaVehiculos.clear();
	}
	
	public JSONObject report() {

		JSONObject json = new JSONObject();
		JSONArray Array = new JSONArray();
		JSONArray ArrayVehicle = new JSONArray();
		JSONArray ArrayRoad = new JSONArray();
		
		json.put("junctions", Array);
		for (Junction junction : listaCruces)
		{
			Array.put(junction.report());
		}
		
		json.put("roads", ArrayRoad);
		for (Road roads : listaCarreteras)
		{
			ArrayRoad.put(roads.report());
		}
		
		json.put("vehicles", ArrayVehicle);
		for (Vehicle vehicle : listaVehiculos)
		{
			ArrayVehicle.put(vehicle.report());
		}
		
		return json;
	}
}
