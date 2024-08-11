package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 720;
	final int FPS = 60;
	Thread gameThread;
	PlayManager pm;
	public static Sound music = new Sound();
	public static Sound se = new Sound();
	
	public GamePanel()
	{
		//settings
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));//1280x720 window size
		this.setBackground(Color.black);
		this.setLayout(null);
		
		//implement KeyListener
		this.addKeyListener(new KeyHandler());
		setFocusable(true);
		
		pm = new PlayManager();
		
	}
	
	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();
		
		music.play(0,  true);
		music.loop();
	}

	@Override
	public void run() {
		//game loop
		double drawInterval = 250000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread !=null) {
			currentTime = System.nanoTime();
			
			delta+= (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1)
			{
				update();
				repaint();
				delta--;
			}
		}
	}
	
	public void update() {
		//only updates the game if the game is not paused
		if(KeyHandler.pause == false && pm.gameOver == false) {
			pm.update();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		pm.draw(g2);
	}
}
