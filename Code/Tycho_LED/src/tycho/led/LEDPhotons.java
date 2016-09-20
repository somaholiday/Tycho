package tycho.led;

import processing.core.PApplet;
import processing.core.PImage;
import tycho.Config;

public class LEDPhotons {
	private static final int PARTICLE_COUNT = 1;

	PImage texture;
	LEDPhoton photons[];
	PApplet pap;
	int particleIndex = 0;

	public LEDPhotons(PApplet pap) {
		this.pap = pap;
		texture = pap.loadImage(Config.PARTICLE_FILENAME);
		texture.resize(Config.PARTICLE_LENGTH, 1);

		photons = new LEDPhoton[PARTICLE_COUNT];
		
		// start all particles off the edge, so they start dead
		int startX = pap.width + texture.width + 1;
		
		for (int i = 0; i < photons.length; i++) {
			photons[i] = new LEDPhoton(pap, startX, Config.LED_Y, texture);
		}
	}

	public void emit(int c) {
		photons[particleIndex].x = -1;
		photons[particleIndex].setColor(c);
		particleIndex = (particleIndex + 1) % photons.length;
	}

		
	public void draw() {
		for (LEDPhoton particle : photons) {
			particle.update();
			particle.draw();
		}
	}
	
	public boolean report() {
		for (LEDPhoton particle : photons) {
			if (particle.isAtEdge()) {
				return true;
			}
		}
		return false;
	}
	
	public int reportColor() {
		for (LEDPhoton particle : photons) {
			if (particle.isAtEdge()) {
				return pap.color(particle.hue, particle.saturation, particle.intensity);
			}
		}
		return 0;
	}
}
