package simulator.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
	
	// Añadir los atributos necesarios, si hace falta …
	
	private JLabel tiempo;
	private JLabel grupos;
	private double t = 0;
	
	StatusBar(Controller ctrl) {
		initGUI();
		// TODO registrar this como observador
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		
		tiempo = new JLabel("0.0");
		this.add(new JLabel("Tiempo: "));
		this.add(tiempo);
		

		grupos = new JLabel("0");
		this.add(new JLabel("Grupos: "));
		this.add(grupos);

		JSeparator s = new JSeparator(JSeparator.VERTICAL);
		s.setPreferredSize(new Dimension(10, 20));
		this.add(s);
		
		}
		
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		tiempo.setText(Double.toString(time));
		grupos.setText(Integer.toString(groups.size()));
		
	}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		tiempo.setText(Double.toString(time));
		grupos.setText(Integer.toString(groups.size()));
		
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		grupos.setText(Integer.toString(groups.size()));
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		grupos.setText(Integer.toString(groups.size()));
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
		grupos.setText(Integer.toString(groups.size()));
	}
		
}
