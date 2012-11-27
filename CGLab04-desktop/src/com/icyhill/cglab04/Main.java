package com.icyhill.cglab04;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "CGLab04";
		cfg.useGL20 = false;
		cfg.width = 1024;
		cfg.height = 768;
		cfg.useGL20 = true;
		
		new LwjglApplication(new MainGame(), cfg);
	}
}
