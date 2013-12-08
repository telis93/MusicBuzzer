import java.awt.Color;
import java.util.ArrayList;

import abc.notation.KeySignature;
import abc.notation.Tune;
import abc.parser.TuneParser;
import abc.ui.swing.JScoreComponent;

public class MusicBuzzerTune  extends Tune {
	
	private ArrayList<Note> notes;
	private JScoreComponent scoreUI;

	public MusicBuzzerTune(ArrayList<Note> notes) {
		super();
		this.notes = notes;
		scoreUI = new JScoreComponent();
	}
	
	public void addNotes() {
		Music music = this.getMusic();
		String tuneAsString = "X:0\nT:Score\nK:C\n";
		KeySignature key = new KeySignature(abc.notation.Note.C, KeySignature.MAJOR);
		music.addElement(key);
		for(Note n: notes) {
			tuneAsString += n.toABC();
		}
		scoreUI.setVisible(true);
		scoreUI.setBackground(Color.WHITE);
		scoreUI.setForeground(Color.BLACK);
		scoreUI.setTune((new TuneParser()).parse(tuneAsString));
	}
	
	public JScoreComponent getScoreComponent() {
		return scoreUI;
	}
	

}
