package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver{
	
	private Controller _ctrl;
	private JToolBar _toolaBar;
	private JFileChooser _fc;
	private boolean _stopped = true; // utilizado en los botones de run/stop
	private JButton _quitButton;
	
		
	private JButton botonFile;
	private JButton botonForceLaws;
	private ForceLawsDialog forceLawsDialog;
	private JButton botonRun;
	private JButton botonStop;
	private JButton botonViewer;
	private JSpinner steps;
	private JLabel stringSteps;
	private JTextField deltatime;
	private JLabel stringDT;
	private double delta_time;
	

	private JButton botonDel;
	private RemoveDialog delDialog;
	
	
	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		
		setLayout(new BorderLayout());
		_toolaBar = new JToolBar();
		add(_toolaBar, BorderLayout.PAGE_START);
		
		// crear los diferentes botones/atributos y añadirlos a _toolaBar.
		// Todos ellos han de tener su correspondiente tooltip. Puedes utilizar
		// _toolaBar.addSeparator() para añadir la línea de separación vertical
		// entre las componentes que lo necesiten
		// Quit Button
		
		_toolaBar.addSeparator();
		botonCargaFile();
		
		_toolaBar.addSeparator();
		botonForceLaws();
		botonViewer();
		
		botonDelete();
		
		_toolaBar.addSeparator();
		botonEjec();
		botonParada();
		spinnerSteps();
		deltaTimeStr();
		
		_toolaBar.add(Box.createGlue()); // this aligns the button to the right
		_toolaBar.addSeparator();
		_quitButton = new JButton();
		_quitButton.setToolTipText("Quit");
		_quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		_quitButton.addActionListener((e) -> Utils.quit(this));
		_toolaBar.add(_quitButton);
		
		_fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON", "json");
		_fc.setFileFilter(filter);
		
	}

	private void botonCargaFile() {

		_fc = new JFileChooser();
		_fc.setDialogTitle("Selecciona el fichero ");
		_fc.setCurrentDirectory(new File("./resources/examples/"));
		_fc.setCurrentDirectory(new File("resources/examples/input"));
		
		_fc.setMultiSelectionEnabled(false);
		_fc.setFileFilter(new FileNameExtensionFilter("JavaScript Object Notation", "json"));
		
		
		botonFile = new JButton();
		botonFile.setToolTipText("Carga los datos en la aplicación");
		botonFile.setIcon(new ImageIcon("./resources/icons/open.png"));
		botonFile.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cargaFichero();
			}
		});
		
		_toolaBar.add(botonFile);
		
	}
	
	public void cargaFichero() {
		

		updateUI();
		
		int aux = _fc.showOpenDialog(Utils.getWindow(this));
		
		if (aux == JFileChooser.APPROVE_OPTION) {
			
			JOptionPane.showMessageDialog((Frame) SwingUtilities.getWindowAncestor(this), "Has elegido: " + _fc.getSelectedFile());
			
			
			File f = _fc.getSelectedFile();
			InputStream inputStream;

			try {
				inputStream = new FileInputStream(f);
				_ctrl.reset();
				_ctrl.loadData(inputStream);
				
			} catch (Exception e1) {
				JOptionPane.showMessageDialog((Frame) SwingUtilities.getWindowAncestor(this), "Ha ocurrido un error");
			}
		}
		else {
			if (aux == JFileChooser.CANCEL_OPTION)
				JOptionPane.showMessageDialog((Frame) SwingUtilities.getWindowAncestor(this), "Se ha cancelado la seleccion");
			else
				JOptionPane.showMessageDialog((Frame) SwingUtilities.getWindowAncestor(this), "Ha ocurrido un error");
		}	
		
	}

	
	private void botonForceLaws() {
		
		botonForceLaws = new JButton();
		botonForceLaws.setToolTipText("Force Laws");
		botonForceLaws.setIcon(new ImageIcon("resources/icons/physics.png"));
		botonForceLaws.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent arg0) {
	            
	            creaForceLaws();
	        }
	    });
	    _toolaBar.add(botonForceLaws);
	}
	
	private void creaForceLaws() {
		
		if(forceLawsDialog == null)
			forceLawsDialog = new ForceLawsDialog((Frame) SwingUtilities.getWindowAncestor(this), _ctrl);
		
		forceLawsDialog.open();
		
	}
	
	private void botonViewer() {
		
		botonViewer = new JButton();
		botonViewer.setToolTipText("Viewer");
		botonViewer.setIcon(new ImageIcon("resources/icons/viewer.png"));
		botonViewer.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent arg0) {
	            
	        	creaViewer();
	        }
	    });
	    _toolaBar.add(botonViewer);
	}
	
	private void creaViewer() {
		
		new ViewerWindow((JFrame) SwingUtilities.getWindowAncestor(this), _ctrl);
		
	}
	
	private void botonDelete() {
		
		botonDel = new JButton();
		botonDel.setToolTipText("Remove Body");
		botonDel.setIcon(new ImageIcon("resources/icons/delete.png"));
		botonDel.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent arg0) {
	            
	        	creaDelete();
	        }
	    });
	    _toolaBar.add(botonDel);
	}
	
	private void creaDelete() {
		
		if(delDialog == null)
			delDialog = new RemoveDialog((Frame) SwingUtilities.getWindowAncestor(this), _ctrl);
		
		delDialog.open();
		
	}
	
	private void botonEjec() {
		
		botonRun = new JButton();
		
		botonRun.setToolTipText("Ejecuta la simulación");
		botonRun.setIcon(new ImageIcon("./resources/icons/run.png"));
		botonRun.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enableToolBar(false);
				_stopped = false;
				
				try {

					delta_time = Double.parseDouble(deltatime.getText());
			        _ctrl.setDeltaTime(delta_time);
					run_sim((Integer) steps.getValue());
				}
				catch(Exception ee){
					
					Utils.showErrorMsg("Se ha producido un error");
				}
				
			}

		});
		
		_toolaBar.add(botonRun);
	}
	
	private void enableToolBar(boolean b) {
		botonFile.setEnabled(b);
		botonForceLaws.setEnabled(b);
		botonViewer.setEnabled(b);
		botonRun.setEnabled(b);
		
	}
	private void botonParada() {
		
		botonStop = new JButton();
		botonStop.setToolTipText("Detiene la simulación");
		botonStop.setIcon(new ImageIcon("./resources/icons/stop.png"));
		botonStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stop();		
			}
		});
		
		_toolaBar.add(botonStop);
		
	}
	
	private void stop() {
		_stopped = true;
		enableToolBar(true);		
	}
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
				
			} 
			catch (Exception e) {
				
				Utils.showErrorMsg("Se ha producido un error");
				enableToolBar(true);
				
				_stopped = true;
				return;
			}
			
			SwingUtilities.invokeLater(() -> run_sim(n - 1));
		} 
		
		else {
			_stopped = true;
		}
	}
	
	private void spinnerSteps() {
		
		stringSteps = new JLabel(" Steps: ", JLabel.CENTER);
		
		steps = new JSpinner(new SpinnerNumberModel(10000, 1, 99999, 1));
		steps.setMinimumSize(new Dimension(80, 30));
		steps.setMaximumSize(new Dimension(200, 30));
		steps.setPreferredSize(new Dimension(80, 30));
				
		_toolaBar.add(stringSteps);
		_toolaBar.add(steps);
	}
	
	private void deltaTimeStr() {
		
		stringDT = new JLabel(" Delta-Time: ", JLabel.CENTER);
		
		deltatime = new JTextField("1000");
		deltatime.setMinimumSize(new Dimension(80, 30));
		deltatime.setMaximumSize(new Dimension(200, 30));
		deltatime.setPreferredSize(new Dimension(80, 30));
				
		_toolaBar.add(stringDT);
		_toolaBar.add(deltatime);
	}
	
	
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		delta_time = dt;
		
	}
	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyRemoved(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		
	}
	
}
