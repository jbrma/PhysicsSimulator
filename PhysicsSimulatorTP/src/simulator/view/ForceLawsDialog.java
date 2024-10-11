package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.SimulatorObserver;

public class ForceLawsDialog extends JDialog implements SimulatorObserver {
	
	private DefaultComboBoxModel<String> _lawsModel;
	private DefaultComboBoxModel<String> _groupsModel;
	private DefaultTableModel _dataTableModel;
	private Controller _ctrl;
	private List<JSONObject> _forceLawsInfo;
	private String[] _headers = { "Key", "Value", "Description" };
	
	private List<JSONObject> gruposDisp;
	private JPanel opciones;
	private JButton ok;
	private JButton cancel;
	private JTable t;
	
	private JSONObject key;
	private int selectedIndex;
	
	private int _status = 0;
	
		ForceLawsDialog(Frame parent, Controller ctrl) {
		super(parent, true);
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
		}
		
		private void initGUI() {
			
			setTitle("Force Laws Selection");
			
			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
			setContentPane(mainPanel);
			
			JLabel texto = new JLabel("Select a force law and provide values for the parameters in the Value column.");
			texto.setAlignmentX(CENTER_ALIGNMENT);
			mainPanel.add(texto);
			mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
			
			JPanel botones = new JPanel();
			botones.setAlignmentX(CENTER_ALIGNMENT);
			
			
			_forceLawsInfo = _ctrl.getForceLawsInfo();
			
			
			// force laws
			JLabel textoLaws =new JLabel("Force Law: ", JLabel.CENTER);
			_lawsModel = new DefaultComboBoxModel<>();
			for (JSONObject info : _forceLawsInfo) {
			    String desc = info.getString("desc");
			    _lawsModel.addElement(desc);
			}
			JComboBox<String> lawsComboBox = new JComboBox<String>(_lawsModel);
			lawsComboBox.setVisible(true);
			selectedIndex = lawsComboBox.getSelectedIndex();
			lawsComboBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					valoresTabla(lawsComboBox);
				}
			});
			botones.add(textoLaws);
			botones.add(lawsComboBox);

			// groups
			JLabel textoGroups =new JLabel("Group: ", JLabel.CENTER);
			_groupsModel = new DefaultComboBoxModel<>();
			
			JComboBox<String> groupsComboBox = new JComboBox<String>(_groupsModel);
			groupsComboBox.setVisible(true);
			botones.add(textoGroups);
			botones.add(groupsComboBox);
			
			
			
			// tabla
			
			_dataTableModel = new DefaultTableModel(_headers, 0) {
				
				@Override
				public boolean isCellEditable(int row, int column) {
					return column == 1;
				}

			};

			_dataTableModel.setColumnIdentifiers(_headers);
			t = new JTable(_dataTableModel);
			
			valoresTabla(lawsComboBox);
			
			t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			t.setFillsViewportHeight(true);
			JScrollPane scrollPane = new JScrollPane(t);
			mainPanel.add(scrollPane);
			mainPanel.add(botones);
			
			
			// opciones OK y cancel
			
			opciones = new JPanel();
			opciones.setAlignmentX(CENTER_ALIGNMENT);
			mainPanel.add(opciones);
			
			ok = new JButton("OK");
			ok.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JSONObject data = new JSONObject();
						for (int i = 0; i < _dataTableModel.getRowCount(); i++) {	

							if(_dataTableModel.getValueAt(i, 0) == null || _dataTableModel.getValueAt(i, 1) == null)
								throw new Exception("No puede haber campos vacíos");
							
							String val = (String) _dataTableModel.getValueAt(i, 1).toString();
							String aux[] = val.split(",");
							
							if(aux.length == 1) {	
								data.put(_dataTableModel.getValueAt(i, 0).toString(),
										_dataTableModel.getValueAt(i, 1).toString());
							}
							else {
								JSONArray valor = new JSONArray();
								
								for(int j = 0; j < aux.length; j++) {
									try {
										valor.put(Double.parseDouble(aux[j]));
									}
									catch(Exception ee) {
										throw new Exception("El valor de c debe estar en el formato ' 0,0 ' ");   
									}
								}
								data.put(_dataTableModel.getValueAt(i, 0).toString(), valor);
							}
							
						}
						
						JSONObject json = new JSONObject();
						json.put("type", _forceLawsInfo.get(selectedIndex).getString("type"));
						json.put("data", data);
						_ctrl.setForcesLaws((String) groupsComboBox.getSelectedItem(), json);
						
						_status = 1;
						setVisible(false);
						
					} catch (Exception ex) {
						Utils.showErrorMsg(ex.getMessage());
					}
				}
			});
			opciones.add(ok);
			
			cancel = new JButton("Cancel");
			cancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					_status = 0;
					setVisible(false);	
				}
			});
			opciones.add(cancel);

			setPreferredSize(new Dimension(700, 400));
			pack();
			setResizable(false);
			setVisible(false);
		}
		
		public void open() {
			
			if (_groupsModel.getSize() == 0)
				return;
			
			setLocationRelativeTo(getParent());
			
			pack();
			setVisible(true);
			//return;
		}
		
		public JSONArray transformaJSONArray(String cValue){
			
			cValue = cValue.replaceAll("\\s", ""); // Eliminar espacios en blanco
			cValue = cValue.replaceAll("\\[", ""); // Eliminar el corchete de apertura
			cValue = cValue.replaceAll("\\]", ""); // Eliminar el corchete de cierre
			String[] parts = cValue.split(","); // Dividir en dos partes separadas por coma
			
			double x = Double.parseDouble(parts[0]);
			double y = Double.parseDouble(parts[1]);
			JSONArray cArray = new JSONArray();
			cArray.put(x);
			cArray.put(y);

			return cArray;
		}
		
		public void valoresTabla(JComboBox<String> lawsComboBox) {
			
			selectedIndex = lawsComboBox.getSelectedIndex();
			
			JSONObject j = _forceLawsInfo.get(selectedIndex);
			String a = j.get("desc").toString();
			
			if(lawsComboBox.getSelectedItem().toString().equals(a)) {
				key = j.getJSONObject("data");
			}
			
			
			_dataTableModel.setRowCount(key.length());
			int p = 0;
			for(String s : key.keySet()) {
				String cont = key.getString(s);
				t.setValueAt( s, p, 0 );
				t.setValueAt(cont, p, 2);
				t.setValueAt("", p, 1);
				p++;
			}
			
		}

		@Override
		public void onAdvance(Map<String, BodiesGroup> groups, double time) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
			_groupsModel.removeAllElements();
		}

		@Override
		public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
			for(BodiesGroup g: groups.values()) {
				_groupsModel.addElement(g.getId());;
			}
		}

		@Override
		public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
			_groupsModel.addElement(g.getId());
		}

		@Override
		public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDeltaTimeChanged(double dt) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onForceLawsChanged(BodiesGroup g) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onBodyRemoved(Map<String, BodiesGroup> groups, Body b) {
			
		}
		
	}
