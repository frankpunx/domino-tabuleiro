package domino.logic;

import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import domino.assetsLoader.AssetsLoader;
import domino.interfaces.DominoServer;
import domino.logic.Board.PieceHorientation;

public class PlayGame {

	private Stage stage;
	private GameState currentState;
	private Board board;

	private DominoServer server;	

	public enum GameState {
		WAIT, READY, PLAYER1, PLAYER2
	}

	public PlayGame() {

		board = new Board();
		server = new DominoServer(this);
		stage = new Stage(new StretchViewport(1920, 1080));
		currentState = GameState.WAIT;
	}

	public void update() {

		switch (currentState) {
		case WAIT:
			break;

		case READY:

			ArrayList<String> temp = new ArrayList<String>();
			for(int i = 0; i < board.getPlayerPieces1().size(); i++) {
				temp.add(board.getPlayerPieces1().get(i).getPieceString());
			}

			ArrayList<String> temp2 = new ArrayList<String>();

			for(int i = 0; i < board.getPlayerPieces2().size(); i++) {
				temp2.add(board.getPlayerPieces2().get(i).getPieceString());
			}

			ArrayList<String> temp3 = board.getPossiblePlayableValues();

			server.getPlayersList().get(0).sendPieces(temp, temp3);

			server.getPlayersList().get(1).sendPieces(temp2, temp3);

			stage.addActor(getImage(board.getRoot().getPieceString()));
			AssetsLoader.setImagePosition(getImage(board.getRoot().getPieceString()), AssetsLoader.X_INIT_POSITION, AssetsLoader.Y_INIT_POSITION, board.getRoot().getRotation());
			currentState = GameState.PLAYER1;
			break;

		case PLAYER1:

			String returnFromClient = server.getPlayersList().get(0).getPlayerMove();

			if(returnFromClient.equals(""))
				break;

			if(returnFromClient == "-2") {

				if(board.getAvailablePieces().size() == 0)
					server.getPlayersList().get(0).printMessage("Not enough pieces");
				else
					board.getPlayerPieces1().add(board.getAvailablePieces().remove(0));

			} else {

				Scanner scan = new Scanner(returnFromClient);
				String retPiece = scan.next();
				System.out.println(retPiece);
				String retPosition = scan.next();
				System.out.println(retPosition);
				Piece returnedPiece = getPlayedPiece(board.getPlayerPieces1(), retPiece).clone();
				System.out.println(returnedPiece.getPieceString());

				setPieceToBoard(retPosition, returnedPiece);
				// Passar para o proximo jogador
				currentState = GameState.PLAYER2;
			}

			break;

		case PLAYER2:

			returnFromClient = server.getPlayersList().get(1).getPlayerMove();

			if(returnFromClient.equals(""))
				break;

			if(returnFromClient == "-2") {

				if(board.getAvailablePieces().size() == 0)
					server.getPlayersList().get(1).printMessage("Not enough pieces");
				else
					board.getPlayerPieces2().add(board.getAvailablePieces().remove(0));

			} else {

				// Passar para o proximo jogador
				currentState = GameState.PLAYER1;
			}

			break;
		default:
			break;
		}

	}



	public Stage getStage() {
		return stage;
	}

	public Image getImage(String image) {
		return AssetsLoader.imageTable.get(image);
	}

	public void setPieceToBoard(String retPosition, Piece piece) {

		if(retPosition.equals("leftNorth"))
			setPieceLeftNorth(piece);
		else if(retPosition.equals("leftEast"))
			setPieceLeftEast(piece);
		else if(retPosition.equals("leftSouth"))
			setPieceLeftSouth(piece);
		else if(retPosition.equals("leftWest"))
			setPieceLeftWest(piece);
		else if(retPosition.equals("rightNorth"))
			setPieceRightNorth(piece);
		else if(retPosition.equals("rightEast"))
			setPieceRightEast(piece);
		else if(retPosition.equals("rightSouth"))
			setPieceRightSouth(piece);
		else if(retPosition.equals("rightWest"))
			setPieceRightWest(piece);


	}

