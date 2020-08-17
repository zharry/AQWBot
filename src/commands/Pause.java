package commands;

public class Pause extends Command {

	int miliseconds;
	public Pause(String[] args) {
		super(args);
		this.miliseconds = Integer.parseInt(args[1]);
	}

	@Override
	public void Run() {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
		}
	}

}
