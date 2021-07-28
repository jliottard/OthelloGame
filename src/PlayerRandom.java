import java.util.Random;
import java.util.*;


/**
 * PlayerRandom will choose randomly its moves
 * Can be used for a dumb IA to play against
 */
public class PlayerRandom implements InterfacePlayer{
	// Default value for initialization
	public final static Position DEFAULT_COORDINATES = new Position(0,0);
	
	/**
	 * 
	 * represents  coordinates
	 */
	Position wishedCellPosition;
	private CellContent colour;
	
	
	public PlayerRandom(CellContent color) {
		this.wishedCellPosition = DEFAULT_COORDINATES;
		this.colour = color;
	}
	
	public Position getPosition() {
			int max = 10;
			int min = 0;
			int rand1 = (int)(Math.random() * (max -(-min))) + (-min);
			int rand2 = (int)(Math.random() * (max -(-min))) + (-min);
			return new Position(rand1,rand2);
	}
	
	public CellContent getColor()
	{
		return this.colour;
	}
	
	public void setPosition(Position p) {
		this.wishedCellPosition = p; 
	}
	
	
}
