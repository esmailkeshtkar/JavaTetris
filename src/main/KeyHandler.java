package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	public static boolean up, down, left, right = false;
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		
		if(code==KeyEvent.VK_W)
			up = true;
		if(code==KeyEvent.VK_A)
			left = true;
		if(code==KeyEvent.VK_S)
			down = true;
		if(code==KeyEvent.VK_D)
			right = true;
				
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
