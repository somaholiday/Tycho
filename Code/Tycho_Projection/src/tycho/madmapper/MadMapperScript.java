package tycho.madmapper;

import processing.core.PApplet;

public class MadMapperScript {
	private MadMapperOSC osc;
	
	private static final int FRAMES_PER_PRESET = 30 * 45;
	private static final int FRAMES_PER_FADE = 30 * 6;
	private static final int FRAMES_BETWEEN_FADES = 120;
	
	private int currentFrameCount = 0;
	
	private int currentPreset = 0;
	private static final int NUM_PRESETS = 5;
	
	public MadMapperScript() {
		osc = new MadMapperOSC();
	}
	
	public void update() {
		currentFrameCount++;
		
		// FADE IN 
		if (currentFrameCount < FRAMES_PER_FADE) {
			float fade = PApplet.norm(currentFrameCount, 0, FRAMES_PER_FADE);
			osc.sendFade(fade);
		}
		
		// FADE OUT
		if (FRAMES_PER_PRESET - currentFrameCount < FRAMES_PER_FADE) {
			float fade = PApplet.norm(FRAMES_PER_PRESET - currentFrameCount, 0, FRAMES_PER_FADE);
			osc.sendFade(fade);
		}
		
		if (currentFrameCount > FRAMES_PER_PRESET + FRAMES_BETWEEN_FADES) {
			nextPreset();
			currentFrameCount = 0;
		}
	}
	
	public void nextPreset() {
		currentPreset = (currentPreset + 1) % NUM_PRESETS;
		osc.selectPreset(currentPreset + 1);
	}
}
