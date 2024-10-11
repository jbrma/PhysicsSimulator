package extra.dialog.ex3;

import javax.swing.*;

import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	JSONBuilderDialog _dialog;

	public MainWindow() {
		super("Custom Dialog Example");
		initGUI();
	}

	private void initGUI() {

		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);

		mainPanel.add(new JLabel("Click "));
		JButton here = new JButton("HERE");
		here.addActionListener((e) -> build_json());
		mainPanel.add(here);
		mainPanel.add(new JLabel(" to build your json"));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	protected void build_json() {

		// we create the dialog only once
		if (_dialog == null) {

			// if you're in a JPanel class, you can use the following
			//
			// (Frame) SwingUtilities.getWindowAncestor(this)
			//
			// in order to get the parent JFrame. Then pass it to the
			// constructor of MyDialogWindow instead of 'this'
			//
			_dialog = new JSONBuilderDialog(this);
		}

		int status = _dialog.open();

		if (status == 0) {
			System.out.println("Canceled");
		} else {
			System.out.println("Here is your JSON:");
			System.out.println();
			System.out.println(_dialog.getJSON());
		}
	}

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeAndWait(() -> new MainWindow());
	}
}
