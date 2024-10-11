package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame {

	private Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
		
	}
	
	private void initGUI() {
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		
		// crear ControlPanel y añadirlo en PAGE_START de mainPanel
		
		mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
		
		// crear StatusBar y añadirlo en PAGE_END de mainPanel
		
		mainPanel.add(new StatusBar(_ctrl),BorderLayout.PAGE_END);

		// Definición del panel de tablas (usa un BoxLayout vertical)
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
		contentPanel.add(new InfoTable("Groups", new GroupsTableModel(_ctrl)));
		contentPanel.add(new InfoTable("Bodies", new BodiesTableModel(_ctrl)));
		
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		
		
		addWindowListener(new WindowAdapter(){
			 @Override
			    public void windowClosing(WindowEvent e) {
			        Utils.quit(MainWindow.this);
			    }
		});
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
}
