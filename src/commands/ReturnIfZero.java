package commands;

import AQWBotRuntime.AQWBotRuntime;

public class ReturnIfZero extends Command {

	String variable;

	public ReturnIfZero(String[] args) {
		super(args);
		this.variable = args[1];
	}

	@Override
	public void Run() {
		int variableValue = AQWBotRuntime.variables.get(variable);
		if (variableValue == 0) {
			int line = AQWBotRuntime.branchReturn.pop();
			AQWBotRuntime.ProgramCounter = line;
		}
	}

}
