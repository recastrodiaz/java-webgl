package com.icyhill.cglab04;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;

/**
 * 
 * @author Rodrigo Castro
 * 
 */
public class Water implements Disposable {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final float SQ2 = (float) Math.sqrt(2);

	private static final float WIDTH = 1000;
	private static final float STEP = 20;

	private static final float WATER_HEIGHT = 0;

	// This should match the number defined on the WaterShader
	private static final int NB_WAVES = 3;
	private static final float[] AMPLITUDE = { 2f, 1.5f, 1f, 0.125f, 0.25f,
			1f, 2f, 0.5f };
	private static final float[] WAVELENGTH = { 30f, 13f, 15f, 7f, 9f, 13f, 15f,
			23f };
	private static final float[] SPEED = { 3f, 5f, 2f, 2f, 2f, 3f, 7f, 8f };
	private static final float[] DIRECTION = { //
	SQ2, SQ2, //
			-SQ2, SQ2, //
			SQ2, -SQ2, //
			SQ2, SQ2, //
			1, 3, //
			0, 1, //
			SQ2, SQ2,//
			0, 1, //
			SQ2, SQ2 };

	static {
		// Cf. vertex shader
		assert (NB_WAVES <= 8);
		assert (NB_WAVES <= AMPLITUDE.length);
		assert (NB_WAVES <= WAVELENGTH.length);
		assert (NB_WAVES <= SPEED.length);
		assert (2 * NB_WAVES <= DIRECTION.length);
	}

	// ===========================================================
	// Fields
	// ===========================================================

	Mesh mesh;
	Matrix4 model;
	int cubeMapTextureId;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Water(int cubeMapTextureId) {

		float[] vertices = createVertices(WIDTH, STEP);
		short[] indices = createIndices(WIDTH, STEP);
		this.mesh = new Mesh(true, vertices.length, indices.length,
				VertexAttribute.Position());
		mesh.setVertices(vertices);
		mesh.setIndices(indices);
		this.model = new Matrix4();
		this.cubeMapTextureId = cubeMapTextureId;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void dispose() {
		mesh.dispose();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void render(ShaderProgram waterShader, Camera camera, float time) {
		waterShader.setUniformf("waterHeight", WATER_HEIGHT);
		waterShader.setUniformf("time", time);
		waterShader.setUniform1fv("amplitude", AMPLITUDE, 0, NB_WAVES);
		waterShader.setUniform1fv("wavelength", WAVELENGTH, 0, NB_WAVES);
		waterShader.setUniform1fv("speed", SPEED, 0, NB_WAVES);
		waterShader.setUniform2fv("direction", DIRECTION, 0, 2 * NB_WAVES);
		waterShader.setUniformf("cameraPos", camera.position);
		waterShader.setUniformi("envMap", cubeMapTextureId);

		model.setToTranslation(0, 0, -1);

		waterShader.setUniformMatrix("u_M", model);
		waterShader.setUniformMatrix("u_VP", camera.combined);

		mesh.render(waterShader, GL20.GL_TRIANGLES);
	}

	private float[] createVertices(float width, float step) {
		int slices = (int) (width / step);
		assert (slices >= 1);
		// 4 vertices per square slice
		float[] vertices = new float[4 * 3 * slices * slices];
		int cnt = 0;
		for (int i = 0; i < slices; i++) {
			for (int j = 0; j < slices; j++) {
				// A square made of two triangles (P1,P2,P3), (P3,P2,P4)
				float x1 = step * (i + 0) - width / 2;
				float x2 = step * (i + 1) - width / 2;
				float z1 = step * (j + 0) - width / 2;
				float z2 = step * (j + 1) - width / 2;
				// P1
				vertices[cnt++] = x1;
				vertices[cnt++] = z1;
				vertices[cnt++] = 0;
				// P2
				vertices[cnt++] = x2;
				vertices[cnt++] = z1;
				vertices[cnt++] = 0;
				// P3
				vertices[cnt++] = x1;
				vertices[cnt++] = z2;
				vertices[cnt++] = 0;
				// P4
				vertices[cnt++] = x2;
				vertices[cnt++] = z2;
				vertices[cnt++] = 0;

			}
		}
		assert (cnt == 12 * slices * slices);
		return vertices;
	}

	private short[] createIndices(float width, float step) {
		int slices = (int) (width / step);
		assert (slices >= 1);
		// 6 vertices per square slice
		short[] indices = new short[6 * slices * slices];
		int cnt = 0;
		for (int i = 0; i < slices * slices; i++) {
			indices[cnt++] = (short) (4 * i);
			indices[cnt++] = (short) (4 * i + 1);
			indices[cnt++] = (short) (4 * i + 2);
			indices[cnt++] = (short) (4 * i + 2);
			indices[cnt++] = (short) (4 * i + 1);
			indices[cnt++] = (short) (4 * i + 3);
		}
		assert (cnt == 6 * slices * slices);
		assert (cnt <= 32767);
		return indices;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
