Java WebGL
=============

A showcase project to test the compatibility between Java and WebGL.

More info at my [wordpress blog](http://redkiing.wordpress.com/2012/11/13/java-webgl-and-cross-platform-game-development/).

Running the code
----------------

1. Import the eclipse projects.
2. Clone and import the model-loaders libgdx extension project.
3. Fix any classpath errors if any.
4. Run the desktop application or compile and launch the GWT project. 
5. Browse the code and enjoy!

Troubleshooting
---------------

If you encounter the following error when running the GWT app: "GWT could not create an array of type Material" (or something like that).
Then you need to apply the following (workaround) [patch](https://github.com/recastrodiaz/libgdx/commit/660e1e467c578bad63ce8feedb555351621ec380)

License
----------------

The code is released under the MIT License, excepting the Water shaders, the 3D models and the textures.