	public Piece getPlayedPiece(ArrayList< Piece> pieces, String pieceString) {
		Piece temp = new Piece(0, 0, Rotation.NORTH); // TODO

		for(Piece piece : pieces) {
			if(piece.getPieceString().equalsIgnoreCase(pieceString))
				temp = piece.clone();
		}

		return temp;
	}

	public void setReady() {
		currentState = GameState.READY;
	}

	private void setPieceRightEast(Piece piece) {
		if(board.getRightSidePieceHorientation().equals(PieceHorientation.NONE)) {
			if(board.getRootHorientation().equals(PieceHorientation.HORIZONTAL_ROOT))
			{	
				board.addPieceToExtremity(piece, false);
				if(piece.getFirstExtremity().isLinkStatus())
					piece.setRotation(Rotation.WEST);
				else if(piece.getSecondExtremity().isLinkStatus())
					piece.setRotation(Rotation.EAST);
				stage.addActor(getImage(piece.getPieceString()));
				AssetsLoader.setImagePosition(getImage(piece.getPieceString()), board.getXPosRight() + AssetsLoader.PIECE_HEIGHT,
						board.getYPosLeft(), piece.getRotation());
				board.setXPosRight(board.getXPosRight() + AssetsLoader.PIECE_HEIGHT);
				board.setRightSidePieceHorientation(PieceHorientation.HORIZONTAL_RIGHT);
			}
			else if(board.getRootHorientation().equals(PieceHorientation.VERTICAL_ROOT)) {
				board.addPieceToExtremity(piece, false);
				if(piece.getFirstExtremity().isLinkStatus())
					piece.setRotation(Rotation.WEST);
				else if(piece.getSecondExtremity().isLinkStatus())
					piece.setRotation(Rotation.EAST);
				stage.addActor(getImage(piece.getPieceString()));
				AssetsLoader.setImagePosition(getImage(piece.getPieceString()), 
						board.getXPosRight() + AssetsLoader.PIECE_HEIGHT/2 + AssetsLoader.PIECE_WIDTH/2,board.getYPosLeft(), 
						piece.getRotation());
				board.setXPosRight(board.getXPosRight() + AssetsLoader.PIECE_HEIGHT/2 + AssetsLoader.PIECE_WIDTH/2);
				board.setRightSidePieceHorientation(PieceHorientation.HORIZONTAL_RIGHT);
			}
		}
		else if(board.getRightSidePieceHorientation().equals(PieceHorientation.HORIZONTAL_RIGHT)) {
			board.addPieceToExtremity(piece, false);
			if(piece.getFirstExtremity().isLinkStatus())
				piece.setRotation(Rotation.WEST);
			else if(piece.getSecondExtremity().isLinkStatus())
				piece.setRotation(Rotation.EAST);
			stage.addActor(getImage(piece.getPieceString()));
			AssetsLoader.setImagePosition(getImage(piece.getPieceString()), board.getXPosRight() + AssetsLoader.PIECE_HEIGHT,
					board.getYPosLeft(), piece.getRotation());
			board.setXPosRight(board.getXPosRight() + AssetsLoader.PIECE_HEIGHT);
			board.setRightSidePieceHorientation(PieceHorientation.HORIZONTAL_RIGHT);
		}
		else if(board.getRightSidePieceHorientation().equals(PieceHorientation.VERTICAL_DOWN)) {
			board.addPieceToExtremity(piece, false);
			if(piece.getFirstExtremity().isLinkStatus())
				piece.setRotation(Rotation.WEST);
			else if(piece.getSecondExtremity().isLinkStatus())
				piece.setRotation(Rotation.EAST);
			stage.addActor(getImage(piece.getPieceString()));
			AssetsLoader.setImagePosition(getImage(piece.getPieceString()), board.getXPosRight() + AssetsLoader.PIECE_HEIGHT/2 + AssetsLoader.PIECE_WIDTH/2,
					board.getYPosLeft() - AssetsLoader.PIECE_WIDTH/2, piece.getRotation());
			board.setXPosRight(board.getXPosRight() + AssetsLoader.PIECE_HEIGHT/2 + AssetsLoader.PIECE_WIDTH/2);
			board.setYPosLeft(board.getYPosLeft() - AssetsLoader.PIECE_WIDTH/2);
			board.setRightSidePieceHorientation(PieceHorientation.HORIZONTAL_RIGHT);
		}
		else if(board.getRightSidePieceHorientation().equals(PieceHorientation.VERTICAL_UP)) {
			board.addPieceToExtremity(piece, false);
			if(piece.getFirstExtremity().isLinkStatus())
				piece.setRotation(Rotation.WEST);
			else if(piece.getSecondExtremity().isLinkStatus())
				piece.setRotation(Rotation.EAST);
			stage.addActor(getImage(piece.getPieceString()));
			AssetsLoader.setImagePosition(getImage(piece.getPieceString()), board.getXPosRight() + AssetsLoader.PIECE_HEIGHT/2 + AssetsLoader.PIECE_WIDTH/2,
					board.getYPosLeft() + AssetsLoader.PIECE_WIDTH/2, piece.getRotation());
			board.setXPosRight(board.getXPosRight() + AssetsLoader.PIECE_HEIGHT/2 + AssetsLoader.PIECE_WIDTH/2);
			board.setYPosLeft(board.getYPosLeft() + AssetsLoader.PIECE_WIDTH/2);
			board.setRightSidePieceHorientation(PieceHorientation.HORIZONTAL_RIGHT);
		}
	}

