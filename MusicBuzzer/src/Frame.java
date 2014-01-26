import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
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
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jm.constants.Frequencies;
import jm.constants.Scales;
import jm.gui.cpn.TrebleStave;
import jm.gui.helper.HelperGUI;
import jm.gui.histogram.Histogram;
import jm.gui.show.ShowPanel;
import jm.gui.show.ShowScore;
import jm.gui.sketch.SketchScore;
import jm.gui.wave.WaveView;
import jm.music.data.Note;
import jm.music.data.Phrase;
import jm.music.data.Score;

import org.gpl.JSplitButton.JSplitButton;
import org.gpl.JSplitButton.action.SplitButtonActionListener;

@SuppressWarnings("serial")
public class Frame extends JFrame {
	private GridBagLayout layout;
	private JComboBox<MusicBuzzerNote> list;
	private JComboBox<MusicBuzzerDuration> durationList;
	private JSlider slider;
	private JToggleButton dot;
	private JToggleButton sharp;
	private JToggleButton flat;
	private JButton addButton;
	private ArrayList<MusicBuzzerNote> notes;
	private JButton playButton;
	private JSplitButton removeButton;
	private JPopupMenu removePopupMenu;
	private JMenuItem removeAllMenuItem;
	private DefaultComboBoxModel<MusicBuzzerNote> notesListModel;
	private Score score;

	public Frame() {
		super("MusicBuzzer");
		this.notes = new ArrayList<MusicBuzzerNote>();
		notesListModel = new DefaultComboBoxModel<MusicBuzzerNote>();
		score = new Score();
		Container panel = this.getContentPane();
		list = new JComboBox<MusicBuzzerNote>();
		list.setFont(list.getFont().deriveFont((float) 35));
		((JLabel) list.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		addNotes();
		durationList = new JComboBox<MusicBuzzerDuration>();
		addDurations();
		durationList.setSelectedIndex(2);

		layout = new GridBagLayout();

		slider = new JSlider(-3, 3);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setOrientation(JSlider.VERTICAL);
		slider.setPreferredSize(new Dimension(slider.getPreferredSize().width,
				slider.getPreferredSize().height - 90));

		sharp = new JToggleButton(MusicBuzzerNote.SHARP_SIGN);
		sharp.setFont(sharp.getFont().deriveFont((float) 35));
		flat = new JToggleButton(MusicBuzzerNote.FLAT_SIGN);
		flat.setFont(flat.getFont().deriveFont((float) 35));
		dot = new JToggleButton(".");
		dot.setFont(dot.getFont().deriveFont((float) 35));
		addButton = new JButton("Add Note");
		addButton.setFont(addButton.getFont().deriveFont((float) 18));
		Image img = null;
		try {
			img = ImageIO.read(Frame.class.getResource("/resources/playButtonIcon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		playButton = new JButton(new ImageIcon(img));
		playButton.setFont(playButton.getFont().deriveFont((float) 18));
		removeButton = new JSplitButton("Remove...");
		Dimension tempDimension = removeButton.getPreferredSize();
		tempDimension = new Dimension(tempDimension.width + tempDimension.width
				/ 2, tempDimension.height);
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
		slider.addChangeListener(new OctaveSliderListener());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;

		this.setLayout(layout);
		c.insets = new Insets(5, 5, 5, 5);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(list, c);
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 0;
		panel.add(sharp, c);
		c.gridx = 2;
		c.gridy = 1;
		panel.add(flat, c);
		c.gridheight = 2;
		c.gridx = 3;
		c.gridy = 0;
		panel.add(slider, c);
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(durationList, c);
		c.gridx = 1;
		c.gridy = 1;
		panel.add(dot, c);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		panel.add(addButton, c);
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 2;
		panel.add(playButton, c);
		c.gridx = 3;
		c.gridy = 2;
		panel.add(removeButton, c);
		this.pack();
		score.createPart().add(new Phrase(new Note(60,0.5)));
		JScore<TrebleStave> scorePanel = new JScore<>(score, TrebleStave.class);
		this.pack();
		scorePanel.setSize(this.getWidth(), scorePanel.getHeight());
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 3;
		panel.add(scorePanel, c);
		this.pack();
		this.setMinimumSize(this.getSize());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public ArrayList<MusicBuzzerNote> getNotes() {
		return notes;
	}

	private void addDurations() {
		Font freeSerif = null;
		InputStream is = Frame.class
				.getResourceAsStream("/resources/FreeSerif.ttf");
		try {
			freeSerif = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		freeSerif = freeSerif.deriveFont(Font.PLAIN, 35);
		durationList.setFont(freeSerif);
		DefaultComboBoxModel<MusicBuzzerDuration> musicBuzzerDurations = new DefaultComboBoxModel<MusicBuzzerDuration>();

		musicBuzzerDurations.addElement(new MusicBuzzerDuration(
				MusicBuzzerDuration.WHOLE_NOTE));
		musicBuzzerDurations.addElement(new MusicBuzzerDuration(
				MusicBuzzerDuration.HALF_NOTE));
		musicBuzzerDurations.addElement(new MusicBuzzerDuration(
				MusicBuzzerDuration.QUARTER_NOTE));
		musicBuzzerDurations.addElement(new MusicBuzzerDuration(
				MusicBuzzerDuration.EIGHTH_NOTE));
		musicBuzzerDurations.addElement(new MusicBuzzerDuration(
				MusicBuzzerDuration.SIXTEENTH_NOTE));
		musicBuzzerDurations.addElement(new MusicBuzzerDuration(
				MusicBuzzerDuration.THIRTYSECOND_NOTE));
		musicBuzzerDurations.addElement(new MusicBuzzerDuration(
				MusicBuzzerDuration.THIRTYSECOND_NOTE / 2));
		musicBuzzerDurations.addElement(new MusicBuzzerDuration(
				MusicBuzzerDuration.THIRTYSECOND_NOTE / 4));

		durationList.setModel(musicBuzzerDurations);
	}

	private void addNotes() {
		for (int i = 0; i < 7; i++) {
			double freq = Frequencies.FRQ[60 + Scales.MAJOR_SCALE[(5 + i) % 7]];
			notesListModel.addElement(new MusicBuzzerNote(Character
					.toString((char) ('A' + i)) + 4, freq));
		}

		list.setModel(notesListModel);

	}

	class OctaveSliderListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			if (!slider.getValueIsAdjusting()) {
				for (int i = 0; i < notesListModel.getSize(); i++) {
					notesListModel.getElementAt(i).updateOctave(slider.getValue());
				}
				list.repaint();
			}
		}
	}

	class SharpButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (flat.isSelected()) {
				flat.setSelected(false);
			}
		}
	}

	class FlatButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (sharp.isSelected()) {
				sharp.setSelected(false);
			}
		}
	}

