package tycho;

import java.io.IOException;
import com.illposed.osc.MessageSelector;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCMessageEvent;
import com.illposed.osc.OSCMessageListener;
import com.illposed.osc.messageselector.OSCPatternAddressMessageSelector;
import com.illposed.osc.transport.udp.OSCPortIn;
import codeanticode.syphon.SyphonServer;
import processing.core.PApplet;
import processing.core.PImage;
import processing.opengl.PJOGL;
import tycho.effects.Ring;
import tycho.effects.scene.EffectScene;
import tycho.madmapper.MadMapperScript;
import tycho.particle.GalaxyParticles;

public class Tycho_Projection extends PApplet {

	private OSCPortIn oscPortIn;
	static final int OSC_PORT_IN = 7777;

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

	public void settings() {
		size(SIZE_W, SIZE_H, P2D);
		PJOGL.profile = 1; // magic for Syphon (https://github.com/Syphon/Processing/issues/23#issuecomment-179707694)
	}

	public void setup() {
		colorMode(HSB, 360, 100, 100);
		blendMode(ADD);
		
		System.out.println("🔥🔥🔥🔥🔥🔥🔥");
		System.out.println("Data path is: " + this.dataPath("") + '/');

		PApplet pap = this;
		PImage texture = pap.loadImage("ring.png");

		rings = new Ring[100];

		for (int i = 0; i < rings.length; i++) {
			rings[i] = new Ring(pap, texture);
		}

		// Projection Image
		syphon = new SyphonServer(pap, "Tycho");
		// galaxyParticles = new GalaxyParticles(pap);
		script = new MadMapperScript();
		// effectScenes = new EffectScene[] { new RippleScene(pap) };

		setupOSCListener();

		prepareExitHandler();

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
		for (int i = 0; i < rings.length; i++) {
			rings[i].draw();
		}
		popStyle();

		syphon.sendScreen();

		// showFrameRate();
	}

	public void showFrameRate() {
		if (frameCount % 30 == 0) {
			frame.setTitle("FPS : " + nf(frameRate, 2, 2));
		}
	}

	public void addRing(int color) {
		Ring ring = rings[ringIndex];
		ring.setHue(hue(color));
		ring.setSaturation(saturation(color));
		ring.respawn(width * .5f, height * .5f);
		ringIndex = (ringIndex + 1) % rings.length;
	}

	private void setupOSCListener() {
		try {
			oscPortIn = new OSCPortIn(OSC_PORT_IN);
		} catch (IOException e) {
			e.printStackTrace();
		}

		MessageSelector selector = new OSCPatternAddressMessageSelector("/contact");

		OSCMessageListener listener = new OSCMessageListener() {
			public void acceptMessage(OSCMessageEvent event) {
				// System.out.println("Contact received!");
				OSCMessage message = event.getMessage();

				int color = (int) message.getArguments().get(0);
				if (color != 0) {
					addRing(color);
				}
			}
		};

		oscPortIn.getDispatcher().addListener(selector, listener);
		oscPortIn.startListening();
	}

	private void prepareExitHandler() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("== SHUTDOWN HOOK ==");

				syphon.stop();
				oscPortIn.stopListening();
				try {
					oscPortIn.disconnect();
					oscPortIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}));
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] {
				// "--present",
				tycho.Tycho_Projection.class.getName() });
	}

}
