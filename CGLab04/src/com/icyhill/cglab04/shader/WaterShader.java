package com.icyhill.cglab04.shader;

public class WaterShader {
	// ===========================================================
	// Constants
	// ===========================================================

	public static final String vertexShader = ""
			+ "const float pi = 3.14159;					\n"
			+ "const int numWaves = 3;						\n"
			+ "attribute vec4 a_position;					\n"
			+ "uniform mat4 u_VP;                           \n"
			+ "uniform mat4 u_M;                            \n"
			+ "uniform float waterHeight;					\n"
			+ "uniform float time;							\n"
			+ "uniform float amplitude[8];					\n"
			+ "uniform float wavelength[8];					\n"
			+ "uniform float speed[8];						\n"
			+ "uniform vec2 direction[8];					\n"
			+ "varying vec3 position;						\n"
			+ "varying vec3 worldNormal;					\n"
			+ "varying vec3 eyeNormal;						\n"
			+ "												\n"
			+ "float wave(int i, float x, float y) {		\n"
			+ "    float frequency = 2.*pi/wavelength[i];	\n"
			+ "    float phase = speed[i] * frequency;		\n"
			+ "    float theta = dot(direction[i], vec2(x, y));	\n"
			+ "    return amplitude[i] * sin(theta * frequency + time * phase);	\n"
			+ "}											\n" 
			+ "//---------------------------				\n"
			+ "float waveHeight(float x, float y) {			\n"
			+ "    float height = 0.0;						\n"
			+ "    for (int i = 0; i < numWaves; ++i)		\n"
			+ "        height += wave(i, x, y);				\n"
			+ "    return height;							\n" 
			+ "}											\n"
			+ "												\n"
			+ "float dWavedx(int i, float x, float y) {		\n"
			+ "    float frequency = 2.*pi/wavelength[i];	\n"
			+ "    float phase = speed[i] * frequency;		\n"
			+ "    float theta = dot(direction[i], vec2(x, y));\n"
			+ "    float A = amplitude[i] * direction[i].x * frequency;\n"
			+ "    return A * cos(theta * frequency + time * phase);\n" + "}\n"
			+ "												\n"
			+ "float dWavedy(int i, float x, float y) {		\n"
			+ "    float frequency = 2.*pi/wavelength[i];	\n"
			+ "    float phase = speed[i] * frequency;		\n"
			+ "    float theta = dot(direction[i], vec2(x, y));\n"
			+ "    float A = amplitude[i] * direction[i].y * frequency;		\n"
			+ "    return A * cos(theta * frequency + time * phase);\n"
			+ "}											\n" 
			+ "												\n"
			+ "vec3 waveNormal(float x, float y) {			\n"
			+ "    float dx = 0.0;							\n" 
			+ "    float dy = 0.0;							\n"
			+ "    for (int i = 0; i < numWaves; ++i) {		\n"
			+ "        dx += dWavedx(i, x, y);				\n"
			+ "        dy += dWavedy(i, x, y);				\n" 
			+ "    }										\n"
			+ "    vec3 n = vec3(-dx, -dy, 1.0);			\n"
			+ "    return normalize(n);						\n" 
			+ "}				\n" 
			+ "												\n"
			+ "void main() {								\n" 
			+ "    vec4 pos = a_position;					\n"
			+ "    pos.z = waterHeight + waveHeight(pos.x, pos.y);\n"
			+ "    position = pos.xyz / pos.w;				\n"
			+ "    worldNormal = waveNormal(pos.x, pos.y);	\n"
			+ "    gl_Position = u_VP * u_M * pos;		\n"
			+ "}											\n" 
			+ "";

	public static final String fragmentShader = " "
			+ "#ifdef GL_ES\n"
			+ "precision mediump float;\n"
			+ "#endif\n"
			+ "    "
			+ "varying vec3 position;						\n"
			+ "varying vec3 worldNormal;					\n"
			+ "uniform vec3 cameraPos;						\n"
			+ "uniform samplerCube envMap;					\n" 
			+ "												\n"
			+ "void main() {								\n"
			+ "     vec3 eye = normalize(cameraPos - position);\n"
			+ "     vec3 r = reflect(eye, worldNormal);		\n"
			+ "     vec4 color = textureCube(envMap, r);	\n"
			+ "     color.a = 0.1;							\n"
			+ "     gl_FragColor = color;					\n" 
			+ "}											\n"
			+ "												\n";

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
