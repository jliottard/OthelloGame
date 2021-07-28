/**
 * Creation of Othello Game Launcher 
 *  
 *
 *
 */
public class OthelloGameLauncher {

	/**
	 * Creation of a main function
	 * @param args
	 */
	public static void main(String[] args) {
		
		PlayerRandom playerR1 = new PlayerRandom(CellContent.BLACK);
		PlayerRandom playerR2 = new PlayerRandom(CellContent.WHITE);
		PlayerKeyboard playerK1 = new PlayerKeyboard(CellContent.BLACK);
		PlayerKeyboard playerK2 = new PlayerKeyboard(CellContent.WHITE);
		
		PlayerUX playerW1 = new PlayerUX(CellContent.BLACK);
		PlayerUX playerW2 = new PlayerUX(CellContent.WHITE);
		
		Window window = new Window(playerW1,playerW2);
		
		new OthelloGame(playerW1, playerW2, window).play();
		
	}

}
