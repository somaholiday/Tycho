package tycho.led;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import tycho.Config;

@SuppressWarnings("serial")

public class LEDPhoton extends PVector {
	private static final PVector velocity = new PVector(2.5f, 0);

	PApplet pap;
	PImage texture;

	float hue, saturation, intensity;

	public LEDPhoton(PApplet pap, float x, float y, PImage texture) {
		super(x, y);
		this.pap = pap;
		this.texture = texture;
	}

	public void setColor(int c) {
		hue = pap.hue(c);
		saturation = pap.saturation(c);
		intensity = Config.PARTICLE_BRIGHTNESS;
	}
	
	public boolean isDead() {
		return x - texture.width > pap.width;
	}
	
	public boolean isAtEdge() {
		return (x > pap.width) && (x - (texture.width *.5) < pap.width) && !isDead();
	}

	public void update() {
		if (!isDead()) {
			this.add(velocity);
		}
	}

	public void draw() {
		// If the particle is still alive, draw it
		if (!isDead()) {
			pap.tint(hue, saturation, intensity);
			pap.image(texture, x - texture.width, y, texture.width, 3 * texture.height);
			pap.noTint();
		}
	}
};