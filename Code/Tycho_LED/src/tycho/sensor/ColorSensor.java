package tycho.sensor;

import processing.core.PApplet;

public abstract class ColorSensor {

	int lastColor;
	PApplet pap;

	public ColorSensor(PApplet pap) {
		this.pap = pap;
	}
	
	public abstract int readColor();
}