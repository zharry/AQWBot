package commands;

import AQWBotRuntime.AQWBotRuntime;

public class QuestAction extends Command {

	public QuestAction(String[] args) {
		super(args);
	}

	@Override
	public void Run() {
		try {
			AQWBotRuntime.click(160, 460);
		} catch (InterruptedException e) {
		}
	}

}
