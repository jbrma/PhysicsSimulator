package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator implements Observable<SimulatorObserver> {

	 	private double timePerStep; // tiempo por paso actual (delta-time)
	    private ForceLaws forceLaws;
	    private Map<String, BodiesGroup> groups;
	    private Map<String, BodiesGroup> _groupsRO;
	    private double currentTime; // // tiempo actual
	    private ArrayList<String> groupIds;
	    
	    private List<SimulatorObserver> listaObservadores;
        
	
	public PhysicsSimulator(ForceLaws _forceLaws, double _timePerStep) {
		 if (_timePerStep <= 0) {
	            throw new IllegalArgumentException("Time per step must be positive");
	        }
	     if (_forceLaws == null) {
	            throw new IllegalArgumentException("Force laws cannot be null");
	        }
	        this.timePerStep = _timePerStep;
	        this.forceLaws = _forceLaws;
	        this.groups = new HashMap<>();
	        this._groupsRO = Collections.unmodifiableMap(groups);
	        this.currentTime = 0;
	        this.groupIds = new ArrayList<>();
	        this.listaObservadores = new ArrayList<>();
	}

	public void advance() {
        for (BodiesGroup group : groups.values()) {
            group.advance(timePerStep);
        }
        currentTime += timePerStep;
        
        for(SimulatorObserver so : listaObservadores)
        	so.onAdvance(_groupsRO, currentTime);
    }

    public void addGroup(String id) {
        if (groups.containsKey(id)) {
            throw new IllegalArgumentException("Group with id " + id + " already exists");
        }
        
        groupIds.add(id);
        
        BodiesGroup group = new BodiesGroup(id, forceLaws);
        groups.put(id, group);
        
        for(SimulatorObserver so : listaObservadores)
			so.onGroupAdded(_groupsRO, group);
    }

    public void addBody(Body b) {
        String groupId = b.getgId();
        if (!groups.containsKey(groupId)) {
            throw new IllegalArgumentException("Group with id " + groupId + " does not exist");
        }
        BodiesGroup group = groups.get(groupId);
        group.addBody(b);
        
        for(SimulatorObserver so : listaObservadores)
			so.onBodyAdded(_groupsRO, b);
    }

    public void setForceLaws(String id, ForceLaws fl) {
        if (!groups.containsKey(id)) {
            throw new IllegalArgumentException("Group with id " + id + " does not exist");
        }
        BodiesGroup group = groups.get(id);
        group.setForceLaws(fl);
        
        for(SimulatorObserver so : listaObservadores)
        	so.onForceLawsChanged(group);
    }

    public JSONObject getState() {
        JSONObject state = new JSONObject();
       
        JSONArray groupStates = new JSONArray();
        for (String groupId : groupIds) {
            groupStates.put(groups.get(groupId).getState());
        }

        state.put("groups", groupStates);
        state.put("time", currentTime);

        return state;
    }


	public void reset() {
		
		groups.clear();
		groupIds.clear();
		timePerStep = 0.0;
		
		for(SimulatorObserver so : listaObservadores)
			so.onReset(_groupsRO, currentTime, timePerStep);
		
	}
	
	public void setDeltaTime(double t) {
		
		if(t < 0) {
			throw new IllegalArgumentException("t is non-positive");
		}
		timePerStep = t;
			
		for(SimulatorObserver so : listaObservadores)
			so.onDeltaTimeChanged(t);
	}
	
	@Override
    public String toString() {
        return getState().toString();
    }

	@Override
	public void addObserver(SimulatorObserver o) {
		if(!listaObservadores.contains(o))
			listaObservadores.add(o);
			o.onRegister(_groupsRO, currentTime, timePerStep);
		
	}

	@Override
	public void removeObserver(SimulatorObserver o) {
		if(listaObservadores.contains(o))
			listaObservadores.remove(o);
	}

	public void removeBody(String gid, String id) {
		
        if (!groups.containsKey(gid)) {
            throw new IllegalArgumentException("Group with id " + gid + " does not exist");
        }
        BodiesGroup group = groups.get(gid);
        Body b = group.removeBody(id);
        
        if (b == null) {
            throw new IllegalArgumentException("Body with id " + id + " does not exist");
        }
        
        for(SimulatorObserver so : listaObservadores)
        	so.onBodyRemoved(_groupsRO, b);
		
		
	}
}
