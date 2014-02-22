package wordnet.main;

import java.io.FileInputStream;
import net.didion.jwnl.JWNL;
import wordnet.ui.MainWindow;

public class PowerSearch {
	public static void main(String[] args) throws Exception {
		String path = PowerSearch.class.getResource("file_properties.xml").getFile();
		JWNL.initialize(new FileInputStream(path));
		MainWindow.mw = new MainWindow();
		MainWindow.mw.setVisible(true);
	}
}