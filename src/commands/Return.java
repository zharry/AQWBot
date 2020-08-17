package commands;

import AQWBotRuntime.AQWBotRuntime;

public class Return extends Command {

	public Return(String[] args) {
		super(args);
	}

	@Override
	public void Run() {
		int line = AQWBotRuntime.branchReturn.pop();
		AQWBotRuntime.ProgramCounter = line;
	}

}
