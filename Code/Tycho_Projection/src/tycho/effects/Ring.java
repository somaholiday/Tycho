package tycho.effects;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public class Ring {
	float x, y, size, intensity, hue, saturation;
	PApplet pap;
	PImage texture;
	
	public Ring(PApplet pap, PImage texture) {
		this.pap = pap;
		this.texture = texture;
	}
	
	public void setHue(float hue) {
		this.hue = hue;
	}
	
	public void setSaturation(float sat) {
		this.saturation = sat;
	}

	public void respawn(float x, float y) {
		this.x = x;
		this.y = y;

		//
		intensity = 50;

		// Default size is based on the screen size
		size = pap.height * 0.1f;
	}

	public void draw() {
		// Particles fade each frame
		intensity *= 0.975;

		// They grow at a rate based on their intensity
		size += pap.height * intensity * 0.02 * 0.03;

		// If the particle is still alive, draw it
		if (intensity >= 1) {
//			pg.blendMode(PConstants.ADD);
			pap.tint(hue, saturation, intensity);
			pap.image(texture, x - size / 2, y - size / 2, size, size);
		}
	}
}
