package pieces;

import java.awt.Color;

public class BarPiece extends Piece{
	
	public BarPiece() {
		create(Color.YELLOW);
	}
	
	public void setXY(int x, int y) {
		// [b1][b0][b2][b3]
		b[0].x = x;
		b[0].y = y;
		b[1].x = b[0].x - Block.SIZE;
		b[1].y = b[0].y;
		b[2].x = b[0].x + Block.SIZE;
		b[2].y = b[0].y;
		b[3].x = b[0].x + Block.SIZE*2;
		b[3].y = b[0].y;
		
	}
	
	public void getDirection1() {
		// [b1][b0][b2][b3]
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x - Block.SIZE;
		tempB[1].y = b[0].y;
		tempB[2].x = b[0].x + Block.SIZE;
		tempB[2].y = b[0].y;
		tempB[3].x = b[0].x + Block.SIZE*2;
		tempB[3].y = b[0].y;
		
		updateXY(1);
	}
	public void getDirection2() {
		// [b1]
		// [b0]
		// [b2]
		// [b3]
				
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x;
		tempB[1].y = b[0].y - Block.SIZE;
		tempB[2].x = b[0].x;
		tempB[2].y = b[0].y + Block.SIZE;
		tempB[3].x = b[0].x;
		tempB[3].y = b[0].y + Block.SIZE*2;
		
		updateXY(2);
	}
	
	//since the bar piece only has 2 directions (horizontal and vertical)
	//we can call direction 1 and 2 to rotate it those directions
	public void getDirection3() {
		getDirection1();
	}
	public void getDirection4() {
		getDirection2();
	}
}
