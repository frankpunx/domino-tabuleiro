package domino.gui.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import domino.gui.DominoTable;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = DominoTable.TITLE + DominoTable.VERSION;
		config.vSyncEnabled = true;
		config.width = 1920;
		config.height = 1080;
		new LwjglApplication(new DominoTable(), config);
	}
}


