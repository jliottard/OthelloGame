import java.util.*;

/**
 * Represents an Othello game
 * There's site with rules and history of Othello game  : https://en.wikipedia.org/wiki/Reversi
 * 
 */

public class OthelloGame {	
	
	// Choose their own move to play on their turn
	private InterfacePlayer blackPlayer;
	private InterfacePlayer whitePlayer;
	
	// 2D grid filled with cellContents
	private Board realBoard;
	
	// Current total of player's cells
	private int blackPlayerScore;
	private int whitePlayerScore;
	
	
	// The way of displaying the game
	private InterfaceUX ux;
	
	
	/**
	 * 	Create a new Othello game, ready to be played
	 * 
	 *  the board is in its starting configuration :
	 *   - the players are ready to play, black player starts
	 *  
	 * 
	 */
	public OthelloGame(InterfacePlayer blackPlayer, InterfacePlayer whitePlayer, InterfaceUX ux)
	{	
		this.realBoard = new Board();
		this.blackPlayer = blackPlayer;
		this.whitePlayer = whitePlayer;
		updateScores(); //initialization of scores
		this.ux = ux;
	}
	
	/**
	 * Starts a new game.
	 * 
	 */
	public void play()
	{	
		this.ux.setBoardSize(realBoard.getBoardHeight(), realBoard.getBoardWidth());
		InterfacePlayer currentPlayer = this.whitePlayer;
		boolean previousPlayerHasPlayed = true;
		int countOfTurn = 0;
		while (true)
		{
			ux.setCellContents(this.realBoard.getBoardPawn());
			ux.setPlayerScore(this.blackPlayer.getColor(), this.blackPlayerScore);
			ux.setPlayerScore(this.whitePlayer.getColor(), this.whitePlayerScore);
			
			currentPlayer = this.nextPlayer(currentPlayer);
			this.ux.setCurrentPlayer(currentPlayer.getColor());
			Map<Position, Set<Position>> validPositionsMap =  this.realBoard.getValidMoves(currentPlayer.getColor());
			if(!validPositionsMap.isEmpty())
			{
				Position position = null;
				while(true) // ask endlessly a valid position
				{
					position = currentPlayer.getPosition();
					if (validPositionsMap.containsKey(position))
					break;
				}
				// getting a valid position
				this.realBoard.updateBoard(position, currentPlayer.getColor(), validPositionsMap.get(position));
				previousPlayerHasPlayed = true;
			}
			else {
				if (!previousPlayerHasPlayed)
					// both players hasn't played in a row, no move available
					break;
				previousPlayerHasPlayed = false;
			}
			this.updateScores();
			countOfTurn++;
		}
		ux.setCellContents(this.realBoard.getBoardPawn());
		
		if(this.blackPlayerScore < this.whitePlayerScore)
		{
			ux.signalEndOfGame(this.whitePlayer, this.blackPlayerScore);
		}
		else {
			ux.signalEndOfGame(this.blackPlayer, this.blackPlayerScore);
		}
		
	}
			
	private InterfacePlayer nextPlayer(InterfacePlayer currentPlayer) {
		if (currentPlayer == this.blackPlayer)
			return this.whitePlayer;
		return this.blackPlayer;
	}
	
	private void updateScores() {
		this.blackPlayerScore = this.realBoard.getNumberOf(CellContent.BLACK);
		this.whitePlayerScore = this.realBoard.getNumberOf(CellContent.WHITE);
	}
	
	
	
	/**
	 * Display in String the state of the board
	 */
	public String toString()
	{
		String result = "  ";
		for (int legendIndex = 0; legendIndex < this.realBoard.getBoardWidth() ; legendIndex++ )
			result = result + " " + legendIndex;
		result = result + "\n";
		for (int columnIndex = 0; columnIndex < this.realBoard.getBoardHeight() ; columnIndex++ )
		{
			result = result + "  ";
			for (int boardrow = 0; boardrow < this.realBoard.getBoardWidth()*2+1; boardrow++ )
			{	
				
				result = result + "-";
			}
			result = result + "\n";
			result = result + columnIndex;
			result = result + " |";
			for (int rowIndex = 0; rowIndex < this.realBoard.getBoardWidth(); rowIndex++)
			{
				switch(this.realBoard.getCellContent(new Position(rowIndex,columnIndex)))
				{
					case EMPTY :
						result = result + this.realBoard.getCellContent(new Position(rowIndex,columnIndex)).toString();
						break;
					case BLACK :
						result = result + this.realBoard.getCellContent(new Position(rowIndex,columnIndex)).toString();
						break;
					case WHITE :
						result = result + this.realBoard.getCellContent(new Position(rowIndex,columnIndex)).toString();
						break;
				}
				result = result + "|";
			}
			result = result + "\n";
		}
		result = result + "  ";
		for (int boardrow = 0; boardrow < this.realBoard.getBoardWidth()*2+1; boardrow++ ) {
			
			result = result + "-";
		}
		result = result + "\n";
		return result;
	}

	
	
}
