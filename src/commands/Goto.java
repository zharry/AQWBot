package commands;

import AQWBotRuntime.AQWBotRuntime;

public class Goto extends Command {

	String label;
	public Goto(String[] args) {
		super(args);
		this.label = args[1];
	}

	@Override
	public void Run() {
		int line = AQWBotRuntime.labels.get(this.label);
		AQWBotRuntime.ProgramCounter = line;
	}

}
