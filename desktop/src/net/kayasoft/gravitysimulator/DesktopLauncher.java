package net.kayasoft.gravitysimulator;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Simulasyon Uygulamasi");
		config.setWindowedMode(1440, 900);
		config.setResizable(false);
		config.setWindowIcon("blue.png");
		new Lwjgl3Application(new BalonSimulation(), config);
	}
}
