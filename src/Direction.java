
public enum Direction {
	UP(-1,0),
	DOWN(1,0),
	LEFT(0,-1),
	RIGHT(0,1),
	UP_LEFT(-1,-1),
	UP_RIGHT(-1,1),
	DOWN_LEFT(1,-1),
	DOWN_RIGHT(1,1);
	
	/*
	 *	The changes of coordinates
	 */
	private int deltaRow;
	private int deltaColumn;
	
	/*
	 * Represents the 8 potential directions from a grid's cell
	 */
	private Direction(int row, int column) {
		this.deltaRow = row;
		this.deltaColumn = column;
	}
	
	public int getDeltaRow()
	{
		return this.deltaRow;
	}
	public int getDeltaColumn()
	{
		return this.deltaColumn;
	}
}
