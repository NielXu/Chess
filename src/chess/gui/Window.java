package chess.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window {

	private JFrame frame;
	
	public Window(){
		frame = new JFrame("Chess");
		frame.setSize(640, 679);
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
	}
	
	public void addPanel(JPanel panel){
		frame.add(panel);
	}
	
	public void show(){
		frame.setVisible(true);
	}
	
}
