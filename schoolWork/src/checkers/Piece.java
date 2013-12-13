package checkers;

import java.awt.Point;

public class Piece {
	boolean isRed;
	boolean isKing;
	boolean isVisible = true;
	Point cord;
	Piece(){
		this.isRed = false;
		this.cord = new Point(0,0);
		this.isKing = false;
	}
	Piece(boolean isRed, Point cord){
		this.isRed = isRed;
		this.cord = cord;
		this.isKing = false;
	}
	Piece(boolean isRed, int x, int y){
		this.isRed = isRed;
		this.cord = new Point(x,y);
		this.isKing = false;
	}
	void capture(){
		this.isVisible = false;
	}
	void king(){
		this.isKing = true;
	}
	Piece move(int x,int y){
		this.cord = new Point(x,y);
		return this;
	}
	int getX(){
		return (int)this.cord.getX();
	}
	int getY(){
		return (int)this.cord.getY();
	}
	Point getCord(){
		return this.cord;
	}

	boolean equalz(Piece p){
		return this.isRed == p.isRed 
				&& this.isKing == p.isKing
				&& this.isVisible == p.isVisible
				&& this.cord.equals(p.getCord());
	}
}
