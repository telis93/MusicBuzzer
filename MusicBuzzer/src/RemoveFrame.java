import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;


@SuppressWarnings("serial")
public class RemoveFrame extends JFrame{
	
	private Frame parent;
	private JList<Note> list;
	private JButton okButton;
	private JButton removeButton;
	private Container panel;

	public RemoveFrame(Frame parent) {
		super("Remove");
		this.parent = parent;
		list = new JList<Note>((Note[]) parent.getNotes().toArray(new Note[parent.getNotes().size()]));
		list.setPreferredSize(new Dimension(150, 200));
		okButton = new JButton("OK");
		okButton.addActionListener(new OkButtonListener());
		removeButton = new JButton("Remove");
		removeButton.addActionListener(new removeButtonListener());
		panel = this.getContentPane();
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 5;
		panel.add(list,c);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 1;
		panel.add(okButton,c);
		c.gridx = 1;
		c.gridy = 1;
		panel.add(removeButton,c);
		
		this.pack();
		this.setMinimumSize(this.getSize());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	class OkButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			RemoveFrame.this.dispose();	
		}
	}
	
	class removeButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			parent.getNotes().remove(list.getSelectedValue());
			list.setListData((Note[]) parent.getNotes().toArray(new Note[parent.getNotes().size()]));
		}
	}
	
	

}
