package domino.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;

import domino.assetsLoader.AssetsLoader;
import domino.screens.GameScreen;

public class DominoTable extends Game {

	public final static String TITLE = "Domino Table";
	public final static String VERSION = "FEUP edition 0.0.1";
	public static OrthographicCamera camera; // faz resize de todas as classes screen do jogo

	@Override
	public void create () {
		
		AssetsLoader.load();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1920, 1080);
		setScreen(new GameScreen());

	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void pause() {
		super.pause();

	}

	@Override
	public void resume() {
		super.resume();

	}

	@Override
	public void dispose() {
		super.dispose();

	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

	}
}