	class AddButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			MusicBuzzerNote selectedNote = ((MusicBuzzerNote) list
					.getSelectedItem()).clone();
			MusicBuzzerDuration selectedDuration = ((MusicBuzzerDuration) durationList
					.getSelectedItem()).clone();

			if (sharp.isSelected())
				selectedNote.setSharp(true);
			if (flat.isSelected())
				selectedNote.setFlat(true);
			if (dot.isSelected())
				selectedDuration.setDotted(true);
			selectedNote.setMusicBuzzerDuration(selectedDuration);
			notes.add(selectedNote);
		}
	}

	class PlayButtonListener implements ActionListener {
		
		private ImageIcon pauseIcon;
		private Icon playIcon = playButton.getIcon();
		private volatile boolean playing = false;
	    private volatile File file;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Writer writer = null;

			try {
				Image image = ImageIO.read(Frame.class.getResource("/resources/pauseButtonIcon.png"));
				image = image.getScaledInstance(playIcon.getIconWidth(), playIcon.getIconHeight(), Image.SCALE_SMOOTH);
				pauseIcon = new ImageIcon(image);
			} catch (IOException e1) {
			}
			
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					file.delete();
					if(!file.exists()) {
						InputStream is = Frame.class.getResourceAsStream("/resources/Beep.exe");
						try {
							Files.copy(is, file.getAbsoluteFile().toPath());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					try {
						Process p = Runtime.getRuntime().exec("beep.bat");
						p.waitFor();
					} catch (Exception e) {
						e.printStackTrace();
					} 
					file.delete();
					playing = false;
					playButton.setIcon(playIcon);
				}
			});
			
			if (playing) {
				playButton.setIcon(playIcon);
				try {
					Runtime.getRuntime().exec("taskkill /F /IM " + file.getName());
				} catch (IOException e1) {}
				playing = false;
			} else {
				playButton.setIcon(pauseIcon);
				file = file = new File(UUID.randomUUID().toString() + ".exe");
				try {
				    writer = new BufferedWriter(new OutputStreamWriter(
				          new FileOutputStream("beep.bat"), "utf-8"));
				    writer.write(file.getName() + " ");
				    for(MusicBuzzerNote n: notes) {
					    double duration = n.getDuration(100);
					    if(n.getMusicBuzzerDuration().isDotted()) 
					    	duration += duration/2;
				    	writer.write(n.getFrequency() + " " + duration + " /s 1 ");
				    }
				    
				} catch (IOException ex) {
				  System.out.println("hi");
				} finally {
				   try {writer.close();} catch (Exception ex) {}
				}
				t.start();
				playing = true;
			}
		}
	}

	class RemoveButtonListener implements SplitButtonActionListener {
		@Override
		public void buttonClicked(ActionEvent e) {
			new RemoveFrame(Frame.this);
		}

		@Override
		public void splitButtonClicked(ActionEvent arg0) {
		}
	}

	class RemoveAllMenuItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			notes.clear();
		}
	}

}
