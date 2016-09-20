package tycho.particle;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public class GalaxyParticles {
	/*
	 * Rotate was made by Jared "BlueThen" C. Created on October 31, 2009.
	 * Refined and commented on November 3 of 2009, November 6 of 2009, and
	 * November 8 of 2009. www.bluethen.com
	 */

	/* Particle count. */
	int particleCount = 200;
	/* Here we create a global Particle array using our particleCount */
	Particle[] particles = new Particle[particleCount + 1];
	/* Setup, initialization, etc. */

	int rx, ry;
	
	float size = 50;

	int RAD;
	private static final int PG_WIDTH = 300;
	private static final int PG_HEIGHT = 300;
	public PGraphics pg;
	private PApplet pap;
	private PImage particleImage;

	public GalaxyParticles(PApplet pap) {
		this.pap = pap;
		pg = pap.createGraphics(PG_WIDTH, PG_HEIGHT, PConstants.P2D);
//		pg.blendMode(PConstants.ADD);
		pg.textFont(pap.createFont("Verdana", 40));

		particleImage = pap.loadImage("dot.png");

		/* The stroke color is the color used for our particles */
		pg.stroke(1);
		/*
		 * The fill color is the background color, meant for inside the border,
		 * since it is drawn using quad()
		 */
		pg.fill(0);
		/* Frame rate */
		// pg.setFrameRate(30);

		/*
		 * Variables are used to keep track of the x and y coordinates of the
		 * cursor.
		 */
		rx = (int) (pg.width * .5);
		ry = (int) (pg.height * .5);

		RAD = (int) (pg.width * .5 * Math.sqrt(2));

		/* The particles are created. */
		for (int x = particleCount; x >= 0; x--) {
			/*
			 * We call the particle function inside its class to set up a new
			 * particle. Each is positioned randomly.
			 */
			particles[x] = new Particle();
		}
	}

	public void draw() {
		pg.beginDraw();
		/* The screen is cleared, and the background is colored black */
		pg.background(0);
		/* We draw our border. It is 10 pixels from all sides. */
//		pg.quad(10, 10, pg.width - 10, 10, pg.width - 10, pg.height - 10, 10, pg.height - 10);
		/* The particles are looped through, then updated. */
		for (int i = particleCount; i >= 0; i--) {
			Particle particle = (Particle) particles[i];
			particle.update();
		}

		pg.pushStyle();
		pg.noStroke();
		pg.fill(255, 0, 0);
		pg.text(PApplet.nf(pap.frameRate, 2, 2), pg.width * .5f, pg.height * .5f);
		pg.popStyle();

		pg.endDraw();
	}

	class Particle {
		/*
		 * Our global class variables. These variables will be kept track of for
		 * each frame and throughout each function.
		 */
		/*
		 * x and y represents the coordinates. vx and vy represents the
		 * velocities or speed and direction of the particles.
		 */
		float x;
		float y;
		float vx;
		float vy;

		int life;

		/* We call this to set up a new particle. */
		Particle() {
			/*
			 * The x and y coordinates of the particle is random and within the
			 * borders.
			 */
			x = pap.random(10, pg.width - 10);
			y = pap.random(10, pg.height - 10);
			life = (int) (pap.random(300, 3000));
		}

		/* Here we update the coordinates and redraw the particle. */
		void update() {
			life--;
			if (life <= 0) {
				x = pap.random(pg.width);
				y = pap.random(pg.height);
				vx = vy = 0;
				life = (int) (pap.random(300, 3000));
				return;
			}

			/*
			 * The radius variable stores the distance between the cursor and
			 * the particle.
			 */
			float radius = PApplet.dist(x, y, rx, ry);
			/* Proceed if the particle is within 150 pixels of the cursor. */
			if (radius < RAD) {
				/*
				 * atan2 is used to find the angle between the cursor and the
				 * particle.
				 */
				float angle = PApplet.atan2(y - ry, x - rx);
				/*
				 * Most of our math is done here. Again, vx and vy are the
				 * veloicities. They're not the actual coordinates, but they're
				 * the speed and direction of the particles. The base formula
				 * here is (for (x,y)): (c + r * cos(a), c + r * sin(a)). c is
				 * the center, r is the radius, a is the angle. This formula
				 * finds the edge of a circle according to an angle, radius, and
				 * center. In the formula below, we use: c: x/y The center is
				 * technically x and y, since vx and vy are later added on to x
				 * and y.
				 * 
				 * r: (RAD - radius) * 0.01 Remember that radius is the distance
				 * from the cursor to the particle. The radius/distance in our
				 * formula can also represent the speed of the particle. The max
				 * radius is RAD, as limited to by our if statement above.
				 * Because of this, we subtract the distance from 150 to invert
				 * this value If we didn't, the particles would be moving as
				 * fast as how far they are. Multiplying it by 0.01 tones it
				 * down, so the particles don't get too out of control.
				 * 
				 * a: angle + (0.7 + 0.0005 * (RAD - radius)) variable angle is
				 * the angle between the cursor and the particle. it is added to
				 * the radius, which is inverted, multiplied by 0.0005. If we
				 * used angle alone, the particles would simply fly towards our
				 * cursor, and nothing else. Adding on 0.7 turns each particle
				 * by 0.7 radians, but to add to our pretty effect, we throw in
				 * the radius into the equation. Having the radius inverted
				 * (multiplied by 0.0005 to tone it down) will decrease the
				 * effect on particles by how far away they are and increase the
				 * effect on particles by how close they are to the cursor.
				 */

				float vx_factor = 0.001f;// map(sin(frameCount*.003), -1, 1,
											// 0.0005, 0.003);
				float vy_factor = 0.001f;// map(sin(frameCount*.007 + HALF_PI),
											// -1, 1, 0.001, 0.008);

				vx -= (RAD - radius) * vx_factor * Math.cos(angle + (0.7 + 0.001 * (RAD - radius)));
				vy -= (RAD - radius) * vy_factor * Math.sin(angle + (0.7 + 0.0001 * (RAD - radius)));
			}

			/*
			 * x and y are increased by our velocities. This completes our
			 * formula c + r * cos(a) or sin(a), with vx/vy being the r * cos(a)
			 * or sin(a)
			 */
			x += vx * .2;
			y += vy * .2;

			/* The velocities are decreased by 3% to simulate friction. */
			vx *= 0.97;
			vy *= 0.97;

			/*
			 * Boundary collision is calculated here. If the particle is beyond
			 * the boundary, its velocity is reversed and the particle is moved
			 * back into the main area.
			 */
			if (x > pg.width - 10) {
				vx *= -1;
				x = pg.width - 11;
			}
			if (x < 10) {
				vx *= -1;
				x = 11;
			}
			if (y > pg.height - 10) {
				vy *= -1;
				y = pg.height - 11;
			}
			if (y < 10) {
				vy *= -1;
				y = 11;
			}
			/*
			 * The particle is drawn. (int) is used because decimals for some
			 * reason makes the particle not draw for a lot of the time,
			 * resulting in a flicker.
			 */
			// pg.point((int)x, (int)y);
			pg.image(particleImage, x - size/2, y - size/2, size, size);
		}
	}
}
