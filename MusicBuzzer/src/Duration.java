
public class Duration {
	DurationValue value;
	public Duration(DurationValue value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		if(value == DurationValue.WHOLE)
			return "\uD834\uDD5D";
		else if(value == DurationValue.HALF)
			return "\uD834\uDD5E";
		else if(value == DurationValue.QUARTER)
			return "\u2669";
		else if(value == DurationValue.EIGHTTH)
			return "\u266A";
		else if(value == DurationValue.SIXTEENTH)
			return "\uD834\uDD61";
		else if(value == DurationValue.THIRTYSECOND)
			return "\uD834\uDD62";
		else if(value == DurationValue.SIXTYFOURTH)
			return "\uD834\uDD63";
		else
			return "\uD834\uDD64";
	}
	
	public double getDuration(int tempo) {
		if(value == DurationValue.WHOLE)
			return 4*(60/(double) tempo)*1000;
		else if(value == DurationValue.HALF)
			return 2*(60/(double) tempo)*1000;
		else if(value == DurationValue.QUARTER)
			return (60/(double) tempo)*1000;
		else if(value == DurationValue.EIGHTTH)
			return (60/(double) tempo)*1000/2.0;
		else if(value == DurationValue.SIXTEENTH)
			return (60/(double) tempo)*1000/4.0;
		else if(value == DurationValue.THIRTYSECOND)
			return (60/(double) tempo)*1000/8.0;
		else if(value == DurationValue.SIXTYFOURTH)
			return (60/(double) tempo)*1000/16.0;
		else
			return (60/(double) tempo)*1000/32.0;
	}
}
