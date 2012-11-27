package com.icyhill.cglab04;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.icyhill.cglab04.shader.DiffuseShader;
import com.icyhill.cglab04.shader.SkyboxShader;
import com.icyhill.cglab04.shader.WaterShader;

public class MainGame implements ApplicationListener {
	private PerspectiveCamera camera;
	
	CameraController inputProcessor;

	private ShaderProgram diffuseShader;
	private ShaderProgram skyboxShader;
	private ShaderProgram waterShader;

	private Bear bear;
	
	private Water water;
	
	private Boat boat;

	private Pixmap[] texturesSkyBox;

	private Skybox skybox;
	
	private float time;

	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new PerspectiveCamera(60, w, h);
		camera.up.set(0, 0, 1);
		camera.position.set(0, 0, 50);
		camera.lookAt(0, 10, 50);
		camera.near = 0.1f;
		camera.far = 2000f;

		diffuseShader = new ShaderProgram(DiffuseShader.vertexShader,
				DiffuseShader.fragmentShader);
		if (diffuseShader.isCompiled() == false) {
			throw new RuntimeException("Could not load diffuse shader: "
					+ diffuseShader.getLog());
		}

		skyboxShader = new ShaderProgram(SkyboxShader.vertexShader,
				SkyboxShader.fragmentShader);
		if (skyboxShader.isCompiled() == false) {
			throw new RuntimeException("Could not load skybox shader: "
					+ skyboxShader.getLog());
		}
		
		waterShader = new ShaderProgram(WaterShader.vertexShader,
				WaterShader.fragmentShader);
		if (waterShader.isCompiled() == false) {
			throw new RuntimeException("Could not load water shader: "
					+ waterShader.getLog());
		}

		bear = new Bear();

		texturesSkyBox = new Pixmap[6];

		// Image source: http://www.codemonsters.de/home/content.php?show=cubemaps
		texturesSkyBox[0] = new Pixmap( // right
				Gdx.files.internal("textures/brightday1_positive_x.png"));
		texturesSkyBox[1] = new Pixmap( // left
				Gdx.files.internal("textures/brightday1_negative_x.png"));
		texturesSkyBox[2] = new Pixmap( // top
				Gdx.files.internal("textures/brightday1_positive_y.png"));
		texturesSkyBox[3] = new Pixmap( // down
				Gdx.files.internal("textures/brightday1_negative_y.png"));
		texturesSkyBox[4] = new Pixmap( // front
				Gdx.files.internal("textures/brightday1_positive_z.png"));
		texturesSkyBox[5] = new Pixmap( // back
				Gdx.files.internal("textures/brightday1_negative_z.png"));
		
		skybox = new Skybox(texturesSkyBox);
		
		water = new Water(skybox.getCubeMapTextureUnit());
		
		boat = new Boat( bear );

		inputProcessor = new CameraController(camera);
		Gdx.input.setInputProcessor(inputProcessor);
		
		time = 0;
		
	}

	@Override
	public void dispose() {
		diffuseShader.dispose();
		bear.dispose();
		for (Pixmap texture : texturesSkyBox) {
			texture.dispose();
		}
		skybox.dispose();
		water.dispose();
	}

	@Override
	public void render() {
		
		inputProcessor.pullInputs();
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glDisable(GL20.GL_CULL_FACE);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		camera.update();
		
		skyboxShader.begin();
		skybox.render(skyboxShader, camera);
		skyboxShader.end();
		
		//Gdx.gl.glCullFace(GL20.GL_BACK);
		//Gdx.gl.glEnable(GL20.GL_CULL_FACE);
		
		time += Gdx.graphics.getDeltaTime();
		waterShader.begin();
		water.render(waterShader, camera, time);
		skyboxShader.end();

		diffuseShader.begin();
		boat.render(diffuseShader, camera, time);
		diffuseShader.end();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
