package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {
	
	String[] _header = { "Id", "gId", "Mass", "Velocity", "Position", "Force" };
	List<Body> _bodies;
	
	BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}
	// TODO el resto de métodos van aquí…

	@Override
	public int getRowCount() {
		return _bodies.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return _header.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return _header[columnIndex];
	}

	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		String b = "";
		
		
        switch (columnIndex) {
            case 0:
                b = _bodies.get(rowIndex).getId();
                break;
            case 1:
            	b = _bodies.get(rowIndex).getgId();
            	break;
            case 2:
            	b = Double.toString(_bodies.get(rowIndex).getMass());
            	break;
            case 3:
            	b = _bodies.get(rowIndex).getVelocity().toString();
            	break;
            case 4:
            	b = _bodies.get(rowIndex).getPosition().toString();
            	break;
            case 5:
            	b = _bodies.get(rowIndex).getForce().toString();
            	break;
            default:
                break;
        }
		return b;
	}

	
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		fireTableDataChanged();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_bodies.clear();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for(BodiesGroup bg: groups.values()) {
			for(Body body : bg) {
				_bodies.add(body);
			}
		}

		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		_bodies.add(b);
		fireTableStructureChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}

	@Override
	public void onBodyRemoved(Map<String, BodiesGroup> groups, Body b) {
		_bodies.remove(b);
		fireTableStructureChanged();
	}

}
