
public class Note {
	private String name;
	private double freq;
	private boolean sharp;
	private boolean flat;
	private DurationValue duration;
	
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
		if(sharp && !this.sharp)
			freq *= Math.pow(2,1/12.0);
		else if(!sharp && this.sharp)
			freq /=Math.pow(2,1/12.0);
		this.sharp = sharp;
			
	}
	
	public void setFlat(boolean flat) {
		if(flat && !this.flat)
			freq /= Math.pow(2,1/12.0);
		else if(!flat && this.flat)
			freq *= Math.pow(2,1/12.0);
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
