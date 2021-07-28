
/**
 * CellContent class allows us to choose the color for disk/cell
 * 
 *
 */

public enum CellContent
{
	EMPTY(" "),
	BLACK("O"),
	WHITE("X");
	
	// Value of the cell
	private String symbol;
	
	// Represents the cell's content
	private CellContent(String theSymbol)
	{
		this.symbol = theSymbol;
	}

	// Only for console display use
	public String toString()
	{
		return this.symbol;
	}
	
	public String getName() {
		switch(this.symbol) {
		case " " : return "Empty";
		case "O" : return "Black";
		case "X" : return "White";
		}
		return null;
	}
	
} 

