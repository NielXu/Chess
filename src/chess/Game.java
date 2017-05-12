package chess;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import chess.gui.GamePanel;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class Game implements MouseMotionListener , MouseListener{
	
	/**bPieces means black pieces, wPieces means white pieces, currentPieces represents which color of pieces is moving**/
	private List<Piece> bPieces , wPieces , currentPieces;
	
	/**The GamePanel**/
	private GamePanel panel;
	
	/**If a piece is being dragged, if this is white pieces turn or black pieces turn**/
	private boolean dragging , white_turn;
	
	/**The piece that player is dragging**/
	private Piece draggingPiece;
	
	/**The board matrix , positive number represents white, negative represents black
	 * 0 - whitespaces 1 - Pawn , 2 - Rooks , 3 - Knight , 4 - Bishop , 5 - Queen , 6 - King**/
	private int[][] matrix;
	
	public Game(){
		
	}
	
	/**
	 * Start a new game
	 */
	public void start(){
		init();
		panel = new GamePanel(this);		
	}
	
	//init the Game
	private void init(){
		bPieces = new ArrayList<Piece>();
		wPieces = new ArrayList<Piece>();
		matrix = new int[8][8];
		initPieces();
	}
	
	//Initialize all pieces at the start of the game
	private void initPieces(){
		//init black pieces
		for(int i=0;i<8;i++){
			bPieces.add(new Pawn(i , 1 , true));
			matrix[i][1] = -9;
		}
		//1 - Pawn , 2 - Rooks , 3 - Knight , 4 - Bishop , 5 - Queen , 6 - King , 9 unmoved pawn , 10 - unmoved King , 11 - unmoved Rook
		bPieces.add(new Rook(0,0,true));
		matrix[0][0] = -11;
		bPieces.add(new Knight(1,0,true));
		matrix[1][0] = -3;
		bPieces.add(new Bishop(2,0,true));
		matrix[2][0] = -4;
		bPieces.add(new Queen(3,0,true));
		matrix[3][0] = -5;
		bPieces.add(new King(4,0,true));
		matrix[4][0] = -10;
		bPieces.add(new Bishop(5,0,true));
		matrix[5][0] = -4;
		bPieces.add(new Knight(6,0,true));
		matrix[6][0] = -3;
		bPieces.add(new Rook(7,0,true));
		matrix[7][0] = -11;
		
		//init white pieces
		for(int i=0;i<8;i++){
			wPieces.add(new Pawn(i , 6 , false));
			matrix[i][6] = 9;
		}
		wPieces.add(new Rook(0,7,false));
		matrix[0][7] = 11;
		wPieces.add(new Knight(1,7,false));
		matrix[1][7] = 3;
		wPieces.add(new Bishop(2,7,false));
		matrix[2][7] = 4;
		wPieces.add(new Queen(3,7,false));
		matrix[3][7] = 5;
		wPieces.add(new King(4,7,false));
		matrix[4][7] = 10;
		wPieces.add(new Bishop(5,7,false));
		matrix[5][7] = 4;
		wPieces.add(new Knight(6,7,false));
		matrix[6][7] = 3;
		wPieces.add(new Rook(7,7,false));
		matrix[7][7] = 11;
		
		currentPieces = wPieces;//White first
		white_turn = true;
	}
	
	/**
	 * Get the list of the black pieces
	 * @return The list of the black pieces
	 */
	public List<Piece> getBlackPieces(){
		return bPieces;
	}
	
	/**
	 * Get the list of the white pieces
	 * @return The list of the white pieces
	 */
	public List<Piece> getWhitePiece(){
		return wPieces;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//If no piece is being dragged, find one
		if(!dragging){
			draggingPiece = null;
			
			for(Piece piece : currentPieces){
				if(e.getX() >= piece.getX() && e.getX() <= piece.getX()+80 && e.getY() >= piece.getY() && e.getY() <= piece.getY()+80){
					draggingPiece = piece;
					break;
				}
			}
		}
		//If there's one piece being dragged
		if(draggingPiece != null){
			//The piece follow with the mouse
			draggingPiece.setX(e.getX() - 40);
			draggingPiece.setY(e.getY() - 40);
			//Set dragging as true, refresh the panel
			dragging = true;
			panel.refresh();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//If there's a piece that being dragged, and player now release the mouse
		if(dragging){
			
			//find the closetest block to place in
			int cx = (int)Math.ceil(e.getX() / 80);
			int cy = (int)Math.ceil((e.getY()-39) / 80);
			//Original axises for the piece
			int oIndexX = draggingPiece.getIndexX();
			int oIndexY = draggingPiece.getIndexY();
			
			//Make sure player's mouse is on the valid location
			if(cx >= 0 && cx <=7 && cy >=0 && cy <= 7){
				draggingPiece.setIndeX(cx);
				draggingPiece.setIndexY(cy);
				
				//If player is not staying at the same box
				if(!(oIndexX == cx && oIndexY == cy)){
					//Draw a line represents the move
					panel.drawArrow(oIndexX, oIndexY, cx, cy);
					
					//If original place is a black piece, move to place is a white piece, eat it
					if(matrix[oIndexX][oIndexY] < 0 && matrix[cx][cy] > 0){
						//Iterator to find that piece that will be ate by opponent
						for(int i=0;i<wPieces.size();i++){
							if(wPieces.get(i).getIndexX() == cx && wPieces.get(i).getIndexY() == cy){
								matrix[wPieces.get(i).getIndexX()][wPieces.get(i).getIndexY()] = 0;
								wPieces.remove(i);
								break;
							}
						}
					}
					//If original place is a white piece, move to place is a black piece, eat it
					else if(matrix[oIndexX][oIndexY] > 0 && matrix[cx][cy] < 0){
						for(int i=0;i<bPieces.size();i++){
							//Iterator to find that piece that will be ate by opponent
							if(bPieces.get(i).getIndexX() == cx && bPieces.get(i).getIndexY() == cy){
								matrix[bPieces.get(i).getIndexX()][bPieces.get(i).getIndexY()] = 0;
								bPieces.remove(i);
								break;
							}
						}
					}
					
					//Update the matrix
					int oldVar = matrix[oIndexX][oIndexY];
					matrix[cx][cy] = oldVar;
					matrix[oIndexX][oIndexY] = 0;
					
					//Switch between black and white
					if(white_turn){
						currentPieces = bPieces;
						white_turn = false;
					}
					else{
						currentPieces = wPieces;
						white_turn = true;
					}
				}
			}
			//If it is out of range, put it back to original position, does not count as one move
			else{
				draggingPiece.setIndeX(oIndexX);
				draggingPiece.setIndexY(oIndexY);
			}
			
			//Refresh the jpanel
			panel.refresh();
			//Ready for next dragging piece
			dragging = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
}
