package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * Clase de lanzamiento para la aplicación de escritorio.
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("BlockBreaker");
		config.setWindowedMode(800, 480);
		config.setResizable(false); // Evita que la ventana sea redimensionable
		new Lwjgl3Application(new BlockBreakerGame(), config);
	}
}
