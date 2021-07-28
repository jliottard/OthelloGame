import java.util.Scanner;


/**
 *	PlayerUX for interacting with the Window
 */
public class PlayerUX implements InterfacePlayer{
	
	public final static Position COORDINATES_DEFAULT = new Position(0,0);
	
	/**
	 * represents  coordinates
	 */
	private Position wishedCellPosition;
	private CellContent colour;
	
	
	public PlayerUX(CellContent color) {
		this.wishedCellPosition = COORDINATES_DEFAULT;
		this.colour = color;
	}
	
	public Position getPosition() {
		return wishedCellPosition;
	}
	
	public CellContent getColor() {
		return this.colour;
	}
	
	public void setPosition(Position p) {
		this.wishedCellPosition = p; 
	}
	
}
