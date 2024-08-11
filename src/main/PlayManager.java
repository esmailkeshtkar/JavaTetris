package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import pieces.BarPiece;
import pieces.Block;
import pieces.L1Piece;
import pieces.L2Piece;
import pieces.Piece;
import pieces.SquarePiece;
import pieces.TPiece;
import pieces.Z1Piece;
import pieces.Z2Piece;

public class PlayManager {
	//Main play area
	
	final int WIDTH = 360;
	final int HEIGHT = 600;
	public static int left_x, right_x, top_y, bottom_y;
	
	//piece
	Piece currentPiece;
	final int PIECE_START_X, PIECE_START_Y;
	
	//utilized for the next piece
	Piece nextPiece;
	final int NEXTPIECE_X, NEXTPIECE_Y;
	public static ArrayList<Block> staticBlocks = new ArrayList<>();
	
	public static int dropInterval = 60; //drops every 60 frames 
	
	//effects
	boolean effectCounterOn;
	int effectCounter;
	ArrayList<Integer> effectY = new ArrayList<>();
	
	//Score
	int level = 1, lines, score;
	
	boolean gameOver;
	
	
	public PlayManager() {
		
		//sets the main play game area
		left_x = (GamePanel.WIDTH/2) - (WIDTH/2); //1280/2 - 360/2 = 460
		right_x = left_x + WIDTH;
		top_y = 50;
		bottom_y = top_y + HEIGHT;
		
		PIECE_START_X = left_x + (WIDTH/2) - Block.SIZE;
		PIECE_START_Y = top_y + Block.SIZE;
		
		NEXTPIECE_X = right_x+175;
		NEXTPIECE_Y = top_y+500;
		
		//set starting piece
		currentPiece = pickPiece();
		currentPiece.setXY(PIECE_START_X, PIECE_START_Y);
		
		//picking the next piece randomly
		nextPiece = pickPiece();
		nextPiece.setXY(NEXTPIECE_X, NEXTPIECE_Y);
		
	}
	
	private Piece pickPiece() {
		//pick a random piece
		Piece piece = null;
		int i = new Random().nextInt(7); 
		//picks random number 0 to 6 
		//based on the number we return a different piece
		switch(i) {
		case 0: piece = new L1Piece(); break;
		case 1: piece = new L2Piece(); break;
		case 2: piece = new TPiece(); break;
		case 3: piece = new SquarePiece(); break;
		case 4: piece = new Z1Piece(); break;
		case 5: piece = new Z2Piece(); break;
		case 6: piece = new BarPiece(); break;
		}
		
		return piece;
	}
	
	public void update() {
		//checks if the current piece is active
		if(currentPiece.active == false){
			//if the current piece is not active then put it into staticBlocks array
			staticBlocks.add(currentPiece.b[0]);
			staticBlocks.add(currentPiece.b[1]);
			staticBlocks.add(currentPiece.b[2]);
			staticBlocks.add(currentPiece.b[3]);
			
			//check if the game is over
			if(currentPiece.b[0].x == PIECE_START_X && currentPiece.b[0].y == PIECE_START_Y){
				//current piece collided with a block and cannot move and its x/y are the same as the next piece
				GamePanel.music.stop(); // play game over music
				GamePanel.se.play(2,  false);
				gameOver = true;
			}
			
			currentPiece.deactivating = false;
			//replace the current piece with next piece
			currentPiece = nextPiece;
			currentPiece.setXY(PIECE_START_X, PIECE_START_Y);
			nextPiece = pickPiece();
			nextPiece.setXY(NEXTPIECE_X, NEXTPIECE_Y);
			
			//when a piece becomes inactive check if the line can be deleted
			checkDelete();
		}
		else
		currentPiece.update();
	}
	
	public void checkDelete() {
		int x = left_x;
		int y= top_y;
		int blockCount = 0;
		int lineCount = 0;
		
		while(x < right_x && y < bottom_y) {
			
			for(int i = 0; i < staticBlocks.size(); i++)
			{
				if(staticBlocks.get(i).x == x && staticBlocks.get(i).y == y)
					//increase the count if there is a static block
					blockCount++;
			}
			
			x+= Block.SIZE;
			
			if(x== right_x){
				//if the total blocks is 12, the row is completely filled and can be deleted
				if(blockCount == 12) {
					
					//when a line is deleted add an effect to the array 
					effectCounterOn = true;
					effectY.add(y);
					
					for(int i = staticBlocks.size()-1; i > -1; i--) { //check from the largest number
						//remove all blocks from the current y line
						if(staticBlocks.get(i).y == y)
							staticBlocks.remove(i);
					}
					
					lineCount++;
					lines++;
					//increase drop speed when the line score hits a certain number
					//every 10 lines the level increases
					if(lines % 10 == 0 && dropInterval > 1)
					{
						level++;
						if(dropInterval > 10)
							dropInterval -=10;
						else
							dropInterval -=1;
					}
					
					//a line has been deleted so we need to slide down the blocks that are above it
					for(int i = 0; i < staticBlocks.size(); i++)
					{
						//if a block is above the current y, move it down by the block size
						if(staticBlocks.get(i).y < y) {
							staticBlocks.get(i).y += Block.SIZE;
						}
					}
				}
				
				blockCount = 0;
				x = left_x;
				y += Block.SIZE;
			}
			
			//Calculate score based on the level and number of lines deleted
			if(lineCount >0)
			{
				GamePanel.se.play(1,  false);
				int singleLineScore = 10 * level;
				score+= singleLineScore * lineCount;
			}
		}
	}
	
	//draw method used to draw the game and effects
	public void draw(Graphics2D g2) {
		//main play area
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(4f));
		g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8);
		
		//draws the next piece waiting room area
		int x = right_x+100;
		int y = bottom_y-200;
		g2.drawRect(x, y, 200, 200);
		g2.setFont(new Font("Arial", Font.PLAIN, 15));
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.drawString("NEXT", x+75, y+30);
		
		//draw the score frame
		g2.drawRect(x, top_y, 200, 300);
		x+=20;
		y = top_y + 90;
		g2.drawString("LEVEL: " + level, x, y); y+=70;
		g2.drawString("LINES :" + lines, x, y); y+=70;
		g2.drawString("SCORE: " + score, x, y);
		
		//draw the current piece
		if(currentPiece != null)
			currentPiece.draw(g2);
		
		//draw the next piece
		nextPiece.draw(g2);
		
		//draw the static blocks array and draw the piece 1 by 1
		for(int i = 0; i < staticBlocks.size(); i++) {
			staticBlocks.get(i).draw(g2);
		}
		
		//draws an effect when a line is cleared
		if(effectCounterOn){
			effectCounter++;
			g2.setColor(Color.WHITE);
			for(int i = 0; i< effectY.size(); i++) {
				g2.fillRect(left_x, effectY.get(i), WIDTH, Block.SIZE);
			}
			
			if(effectCounter == 10)
			{
				effectCounterOn = false;
				effectCounter = 0;
				effectY.clear();
			}
		}
		
		g2.setFont(g2.getFont().deriveFont(50f));
		
		//draw pause screen
		if(KeyHandler.pause) {
			g2.setColor(Color.WHITE);
			x = left_x + 70;
			y  = top_y + 320;
			g2.drawString("PAUSED", x, y);
		}
			
		//draw game over screen
		if(gameOver) {
			g2.setColor(Color.RED);
			x = left_x + 25;
			y = top_y + 320;
			g2.drawString("GAME OVER", x, y);
		}
	}
}
