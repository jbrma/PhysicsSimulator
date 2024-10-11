package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

public class RemoveDialog extends JDialog implements SimulatorObserver {
	
	private DefaultComboBoxModel<String> _bodiesModel;
	private DefaultComboBoxModel<String> _groupsModel;
	private Controller _ctrl;
	private List<Body> bodies;
	private JPanel opciones;
	private JButton remove;
	private JButton cancel;

	
	RemoveDialog(Frame parent, Controller ctrl) {
		super(parent, true);
		bodies = new ArrayList<Body>();
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
		}
		
		private void initGUI() {
			
			setTitle("Remove Body");
			
			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
			setContentPane(mainPanel);
			
			JLabel texto = new JLabel("Select a group and a body to be removed from the simulation and click REMOVE");
			texto.setAlignmentX(CENTER_ALIGNMENT);
			mainPanel.add(texto);
			mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
			
			JPanel botones = new JPanel();
			botones.setAlignmentX(CENTER_ALIGNMENT);
			

			// groups
			JLabel textoGroups =new JLabel("Group: ", JLabel.CENTER);
			_groupsModel = new DefaultComboBoxModel<>();
			
			JComboBox<String> groupsComboBox = new JComboBox<String>(_groupsModel);
			groupsComboBox.setVisible(true);
			groupsComboBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String selected = groupsComboBox.getSelectedItem().toString();
					_bodiesModel.removeAllElements();
					for(Body b : bodies) {
						if(b.getgId().equals(selected)) {
							_bodiesModel.addElement(b.getId());
						}
					}
				}
				
			});
			botones.add(textoGroups);
			botones.add(groupsComboBox);
			
			// bodies
			
			
			JLabel textoBodies =new JLabel("Body: ", JLabel.CENTER);
			_bodiesModel = new DefaultComboBoxModel<>();	
			JComboBox<String> bodiesComboBox = new JComboBox<String>(_bodiesModel);
			bodiesComboBox.setVisible(true);
			
			botones.add(textoBodies);
			botones.add(bodiesComboBox);

			
			mainPanel.add(botones);
			
			
			// opciones remove y cancel
			
			opciones = new JPanel();
			opciones.setAlignmentX(CENTER_ALIGNMENT);
			mainPanel.add(opciones);
			
			remove = new JButton("REMOVE");
			remove.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					try {
					_ctrl.removeBody((String) groupsComboBox.getSelectedItem(), 
							(String) bodiesComboBox.getSelectedItem());
					setVisible(false);
					}
					catch(Exception ex){
						Utils.showErrorMsg(ex.getMessage());
					}
				}
			});
			
			cancel = new JButton("Cancel");
			cancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);	
				}
			});
			opciones.add(cancel);
			opciones.add(remove);
			
			setPreferredSize(new Dimension(300, 200));
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
		

		@Override
		public void onAdvance(Map<String, BodiesGroup> groups, double time) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
			_groupsModel.removeAllElements();
			_bodiesModel.removeAllElements();
			bodies.clear();
		}

		@Override
		public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
			for(BodiesGroup g: groups.values()) {
				_groupsModel.addElement(g.getId());
				for(Body b : g) {
					bodies.add(b);
				}
			}
		}

		@Override
		public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
			_groupsModel.addElement(g.getId());
		}

		@Override
		public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
			bodies.add(b);
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
