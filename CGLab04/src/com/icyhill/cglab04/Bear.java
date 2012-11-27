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

public class Bear implements Disposable {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Texture skinTexture;
	private final Texture eyeTexture;
	private final StillModel bearModel;

	// Temporary objects
	private final Matrix4 model;
	private final Matrix4 temp;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Bear() {

		skinTexture = new Texture(Gdx.files.internal("models/skin.jpg"));
		eyeTexture = new Texture(Gdx.files.internal("models/eye.jpg"));
		bearModel = ModelLoaderRegistry.loadStillModel(Gdx.files
				.internal("models/teddy.g3dt"));
		bearModel.getSubMesh("bear").material = new Material("teddySkin",
				new TextureAttribute(
						skinTexture, 0, "u_sampler"));
		Material eyeMaterial = new Material("teddyEye", new TextureAttribute(
				eyeTexture, 0, "u_sampler"));
		bearModel.getSubMesh("beareye").material = eyeMaterial;
		bearModel.getSubMesh("beareye1").material = eyeMaterial;

		temp = new Matrix4();
		model = new Matrix4();
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void dispose() {
		skinTexture.dispose();
		eyeTexture.dispose();
		bearModel.dispose();
	}

	public void render(ShaderProgram diffuseShader, Camera camera,
			Matrix4 currentModel) {

		diffuseShader.setUniformMatrix("u_VP", camera.combined);

		temp.idt();
		temp.rotate(1, 0, 0, 90);

		model.idt();
		model.mul(currentModel);

		diffuseShader.setUniformMatrix("u_M", model.mul(temp));

		bearModel.render(diffuseShader);
	}
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
