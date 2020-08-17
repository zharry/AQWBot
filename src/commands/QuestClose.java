package commands;

import AQWBotRuntime.AQWBotRuntime;

public class QuestClose extends Command {

	public QuestClose(String[] args) {
		super(args);
	}

	@Override
	public void Run() {
		try {
			AQWBotRuntime.click(320, 126);
		} catch (InterruptedException e) {
		}
	}

}
