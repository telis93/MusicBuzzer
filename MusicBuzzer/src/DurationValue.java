
public class DurationValue {
	public static enum Value {
		WHOLE, HALF, QUARTER, EIGHTTH, SIXTEENTH, 
		THIRTYSECOND, SIXTYFOURTH, ONEHUNDREDTWENTYEIGHTTH
	};
	Value value;
	public DurationValue(Value value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		if(value == Value.WHOLE)
			return "\uD834\uDD5D";
		else if(value == Value.HALF)
			return "\uD834\uDD5E";
		else if(value == Value.QUARTER)
			return "\u2669";
		else if(value == Value.EIGHTTH)
			return "\u266A";
		else if(value == Value.SIXTEENTH)
			return "\uD834\uDD61";
		else if(value == Value.THIRTYSECOND)
			return "\uD834\uDD62";
		else if(value == Value.SIXTYFOURTH)
			return "\uD834\uDD63";
		else
			return "\uD834\uDD64";
	}
	
	public double getDuration(int tempo) {
		if(value == Value.WHOLE)
			return 4*(60/(double) tempo)*1000;
		else if(value == Value.HALF)
			return 2*(60/(double) tempo)*1000;
		else if(value == Value.QUARTER)
			return (60/(double) tempo)*1000;
		else if(value == Value.EIGHTTH)
			return (60/(double) tempo)*1000/2.0;
		else if(value == Value.SIXTEENTH)
			return (60/(double) tempo)*1000/4.0;
		else if(value == Value.THIRTYSECOND)
			return (60/(double) tempo)*1000/8.0;
		else if(value == Value.SIXTYFOURTH)
			return (60/(double) tempo)*1000/16.0;
		else
			return (60/(double) tempo)*1000/32.0;
	}
}
