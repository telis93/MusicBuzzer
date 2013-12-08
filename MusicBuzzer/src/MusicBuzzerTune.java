import java.util.ArrayList;

import abc.notation.Tune;

public class MusicBuzzerTune  extends Tune {
	
	private ArrayList<Note> notes;

	public MusicBuzzerTune(ArrayList<Note> notes) {
		super();
		this.notes = notes;
	}
	
	public void addNotes() {
		Music music = new Music();
		for(Note n: notes){
			music.addElement(n.toABC());
		}

	}
	

}
