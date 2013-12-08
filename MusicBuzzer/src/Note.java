import abc.notation.AccidentalType;


public class Note {
	private String name;
	private double freq;
	private boolean sharp;
	private boolean flat;
	private int octave;
	private Duration duration;
	final static public String SHARP_SIGN = "#";
	final static public String FLAT_SIGN = "\u266D";
	
	public Note(String name, double freq) {
		this.name = name;
		this.freq = freq;
		this.flat = false;
		this.sharp = false;
		this.duration = new Duration(DurationValue.QUARTER);
	}
	
	public byte nametoABC() {
		char name = this.name.charAt(0);
		if(name == 'A')
			return abc.notation.Note.A;
		else if(name == 'B')
			return abc.notation.Note.B;
		else if(name == 'C')
			return abc.notation.Note.C;
		else if(name == 'D')
			return abc.notation.Note.D;
		else if(name == 'E')
			return abc.notation.Note.E;
		else if(name == 'F')
			return abc.notation.Note.F;
		else if(name == 'G')
			return abc.notation.Note.G;
		else 
			return ' ';
				
	}
	
	public abc.notation.Note toABC() {
		abc.notation.Note value = new abc.notation.Note(nametoABC());
		if(sharp)
			value.setAccidental(AccidentalType.SHARP);
		else if(flat)
			value.setAccidental(AccidentalType.FLAT);
		return value;
	}
	
	
	
	public void setOctave(int octave) {
		this.octave = octave;
	}
	
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	
	public Duration getDuration() {
		return duration;
	}
	public void setSharp(boolean sharp) {
		if(sharp && !this.sharp){
			freq *= Math.pow(2,1/12.0);
			name += SHARP_SIGN;
		}
		else if (!sharp && this.sharp) {
			freq /=Math.pow(2,1/12.0);
			name = Character.toString(name.charAt(0));
		}
		this.sharp = sharp;
			
	}
	
	public void setFlat(boolean flat) {
		if(flat && !this.flat) {
			freq /= Math.pow(2,1/12.0);
			name += FLAT_SIGN;
		}
		else if(!flat && this.flat) {
			freq *= Math.pow(2,1/12.0);
			name = name.substring(0, name.indexOf(FLAT_SIGN));
		}
		this.flat = flat;
	}
	
	public String getName() {
		return name;
	}
	
	public double getFreq() {
		return freq;
	}
	
	public boolean isFlat() {
		return flat;
	}
	
	public boolean isSharp() {
		return sharp;
	}
	
	public String toString() {
		return getName();
	}
	
	public void updateOctave(int value) {
		freq *= Math.pow(2, value);
		octave = value;
	}
	
	@Override
	protected Note clone() {
		Note cloned = new Note(name, freq);
		cloned.setFlat(flat);
		cloned.setSharp(sharp);
		return cloned;
	}

}
