package simulator.model;

public class NewJunctionEvent extends Event{

	private int time;
	private String id;
	private LightSwitchingStrategy estrategiaColor;
	private DequeuingStrategy estrategiaElimina;
	private int x;
	private int y;
	
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy,
			DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
			super(time);
			// ...
			
			if( lsStrategy == null || dqStrategy == null) throw new IllegalArgumentException("Ninguna de las estrategias puede ser null.");
			if( xCoor < 0 || yCoor < 0) throw new IllegalArgumentException("Ninguna de las coordenadas puede ser negativa");
			
			this.time = time;
			estrategiaColor = lsStrategy;
			estrategiaElimina = dqStrategy;
			x = xCoor;
			y = yCoor;
			this.id = id;
			}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		
		Junction j = new Junction(id, estrategiaColor, estrategiaElimina, x, y);
		map.addJunction(j);
		
	}

}
