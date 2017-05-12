package chess.pieces;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public abstract class Piece {

	private Image image;
	
	private int x , y;
	private int indexX , indexY;
	
	private boolean isBlack;
	
	public Piece(int x , int y , String name , boolean isBlack){
		this.indexX = x;
		this.indexY = y;
		this.x = indexX*80;
		this.y = indexY*80+39;
		this.isBlack = isBlack;
		init(name);
	}
	
	private void init(String name){
		String dir = "Resource/w"+name+".png";
		if(isBlack) dir = "Resource/b"+name+".png";
		
		image = Toolkit.getDefaultToolkit().getImage(dir);
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setIndeX(int indexX){
		this.indexX = indexX;
		x = indexX * 80;
	}
	
	public void setIndexY(int indexY){
		this.indexY = indexY;
		y = indexY * 80 + 39;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getIndexX(){
		return indexX;
	}
	
	public int getIndexY(){
		return indexY;
	}
	
	public void draw(Graphics g){
		g.drawImage(image, x, y, null);
	}
	
}
