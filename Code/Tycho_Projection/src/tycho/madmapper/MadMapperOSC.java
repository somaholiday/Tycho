package tycho.madmapper;

import netP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscP5;

public class MadMapperOSC {

	private OscP5 osc;
	private NetAddress madMapper;

	public MadMapperOSC() {
		osc = new OscP5(this, 9000);
		madMapper = new NetAddress("127.0.0.1", 8010);
	}

	public void sendFade(float fade) {
		OscMessage msg = new OscMessage("master/fadeToBlack");
		msg.add(fade);

		osc.send(msg, madMapper);
	}

	public void selectPreset(int presetId) {
		OscMessage msg = new OscMessage("/presets/select");
		msg.add(presetId);
		
		osc.send(msg, madMapper);
	}
}
