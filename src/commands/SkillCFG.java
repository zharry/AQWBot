package commands;

import AQWBotRuntime.AQWBotRuntime;

public class SkillCFG extends Command {

	String option;
	String value;

	public SkillCFG(String[] args) {
		super(args);
		this.option = args[1];
		this.value = args[2];
	}

	@Override
	public void Run() {
		AQWBotRuntime.config.put("SKILLCFG." + this.option, this.value);
	}

}
