package tycho;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCSerializeException;
import com.illposed.osc.transport.udp.OSCPortOut;

import processing.core.PApplet;
import tycho.led.LEDPhotons;
import tycho.led.LEDs;
import tycho.sensor.ColorSensor;
import tycho.sensor.DummyColorSensor;

@SuppressWarnings("serial")
public class Tycho_LED extends PApplet {

	private OSCPortOut oscPort;
	static final int OSC_PORT_OUT = 7777;

	// Color Sensor
	ColorSensor colorSensor;

	// LEDs
	LEDs leds;
	LEDPhotons photons;

	static final int FRAMES_PER_EMIT = 240;
	static final int TIME_BETWEEN_MSG = 100;

	int SIZE_W = 100;
	int SIZE_H = 6;

//	public void settings() {
//		size(100, 6, P2D);
//	}

	public void setup() {
		size(100, 6, P2D);
		colorMode(HSB, 360, 100, 100);
		blendMode(ADD);

		PApplet pap = this;

		// Color Sensor
		// colorSensor = new CameraColorSensor(pap);
		colorSensor = new DummyColorSensor(pap);

		// LEDs
		leds = new LEDs(pap);
		photons = new LEDPhotons(pap);

		try {
			oscPort = new OSCPortOut(InetAddress.getLocalHost(), OSC_PORT_OUT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		background(0);
	}

	long lastMsg = 0;

	public void draw() {
		background(0);

		// Color Sensor
		int col = colorSensor.readColor();
		showColorDebug(col);

		// LEDs
		if (frameCount % FRAMES_PER_EMIT == 0) {
			photons.emit(col);
		}

		photons.draw();

		if (photons.report()) {
			int reportColor = photons.reportColor();
			// println(reportColor);
			// println(hue(reportColor), saturation(reportColor),
			// brightness(reportColor));

			// show report indicator
			pushStyle();
			noStroke();
			fill(0, 100, 100);
			rect(0, 3, width, width);
			popStyle();

			if (System.currentTimeMillis() - lastMsg > TIME_BETWEEN_MSG) {
				// send OSC report

				final List<Object> args = new ArrayList<>(1);
				args.add(reportColor);
				final OSCMessage msg = new OSCMessage("/contact", args);
				try {
					oscPort.send(msg);
				} catch (IOException | OSCSerializeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				lastMsg = System.currentTimeMillis();
			}
		}

//		 showFrameRate();
	}

	public void showColorDebug(int col) {
		pushStyle();
		noStroke();
		fill(col);

		// color indicator
		rect(0, 0, width * .1f, width * .1f);
		popStyle();
	}

	public void showFrameRate() {
		if (frameCount % 30 == 0) {
			frame.setTitle(nf(frameRate, 2, 2));
		}
	}

	public void keyPressed() {

	}

	public static void main(String _args[]) {
		PApplet.main(new String[] {
				// "--present",
				tycho.Tycho_LED.class.getName() });
	}

}
