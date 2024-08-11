package pieces;

import java.awt.Color;

public class SquarePiece extends Piece{
	
	public SquarePiece() {
		create(Color.ORANGE);
	}
	
	public void setXY(int x, int y) {   
		// [b0][b1]
		// [b2][b3]
		
		b[0].x = x;
		b[0].y = y;
		b[1].x = b[0].x;
		b[1].y = b[0].y + Block.SIZE;
		b[2].x = b[0].x + Block.SIZE;
		b[2].y = b[0].y;
		b[3].x = b[0].x + Block.SIZE;
		b[3].y = b[0].y + Block.SIZE;
	}
	
	//square does not need to be rotated
	public void getDirection1() {}
	public void getDirection2() {}
	public void getDirection3() {}
	public void getDirection4() {}
}
