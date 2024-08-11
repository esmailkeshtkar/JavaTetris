package pieces;

import java.awt.Color;

public class Z2Piece extends Piece{
	
	public Z2Piece() {
		create(Color.white);
	}
	
	public void setXY(int x, int y) {   
		// [b3]   
		// [b2][b0]
		//     [b1]
		
		b[0].x = x;
		b[0].y = y;
		b[1].x = b[0].x;
		b[1].y = b[0].y - Block.SIZE;
		b[2].x = b[0].x + Block.SIZE;
		b[2].y = b[0].y;
		b[3].x = b[0].x + Block.SIZE;
		b[3].y = b[0].y + Block.SIZE;
	}
	
	public void getDirection1() {
		// [b3]   
		// [b2][b0]
		//     [b1]
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x;
		tempB[1].y = b[0].y - Block.SIZE;
		tempB[2].x = b[0].x + Block.SIZE;
		tempB[2].y = b[0].y;
		tempB[3].x = b[0].x + Block.SIZE;
		tempB[3].y = b[0].y + Block.SIZE;
		
		updateXY(1);
	}
	public void getDirection2() {
		// [b3][b2]
	    //     [b0][b1]
				
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x - Block.SIZE;
		tempB[1].y = b[0].y;
		tempB[2].x = b[0].x;
		tempB[2].y = b[0].y - Block.SIZE;
		tempB[3].x = b[0].x + Block.SIZE;
		tempB[3].y = b[0].y - Block.SIZE;
		
		updateXY(2);
	}
	
	//only has 2 directions
	public void getDirection3() {
		getDirection1();
	}
	public void getDirection4() {
		getDirection2();
	}
}
