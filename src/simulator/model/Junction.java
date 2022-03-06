package simulator.model;

import java.util.*;

import org.json.*;

public class Junction extends SimulatedObject {

	
	private List<Road> listaEntrantes;
	private Map<Junction,Road> mapaSalientes;
	private List<List<Vehicle>> listaColas; //lista de colas para carreteras entrantes
	private Map<Road,List<Vehicle>> carreteraCola;
	private int indSemaforo;
	private int cambioSemaforo;
	private LightSwitchingStrategy estrategiaColor;
	private DequeuingStrategy estrategiaElimina;
	private int x;
	private int y;
	private String id;
	
	protected Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id);
		// TODO Auto-generated constructor stub
		
		if( lsStrategy == null || dqStrategy == null) throw new IllegalArgumentException("Ninguna de las estrategias puede ser null.");
		if( xCoor < 0 || yCoor < 0) throw new IllegalArgumentException("Ninguna de las coordenadas puede ser negativa");
		
		listaEntrantes = new ArrayList<Road>();
		mapaSalientes = new HashMap<Junction, Road>();
		listaColas = new ArrayList<List<Vehicle>>();
		carreteraCola = new HashMap<Road, List<Vehicle>>();
		
		estrategiaColor = lsStrategy;
		estrategiaElimina = dqStrategy;
		x = xCoor;
		y = yCoor;
		this.id = id; 
	}

	/*añade r al final de la lista de carreteras entrantes,
	crea una cola (una instancia de LinkedList por ejemplo) para r y la añade al final de
	la lista de colas.*/
	void addIncomingRoad(Road r) {
		
		if(!this.equals(r.getCruceDestino())) throw new IllegalArgumentException("El cruce de destino no es igual al cruce actual.");
		
		listaEntrantes.add(r);
		List<Vehicle> cola_r = new LinkedList<Vehicle>();
		listaColas.add(cola_r);
		carreteraCola.put(r, cola_r);
	}
	
	/*añade el par (j,r) al mapa de carreteras salientes,
	donde j es el cruce destino de la carretera r.*/
	void addOutGoingRoad(Road r) {
		
		if(mapaSalientes.get(r.getCruceDestino()) != null) throw new IllegalArgumentException("Existe otra carretera que va al cruce de destino.");
		
		if(!this.equals(r.getCruceDestino())) throw new IllegalArgumentException("El cruce de destino no es igual al cruce actual.");
		
		mapaSalientes.put(r.getCruceDestino(), r);
	}
	
	/* añade el vehículo v a la cola de la carretera r, donde r es la
	carretera actual del vehículo v.*/
	void enter(Vehicle v) {
		
		Road aux = v.getRoad();
		List<Vehicle> lista = carreteraCola.get(aux);
		
		lista.add(v);
	}
	
	/*devuelve la carretera que va desde el cruce actual al cruce
	j. Para esto debes buscar en el mapa de carreteras salientes.*/
	Road roadTo(Junction j) {
		return mapaSalientes.get(j);	
	}
	
	
	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		
		int siguiente;
		
		if(indSemaforo != -1 && !listaColas.isEmpty()) {
			
			List<Vehicle> lista_avanzan = listaColas.get(indSemaforo);
			List<Vehicle> avanzan = estrategiaElimina.dequeue(lista_avanzan);
			
			for(Vehicle vehicle : avanzan) {
				
				vehicle.moveToNextRoad();
				lista_avanzan.remove(vehicle);
			}		
		}
		
		siguiente = estrategiaColor.chooseNextGreen(listaEntrantes, listaColas, indSemaforo, cambioSemaforo, time);
		
		if(siguiente != indSemaforo) {
			
			indSemaforo = siguiente;
			cambioSemaforo = time;
		}
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		JSONObject imprime = new JSONObject();
		
		imprime.put("id", id);
		
		if (indSemaforo == -1)
		{
			imprime.put("green", "none");
		}
		else
		{
			imprime.put("green", listaEntrantes.get(indSemaforo).getId());
		}
		
		JSONArray listaArray = new JSONArray();
		imprime.put("queues", listaArray);
		
		for (Road roads : listaEntrantes)
		{
			JSONObject jsonCarreteras = new JSONObject();
			listaArray.put(jsonCarreteras);
			jsonCarreteras.put("road", roads.getId());
			
			JSONArray listaArray2 = new JSONArray();
			jsonCarreteras.put("vehicles", listaArray2);
			for (Vehicle v : carreteraCola.get(roads))
			{
				listaArray2.put(v.getId());
			}
		}
		
		return imprime;
	}

	
	// GETTERS
	
	public String getId() {
		
		return id;
	}
	
	// SETTERS
	
	public void setId(String id) {
		
		this.id = id;
	}
}
