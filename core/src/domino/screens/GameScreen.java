package domino.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import domino.assetsLoader.AssetsLoader;
import domino.gui.DominoTable;
import domino.logic.PlayGame;

public class GameScreen implements Screen {

	private static SpriteBatch batchPlay;
	private PlayGame playGame;

	@Override
	public void show() {

		batchPlay = new SpriteBatch();
		playGame = new PlayGame();

		Gdx.input.setInputProcessor(playGame.getStage());

		
		
	}
		

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		DominoTable.camera.update();
		batchPlay.setProjectionMatrix(DominoTable.camera.combined);

		batchPlay.begin();
		AssetsLoader.spritePlayTable.draw(batchPlay);
		batchPlay.end();
		
		playGame.update();

		playGame.getStage().act(delta);
		playGame.getStage().draw();

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

		playGame.getStage().dispose();
		batchPlay.dispose();

	}

}