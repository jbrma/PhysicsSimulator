package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class GroupsTableModel extends AbstractTableModel implements SimulatorObserver {
	
	String[] _header = { "Id", "Force Laws", "Bodies" };
	List<BodiesGroup> _groups;
	
	GroupsTableModel(Controller ctrl) {
		_groups = new ArrayList<>();
		ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return _groups == null ? 0 : _groups.size();
	}

	@Override
	public int getColumnCount() {
		return _header.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return _header[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		String g = " ";
		
        switch (columnIndex) {
            case 0:
                g = _groups.get(rowIndex).getId();
                break;
            case 1:
            	g = _groups.get(rowIndex).getForceLawsInfo().toString();
            	break;
            case 2:
            	String bodiesInGroup = " ";
   
            		for(Body b : _groups.get(rowIndex)) {
            			bodiesInGroup += b.getId() + " ";
            		}
        
            	g = bodiesInGroup;
            	break;
            default:
                break;
        }
		return g;
	}


	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_groups.clear();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {

		for(BodiesGroup g: groups.values()) {
			_groups.add(g);
		}
		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		_groups.add(g);
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		fireTableStructureChanged();
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		fireTableDataChanged();
	}

	@Override
	public void onBodyRemoved(Map<String, BodiesGroup> groups, Body b) {
		fireTableStructureChanged();
	}

}
