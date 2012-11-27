package com.icyhill.cglab04.shader;

public class DiffuseShader {

	public static final String vertexShader = ""
			+ "attribute vec4 a_position;                    \n"
			+ "attribute vec2 a_texCoord0;                   \n"
			+ "uniform mat4 u_VP;                            \n"
			+ "uniform mat4 u_M;                             \n"
			+ "varying vec2 v_textureCoord;                  \n"
			+ "void main() {                                 \n"
			+ "	  gl_Position = u_VP * u_M * a_position;     \n"
			+ "   v_textureCoord = a_texCoord0;              \n"
			+ "}                                             \n";

	public static final String fragmentShader = ""
			+ "#ifdef GL_ES                                  \n"
			+ "precision mediump float;                      \n"
			+ "#endif                                        \n"
			+ "                                              \n"
			+ "varying vec2 v_textureCoord;                  \n"
			+ "uniform sampler2D u_sampler;                  \n"
			+ "                                              \n"
			+ "void main() {                                 \n"
			+ "   gl_FragColor = texture2D(u_sampler, v_textureCoord);\n"
			+ "}                                             \n";
}