package commands;

import AQWBotRuntime.AQWBotRuntime;

public class Quest extends Command {

	int num;

	public Quest(String[] args) {
		super(args);
		num = Integer.parseInt(args[1]);
	}

	@Override
	public void Run() {
		try {
			AQWBotRuntime.click(60, 170 + (num - 1) * 20);
		} catch (InterruptedException e) {
		}
	}

}
