import jm.constants.RhythmValues;


public class MusicBuzzerDuration implements RhythmValues{
	private double value;
	public boolean dotted;
	
	public MusicBuzzerDuration(double value) {
		this.value = value;
		this.dotted = false;
	}
	
	public double getDuration() {
		return value;
	}
	
	public boolean isDotted() {
		return dotted;
	}
	
	public void setDotted(boolean dotted) {
		this.dotted = dotted;
	}
	
	@Override
	protected MusicBuzzerDuration clone() {
		MusicBuzzerDuration value = new MusicBuzzerDuration(this.value);
		this.setDotted(dotted);
		return value;
	}
	
	@Override
	public String toString() {
		if(value == WHOLE_NOTE)
			return "\uD834\uDD5D";
		else if(value == HALF_NOTE)
			return "\uD834\uDD5E";
		else if(value == QUARTER_NOTE)
			return "\u2669";
		else if(value == EIGHTH_NOTE)
			return "\u266A";
		else if(value == SIXTEENTH_NOTE)
			return "\uD834\uDD61";
		else if(value == THIRTYSECOND_NOTE)
			return "\uD834\uDD62";
		else if(value == THIRTYSECOND_NOTE/2)
			return "\uD834\uDD63";
		else
			return "\uD834\uDD64";
	}
}
