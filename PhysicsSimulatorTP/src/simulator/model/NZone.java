package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import simulator.control.Controller;
import simulator.misc.Vector2D;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class NZone implements SimulatorObserver {
	
	Map<Body, Vector2D> posIni;
	Map<Body, Vector2D> posAct;
	Map<Body, Integer> cont;
	private double N;
	
	public NZone(Controller ctrl, double _N) {
		posIni = new HashMap<>();
		posAct = new HashMap<>();
		cont = new HashMap<>();
		N = _N;
		ctrl.addObserver(this);
	}

	public String str() {
		String aux = "";
		
		for(Body b : cont.keySet()) {
		
			aux += b.getId() + ":" + b.getgId() + " => " + String.valueOf(cont.get(b)) + "\n";
		}
		
		return aux;
	}
	
	
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		for(BodiesGroup bg: groups.values()) {
			for(Body b : bg) {

				double distanciaAnt = posAct.get(b).distanceTo(posIni.get(b));
				double distanciaActual = b.getPosition().distanceTo(posIni.get(b));
				
				if(distanciaActual > N && distanciaAnt <= N) {
					cont.put(b, cont.get(b) + 1);
				}
				posAct.put(b, b.getPosition());
			}
		}
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		posIni.clear();
		posAct.clear();
		cont.clear();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for(BodiesGroup bg: groups.values()) {
			for(Body body : bg) {
				posIni.put(body, body.getPosition());
				posAct.put(body, body.getPosition());
				cont.put(body, 0);
			}
		}

	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		posIni.put(b, b.getPosition());
		posAct.put(b, b.getPosition());
		cont.put(b, 0);
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}

	@Override
	public void onBodyRemoved(Map<String, BodiesGroup> groups, Body b) {
		posIni.remove(b);
		posAct.remove(b);
		cont.remove(b);
	}

}
