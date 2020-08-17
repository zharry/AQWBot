package commands;

import AQWBotRuntime.AQWBotRuntime;

public class Var extends Command {

	String name;
	int value;
	public Var(String[] args) {
		super(args);
		this.name = args[1];
		this.value = Integer.parseInt(args[2]);
	}

	@Override
	public void Run() {
		AQWBotRuntime.variables.put(name, value);
	}

}
