import jm.music.data.Note;

@SuppressWarnings("serial")
public class MusicBuzzerNote extends Note {
	private String name;
	private MusicBuzzerDuration musicBuzzerDuration;
	private boolean sharp;
	private boolean flat;
	private int octave;
	final static public String SHARP_SIGN = "#";
	final static public String FLAT_SIGN = "\u266D";

	public MusicBuzzerNote(String name, double freq) {
		super(freq, jm.constants.Durations.QUARTER_NOTE);
		this.name = name;
		this.flat = false;
		this.sharp = false;
		this.octave = 0;
	}
	
	public void setMusicBuzzerDuration(MusicBuzzerDuration musicBuzzerDuration) {
		this.musicBuzzerDuration = musicBuzzerDuration;
		setDuration(musicBuzzerDuration.getDuration());
	}
	public void setOctave(int octave) {
		this.octave = octave;
	}

	public void setSharp(boolean sharp) {
		if (sharp && !this.sharp) {
			name += SHARP_SIGN;
			setFrequency(getFrequency()*Math.pow(2, 1.0/12));
		} else if (!sharp && this.sharp) {
			name = name.substring(0, name.indexOf(SHARP_SIGN));
			setFrequency(getFrequency()*Math.pow(2, -1.0/12));
		}
		this.sharp = sharp;

	}

	public void setFlat(boolean flat) {
		if (flat && !this.flat) {
			name += FLAT_SIGN;
			setFrequency(getFrequency()*Math.pow(2, -1.0/12));
		} else if (!flat && this.flat) {
			name = name.substring(0, name.indexOf(FLAT_SIGN));
			setFrequency(getFrequency()*Math.pow(2, 1.0/12));
		}
		this.flat = flat;
	}

	public String toString() {
		return name;
	}

	public void updateOctave(int value) {
		setFrequency(getFrequency() * Math.pow(2, value));
		name = "" + name.charAt(0) + (value + 4) + ((flat || sharp)?name.charAt(2):"");
	}
	public MusicBuzzerDuration getMusicBuzzerDuration() {
		return musicBuzzerDuration;
	}
	
	public double getDuration(int tempo) {
			return (getDuration()/(double) tempo)*1000*60;
	}

	@Override
	protected MusicBuzzerNote clone() {
		Note note = copy();
		MusicBuzzerNote MBnote = new MusicBuzzerNote(name,
				note.getFrequency());
		MBnote.setOctave(this.octave);

		return MBnote;
	}

}
