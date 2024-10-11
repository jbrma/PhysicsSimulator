package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.*;
import simulator.model.*;

public class Controller {
	
	 	private PhysicsSimulator simulator;
	    private Factory<Body> bodyFactory;
	    private Factory<ForceLaws> forceLawsFactory;

	    private List<JSONObject> states;

	    public Controller(PhysicsSimulator simulator, Factory<Body> bodyFactory,
	                      Factory<ForceLaws> forceLawsFactory) {
	        this.simulator = simulator;
	        this.bodyFactory = bodyFactory;
	        this.forceLawsFactory = forceLawsFactory;
	        this.states = new ArrayList<>();
	    }


	    public void loadData(InputStream in) {
	        JSONObject jsonInput = new JSONObject(new JSONTokener(in));

	        JSONArray groups = jsonInput.getJSONArray("groups");
	        for (int i = 0; i < groups.length(); i++) {
	            simulator.addGroup(groups.getString(i));
	        }
	        
	        if(jsonInput.has("laws")) {
	        	
	        	JSONArray laws = jsonInput.getJSONArray("laws");
		        for (int i = 0; i < laws.length(); i++) {
		            ForceLaws force = forceLawsFactory.createInstance(laws.getJSONObject(i).getJSONObject("laws"));
		            simulator.setForceLaws(laws.getJSONObject(i).getString("id"), force);
		        }
	        }
	        
	        JSONArray bodies = jsonInput.getJSONArray("bodies");
	        for (int i = 0; i < bodies.length(); i++) {
	            Body body = bodyFactory.createInstance(bodies.getJSONObject(i));
	            simulator.addBody(body);
	        }
	    }
	    
	    public void run(int n, OutputStream out) {
	        PrintStream p = new PrintStream(out);
	        
	        p.println("{");
	        p.println(" \"states\": [");

	        p.print(simulator.getState());

	        for (int i = 0; i < n; i++) {
	            simulator.advance();
	            p.println("," + simulator.getState().toString());
	        }

//	        for (int i = 0; i < states.size(); i++) {
//	            if (i > 0) {
//	                p.print(",");
//	            }
//	            p.print(states.get(i).toString());
//	        }

	        p.println("]");
	        p.println("}");
	       
	    }
	    
	    public void reset() {
	    	
	    	simulator.reset();
	    }
	    
	    public void setDeltaTime(double dt) {
	    	
	    	simulator.setDeltaTime(dt);
	    }
	    
	    public void addObserver(SimulatorObserver o) {
	    	
	    	simulator.addObserver(o);
	    }
	    
	    public void removeObserver(SimulatorObserver o) {
	    	
	    	simulator.removeObserver(o);
	    }

	    public List<JSONObject> getForceLawsInfo(){
	    	
	    	return forceLawsFactory.getInfo();
	    }
	    
	    public void setForcesLaws(String gId, JSONObject info) {
			ForceLaws fl = forceLawsFactory.createInstance(info);
			simulator.setForceLaws(gId, fl);
		}
	    
	    public List<JSONObject> getStates(){
	    	
	    	return states;
	    }
		
		public void run(int n) {
			for(int i=0; i<n; i++) simulator.advance();
		}
		
		public void  removeBody(String gid, String id) {
			simulator.removeBody(gid, id);
		}
}
