package domino.interfaces;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import domino.logic.PlayGame;
import lipermi.exception.LipeRMIException;
import lipermi.handler.CallHandler;
import lipermi.handler.filter.GZipFilter;
import lipermi.net.IServerListener;
import lipermi.net.Server;

public class DominoServer implements ServerServices {
	
	/**
	 * Lista de clientes (para efectuar callback)
	 */
	private List<ClientServices> playersList = new ArrayList<ClientServices>();

	private static final int PORT = 124;

	/**
	 * Objecto responsavel por toda a conexao
	 */
	private CallHandler callHandler;

	/**
	 * Objecto que representa a entidade servidor
	 */
	private Server server;
	
	private PlayGame playGame;

	public DominoServer(PlayGame pg) {

		playGame = pg;
		
		// Preparar servidor

		server = new Server();
		callHandler = new CallHandler();

		server.addServerListener(new IServerListener() {
			public void clientConnected(Socket socket) {
				System.out.println("Client connected: " + socket.getInetAddress());

			}



			public void clientDisconnected(Socket socket) {
				System.out.println("Client disconnected: " + socket.getInetAddress());

			}
		});

		// Publicar as funcionalidades desejadas
		try {

			callHandler.registerGlobal(ServerServices.class, this);
		} catch (LipeRMIException e) {

			e.printStackTrace();
		}

		// Publicar na rede
		try {

			server.bind(PORT, callHandler, new GZipFilter());
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@Override
	public boolean addPlayer(ClientServices pl) {

		if(playersList.size() >= 2)
			return false;
		
		else if(playersList.size() == 0) {
			playersList.add(pl);
			return true;
		}
		else if(playersList.size() == 1) {
			playersList.add(pl);
			playGame.setReady();
		}
		
		return true;
	}
	
	public List<ClientServices> getPlayersList() {
		return playersList;
	}

	@Override
	public boolean addPieceToLeft(int p) {
		return false;
	}

	@Override
	public boolean addPieceToRight(int p) {
		return false;
	}

	@Override
	public boolean getPieceFromGraveyard() {
		return false;
	}

}
