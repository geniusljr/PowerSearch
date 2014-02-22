package wordnet.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import wordnet.ui.MainWindow;
/**
 * 实现拖放查词功能的鼠标监听器
 * @author Dragon
 *
 */
public class MyMouseListener implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(MainWindow.mw.inputTextField.getText().length()>0)
		MainWindow.mw.searchStart(new String(MainWindow.mw.inputTextField.getText()), false);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(MainWindow.mw.inputTextField.getText().length()>0)
		MainWindow.mw.searchStart(new String(MainWindow.mw.inputTextField.getText()), false);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		//MainWindow.mw.searchStart(new String(MainWindow.mw.inputTextField.getText()), false);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
}