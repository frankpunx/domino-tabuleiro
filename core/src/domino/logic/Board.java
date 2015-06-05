package domino.logic;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import domino.assetsLoader.AssetsLoader;


public class Board implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4767322503220966804L;
	/**
	 * Note that the playable pieces are the first piece and the last one.
	 */
	private ArrayList<Piece> allPiecesOnTable;
	private ArrayList<Piece> availablePieces;
	private ArrayList<Piece> playerPieces1;
	private ArrayList<Piece> playerPieces2;
	private Piece root;
	private PieceHorientation rootHorientation;
	private PieceHorientation leftSidePieceHorientation;
	private PieceHorientation rightSidePieceHorientation;
	private int X_POS_LEFT = AssetsLoader.X_INIT_POSITION;
	private int Y_POS_LEFT = AssetsLoader.Y_INIT_POSITION;
	private int X_POS_RIGHT = AssetsLoader.X_INIT_POSITION;
	private int Y_POS_RIGHT = AssetsLoader.Y_INIT_POSITION;
	
	public enum PieceHorientation {
		VERTICAL_ROOT, HORIZONTAL_ROOT, VERTICAL_UP, VERTICAL_DOWN, HORIZONTAL_LEFT, HORIZONTAL_RIGHT, NONE
	}

	public Board() {

		rootHorientation = PieceHorientation.NONE;
		leftSidePieceHorientation = PieceHorientation.NONE;
		rightSidePieceHorientation = PieceHorientation.NONE;
		allPiecesOnTable = new ArrayList<Piece>();
		availablePieces = new ArrayList<Piece>();
		playerPieces1 = new ArrayList<Piece>();
		playerPieces2 = new ArrayList<Piece>();

		// Creates all pieces available piece. NOTE: first <= second 
		for (int i = 0; i <= 6; i++) {
			for (int j = 0; j <= i; j++) {

				Piece temp = new Piece(j, i, Rotation.NORTH);
				availablePieces.add(temp);
			}
		}

		// Shuffle the available pieces 
		Collections.shuffle(availablePieces);

		// Add pieces to player
		for (int i = 0; i < 7; i++) {
			playerPieces1.add(availablePieces.remove(0));
		}

		for (int i = 0; i < 7; i++) {
			playerPieces2.add(availablePieces.remove(0));
		}
		
		root = availablePieces.remove(0);
		addPieceToExtremity(root, false);
		
		if(root.isDoubleValues()) {
			rootHorientation = PieceHorientation.VERTICAL_ROOT;
		}
		else {
			root.setRotation(Rotation.WEST);
			rootHorientation = PieceHorientation.HORIZONTAL_ROOT;
		}
	}

	/**
	 * Obter os valores que estao nas extremidades
	 * @return ArrayLista com os valores nas extremidades (extremidade esquerda -> indice: 0; extremidade direita -> indice: 1)
	 */
	public final ArrayList<String> getPossiblePlayableValues() {

		String extremity1, extremity2;
		
		if(allPiecesOnTable.size() == 0)
			return null;

		ArrayList<String> playableValues = new ArrayList<String>();

		// No caso de ser a primeira peca
		if(allPiecesOnTable.size() == 1) {
			extremity1 = allPiecesOnTable.get(0).getFirstExtremity().getValue() + "";
			extremity2 = allPiecesOnTable.get(0).getSecondExtremity().getValue() + "";
			playableValues.add(extremity1);
			playableValues.add(extremity2);

		} else {

			// Extremidade esquerda
			String leftValue = "-1";

			if(allPiecesOnTable.get(0).getFirstExtremity().isLinkStatus() && !allPiecesOnTable.get(0).getSecondExtremity().isLinkStatus()) {

				leftValue = allPiecesOnTable.get(0).getSecondExtremity().getValue() + "";

			} else if(!allPiecesOnTable.get(0).getFirstExtremity().isLinkStatus() && allPiecesOnTable.get(0).getSecondExtremity().isLinkStatus()) {

				leftValue = allPiecesOnTable.get(0).getFirstExtremity().getValue() + "";

			}

			// Extremidade direita
			String rightValue = "-1";

			if(allPiecesOnTable.get(allPiecesOnTable.size() - 1).getFirstExtremity().isLinkStatus() && !allPiecesOnTable.get(allPiecesOnTable.size() - 1).getSecondExtremity().isLinkStatus()) {

				rightValue = allPiecesOnTable.get(allPiecesOnTable.size() - 1).getSecondExtremity().getValue() + "";

			} else if(!allPiecesOnTable.get(allPiecesOnTable.size() - 1).getFirstExtremity().isLinkStatus() && allPiecesOnTable.get(allPiecesOnTable.size() - 1).getSecondExtremity().isLinkStatus()) {

				rightValue = allPiecesOnTable.get(allPiecesOnTable.size() - 1).getFirstExtremity().getValue() + "";

			}


			playableValues.add(leftValue);
			playableValues.add(rightValue);
		}

		return playableValues;
	}

	public final boolean addPieceToExtremity(Piece p, boolean isLeft) {

		// No caso de um tabuleiro vazio
		if(allPiecesOnTable.size() == 0) {
			allPiecesOnTable.add(p);
			return true;
		}

		// No caso de um tabuleiro com uma peca apenas
		// Convencao: first -> lado esquerdo
		// 			  second -> lado direito

		else if(allPiecesOnTable.size() == 1) {

			Piece pieceToAnalise = allPiecesOnTable.get(0);

			if(isLeft) {
				if(pieceToAnalise.getFirstExtremity().getValue() == p.getFirstExtremity().getValue()) {
					System.err.println("Caso 1");
					p.setExtremityLinkedStatus(true, false);
					//p.setRotation(Rotation.getOpositeRotation(p.getRotation()));
					pieceToAnalise.getFirstExtremity().setLinkStatus(true);

				} else if(pieceToAnalise.getFirstExtremity().getValue() == p.getSecondExtremity().getValue()) {
					System.err.println("Caso 2");
					p.setExtremityLinkedStatus(false, true);
					pieceToAnalise.getFirstExtremity().setLinkStatus(true);

				} else 
					return false;

				allPiecesOnTable.add(0, p);

			} else {

				if(pieceToAnalise.getSecondExtremity().getValue() == p.getFirstExtremity().getValue()) {
					System.err.println("Caso 3");

					p.setExtremityLinkedStatus(true, false);
					pieceToAnalise.getSecondExtremity().setLinkStatus(true);

				} else if(pieceToAnalise.getSecondExtremity().getValue() == p.getSecondExtremity().getValue()) {
					System.err.println("Caso 4");

					p.setExtremityLinkedStatus(false, true);
					//p.setRotation(Rotation.getOpositeRotation(p.getRotation()));
					pieceToAnalise.getSecondExtremity().setLinkStatus(true);

				} else 
					return false;

				allPiecesOnTable.add(p);

			}

			return true;

		} else {

			// ---------------------------------------------------------------
			// No caso de um tabuleiro ja com pecas colocadas


			Piece pieceToAnalise = allPiecesOnTable.get(isLeft? 0 : allPiecesOnTable.size() - 1);

			if(! pieceToAnalise.getFirstExtremity().isLinkStatus()) {

				if(pieceToAnalise.getFirstExtremity().getValue() == p.getFirstExtremity().getValue()) {
					System.err.println("Caso 5");

					p.setExtremityLinkedStatus(true, false);
					//if(isLeft)
						//p.setRotation(Rotation.getOpositeRotation(p.getRotation()));
					pieceToAnalise.getFirstExtremity().setLinkStatus(true);

				} else if(pieceToAnalise.getFirstExtremity().getValue() == p.getSecondExtremity().getValue()) {
					System.err.println("Caso 6");

					p.setExtremityLinkedStatus(false, true);
					//if(! isLeft)
						//p.setRotation(Rotation.getOpositeRotation(p.getRotation()));
					pieceToAnalise.getFirstExtremity().setLinkStatus(true);

				} else 
					return false;

			} else if(! pieceToAnalise.getSecondExtremity().isLinkStatus()) {

				if(pieceToAnalise.getSecondExtremity().getValue() == p.getFirstExtremity().getValue()) {
					System.err.println("Caso 7");

					p.setExtremityLinkedStatus(true, false);
					//if(isLeft)
						//p.setRotation(Rotation.getOpositeRotation(p.getRotation()));

					pieceToAnalise.getSecondExtremity().setLinkStatus(true);

				} else if(pieceToAnalise.getSecondExtremity().getValue() == p.getSecondExtremity().getValue()) {
					System.err.println("Caso 8");

					p.setExtremityLinkedStatus(false, true);
					//if(! isLeft)
						//p.setRotation(Rotation.getOpositeRotation(p.getRotation()));

					pieceToAnalise.getSecondExtremity().setLinkStatus(true);

				} else 
					return false;

			} else 
				return false;

			// ------------------------------------------------------------
			// Adicionar peca ao tabuleiro
			if(isLeft)
				allPiecesOnTable.add(0, p);
			else 
				allPiecesOnTable.add(p);

			return true;

		}
	}


	// Peca esquerda -> get(0)
	// Peca direita  -> get(n - 1)
	public final ArrayList<Piece> getBoardExtremities() {
		ArrayList<Piece> l = new ArrayList<Piece>();

		l.add(allPiecesOnTable.get(0));
		l.add(allPiecesOnTable.get(allPiecesOnTable.size() - 1));

		return l;
	}





	public ArrayList<Piece> getAllPiecesOnTable() {
		return allPiecesOnTable;
	}
	
	public ArrayList<Piece> getAvailablePieces() {
		return availablePieces;
	}
	
	public ArrayList<Piece> getPlayerPieces1() {
		return playerPieces1;
	}
	
	public ArrayList<Piece> getPlayerPieces2() {
		return playerPieces2;
	}
	
	public Piece getRoot() {
		return root;
	}
	
	public PieceHorientation getRootHorientation() {
		return rootHorientation;
	}

	public PieceHorientation getLeftSidePieceHorientation() {
		return leftSidePieceHorientation;
	}
	
	public PieceHorientation getRightSidePieceHorientation() {
		return rightSidePieceHorientation;
	}
	
	public int getXPosLeft() {
		return X_POS_LEFT;
	}
	
	public int getYPosLeft() {
		return Y_POS_LEFT;
	}
	
	public int getXPosRight() {
		return X_POS_RIGHT;
	}
	
	public int getYPosRight() {
		return Y_POS_RIGHT;
	}
	
	public void setLeftSidePieceHorientation(PieceHorientation pieceOrientation) {
		this.leftSidePieceHorientation = pieceOrientation;
	}
	
	public void setRightSidePieceHorientation(PieceHorientation pieceOrientation) {
		this.rightSidePieceHorientation = pieceOrientation;
	}
	
	public void setRoot(Piece root) {
		this.root = root;
	}
	
	public void setXPosLeft(int val) {
		X_POS_LEFT = val;
	}
	
	public void setYPosLeft(int val) {
		Y_POS_LEFT = val;
	}
	
	public void setXPosRight(int val) {
		X_POS_RIGHT = val;
	}
	
	public void setYPosRight(int val) {
		Y_POS_RIGHT = val;
	}
	
	
	public String toString() {

		StringBuilder f = new StringBuilder();

		//f.append("All pieces on table: ");

		for (Piece piece : allPiecesOnTable) {
			f.append(piece + " ");
		}

		return f.toString();
	}
}
