package commands;

import AQWBotRuntime.AQWBotRuntime;

public class Skill extends Command {

	int skillChar;
	int delay;

	public Skill(String[] args) {
		super(args);
		this.skillChar = args[1].charAt(0);
		this.delay = Integer.parseInt(args[2]);
	}

	@Override
	public void Run() {
		try {
			AQWBotRuntime.pressKey(this.skillChar);
			int rand = 0;
			if (AQWBotRuntime.config.get("SKILLCFG.RANDINTERVAL") != null) {
				rand = Integer.parseInt(AQWBotRuntime.config.get("SKILLCFG.RANDINTERVAL"));
			}
			Thread.sleep(AQWBotRuntime.random.nextInt(rand) + delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