	private void setPieceRightSouth(Piece piece) {
		if(board.getRightSidePieceHorientation().equals(PieceHorientation.HORIZONTAL_LEFT)) {
			board.addPieceToExtremity(piece, false);
			if(piece.getFirstExtremity().isLinkStatus())
				piece.setRotation(Rotation.NORTH);
			else if(piece.getSecondExtremity().isLinkStatus())
				piece.setRotation(Rotation.SOUTH);
			stage.addActor(getImage(piece.getPieceString()));
			AssetsLoader.setImagePosition(getImage(piece.getPieceString()), board.getXPosRight() + AssetsLoader.PIECE_HEIGHT/2 + AssetsLoader.PIECE_WIDTH/2,
					board.getYPosLeft() - AssetsLoader.PIECE_WIDTH/2, piece.getRotation());
			board.setXPosRight(board.getXPosRight() + AssetsLoader.PIECE_HEIGHT/2 + AssetsLoader.PIECE_WIDTH/2);
			board.setYPosLeft(board.getYPosLeft() - AssetsLoader.PIECE_WIDTH/2);
			board.setRightSidePieceHorientation(PieceHorientation.VERTICAL_DOWN);

		}
		else if(board.getRightSidePieceHorientation().equals(PieceHorientation.HORIZONTAL_RIGHT)) {
			board.addPieceToExtremity(piece, false);
			if(piece.getFirstExtremity().isLinkStatus())
				piece.setRotation(Rotation.NORTH);
			else if(piece.getSecondExtremity().isLinkStatus())
				piece.setRotation(Rotation.SOUTH);
			stage.addActor(getImage(piece.getPieceString()));
			AssetsLoader.setImagePosition(getImage(piece.getPieceString()), board.getXPosRight() - AssetsLoader.PIECE_HEIGHT/2 - AssetsLoader.PIECE_WIDTH/2,
					board.getYPosLeft() - AssetsLoader.PIECE_WIDTH/2, piece.getRotation());
			board.setXPosRight(board.getXPosRight() - AssetsLoader.PIECE_HEIGHT/2 - AssetsLoader.PIECE_WIDTH/2);
			board.setYPosLeft(board.getYPosLeft() - AssetsLoader.PIECE_WIDTH/2);
			board.setRightSidePieceHorientation(PieceHorientation.VERTICAL_DOWN);
		}
		else if(board.getRightSidePieceHorientation().equals(PieceHorientation.VERTICAL_DOWN)) {
			board.addPieceToExtremity(piece, false);
			if(piece.getFirstExtremity().isLinkStatus())
				piece.setRotation(Rotation.NORTH);
			else if(piece.getSecondExtremity().isLinkStatus())
				piece.setRotation(Rotation.SOUTH);
			stage.addActor(getImage(piece.getPieceString()));
			AssetsLoader.setImagePosition(getImage(piece.getPieceString()), board.getXPosRight(),
					board.getYPosLeft() - AssetsLoader.PIECE_HEIGHT, piece.getRotation());
			board.setYPosLeft(board.getYPosLeft() - AssetsLoader.PIECE_HEIGHT);
			board.setRightSidePieceHorientation(PieceHorientation.VERTICAL_DOWN);
		}
	}

