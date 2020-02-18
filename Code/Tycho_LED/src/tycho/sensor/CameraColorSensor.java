package tycho.sensor;

import processing.core.PApplet;
import processing.core.PImage;
import processing.video.Capture;

public class CameraColorSensor extends ColorSensor {
	Capture camera;

	// RESOLUTION
	// TODO: make this all less magic
	private final int RES_W = 80;
	private final int RES_H = 3;
	private final int TARGET_PIXEL = 40;
	private final int FRAMERATE = 30;
	private final String CAMERA_NAME = "Logitech Camera";
//	private final String CAMERA_NAME = "Logitech Camera #2";

	public CameraColorSensor(PApplet pap) {
		super(pap);
		System.out.println("CAMERA");
		PApplet.printArray(Capture.list());
//		System.out.println(Capture.list());
		camera = new Capture(pap, RES_W, RES_H, CAMERA_NAME, FRAMERATE);
		camera.start();
	}

	public int readColor() {
		if (camera.available()) {
			camera.read();

			camera.loadPixels();
			lastColor = camera.pixels[TARGET_PIXEL];
		}

		return lastColor;
	}
	
	public PImage readImage() {
		if (camera.available()) {
			camera.read();
		}
		return camera.get();
	}
}
