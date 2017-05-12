package chess.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import chess.Game;
import chess.pieces.Piece;

public class GamePanel extends JPanel{
	
	private static final long serialVersionUID = 1L;

	/**The Window**/
	private Window window;
	
	/**The chessboard background**/
	private Image board;
	
	/**The Game object**/
	private Game game;
	
	/**Variables that necessary to draw the arrow**/
	private int ox , oy , x , y;
	
	/**If the arrow will be shown or not, change it to false and the arrow will not show up**/
	private boolean showArrow = true;
	
	public GamePanel(Game game){
		window = new Window();
		this.game = game;
		
		//Initialize this panel
		init();
		//Initialize the topbar panel
		initTopbarPanel();
		
		//Add this gamepanel to the window
		window.addPanel(this);
		window.show();
	}
	
	//Init this game panel
	private void init(){
		this.setLayout(new BorderLayout());
		
		this.addMouseMotionListener(game);
		this.addMouseListener(game);
		
		board = Toolkit.getDefaultToolkit().getImage("Resource/chessboard.png"); //The chessboard background
	}
	
	//The topbar of the game panel, contains two buttons, restart and exit.
	private void initTopbarPanel(){
		JPanel top = new JPanel(); //Topbar panel
		top.setBackground(Color.BLACK); //Set topbar background color as black
		
		JButton restart = new JButton("Restart");
		restart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				game.start();
			}
		});
		
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0); //Terminate the program
			}
		});
		
		top.add(restart);
		top.add(exit);
		
		//Add the topbar panel to the gamepanel
		this.add(top , BorderLayout.NORTH);
	}
	
	/**
	 * Refresh the JPanel, the new change will only appear after the JPanel was refreshed.
	 */
	public void refresh(){
		this.repaint();
	}
	
	/**
	 * Draw an arrow, indicate the move, from where to where. This feature can be turned off 
	 * by changing the variable drawArrow to false.
	 * @param ox - The original X
	 * @param oy - The original Y
	 * @param x - The new X
	 * @param y - The new Y
	 */
	public void drawArrow(int ox , int oy , int x , int y){
		this.ox = ox*80+40;
		this.oy = oy*80+39+40;
		this.x = x*80+40;
		this.y = y*80+39+40;
	}
	
	/**
	 * Override the paintComponent method in the JPanel, painting everthing here. 
	 */
	@Override
	public void paintComponent(Graphics g){
		g.drawImage(board, 0, 39, this);
		for(Piece piece : game.getBlackPieces()){
			piece.draw(g);
		}
		for(Piece piece : game.getWhitePiece()){
			piece.draw(g);
		}
		
		if(showArrow){
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.BLACK);
			Stroke old = g2d.getStroke();
			g2d.setStroke(new BasicStroke(3f));
			g.drawLine(ox, oy, x, y);
			g2d.setStroke(old);
		}
	}
	
}
