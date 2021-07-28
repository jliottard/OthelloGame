import java.util.*;


/**
 *	PlayerKeyboard can be used for playing through the terminal
 */
public class PlayerKeyboard implements InterfacePlayer{
	// Default value for initialization
	public final static Position COORDINATES_DEFAULT = new Position(0,0);
	
	/**
	 * represents  coordinates
	 */
	Position wishedCellPosition;
	private CellContent colour;
	
	
	public PlayerKeyboard(CellContent color) {
		this.wishedCellPosition = COORDINATES_DEFAULT;
		this.colour = color;
	}
	
	public Position getPosition() {
		Scanner keyboardX = new Scanner(System.in); 
		System.out.println("Player "+this.getColor()+",enter the X position of your move: ");
		int X = keyboardX.nextInt();
		System.out.println("Player "+this.getColor()+",enter the Y position of your move: ");
		Scanner keyboardY = new Scanner(System.in);
		int Y = keyboardY.nextInt();
		return new Position(X,Y);
	}
		
	public CellContent getColor() {
		return this.colour;
	}
	
	public void setPosition(Position p) {
		this.wishedCellPosition = p;
	}
	
	
}
