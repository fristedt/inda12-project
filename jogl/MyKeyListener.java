package first_test;

import com.jogamp.newt.event.KeyEvent;

public class MyKeyListener implements java.awt.event.KeyListener {

	@Override
	public void keyPressed(java.awt.event.KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(java.awt.event.KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
				System.exit(0);
		}
	}

	@Override
	public void keyTyped(java.awt.event.KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
