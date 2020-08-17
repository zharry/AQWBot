package commands;

import AQWBotRuntime.AQWBotRuntime;

public class GameCFG extends Command {

	String option;
	String value;

	public GameCFG(String[] args) {
		super(args);
		this.option = args[1];
		this.value = args[2];
	}

	@Override
	public void Run() {
		AQWBotRuntime.config.put("GAMECFG." + this.option, this.value);
	}

}
