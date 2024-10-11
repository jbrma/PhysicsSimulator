package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public abstract class Body {

	protected String id;
	protected String gid;
	protected Vector2D v;
	protected Vector2D f;
	protected Vector2D p;
	protected double m;
	
	
	
	public Body(String _id, String _gid, Vector2D _p, Vector2D _v, double _m) {

	        
		 if (_id == null || _gid == null || _v == null || _p == null) {
	            throw new IllegalArgumentException("Null parameter");
	        }
		 if (_id.trim().length() == 0 || _gid.trim().length() == 0) {
	            throw new IllegalArgumentException("Empty id or gid");
	        }
	     if (_m <= 0) {
	            throw new IllegalArgumentException("Non-positive mass");
	        }
	       
	     this.id = _id;
	     this.gid = _gid;
	     this.v = _v;
	     this.p = _p;
	     this.m = _m;
	     this.f = new Vector2D();
	}


    void addForce(Vector2D f) {
        this.f = this.f.plus(f);
    }

    void resetForce() {
        this.f = new Vector2D();
    }

    abstract void advance(double dt);


    public JSONObject getState() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("p", this.getPosition().asJSONArray());
        obj.put("v", this.getVelocity().asJSONArray());
        obj.put("m", this.getMass());
        obj.put("f", this.getForce().asJSONArray());
        return obj;
    }
    
    
    public String toString() {
        return getState().toString();
    }
	
    // GETTERS
    
    public String getId() {
        return id;
    }

    public String getgId() {
        return gid;
    }

    public Vector2D getVelocity() {
        return v;
    }

    public Vector2D getForce() {
        return f;
    }

    public Vector2D getPosition() {
        return p;
    }

    public double getMass() {
        return m;
    }
    
    
	}
