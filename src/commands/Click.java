package commands;

import AQWBotRuntime.AQWBotRuntime;

public class Click extends Command {

	int x;
	int y;

	public Click(String[] args) {
		super(args);
		this.x = Integer.parseInt(args[1]);
		this.y = Integer.parseInt(args[2]);
	}

	@Override
	public void Run() {
		try {
			AQWBotRuntime.click(x, y);
		} catch (InterruptedException e) {
		}
	}

}
