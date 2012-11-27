package com.icyhill.cglab04;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.materials.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;

public class Boat implements Disposable {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final float ROTATION_SPEED_COEFF = 10f;

	// ===========================================================
	// Fields
	// ===========================================================

	private final Texture boatTexture;
	private final StillModel boatModel;

	// Temporary objects
	private final Matrix4 model;
	private final Matrix4 temp;
	private final Matrix4 translation;

	private final Bear bear;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Boat(Bear bear) {

		boatTexture = new Texture(Gdx.files.internal("models/wood.jpg"));
		boatModel = ModelLoaderRegistry.loadStillModel(Gdx.files
				.internal("models/boat.g3dt"));

		boatModel.setMaterial(new Material("uv", new TextureAttribute(boatTexture, 0, "u_sampler")));

		model = new Matrix4();
		temp = new Matrix4();
		translation = new Matrix4();

		this.bear = bear;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void dispose() {
		boatTexture.dispose();
		boatModel.dispose();
	}

	public void render(ShaderProgram diffuseShader, Camera camera, float time) {

		diffuseShader.setUniformMatrix("u_VP", camera.combined);

		temp.idt();
		temp.rotate(0, 1, 0, -90);
		model.idt();
		model.rotate(0, 0, 1, ROTATION_SPEED_COEFF * time)
				.translate(0, 300, 15);
		translation.set(model);
		model.scale(0.07f, 0.07f, 0.07f);

		diffuseShader.setUniformMatrix("u_M", model.mul(temp));

		boatModel.render(diffuseShader);

		bear.render(diffuseShader, camera, translation);

	}
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
