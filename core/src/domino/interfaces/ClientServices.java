package domino.interfaces;
import java.util.ArrayList;



public interface ClientServices {
	
	public void sendWinnerMessage();
	public void sendLostMessage();

	
	public void sendPieces(ArrayList<String> playerPieces, ArrayList<String> playableExtremity);
	public String getPlayerMove();
	
	public void printMessage(String msg);
	
}
