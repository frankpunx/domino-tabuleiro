package domino.interfaces;


public interface ServerServices {

	/** 
	 * Adicionar um jogador ao servidor e, de seguida, sao-lhe entreges 7 pecas
	 * Para efectuar callback 
	 */
	public boolean addPlayer(ClientServices pl);
	
	
	/** Pedido para adicionar uma peca ao tabuleiro 
	 */
	public boolean addPieceToLeft(int p);
	public boolean addPieceToRight(int p);
	
	/**
	 * Buscar peca ao monte
	 * 
	 */
	public boolean getPieceFromGraveyard();

}