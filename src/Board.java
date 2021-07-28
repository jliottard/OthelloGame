import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
	
	/**
	 * Representation of the cases in the table, the index starts at 0 and end at BOARD_WIDTH - 1 (7)
	 *  for example : [1][5] is for the case in the 2nd rows and 6th columns 
	 */
	private CellContent[][] board;
	
	
	/**
	 * MAX_WIDTH represents the maximal width size of the board 
	 */
	private final static int BOARD_WIDTH = 8;
	
	/**
	 * MAX_HEIGHT represents the maximal height size of the board
	 */
	private final static int BOARD_HEIGHT = 8;

	
	
	/**
	 * We are defining our borders in our Othello Game Board.
	 */
	public Board()
	{
		this.board = new CellContent[BOARD_WIDTH][BOARD_HEIGHT];
		
		for (int rowIndex=0; rowIndex<BOARD_WIDTH; rowIndex++)
		{
			for (int columnIndex=0; columnIndex<BOARD_HEIGHT; columnIndex++)
			{
				this.board[rowIndex][columnIndex] = CellContent.EMPTY;
			}
		}
		// Placing the 4 middle coloured cells
		this.board[BOARD_WIDTH/2-1][BOARD_HEIGHT/2-1] = CellContent.WHITE;
		this.board[BOARD_WIDTH/2][BOARD_HEIGHT/2] = CellContent.WHITE;
		this.board[BOARD_WIDTH/2-1][BOARD_HEIGHT/2] = CellContent.BLACK;
		this.board[BOARD_WIDTH/2][BOARD_HEIGHT/2-1] = CellContent.BLACK;
	}
	
	/**
	 * @return the board height
	 */
	public int getBoardHeight(){
		return BOARD_HEIGHT;
	}
	
	/**
	 * @return the board width
	 */
	public int getBoardWidth(){
		return BOARD_WIDTH;
	}
	
	/**
	 * @param position
	 * @return the contents of the position
	 */
	public CellContent getCellContent(Position position) {
		return this.board[position.getX()][position.getY()];
	}
	
	/**
	 * @param position
	 * @return bool
	 */
	public boolean isPositionOnBoard(Position position)
	{
		if (position.getX() >= 0 && position.getX() < BOARD_HEIGHT && position.getY() >= 0 && position.getY() < BOARD_WIDTH){
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * @param pos
	 * @return bool
	 */
	public boolean isPositionAlreadyTaken(Position pos)
	{
		return !(this.board[pos.getX()][pos.getY()] == CellContent.EMPTY);
		
	}
	

	/** return the number of black, white or empty cells in the board
	 * @param color
	 * @return a player colour
	 */
	public int getNumberOf(CellContent color)
	{
		int count = 0;
		for (int rowIndex=0; rowIndex<BOARD_WIDTH; rowIndex++)
		{
			for (int columnIndex=0; columnIndex<BOARD_HEIGHT; columnIndex++)
			{
				if( this.board[rowIndex][columnIndex] == color )
					count++;
			}
		}
		return count;
	}
	
	/**
	 * @param content : player color
	 * @return the posibility of the positions knowing the player color, with the potential future caught pawns 
	 */
	public Map<Position, Set<Position>> getValidMoves(CellContent content)
	{
		Map<Position, Set<Position>> result = new HashMap<Position, Set<Position>>();
		for (int rowIndex=0;rowIndex < BOARD_HEIGHT; rowIndex++)
		{
			for (int columnIndex=0;columnIndex < BOARD_WIDTH; columnIndex++)
			{
				Position actualPositionTested = new Position(rowIndex, columnIndex);
				if (this.isPositionAlreadyTaken(actualPositionTested))
					continue;
				Set<Position> caughtPawnPositions = this.getCaughtPawnPositionsAt(actualPositionTested, content);
				if (caughtPawnPositions.isEmpty())
					continue;
				result.put(actualPositionTested, caughtPawnPositions);
			}
		}
		return result;	
	}

	/**
	 * @param position : starting point where the research of all caught positions begins
	 * @param content : color of the pawn if it would be placed at the position 
	 * @return Set (HashSet) : can be empty,  return a HashSet of all the position which would change if a pawn were
	 * placed at the position (so there are only the opponent positions of pawns)   
	 */
	private Set<Position> getCaughtPawnPositionsAt(Position position, CellContent content) {
		Set<Position> result = new HashSet<Position>();
		for (Direction direction: Direction.values())
		{
			result.addAll(this.getCaughtPawnPositionsFromInADirection(position, content, direction));
		}
		return result;
	}

	/**
	 * @param thePosition
	 * @param theColor
	 * @param theDirection
	 * @return Set of positions where cellcontent (in a given direction) will be changed for the opposite
	 * color if a pawn is placed at thePosition
	 */
	public Set<Position> getCaughtPawnPositionsFromInADirection(Position thePosition, CellContent theColor, Direction theDirection)
	{
		Set<Position> result = new HashSet<Position>();
		int countofCaughtPawns = 0;
		Position currentPosition = thePosition;
		// We're looking for another pawn with the same color of theColor
		while (true) {
			Position neighbour = currentPosition.getPositionTranslated(theDirection);
			if (!this.isPositionOnBoard(neighbour)) {
				countofCaughtPawns = 0;
				break;
			}
			if (this.getCellContent(neighbour) == CellContent.EMPTY) {
				countofCaughtPawns = 0;
				break;
			}
			if (this.getCellContent(neighbour) == theColor && countofCaughtPawns == 0)
			{	//There is no opposite colored pawn here
				countofCaughtPawns = 0;
				break;
			}
			if (this.getCellContent(neighbour) == theColor && countofCaughtPawns > 0)
			{	//Here We've reached the wanted color pawn and got some caught pawns!
				break;
			}
			countofCaughtPawns++;
			result.add(neighbour);
			currentPosition = neighbour;
		}
		if( countofCaughtPawns == 0)
			return new HashSet<Position>();
		return result;
	}
	

	/**
	 * @param position : checked and wanted by the player
	 * @param color : of who is playing this move
	 * @param set : of the positions that will be changed (color switched)
	 */
	public void updateBoard(Position position, CellContent color, Set<Position> set)
	{
		if(!set.isEmpty())
		{
			for (Position caughtPosition: set)
			{
				this.placePawnAt(caughtPosition, color);
			}
			this.placePawnAt(position, color);
		}
	}

	/**
	 * @param position : the location of the pawn
	 * @param color : final color after the change
	 */
	private void placePawnAt(Position position, CellContent color) {
		this.board[position.getX()][position.getY()] = color;
	}
	
	/**
	 * Return a map of all pawns (with their color) on the board
	 */
	public Map<Position,CellContent> getBoardPawn(){
		Map<Position,CellContent> map = new HashMap<Position,CellContent>();
		for(int x=0;x<this.getBoardWidth();x++)
		{
			for(int y=0; y<this.getBoardHeight(); y++)
			{
				Position pos = new Position(x,y);
				if(this.isPositionAlreadyTaken(pos))
					map.put(pos, this.getCellContent(pos));
			}
		}
		return map;
	}
}
