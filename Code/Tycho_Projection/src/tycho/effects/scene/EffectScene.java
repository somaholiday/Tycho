package tycho.effects.scene;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

public abstract class EffectScene {

	private static final int PG_WIDTH = 300;
	private static final int PG_HEIGHT = 300;
	public PGraphics pg;
	PApplet pap;

	private boolean showFrameRate = true;

	public EffectScene(PApplet pap) {
		this.pap = pap;
		pg = pap.createGraphics(PG_WIDTH, PG_HEIGHT, PConstants.P2D);
		pg.textFont(pap.createFont("Verdana", 72));
	}

	public void update() {
		pg.beginDraw();
		pg.background(0);
		this.draw();

		if (showFrameRate) {
			showFrameRate();
		}

		pg.endDraw();
	}

	public void showFrameRate() {
		pg.pushStyle();
		pg.noStroke();
		pg.fill(255, 0, 0);
		pg.text(PApplet.nf(pap.frameRate, 2, 2), pg.width * .5f, pg.height * .5f);
		pg.popStyle();
	}

	public abstract void draw();
}
