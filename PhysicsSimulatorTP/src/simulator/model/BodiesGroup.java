package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public class BodiesGroup implements Iterable<Body>{

	private String id;
    private ForceLaws forceLaws;
    private List<Body> bodies;
    private List<Body> _bodiesRO;
    
	public BodiesGroup(String _id, ForceLaws _forceLaws) {
		
		if (_id == null || _forceLaws == null) {
            throw new IllegalArgumentException("id or forceLaws are null");
        }
        if (_id.trim().length() == 0) {
            throw new IllegalArgumentException("id must contain at least one non-blank character");
        }
        this.id = _id;
        this.forceLaws = _forceLaws;
        this.bodies = new ArrayList<>();
        this._bodiesRO = Collections.unmodifiableList(bodies);
	}

		public String getId() {
	        return id;
	    }

	    public void setForceLaws(ForceLaws fl) {
	        if (fl == null) {
	            throw new IllegalArgumentException("ForceLaws cannot be null.");
	        }
	        this.forceLaws = fl;
	    }

	    public void addBody(Body b) {
	        if (b == null) {
	            throw new IllegalArgumentException("Body cannot be null.");
	        }
	        if (bodies.stream().anyMatch(body -> body.getId().equals(b.getId()))) {
	            throw new IllegalArgumentException("A body with the same id already exists in the group.");
	        }
	        bodies.add(b);
	    }

	    void advance(double dt) {
	        if (dt <= 0) {
	            throw new IllegalArgumentException("Time step must be positive.");
	        }
	       // bodies.forEach(Body::resetForce);
	        
	        for(Body b : bodies ) {
	        	b.resetForce();
	        }
	        
	        //bodies.forEach(Body::addForce(this.forceLaws));
	        forceLaws.apply(bodies);
	        //bodies.forEach(body -> body.advance(dt));
	        for(Body b : bodies ) {
	        	b.advance(dt);
	        }
	    }

	    public JSONObject getState() {
	        JSONArray bodiesJson = new JSONArray();
	        bodies.forEach(body -> bodiesJson.put(body.getState()));
	        JSONObject stateJson = new JSONObject();
	        stateJson.put("id", id);
	        stateJson.put("bodies", bodiesJson);
	        return stateJson;
	    }

	    public String getForceLawsInfo() {
	        return forceLaws.toString();
	    }
	    
	    
	    public Iterator<Body> iterator(){
	    	return _bodiesRO.iterator();
	    }
	    
	    @Override
	    public String toString() {
	        return getState().toString();
	    }
	
	    protected Body removeBody(String id) {
	    	Body aux = null;
	    	
	    	for(Body b : bodies ) {
	    		
	        	if(b.getId().equals(id)) {
	        		aux = b;
	        		bodies.remove(b);
	        	}
	        }
	    	return aux;
	    }
}
