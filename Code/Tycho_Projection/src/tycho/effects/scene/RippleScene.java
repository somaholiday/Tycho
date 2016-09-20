package tycho.effects.scene;

import processing.core.PApplet;
import processing.core.PImage;
import tycho.effects.Ring;

public class RippleScene extends EffectScene {

	Ring[] rings;

	public RippleScene(PApplet pap) {
		super(pap);

		PImage texture = pap.loadImage("ring.png");

		rings = new Ring[100];

		for (int i = 0; i < rings.length; i++) {
			rings[i] = new Ring(pap, texture);
		}
	}

	@Override
	public void draw() {
		pg.pushStyle();
		pg.noStroke();
		pg.fill(255, 255, 0);
		pg.rect(0, 0, pap.frameCount % pg.width, pap.frameCount % pg.width);

		if (pap.frameCount % 60 == 0) {
			Ring ring = rings[(int) (pap.random(rings.length))];
			ring.setHue(255);
			ring.respawn(pg.width * .5f, pg.height * .5f);
		}

		for (int i = 0; i < rings.length; i++) {
			rings[i].draw();
		}

		pg.popStyle();
	}

}
