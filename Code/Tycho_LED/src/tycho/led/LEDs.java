package tycho.led;

import processing.core.PApplet;
import tycho.Config;
import tycho.opc.OPC;

public class LEDs {

	OPC opc;
	PApplet pap;

	public LEDs(PApplet pap) {
		this.pap = pap;
		opc = new OPC(pap, "127.0.0.1", 7890);
		initLEDs();
	}

	void initLEDs() {
		opc.setStatusLed(false);
		opc.showLocations(false);
		
		float w = (float)(pap.width);

		float X = w * .5f;
		float Y = Config.LED_Y;
		float SPACING = w / Config.LED_COUNT;
		float ANGLE = 0;
		boolean REVERSED = true;

		for (int i = 0; i < Config.LED_STRIP_COUNT; i++) {
			opc.ledStrip(64 * (i), Config.LED_COUNT, X, Y + i, SPACING, ANGLE, REVERSED);
		}
	}
}