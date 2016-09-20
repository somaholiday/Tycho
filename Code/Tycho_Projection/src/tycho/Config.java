package tycho;

public interface Config {
	// y coordinate of LED strip and particle registration
	public static final int LED_Y = 0;

	public static final int COLOR_SENSOR_DEBUG_SIZE = 30;

	// number of LEDs per strip
	public static final int LED_COUNT = 60;

	// number of LEDs strips
	public static final int LED_STRIP_COUNT = 3;

	// location of particle image mask
	public static final String PARTICLE_FILENAME = "/Users/soma/Dropbox/Tycho/Projections/comet.png";

	// length of particle gradient (in px)
	public static final int PARTICLE_LENGTH = 180;

	// intensity of tint for particle image mask
	public static final float PARTICLE_BRIGHTNESS = 100;
}
