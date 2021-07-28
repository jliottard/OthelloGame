import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.*;
import java.awt.*;

/**
 *  Video used as a reference for implementing this Windows class : https://www.youtube.com/watch?v=EMu1cC2Vnis
 *
 */
public class Window extends JFrame implements InterfaceUX {
	
	private final static int WIDTH = 600; // of the window
	private final static int HEIGHT = 600; // of the window
	
	private final static int DEFAULT_BOARD_WIDTH = 8;
	private final static int DFAULT_BOARD_HEIGHT = 8;

	public static int boardHeight=DFAULT_BOARD_HEIGHT;
	public static int boardWidth=DEFAULT_BOARD_WIDTH;
	
	public static Map<Position, CellContent> boardContents = null;
	
	public static int xMouse = 0;
	public static int yMouse = 0;
	public static int xCursorBoardPosition;
	public static int yCursorBoardPosition;
	
	private final static int X_VISUAL_CORRECTION = 8;
	private final static int Y_VISUAL_CORRECTION = 31;
	
	public static InterfacePlayer currentPlayerUX=null;
	
	public static InterfacePlayer player1 = null;
	public static InterfacePlayer player2 = null;
	
	private static Position wishedPosition = null;
	
	public int player1Score=2;
	public int player2Score=2;
	
	/**
	 * Display a visual window of the board
	 */
	public Window(InterfacePlayer p1, InterfacePlayer p2)
	{
		boardContents = new HashMap<Position, CellContent>();
		player1 = p1;
		player2 = p2;
		
		this.setTitle("Othello Game");
		// parameters
		this.setSize(WIDTH,HEIGHT);
		this.setVisible(true);
		this.setResizable(true);
		
		//close the app when the window is closed
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// location of the top-left corner of the window 
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x_location = (int) screenSize.getHeight();
		int y_location = (int) screenSize.getWidth();
		this.setLocation(0,0);
		
		//layout manager
		this.setLayout(null);		
		Grid grid = new Grid();
		this.setContentPane(grid);
		
		//Event management
		Move move = new Move();
		this.addMouseMotionListener(move);
		Click click = new Click();
		this.addMouseListener(click);
	}
	
	private class Grid extends JPanel{
		public void paintComponent(Graphics g){
			int windowWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
			int windowHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			
			int xTopLeftCornerBoard = 100;
			int yTopLeftCornerBoard = 100;
			int cellSize = 50;
			
			// Set the background
			g.setColor(Color.WHITE);
			g.fillRect(0,0,windowWidth,windowHeight);
			// Set the board
			g.setColor(Color.getHSBColor((float)110/360, (float)0.50, (float)0.50));
			g.fillRect(xTopLeftCornerBoard,yTopLeftCornerBoard,cellSize*boardHeight,cellSize*boardHeight);
			// Draw the borders
			g.setColor(Color.BLACK);
			g.drawLine(xTopLeftCornerBoard, yTopLeftCornerBoard, xTopLeftCornerBoard+cellSize*boardWidth, yTopLeftCornerBoard); // top border
			g.drawLine(xTopLeftCornerBoard+cellSize*boardWidth, yTopLeftCornerBoard, xTopLeftCornerBoard+cellSize*boardWidth, yTopLeftCornerBoard+cellSize*boardHeight); // right border
			g.drawLine(xTopLeftCornerBoard, yTopLeftCornerBoard+50*boardHeight, xTopLeftCornerBoard+cellSize*boardWidth, yTopLeftCornerBoard+cellSize*boardHeight); // bottom border
			g.drawLine(xTopLeftCornerBoard, yTopLeftCornerBoard, xTopLeftCornerBoard, yTopLeftCornerBoard+cellSize*boardHeight); // left border
			// Draw the grid
			for(int x=0;x<boardWidth;x++) {
				g.drawLine(xTopLeftCornerBoard+(x*cellSize), yTopLeftCornerBoard, xTopLeftCornerBoard+(x*cellSize), yTopLeftCornerBoard+cellSize*boardHeight);
			}
			for(int y=0;y<boardHeight;y++) {
				g.drawLine(xTopLeftCornerBoard, yTopLeftCornerBoard+(y*cellSize), xTopLeftCornerBoard+cellSize*boardWidth, yTopLeftCornerBoard+(y*cellSize));
			}
			// Set the pawns
			if(boardContents!=null) {
				for(int x=0;x<boardWidth;x++) {
					for(int y=0;y<boardHeight;y++)
					{
						Position pos = new Position(x,y);
						if(boardContents.containsKey(pos)) {
							int xOval = xTopLeftCornerBoard+(cellSize*pos.getX());
							int yOval = yTopLeftCornerBoard+(cellSize*pos.getY());
							if(boardContents.get(pos)==CellContent.BLACK) {
								g.setColor(Color.BLACK);
							}
							else {
								g.setColor(Color.WHITE);
							}
							g.fillOval(xOval,yOval,cellSize,cellSize);
						}
					}
				}
			}
			// Display the scores
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", 0, 20));
			g.drawString(player1.getColor().getName() + " player score : " + player1Score, 10, 80);
			g.drawString(player2.getColor().getName() + " player score : " + player2Score, 350, 80);
			// Display who has to play
			g.drawString("Turn : " + currentPlayerUX.getColor().getName() + " player", 200, 40);
			// Get cursor position on the board
			for(int x=0;x<boardWidth;x++) {
				for(int y=0;y<boardHeight;y++)
				{
					if(xMouse > xTopLeftCornerBoard+X_VISUAL_CORRECTION+(cellSize*x) && xMouse < xTopLeftCornerBoard+X_VISUAL_CORRECTION+(cellSize*(x+1)))
						if(yMouse > yTopLeftCornerBoard+Y_VISUAL_CORRECTION+(cellSize*y) && yMouse < yTopLeftCornerBoard+Y_VISUAL_CORRECTION+cellSize*(y+1))
						{
							xCursorBoardPosition = x;
							yCursorBoardPosition = y;
							break;
						}
				}
			}
			this.updateUI();
		}		
	}
	private class Move implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {}

		@Override
		public void mouseMoved(MouseEvent e) {
			xMouse = e.getX();
			yMouse = e.getY();
		}
		
	}
	public class Click implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			wishedPosition = new Position(xCursorBoardPosition,yCursorBoardPosition);
			currentPlayerUX.setPosition(wishedPosition);
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
	}
	
	@Override
	public void setBoardSize(int height, int width) {
		boardHeight = height;
		boardWidth = width;
	}
	
	/*
	 * Give a Map of positions filled with a coloured pawn
	 */
	@Override
	public void setCellContents(Map<Position, CellContent> board) {
		boardContents=board;
		
	}
	@Override
	public void setCurrentPlayer(CellContent color) {
		if(player1.getColor().getName()==color.getName()) currentPlayerUX = player1;
		if(player2.getColor().getName()==color.getName()) currentPlayerUX = player2;
	}
	@Override
	public void setPlayerScore(CellContent playerColor, int score) {
		if(player1.getColor()==playerColor) player1Score = score;
		if(player2.getColor()==playerColor) player2Score = score;
	}
	@Override
	public void signalEndOfGame(InterfacePlayer winner, int winnerScore) {
		JOptionPane.showMessageDialog(this,winner.getColor().getName()+" player wins ! \n With "+winnerScore+ " pawns");
	}
	
}
