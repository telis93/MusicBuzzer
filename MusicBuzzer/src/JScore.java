
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.ParameterizedType;
import java.util.Enumeration;
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

	public JScore(Score score, Class<E> aClass) {
		this.score = score;
		this.aClass = aClass;
		staves = new Vector<>();
		Stave stave = getInstanceOfE();
		stave.setSize(200, 200);
		this.add(stave);
		this.setSize(210, 210);
		Enumeration<Part> enum1 = this.score.getPartList().elements();
		int i = 0;
		while (enum1.hasMoreElements()) {
			Part part = enum1.nextElement();

			Enumeration<Phrase> enum2 = part.getPhraseList().elements();
			while (enum2.hasMoreElements()) {
				Phrase phrase = enum2.nextElement();

				Enumeration<Note> enum3 = phrase.getNoteList().elements();

				while (enum3.hasMoreElements()) {
					Note note = enum3.nextElement();
					stave.getPhrase().add(note);
					if(stave.getWidth() > this.getWidth()) {
						staves.add(stave);
						stave = getInstanceOfE();
						this.add(stave);
					}
				}
			}
			i++; // increment the part index
		}
		this.setVisible(true);
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
