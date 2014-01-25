import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

import org.gpl.JSplitButton.JSplitButton;
import org.gpl.JSplitButton.action.SplitButtonActionListener;



@SuppressWarnings("serial")
public class Frame extends JFrame{
	private GridBagLayout layout;
	private JComboBox<Note> list;
	private JComboBox<Duration> durationList;
	private JSlider slider;
	private JToggleButton dot;
	private JToggleButton sharp;
	private JToggleButton flat;
	private JButton addButton;
	private ArrayList<Note> notes;
	private JButton playButton;
	private JSplitButton removeButton;
	private JPopupMenu removePopupMenu;
	private JMenuItem removeAllMenuItem;
	
	public Frame() {
		super("MusicBuzzer");
		this.notes = new ArrayList<Note>();
		Container panel = this.getContentPane();
		list = new JComboBox<Note>();
		list.setFont(list.getFont().deriveFont((float)35));
		((JLabel)list.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		addNotes();
		durationList = new JComboBox<Duration>();
		addDurations();
		durationList.setSelectedIndex(2);
		
		layout = new GridBagLayout();
		
		slider = new JSlider(-3,3);
		slider.setPaintLabels(true);
	    slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setOrientation(JSlider.VERTICAL);
		slider.setPreferredSize(new Dimension(slider.getPreferredSize().width, slider.getPreferredSize().height-90) );
		
		sharp = new JToggleButton(Note.SHARP_SIGN);
		sharp.setFont(sharp.getFont().deriveFont((float)35));
		flat = new JToggleButton(Note.FLAT_SIGN);
		flat.setFont(flat.getFont().deriveFont((float)35));
		dot = new JToggleButton(".");
		dot.setFont(dot.getFont().deriveFont((float)35));
		addButton = new JButton("Add Note");
		addButton.setFont(addButton.getFont().deriveFont((float) 18));
		Image img = null;
		try {
			img = ImageIO.read(Frame.class.getResource("/resources/playButtonIcon.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		playButton = new JButton(new ImageIcon(img));
		playButton.setFont(playButton.getFont().deriveFont((float) 18));
		removeButton =  new JSplitButton("Remove...");
		Dimension tempDimension = removeButton.getPreferredSize();
		tempDimension = new Dimension(tempDimension.width + tempDimension.width/2, tempDimension.height);
		removeButton.setPreferredSize(tempDimension);
		removeButton.setFont(removeButton.getFont().deriveFont((float) 18));
		removePopupMenu = new JPopupMenu();
		removeButton.setPopupMenu(removePopupMenu);
		removeAllMenuItem = new JMenuItem("Remove ALL");
		removePopupMenu.add(removeAllMenuItem);
		
		sharp.addActionListener(new SharpButtonListener());
		flat.addActionListener(new FlatButtonListener());
		addButton.addActionListener(new AddButtonListener());
		playButton.addActionListener(new PlayButtonListener());
		removeButton.addSplitButtonActionListener(new RemoveButtonListener());
		removeAllMenuItem.addActionListener(new RemoveAllMenuItemListener());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;

		
		this.setLayout(layout);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(list,c);
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 0;
		panel.add(sharp,c);
		c.gridx = 2;
		c.gridy = 1;
		panel.add(flat,c);
		c.gridheight = 2;
		c.gridx = 3;
		c.gridy = 0;
		panel.add(slider,c);
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(durationList,c);
		c.gridx = 1;
		c.gridy = 1;
		panel.add(dot,c);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		panel.add(addButton,c);
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 2;
		panel.add(playButton,c);
		c.gridx = 3;
		c.gridy = 2;
		panel.add(removeButton,c);

		this.pack();
		this.setMinimumSize(this.getSize());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public ArrayList<Note> getNotes() {
		return notes;
	}
	
	private void addDurations() {
		Font freeSerif = null;
		InputStream is = Frame.class.getResourceAsStream("/resources/FreeSerif.ttf");
		try {
				freeSerif = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
				e.printStackTrace();
		}
		freeSerif = freeSerif.deriveFont(Font.PLAIN, 35);
		durationList.setFont(freeSerif);
		DefaultComboBoxModel<Duration> durations = new DefaultComboBoxModel<Duration>();
		
		durations.addElement(new Duration(DurationValue.WHOLE));
		durations.addElement(new Duration(DurationValue.HALF));
		durations.addElement(new Duration(DurationValue.QUARTER));
		durations.addElement(new Duration(DurationValue.EIGHTTH));
		durations.addElement(new Duration(DurationValue.SIXTEENTH));
		durations.addElement(new Duration(DurationValue.THIRTYSECOND));
		durations.addElement(new Duration(DurationValue.SIXTYFOURTH));
		durations.addElement(new Duration(DurationValue.ONEHUNDREDTWENTYEIGHTTH));
		
		durationList.setModel(durations);
	}

	private void addNotes() {
		DefaultComboBoxModel<Note> notes = new DefaultComboBoxModel<Note>();
		int exp = 0;
		int half = 0;
		for(int i=0; i<7; i++) {
			if((i+1)%3 == 0)
				half--;
			exp=(2*i) + half;
			notes.addElement(new Note(Character.toString((char)('A'+i)),440*Math.pow(Math.pow(2, 1/12.0), exp)));
			if(i>=2) {
				notes.getElementAt(i).updateOctave(-1);
				notes.getElementAt(i).setOctave(0);
			}
		}
		
		list.setModel(notes);
		
	}
	
	class SharpButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(flat.isSelected()){
				flat.setSelected(false);
			}
		}
	}
	
	class FlatButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(sharp.isSelected()){
				sharp.setSelected(false);
			}
		}
	}
	
	class AddButtonListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Note selectedNote = ((Note) list.getSelectedItem()).clone();
			Duration selectedDuration = ((Duration) durationList.getSelectedItem()).clone();
			
			selectedNote.updateOctave(slider.getValue());
			if(sharp.isSelected())
				selectedNote.setSharp(true);
			if(flat.isSelected())
				selectedNote.setFlat(true);
			if(dot.isSelected())
				selectedDuration.setDotted(true);
			selectedNote.setDuration(selectedDuration);
			notes.add(selectedNote);
		}
	}
	
	class PlayButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Writer writer = null;
			
			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("beep.bat"), "utf-8"));
			    writer.write("beep ");
			    for(Note n: notes) {
				    double duration = n.getDuration().getDuration(100);
				    if(n.getDuration().isDotted()) 
				    	duration += duration/2;
			    	writer.write(n.getFreq() + " " + duration + " /s 1 ");
			    }
			    
			} catch (IOException ex) {
			  // report
			} finally {
			   try {writer.close();} catch (Exception ex) {}
			}
			File file = new File("beep.exe");
			file.delete();
			InputStream is = Frame.class.getResourceAsStream("/resources/Beep.exe");
			try {
				Files.copy(is, file.getAbsoluteFile().toPath());
				Process p = Runtime.getRuntime().exec("beep.bat");
				p.waitFor();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} 
			file.delete();
		}
	}
	
	class RemoveButtonListener implements SplitButtonActionListener {
		@Override
		public void buttonClicked(ActionEvent e) {
			new RemoveFrame(Frame.this);	
		}

		@Override
		public void splitButtonClicked(ActionEvent arg0) {}
	}
	
	class RemoveAllMenuItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
				notes.clear();
		}
	}

}
