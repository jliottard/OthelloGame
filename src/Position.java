// TODO rename (naming conventions + purpose)
public class Position {

	/*
	 * X coordinate <- x ->
	 */
	private int xCoordinate;
	/*               ^
	 * 				 |
	 * Y coordinate  y
	 * 				 |
	 *               v
	 */
	private int yCoordinate;
	
	//TODO javadoc
	//TODO rename parameters (naming conventions)
	public Position(int theRow, int theColumn)
	{
		xCoordinate = theRow;
		yCoordinate = theColumn;
	}
	
	
	public Position(float x, float y)
	{
		xCoordinate = 0;
		this.yCoordinate = 0;
	}

	
	public Position()
	{
		xCoordinate = 0;
		this.yCoordinate = 0;
	}

	//TODO javadoc
	public int getX() {
		
		return this.xCoordinate;
	}
	
	//TODO javadoc
	public int getY() {
		return this.yCoordinate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + xCoordinate;
		result = prime * result + yCoordinate;
		return result;
		
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (this.xCoordinate != other.xCoordinate)
			return false;
		if (this.yCoordinate != other.yCoordinate)
			return false;
		return true;
	}
	public Position getPositionTranslated(Direction theDirection)
	{
		return new Position(this.getX() + theDirection.getDeltaRow(), this.getY() + theDirection.getDeltaColumn());
	}
	
	public String toString()
	{
		return "["+this.getX()+";"+this.getY()+"]";
	}
	
}
