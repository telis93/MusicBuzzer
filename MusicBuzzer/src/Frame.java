import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class Frame {

	protected Shell shlMusicbuzzer;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Frame window = new Frame();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlMusicbuzzer.open();
		shlMusicbuzzer.layout();
		while (!shlMusicbuzzer.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlMusicbuzzer = new Shell();
		shlMusicbuzzer.setSize(280, 281);
		shlMusicbuzzer.setText("MusicBuzzer");

		CCombo noteCombo = new CCombo(shlMusicbuzzer, SWT.BORDER);
		noteCombo.setFont(SWTResourceManager.getFont("Open Sans", 26, SWT.NORMAL));
		noteCombo.add("A");
		noteCombo.add("B");
		noteCombo.add("C");
		noteCombo.add("D");
		noteCombo.add("E");
		noteCombo.add("F");
		noteCombo.add("G");
		noteCombo.select(0);
		noteCombo.setBounds(10, 10, 75, 50);

		CCombo durationCombo = new CCombo(shlMusicbuzzer, SWT.BORDER);
		durationCombo.setFont(SWTResourceManager.getFont("Open Sans", 26, SWT.NORMAL));
		durationCombo.add(new Duration(DurationValue.WHOLE).toString());
		durationCombo.add(new Duration(DurationValue.HALF).toString());
		durationCombo.add(new Duration(DurationValue.QUARTER).toString());
		durationCombo.add(new Duration(DurationValue.EIGHTTH).toString());
		durationCombo.add(new Duration(DurationValue.SIXTEENTH).toString());
		durationCombo.add(new Duration(DurationValue.THIRTYSECOND).toString());
		durationCombo.add(new Duration(DurationValue.SIXTYFOURTH).toString());
		durationCombo.setBounds(115, 10, 75, 50);

		Button sharpButton = new Button(shlMusicbuzzer, SWT.TOGGLE);
		sharpButton.setFont(SWTResourceManager.getFont("Open Sans", 26, SWT.NORMAL));
		sharpButton.setBounds(10, 78, 50, 50);
		sharpButton.setText(Note.SHARP_SIGN);

		Button dotButton = new Button(shlMusicbuzzer, SWT.TOGGLE);
		dotButton.setFont(SWTResourceManager.getFont("Open Sans", 26, SWT.NORMAL));
		dotButton.setText(".");
		dotButton.setBounds(74, 78, 50, 50);

		Button flatButton = new Button(shlMusicbuzzer, SWT.TOGGLE);
		flatButton.setFont(SWTResourceManager.getFont("Open Sans", 26, SWT.NORMAL));
		flatButton.setText(Note.FLAT_SIGN);
		flatButton.setBounds(140, 78, 50, 50);
		
		Scale scale = new Scale(shlMusicbuzzer, SWT.VERTICAL);
		scale.setPageIncrement(1);
		scale.setMaximum(6);
		scale.setSelection(3);
		scale.setBounds(208, 10, 46, 118);
		
		Button addNoteButton = new Button(shlMusicbuzzer, SWT.NONE);
		addNoteButton.setFont(SWTResourceManager.getFont("Open Sans", 12, SWT.NORMAL));
		addNoteButton.setBounds(10, 144, 114, 40);
		addNoteButton.setText("Add Note");
		
		Button removeNoteButton = new Button(shlMusicbuzzer, SWT.NONE);
		removeNoteButton.setText("Remove Note");
		removeNoteButton.setFont(SWTResourceManager.getFont("Open Sans", 12, SWT.NORMAL));
		removeNoteButton.setBounds(10, 192, 114, 40);
		
		Button playButton = new Button(shlMusicbuzzer, SWT.NONE);
		playButton.setFont(SWTResourceManager.getFont("Open Sans", 20, SWT.NORMAL));
		playButton.setBounds(140, 144, 114, 88);
		playButton.setText("Play");

	}
}
