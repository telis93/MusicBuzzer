import java.awt.HeadlessException;

import javax.swing.*;

@SuppressWarnings("serial")
public class RemoveFrame extends JFrame{
	
	private Frame parent;
	private JList<Note> list;
	private JButton okButton;
	private JButton removeButton;

	public RemoveFrame(Frame parent) {
		super("Remove");
		this.parent = parent;
		list = new JList<Note>((Note[]) parent.getNotes().toArray(new Note[parent.getNotes().size()]));
		okButton = new JButton("OK");
		removeButton = new JButton("Remove");
		
		this.setVisible(true);
		this.pack();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	

}
