package commands;

import AQWBotRuntime.AQWBotRuntime;

public class Branch extends Command {

	String label;
	public Branch(String[] args) {
		super(args);
		this.label = args[1];
	}

	@Override
	public void Run() {
		AQWBotRuntime.branchReturn.push(AQWBotRuntime.ProgramCounter);
		
		int line = AQWBotRuntime.labels.get(this.label);
		AQWBotRuntime.ProgramCounter = line;
	}

}
