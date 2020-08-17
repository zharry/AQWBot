package commands;

import AQWBotRuntime.AQWBotRuntime;

public class DropAccept extends Command {

	int num;

	public DropAccept(String[] args) {
		super(args);
		num = Integer.parseInt(args[1]);
	}

	@Override
	public void Run() {
		try {
			AQWBotRuntime.click(625, 100 + (num - 1) * 20);
		} catch (InterruptedException e) {
		}
	}

}
