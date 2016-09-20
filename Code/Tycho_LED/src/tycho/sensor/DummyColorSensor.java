package tycho.sensor;

import processing.core.PApplet;

public class DummyColorSensor extends ColorSensor {

	float hue = 0;

	public DummyColorSensor(PApplet pap) {
		super(pap);
	}

	public int readColor() {
		lastColor = pap.color(hue, 100, 100);
		hue = (hue + 1) % 360;

		return lastColor;
	}
}
