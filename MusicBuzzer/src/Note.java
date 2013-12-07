
public class Note {
	private String name;
	private double freq;
	private boolean sharp;
	private boolean flat;
	private DurationValue duration;
	final static public String SHARP_SIGN = "#";
	final static public String FLAT_SIGN = "\u266D";
	
	public Note(String name, double freq) {
		this.name = name;
		this.freq = freq;
		this.flat = false;
		this.sharp = false;
		this.duration = new DurationValue(DurationValue.Value.QUARTER);
	}
	
	public void setDuration(DurationValue duration) {
		this.duration = duration;
	}
	
	public DurationValue getDuration() {
		return duration;
	}
	public void setSharp(boolean sharp) {
		if(sharp && !this.sharp){
			freq *= Math.pow(2,1/12.0);
			name += SHARP_SIGN;
		}
		else if (!sharp && this.sharp) {
			freq /=Math.pow(2,1/12.0);
			name = name.substring(0,name.indexOf(SHARP_SIGN));
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
	}
	
	@Override
	protected Note clone() {
		Note cloned = new Note(name, freq);
		cloned.setFlat(flat);
		cloned.setSharp(sharp);
		return cloned;
	}

}
