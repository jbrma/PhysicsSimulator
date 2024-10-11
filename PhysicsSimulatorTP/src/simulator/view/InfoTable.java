package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class InfoTable extends JPanel {
	
	String _title;
	TableModel _tableModel;
	private JTable modelo;
	
	InfoTable(String title, TableModel tableModel) {
		_title = title;
		_tableModel = tableModel;
		initGUI();
	}
	private void initGUI() {
		setPreferredSize(new  Dimension(500, 250));
		// cambiar el layout del panel a BorderLayout()
		setLayout(new BorderLayout());
		
		// añadir un borde con título al JPanel, con el texto _title
		setBorder(BorderFactory.createTitledBorder(_title));
		
		// añadir un JTable (con barra de desplazamiento vertical) que use _tableModel
		JTable table = new JTable(_tableModel); 
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
		
	}
}