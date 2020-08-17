package commands;

import AQWBotRuntime.AQWBotRuntime;

public class Join extends Command {

	String room;

	public Join(String[] args) {
		super(args);
		room = args[1];
	}

	@Override
	public void Run() {
		try {			
			if (AQWBotRuntime.config.get("GAMECFG.PRIVATEROOM") != null) {
				int num = Integer.parseInt(AQWBotRuntime.config.get("GAMECFG.PRIVATEROOM"));
				AQWBotRuntime.typeString("/join " + room + "-" + num);
			} else {
				AQWBotRuntime.typeString("/join " + room);
			}
		} catch (InterruptedException e) {
		}
	}

}
