
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.ParameterizedType;
import java.util.Vector;
import java.util.Vector;

import javax.swing.JPanel;

import jm.gui.cpn.Stave;
import jm.gui.cpn.StaveActionHandler;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

@SuppressWarnings({"serial", "unchecked"})
public class JScore<E extends Stave> extends JPanel {
	private Score score;
	private Vector<Stave> staves;
	private Class<E> aClass;

	public JScore(Score score, Class<E> aClass, int width) {
		this.aClass = aClass;
		staves = new Vector<>();
		this.setSize(width, this.getHeight());
		createStave();
		setScore(score);
		this.setVisible(true);
	}

	public Stave createStave() {
		Stave stave = getInstanceOfE();
		stave.setSize(this.getWidth(), stave.getHeight());
		this.add(stave);
		staves.add(stave);
		return stave;
	}

	public void addParts(Vector<Part> parts) {
		for(Part p: parts)
			addPart(p);
	}
	
	public void setScore(Score score) {
		this.score = score;
		Vector<Part> enum1 = this.score.getPartList();
		addParts(enum1);
	}

	public void addPart(Part part) {
		Vector<Phrase> enum2 = part.getPhraseList();
		addPhrases(enum2);
	}

	public void addPhrases(Vector<Phrase> phrases) {
		for(Phrase p : phrases) 
			addPhrase(p);
	}

	public void addPhrase(Phrase phrase) {
		Vector<Note> enum3 = phrase.getNoteList();

		addNotes(enum3);
	}

	public void addNotes(Vector<Note> notes) {
		for(Note n: notes) 
			addNote(n);
	}

	public void addNote(Note note) {
		staves.get(staves.size()-1).getPhrase().add(note);
		if(staves.get(staves.size()-1).getWidth() > this.getWidth()) {
			createStave();
		}
	}
	
	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		for(Stave s: staves) {
			s.setSize(width, s.getHeight());
		}
	}

	public E getInstanceOfE()
	{
		E instance = null;
		try {
			instance =  aClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return instance;
	} 
	
}
