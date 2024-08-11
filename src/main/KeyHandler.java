package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	public static boolean up, down, left, right, pause; //used for input commands
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		
		//utilizes the WASD keys
		//W rotates the pieces while ASD translate the pieces left right or down
		if(code==KeyEvent.VK_W)
			up = true;
		if(code==KeyEvent.VK_A)
			left = true;
		if(code==KeyEvent.VK_S)
			down = true;
		if(code==KeyEvent.VK_D)
			right = true;
		if(code == KeyEvent.VK_SPACE) {//when space is pressed pauses or unpauses the game
			if(pause)
				pause = false;
			else
				pause = true;
		}
				
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
