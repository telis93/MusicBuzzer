import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		new Frame();

	}

}
