package tycho;

import codeanticode.syphon.SyphonServer;
import oscP5.OscBundle;
import oscP5.OscMessage;
import oscP5.OscP5;
import processing.core.PApplet;
import processing.core.PImage;
import tycho.effects.Ring;
import tycho.effects.scene.EffectScene;
import tycho.madmapper.MadMapperScript;
import tycho.particle.GalaxyParticles;

@SuppressWarnings("serial")
public class Tycho_Projection extends PApplet {

	private OscP5 osc;

	// Projection Image
	SyphonServer syphon;
	GalaxyParticles galaxyParticles;
	MadMapperScript script;
	EffectScene[] effectScenes;
	int currentEffectScene = 0;

	Ring[] rings;
	int ringIndex = 0;

	int SIZE_W = 500;
	int SIZE_H = 500;

	public void setup() {
		size(SIZE_W, SIZE_H, P2D);
		colorMode(HSB, 360, 100, 100);
		blendMode(ADD);

		PApplet pap = this;

		osc = new OscP5(this, 7777);

		// Projection Image
		syphon = new SyphonServer(pap, "Tycho");
		// galaxyParticles = new GalaxyParticles(pap);
		script = new MadMapperScript();
		// effectScenes = new EffectScene[] { new RippleScene(pap) };

		prepareExitHandler();

		PImage texture = pap.loadImage("ring.png");

		rings = new Ring[100];

		for (int i = 0; i < rings.length; i++) {
			rings[i] = new Ring(pap, texture);
		}

		background(0);
	}

	public void draw() {
		background(0);

		// galaxyParticles.draw();

		// image(galaxyParticles.pg, 0, 0);

		// Projection Image
		script.update();

		// EffectScene scene = effectScenes[currentEffectScene];
		// scene.update();
		// image(scene.pg, 0, 0);
		// syphon.sendImage(scene.pg);
		// syphon.sendImage(galaxyParticles.pg);

		pushStyle();
		noStroke();
		// fill(255, 100, 100);
		// rect(0, 0, frameCount % width, frameCount % width);

		for (int i = 0; i < rings.length; i++) {
			rings[i].draw();
		}

		popStyle();

		syphon.sendScreen();

		showFrameRate();
	}

	public void showFrameRate() {
		if (frameCount % 30 == 0) {
			frame.setTitle("FPS : " + nf(frameRate, 2, 2));
		}
	}

	public void keyPressed() {

	}

	public void addRing(int color) {
		Ring ring = rings[ringIndex];
		ring.setHue(hue(color));
		ring.setSaturation(saturation(color));
		ring.respawn(width * .5f, height * .5f);
		ringIndex = (ringIndex + 1) % rings.length;
	}

	void oscEvent(OscBundle bundle) {
		println("bundle received!");

		for (int i = 0; i < bundle.size(); i++) {
			OscMessage m = bundle.getMessage(i);
			print(m.addrPattern());
		}
	}

	void oscEvent(OscMessage msg) {
		// println("message received");
		// println(msg.addrPattern());
		if (msg.checkAddrPattern("/contact")) {
			// println("contact!");
			// println(msg.typetag());
			int color = msg.get(0).intValue();
			if (color != 0) {
				addRing(color);
			}
		}
	}

	private void prepareExitHandler() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("== SHUTDOWN HOOK ==");

				syphon.stop();
			}
		}));
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] {
				// "--present",
				tycho.Tycho_Projection.class.getName() });
	}

}