	private void setPieceRightWest(Piece piece) {
		if(board.getRightSidePieceHorientation().equals(PieceHorientation.HORIZONTAL_LEFT)) {
			board.addPieceToExtremity(piece, false);
			if(piece.getFirstExtremity().isLinkStatus())
				piece.setRotation(Rotation.EAST);
			else if(piece.getSecondExtremity().isLinkStatus())
				piece.setRotation(Rotation.WEST);
			stage.addActor(getImage(piece.getPieceString()));
			AssetsLoader.setImagePosition(getImage(piece.getPieceString()), board.getXPosRight() - AssetsLoader.PIECE_HEIGHT,
					board.getYPosLeft(), piece.getRotation());
			board.setXPosRight(board.getXPosRight() - AssetsLoader.PIECE_HEIGHT);
			board.setRightSidePieceHorientation(PieceHorientation.HORIZONTAL_LEFT);

		}
		else if(board.getRightSidePieceHorientation().equals(PieceHorientation.VERTICAL_DOWN)) {
			board.addPieceToExtremity(piece, false);
			if(piece.getFirstExtremity().isLinkStatus())
				piece.setRotation(Rotation.EAST);
			else if(piece.getSecondExtremity().isLinkStatus())
				piece.setRotation(Rotation.WEST);
			stage.addActor(getImage(piece.getPieceString()));
			AssetsLoader.setImagePosition(getImage(piece.getPieceString()), board.getXPosRight() - AssetsLoader.PIECE_HEIGHT/2 - AssetsLoader.PIECE_WIDTH/2,
					board.getYPosLeft() - AssetsLoader.PIECE_WIDTH/2, piece.getRotation());
			board.setXPosRight(board.getXPosRight() - AssetsLoader.PIECE_HEIGHT/2 - AssetsLoader.PIECE_WIDTH/2);
			board.setYPosLeft(board.getYPosLeft() - AssetsLoader.PIECE_WIDTH/2);
			board.setRightSidePieceHorientation(PieceHorientation.HORIZONTAL_LEFT);
		}
		else if(board.getRightSidePieceHorientation().equals(PieceHorientation.VERTICAL_UP)) {
			board.addPieceToExtremity(piece, false);
			if(piece.getFirstExtremity().isLinkStatus())
				piece.setRotation(Rotation.EAST);
			else if(piece.getSecondExtremity().isLinkStatus())
				piece.setRotation(Rotation.WEST);
			stage.addActor(getImage(piece.getPieceString()));
			AssetsLoader.setImagePosition(getImage(piece.getPieceString()), board.getXPosRight() - AssetsLoader.PIECE_HEIGHT/2 - AssetsLoader.PIECE_WIDTH/2,
					board.getYPosLeft() + AssetsLoader.PIECE_WIDTH/2, piece.getRotation());
			board.setXPosRight(board.getXPosRight() - AssetsLoader.PIECE_HEIGHT/2 - AssetsLoader.PIECE_WIDTH/2);
			board.setYPosLeft(board.getYPosLeft() + AssetsLoader.PIECE_WIDTH/2);
			board.setRightSidePieceHorientation(PieceHorientation.HORIZONTAL_LEFT);
		}

	}

