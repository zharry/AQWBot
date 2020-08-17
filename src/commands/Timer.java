package commands;

import AQWBotRuntime.AQWBotRuntime;

public class Timer extends Command {

	String name;
	String initialVariable;
	
	long startTime = -1;
	
	public Timer(String[] args) {
		super(args);
		this.name = args[1];
		this.initialVariable = args[2];
	}

	@Override
	public void Run() {
		if (startTime != -1) {
			long curTime = System.currentTimeMillis() / 1000;
			long diff = curTime - startTime;
			
			int variableValue = AQWBotRuntime.variables.get(name);
			if (diff > variableValue) {
				AQWBotRuntime.variables.put(name, 0);
				startTime = -1;
				return;
			}
		}
		
		if (AQWBotRuntime.variables.get(name) == null || AQWBotRuntime.variables.get(name) == 0) {
			// Set start timer
			startTime = System.currentTimeMillis() / 1000;
			
			// Add variable
			int initialValue = AQWBotRuntime.variables.get(initialVariable);
			AQWBotRuntime.variables.put(name, initialValue);
		}
	}

}
