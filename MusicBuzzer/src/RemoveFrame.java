import java.awt.HeadlessException;

import javax.swing.*;

@SuppressWarnings("serial")
public class RemoveFrame extends JFrame{
	
	private JFrame parent;

	public RemoveFrame(JFrame parent) {
		super("Remove");
		this.parent = parent;
		
		this.setVisible(true);
		this.pack();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	

}