	private void setPieceRightNorth(Piece piece) {
		if(board.getRightSidePieceHorientation().equals(PieceHorientation.HORIZONTAL_LEFT)) {
			board.addPieceToExtremity(piece, false);
			if(piece.getFirstExtremity().isLinkStatus())
				piece.setRotation(Rotation.SOUTH);
			else if(piece.getSecondExtremity().isLinkStatus())
				piece.setRotation(Rotation.NORTH);
			stage.addActor(getImage(piece.getPieceString()));
			AssetsLoader.setImagePosition(getImage(piece.getPieceString()), board.getXPosRight() - AssetsLoader.PIECE_HEIGHT/2 - AssetsLoader.PIECE_WIDTH/2,
					board.getYPosLeft() + AssetsLoader.PIECE_WIDTH/2, piece.getRotation());
			board.setXPosRight(board.getXPosRight() - AssetsLoader.PIECE_HEIGHT/2 - AssetsLoader.PIECE_WIDTH/2);
			board.setYPosLeft(board.getYPosLeft() + AssetsLoader.PIECE_WIDTH/2);
			board.setRightSidePieceHorientation(PieceHorientation.VERTICAL_UP);

		}
		else if(board.getRightSidePieceHorientation().equals(PieceHorientation.HORIZONTAL_RIGHT)) {
			board.addPieceToExtremity(piece, false);
			if(piece.getFirstExtremity().isLinkStatus())
				piece.setRotation(Rotation.SOUTH);
			else if(piece.getSecondExtremity().isLinkStatus())
				piece.setRotation(Rotation.NORTH);
			stage.addActor(getImage(piece.getPieceString()));
			AssetsLoader.setImagePosition(getImage(piece.getPieceString()), board.getXPosRight() + AssetsLoader.PIECE_HEIGHT/2 + AssetsLoader.PIECE_WIDTH/2,
					board.getYPosLeft() + AssetsLoader.PIECE_WIDTH/2, piece.getRotation());
			board.setXPosRight(board.getXPosRight() + AssetsLoader.PIECE_HEIGHT/2 + AssetsLoader.PIECE_WIDTH/2);
			board.setYPosLeft(board.getYPosLeft() + AssetsLoader.PIECE_WIDTH/2);
			board.setRightSidePieceHorientation(PieceHorientation.VERTICAL_UP);
		}
		else if(board.getRightSidePieceHorientation().equals(PieceHorientation.VERTICAL_UP)) {
			board.addPieceToExtremity(piece, false);
			if(piece.getFirstExtremity().isLinkStatus())
				piece.setRotation(Rotation.SOUTH);
			else if(piece.getSecondExtremity().isLinkStatus())
				piece.setRotation(Rotation.NORTH);
			stage.addActor(getImage(piece.getPieceString()));
			AssetsLoader.setImagePosition(getImage(piece.getPieceString()), board.getXPosRight(),
					board.getYPosLeft() + AssetsLoader.PIECE_HEIGHT, piece.getRotation());
			board.setYPosLeft(board.getYPosLeft() + AssetsLoader.PIECE_HEIGHT);
			board.setRightSidePieceHorientation(PieceHorientation.VERTICAL_UP);
		}

	}

	private void setPieceLeftWest(Piece piece) {
		// TODO Auto-generated method stub

	}

	private void setPieceLeftSouth(Piece piece) {
		// TODO Auto-generated method stub

	}

	private void setPieceLeftEast(Piece piece) {

	}

	private void setPieceLeftNorth(Piece piece) {
		// TODO Auto-generated method stub

	}

}