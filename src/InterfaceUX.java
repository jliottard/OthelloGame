import java.util.Map;

public interface InterfaceUX {

	public void setBoardSize(int height, int width);
	public void setCellContents(Map<Position,CellContent> board);
	public void setCurrentPlayer(CellContent color);
	public void setPlayerScore(CellContent playerColor, int score);
	public void signalEndOfGame(InterfacePlayer winner, int winnerScore);
}
