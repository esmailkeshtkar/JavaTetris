package pieces;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import main.KeyHandler;
import main.PlayManager;

//superclass Piece
public class Piece {
	
	public Block b[] = new Block[4]; //block array used to store the 4 blocks
	public Block tempB[] = new Block[4]; //used for collision so that rotation does not happen if there is a collision
	int autoDropCounter = 0;
	public int direction = 1; //4 directions per piece
	boolean leftCollision, rightCollision, bottomCollision;
	public boolean active = true;
	//gives leeway when the piece hits the bottom so that it can be slightly maneuvered for a short period of time
	public boolean deactivating;
	int deactivateCounter = 0;
	
	public void create(Color c)
	{
		//block array for storing the 4 blocks of each piece
		b[0] = new Block(c);
		b[1] = new Block(c);
		b[2] = new Block(c);
		b[3] = new Block(c);
		tempB[0] = new Block(c);
		tempB[1] = new Block(c);
		tempB[2] = new Block(c);
		tempB[3] = new Block(c);
	}
	
	public void setXY(int x, int y) {}
	public void updateXY(int direction) {
		
		checkRotationCollision();
		if(leftCollision == false && rightCollision == false && bottomCollision == false)
		{
			this.direction = direction;
			b[0].x = tempB[0].x;
			b[0].y = tempB[0].y;
			b[1].x = tempB[1].x;
			b[1].y = tempB[1].y;
			b[2].x = tempB[2].x;
			b[2].y = tempB[2].y;
			b[3].x = tempB[3].x;
			b[3].y = tempB[3].y;
		}
	}
	
	//methods are left blank to be overridden in individual piece class
	public void getDirection1() {}
	public void getDirection2() {}
	public void getDirection3() {}
	public void getDirection4() {}
	public void checkMovementCollision() {
		leftCollision = false;
		rightCollision = false;
		bottomCollision = false;
		
		checkStaticBlockCollision();
		
		//Check collision with the left wall
		for(int i = 0; i <b.length; i++) {
			if(b[i].x == PlayManager.left_x) {
				leftCollision = true;
			}
		}
		
		//check collision with right wall
		for(int i = 0; i <b.length; i++) {
			if(b[i].x +Block.SIZE == PlayManager.right_x) {
				rightCollision = true;
			}
		}
		
		//check collision with bottom wall
		for(int i = 0; i <b.length; i++) {
			if(b[i].y + Block.SIZE == PlayManager.bottom_y) {
				bottomCollision = true;
			}
		}
	}
	
	public void checkRotationCollision() {
		leftCollision = false;
		rightCollision = false;
		bottomCollision = false;
		
		checkStaticBlockCollision();
		
		//Check collision with the left wall
		for(int i = 0; i <b.length; i++) {
			if(tempB[i].x < PlayManager.left_x) {
				leftCollision = true;
			}
		}
		
		//check collision with right wall
		for(int i = 0; i <b.length; i++) {
			if(tempB[i].x +Block.SIZE > PlayManager.right_x) {
				rightCollision = true;
			}
		}
		
		//check collision with bottom wall
		for(int i = 0; i <b.length; i++) {
			if(tempB[i].y + Block.SIZE > PlayManager.bottom_y) {
				bottomCollision = true;
			}
		}
	}
	
	//checks collision with other blocks
	private void checkStaticBlockCollision(){
		
		for(int i = 0; i < PlayManager.staticBlocks.size(); i++)
		{
			int targetX = PlayManager.staticBlocks.get(i).x;
			int targetY = PlayManager.staticBlocks.get(i).y;
			
			//check bottom
			for(int j = 0; j < b.length; j++)
				//a block is right below the current block
				if(b[j].y + Block.SIZE == targetY && b[j].x == targetX)
					bottomCollision = true;
			
			//check left collision
			for(int j = 0; j < b.length; j++)
				//a block is right below the current block
				if(b[j].x + Block.SIZE == targetX && b[j].y == targetY)
					leftCollision = true;
			
			//check right collision
			for(int j = 0; j < b.length; j++)
				//a block is right below the current block
				if(b[j].x + Block.SIZE == targetX && b[j].y == targetY)
					rightCollision = true;
		}
	}
	
	//a deactivating counter for when a tetris hits the bottom
	//lasts 60 frames before the tetris can no longer be moved
	private void deactivating()
	{
		deactivateCounter++;
		
		if(deactivateCounter == 135)
		{
			deactivateCounter = 0;
			checkMovementCollision();
			if(bottomCollision)
				active = false;
		}
	}
	
	public void update(){
		
		if(deactivating)
			deactivating();

		if(KeyHandler.up){
			//rotates the piece when up is pressed
			switch(direction) {
			case 1: getDirection2(); break;
			case 2: getDirection3(); break;
			case 3: getDirection4(); break;
			case 4: getDirection1(); break;
			}
			KeyHandler.up = false;
			GamePanel.se.play(3, false);//play rotation sound
		}
		
		checkMovementCollision();//check if the piece is touching wall or floor
		
		if(KeyHandler.down){
			//moves block down 1
			//if piece is not hitting the bottom it can move down
			if(bottomCollision == false)
			{
				b[0].y += Block.SIZE;
				b[1].y += Block.SIZE;
				b[2].y += Block.SIZE;
				b[3].y += Block.SIZE;
				autoDropCounter = 0;
			}
			KeyHandler.down = false;
		}
		if(KeyHandler.left){
			//moves block left 1
			//if piece is not hitting the left wall it can move left
			if(leftCollision == false){
				b[0].x -= Block.SIZE;
				b[1].x -= Block.SIZE;
				b[2].x -= Block.SIZE;
				b[3].x -= Block.SIZE;
				
				autoDropCounter = 0;
			}
			KeyHandler.left = false;
		}
		if(KeyHandler.right){
			//moves block right 1
			//if piece is not hitting the right wall it can move right
			if(rightCollision == false) {
				b[0].x += Block.SIZE;
				b[1].x += Block.SIZE;
				b[2].x += Block.SIZE;
				b[3].x += Block.SIZE;
			}
			
			autoDropCounter = 0;
			KeyHandler.right = false;
		}
		
		if(bottomCollision){
			if(deactivating == false) {
				GamePanel.se.play(4,  false);//sound effect for bottom collision
			}
			deactivating = true;
		}
		else{
			autoDropCounter++; //counter increases every frame
			//piece drops once every 60 frames
			if(autoDropCounter == PlayManager.dropInterval)
			{
				//piece goes down 1 block
				b[0].y += Block.SIZE;
				b[1].y += Block.SIZE;
				b[2].y += Block.SIZE;
				b[3].y += Block.SIZE;
				autoDropCounter = 0; //reset counter 
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		g2.setColor(b[0].c);
		int margin = 1;
		g2.fillRect(b[0].x+margin, b[0].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
		g2.fillRect(b[1].x+margin, b[1].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
		g2.fillRect(b[2].x+margin, b[2].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
		g2.fillRect(b[3].x+margin, b[3].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
	}
}
