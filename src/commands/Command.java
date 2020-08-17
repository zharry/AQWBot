package commands;

public abstract class Command {
	
	String[] args;
	
	public Command(String[] args) {
		this.args = args;
	}
	
	public abstract void Run();
	
}
