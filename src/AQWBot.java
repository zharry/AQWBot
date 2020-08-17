import java.awt.Robot;
import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.util.StringJoiner;

import AQWBotRuntime.AQWBotRuntime;
import AQWBotRuntime.ScreenHandler;
import commands.Branch;
import commands.Click;
import commands.Command;
import commands.DropAccept;
import commands.GameCFG;
import commands.Goto;
import commands.Join;
import commands.NOP;
import commands.Pause;
import commands.Quest;
import commands.QuestAction;
import commands.QuestClose;
import commands.Return;
import commands.ReturnIfZero;
import commands.Skill;
import commands.SkillCFG;
import commands.Timer;
import commands.Var;

public class AQWBot {

	// PROJECT CONSTANTS
	public static final String VERSION = "20.07.21r1";

	public static void main(String[] args) throws Exception {

		System.out.println("Initializing...");

		AQWBotRuntime.robot = new Robot();
		AQWBotRuntime.random = new Random();
		AQWBotRuntime.screenHandler = new ScreenHandler();

		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		System.out.print("Debug On? [0/1] ");
		AQWBotRuntime.debug = s.nextInt() == 1;

		// Introduction
		System.out.println("Welcome to Harry's AQW Bot version " + VERSION);
		System.out.println("Requires AQLite!");
		System.out.println("Notes:");
		System.out.println(" - Make sure the client window is at (0,0) with default sizing");
		System.out.println(
				" - Use SBP's Custom Drops UI, Inverted Menu, and place the 'Item Drops' text directly underneath the 'Music On/Off' text in /maul");
		System.out.println(" - Use item drop blacklists corresponding to the farmed item");
		System.out.println(" - Quest re-accepting must be enabled");
		System.out.println("----------");

		// Find Bots
		System.out.println("List of Available Bots: ");
		File allBots = new File("bots/");
		if (!allBots.exists()) {
			allBots.mkdir();
			System.out.println("No Bots found!");
		} else {
			AQWBotRuntime.allBotsList = allBots.listFiles();
			for (int i = 0; i < AQWBotRuntime.allBotsList.length; i++)
				if (AQWBotRuntime.allBotsList[i].toString().endsWith(".aqwbot"))
					System.out.println((i + 1) + ". "
							+ AQWBotRuntime.allBotsList[i].toString().replace(".aqwbot", "").replace("bots\\", "")
							+ " ");
		}

		// Get Bot
		System.out.print("What bot would you like to use? ");
		int num = s.nextInt() - 1;
		AQWBotRuntime.botFile = AQWBotRuntime.allBotsList[num];
		System.out.println("----------");

		// Read Bot
		Scanner readBotCommands = new Scanner(AQWBotRuntime.botFile);
		while (readBotCommands.hasNext()) {
			String[] line = readBotCommands.nextLine().split(" ");
			AQWBotRuntime.commands.add(line);
		}
		readBotCommands.close();

		// Parse Bot
		for (int i = 0; i < AQWBotRuntime.commands.size(); i++) {
			String[] commandArgs = AQWBotRuntime.commands.get(i);
			String command = commandArgs[0];
			String cArgs = joinString(commandArgs, " ", 1);

			if (command.startsWith("#")) {
				AQWBotRuntime.parsedCommands.add(new NOP(commandArgs));

				continue;
			}
			if (command.endsWith(":")) {
				AQWBotRuntime.parsedCommands.add(new NOP(commandArgs));

				command = command.replace(":", "");
				AQWBotRuntime.labels.put(command, i);
				continue;
			}
			if (command.startsWith("@")) {
				AQWBotRuntime.parsedCommands.add(new NOP(commandArgs));

				command = command.replace("@", "");
				switch (command) {
				case "author":
					System.out.print("Author:");
					break;
				case "name":
					System.out.print("Bot Name:");
					break;
				case "shortname":
					continue;
				case "version":
					System.out.print("Bot Version:");
					break;
				case "aqbcversion":
					System.out.print("AQBC Version:");
					break;
				case "location":
					System.out.print("Location:");
					break;
				case "recommendedclass":
					System.out.print("Recommended Class:");
					break;
				}
				System.out.println(" " + cArgs);
				continue;
			}

			// Create commands
			switch (command.toLowerCase().trim()) {
			case "var":
				AQWBotRuntime.parsedCommands.add(new Var(commandArgs));
				break;
			case "timer":
				AQWBotRuntime.parsedCommands.add(new Timer(commandArgs));
				break;

			case "skillcfg":
				AQWBotRuntime.parsedCommands.add(new SkillCFG(commandArgs));
				break;
			case "gamecfg":
				AQWBotRuntime.parsedCommands.add(new GameCFG(commandArgs));
				break;

			case "pause":
				AQWBotRuntime.parsedCommands.add(new Pause(commandArgs));
				break;
			case "goto":
				AQWBotRuntime.parsedCommands.add(new Goto(commandArgs));
				break;
			case "branch":
				AQWBotRuntime.parsedCommands.add(new Branch(commandArgs));
				break;
			case "return":
				AQWBotRuntime.parsedCommands.add(new Return(commandArgs));
				break;
			case "returnifzero":
				AQWBotRuntime.parsedCommands.add(new ReturnIfZero(commandArgs));
				break;

			case "skill":
				AQWBotRuntime.parsedCommands.add(new Skill(commandArgs));
				break;
			case "click":
				AQWBotRuntime.parsedCommands.add(new Click(commandArgs));
				break;

			case "dropaccept":
				AQWBotRuntime.parsedCommands.add(new DropAccept(commandArgs));
				break;
			case "quest":
				AQWBotRuntime.parsedCommands.add(new Quest(commandArgs));
				break;
			case "questaction":
				AQWBotRuntime.parsedCommands.add(new QuestAction(commandArgs));
				break;
			case "questclose":
				AQWBotRuntime.parsedCommands.add(new QuestClose(commandArgs));
				break;

			case "join":
				AQWBotRuntime.parsedCommands.add(new Join(commandArgs));
				break;

			default:
				AQWBotRuntime.parsedCommands.add(new NOP(commandArgs));
			}
		}
		System.out.println("----------");
		s.nextLine();

		// Run bot
		for (int i = 3; i > 0; i--) {
			System.out.println(i + "...");
			Thread.sleep(1000);
		}
		while (AQWBotRuntime.ProgramCounter <= AQWBotRuntime.commands.size()) {
			
			if (AQWBotRuntime.debug) {
				System.out.printf("[%3d] %s", AQWBotRuntime.ProgramCounter,
						joinString(AQWBotRuntime.commands.get(AQWBotRuntime.ProgramCounter), " ", 0));
				s.nextLine();
			}

			Command command = AQWBotRuntime.parsedCommands.get(AQWBotRuntime.ProgramCounter);
			command.Run();

			AQWBotRuntime.ProgramCounter++;
		}

	}

	/* Helper Functions */
	public static String joinString(String[] toJoin, String delimiter, int start) {
		StringJoiner joiner = new StringJoiner(delimiter);
		for (int i = start; i < toJoin.length; i++) {
			joiner.add(toJoin[i]);
		}
		String str = joiner.toString();
		return str;
	}

}
