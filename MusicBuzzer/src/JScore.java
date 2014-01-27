import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JPanel;

import jm.gui.cpn.Stave;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

@SuppressWarnings({ "serial", "unchecked" })
public class JScore<E extends Stave> extends JPanel {
	private Score score;
	private Vector<Stave> staves;
	private Class<E> aClass;
	private Frame parent;

	public JScore(Frame parent, Score score, Class<E> aClass) {
		this.parent = parent;
		this.aClass = aClass;
		staves = new Vector<>();
		this.setSize(this.parent.getWidth(), this.getHeight());
		createStave();
		setScore(score);
		this.setVisible(true);
		this.setLayout(new GridLayout(0,1));
		this.setMaximumSize(new Dimension(this.getWidth(), this.getMaximumSize().height));
	}

	public Stave createStave() {
		Stave stave = getInstanceOfE();
		stave.setSize(this.getWidth(), stave.getHeight());
		this.add(stave);
		staves.add(stave);
		stave.addMouseListener(new AddNoteListener());
		this.parent.pack();
		return stave;
	}

	public void addParts(Vector<Part> parts) {
		for (Part p : parts)
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
		for (Phrase p : phrases)
			addPhrase(p);
	}

	public void addPhrase(Phrase phrase) {
		Vector<Note> enum3 = phrase.getNoteList();

		addNotes(enum3);
	}

	public void addNotes(Vector<Note> notes) {
		for (Note n : notes)
			addNote(n);
	}

	public void addNote(Note note) {
		addNote(note, staves.size() - 1);
	}
	
	public void addNote(Note note, int staveIndex) {
		Stave s = staves.get(staveIndex);
		s.getPhrase().add(note);
		if (s.getTotalBeatWidth() > s.getWidth() ) {
			s.getPhrase().removeNote(note);
			Stave tempStave = null;
			if(staveIndex + 1 > staves.size() - 1)
				tempStave = createStave();
			else
				tempStave = staves.get(staveIndex + 1);
			tempStave.getPhrase().addNote(note);
			tempStave = staves.get(staves.size() - 1);
			if(tempStave.getTotalBeatWidth() > tempStave.getWidth()) {
				Note tempNote = tempStave.getPhrase().getNote(tempStave.getPhrase().length() -1 );
				staves.get(staves.size() - 1).getPhrase().removeLastNote();
				addNote(tempNote);
			}
		}
	}


	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		for (Stave s : staves) {
			s.setSize(width, s.getHeight());
		}
	}

	public E getInstanceOfE() {
		E instance = null;
		try {
			instance = aClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return instance;
	}

	class AddNoteListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			Stave editedStave = (Stave) e.getSource();
			Note lastNote = editedStave.getPhrase().getNote(editedStave.getPhrase().length() - 1);
			editedStave.getPhrase().removeLastNote();
			addNote(lastNote,staves.indexOf(editedStave));
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
}
