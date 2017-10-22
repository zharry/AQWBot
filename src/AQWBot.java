import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class AQWBot {

	public static final String VERSION = "17.10.21r1";
	public static Random random = new Random();

	public static boolean smooth;
	public static int roomNumber;

	public static boolean aqbcDebug = false;

	// Bot Variables
	static File botFile;
	public static ArrayList<String> aqbcCommands = new ArrayList<String>();
	public static String botAuthor = "", botName = "", botShortName = "", botVersion = "", aqbcVersion = "",
			location = "", recommendedClass = "", botFileString;

	public static void main(String[] args) throws Exception {
		// Check if AQBC Debug is enabled
		File debugFile = new File("debug.aqwbotconfig");
		if (debugFile.exists())
			aqbcDebug = true;

		Scanner s = new Scanner(System.in);

		System.out.println("Welcome to Harry's AQW Bot version " + VERSION);
		System.out.println("----------");

		System.out.print("Activate Smooth Mouse Movement? (y/N)");
		smooth = s.nextLine().equals("y") ? true : false;
		System.out.print("Private Instance Room ID: ");
		String roomP = s.nextLine();
		roomNumber = Integer.parseInt(roomP.equals("") ? random.nextInt(79999) + "" : roomP) + 10000;
		System.out.println("----------");

		// List out installed bots
		System.out.println("List of Available Bots: ");
		File allBots = new File("bots/");
		if (!allBots.exists()) {
			allBots.mkdir();
			System.out.println("No Bots found!");
		} else {
			File[] allBotsList = allBots.listFiles();
			for (int i = 0; i < allBotsList.length; i++)
				if (allBotsList[i].toString().endsWith(".aqwbot"))
					System.out.println(allBotsList[i].toString().replace(".aqwbot", "").replace("bots\\", "") + " ");
		}
		System.out.println("----------");

		// User chooses bot
		System.out.print("What bot would you like to use? ");
		String bot = s.nextLine();
		if (bot.endsWith(".aqwbot")) {
			bot = bot.replace(".aqwbot", "");
		}
		botFile = new File("bots/" + bot + ".aqwbot");
		botFileString = bot + ".aqwbot";
		Scanner readBotCmds = new Scanner(botFile);
		int readIndex = 1;
		while (readBotCmds.hasNext()) {
			String readLine = readBotCmds.nextLine();
			if (readLine.startsWith("@")) {
				String[] line = readLine.split(" ", 2);
				// Incorrect Argument Amount Exception
				if (line.length != 2)
					throw new AQBCInvalidArgumentAmount(readLine, readIndex, botFileString, 2, line.length);
				// Process Metadata
				if (line[0].startsWith("@author")) {
					botAuthor = line[1];
				} else if (line[0].startsWith("@name")) {
					botName = line[1];
				} else if (line[0].startsWith("@shortname")) {
					botShortName = line[1];
				} else if (line[0].startsWith("@version")) {
					botVersion = line[1];
				} else if (line[0].startsWith("@aqbcversion")) {
					aqbcVersion = line[1];
				} else if (line[0].startsWith("@location")) {
					location = line[1];
				} else if (line[0].startsWith("@recommendedclass")) {
					recommendedClass = line[1];
				}
			}
			// Process AQBC commands
			aqbcCommands.add(readLine);
			readIndex++;
		}
		readBotCmds.close();

		System.out.println("----------");
		System.out.println("Bot: " + botName + " (v" + botVersion + ") | By: " + botAuthor);
		System.out.println("Running with AQBC Language v" + aqbcVersion + " | AQWBot v" + VERSION);
		System.out.println("Starting Location: " + location);
		System.out.println("Using recommended class(es): " + recommendedClass);
		System.out.println("----------");
		System.out.print("Start the bot for " + botShortName + "? (Y) ");
		String a = s.nextLine();

		// Confirm Start
		while (!(a.equals("") || a.equals("Y"))) {
			System.out.print("Start the bot for " + bot + "? (Y) ");
			a = s.nextLine();
		}
		System.out.println("----------");

		// Wait for user to position mouse
		System.out.println("Open ONE instance of 'aq.com/play-now' on your primary display");
		System.out.println("Use display quality: Low");
		System.out.println("Make sure the entire game is within the display");

		// Calibrate Display
		boolean calDone = false, isLoggedOn = false;
		int[] xy = { -1, -1 };
		ScreenHandler screen = new ScreenHandler();
		File prevCalibrate = new File("previous_calibration.aqwbotconfig");
		if (prevCalibrate.exists()) {
			System.out.println("Previous calibration detected. Use? (Y)");
			String u = s.nextLine();
			if (u.equals("") || u.equals("Y")) {
				Scanner fileScanner = new Scanner(prevCalibrate);
				String[] xyProc = fileScanner.nextLine().split(" ");
				fileScanner.close();
				xy[0] = Integer.parseInt(xyProc[0]);
				xy[1] = Integer.parseInt(xyProc[1]);
				calDone = true;

				// Check if already logged in
				if (screen.calibrate() == null) {
					isLoggedOn = true;
				}
			}
		}
		if (!calDone) {
			System.out.println("Calibrating...");
			for (int i = 3; i > 0; i--) {
				System.out.println(i + "...");
				Thread.sleep(1000);
			}
			xy = screen.calibrate();
		}
		System.out.println("Calibration Completed!");
		s.close();
		System.out.println("----------");

		// Initialize Bot Handlers
		ActionHandler action = new ActionHandler(xy[0], xy[1]);
		System.out.println("Bot Initiated!");
		System.out.println("User Status: " + (isLoggedOn ? "Logged In" : "Logged Off"));
		System.out.println("AQW Flash Player Begins at : " + xy[0] + ", " + xy[1] + " Size (706x405)");
		System.out.println("Smooth Mouse movement: " + (smooth ? "Enabled" : "Disabled"));
		System.out.println("Bot is running for: " + bot);
		System.out.println("With Private Room Instance Number: " + roomNumber);
		System.out.println("----------");
		Thread.sleep(1000);

		// Login to Game
		if (!isLoggedOn) {
			System.out.println("Login");
			action.moveMouse(319, 232, 74, 23, true, smooth);
			Thread.sleep(5000);
			System.out.println("Joing 'Galanoth' Server");
			action.moveMouse(361, 162, 194, 15, true, smooth);
			Thread.sleep(10000);
		}

		// AQBC Variable Table
		HashMap<String, String> varTable = new HashMap<String, String>();

		// Start AQBC Interpreter
		int cI = 1; // Command Index
		int startIndex = -1;
		for (; cI <= aqbcCommands.size(); cI++) {
			String cmdProc = aqbcCommands.get(cI - 1);

			// DO and IF Loop
			for (int c = 0; c < 1; c++) {

				// Ignore Newlines and @metedata commands
				if (!(cmdProc.equals("") || cmdProc.startsWith("@"))) {

					// Test for Single Argument Commands
					switch (cmdProc) {

					// START
					case "start":
						startIndex = cI;
						break;
					// END START

					// RESTART
					case "restart":
						cI = startIndex;
						break;
					// END RESTART

					// QUESTACTION
					case "questaction":
						action.questAction();
						break;
					// END QUESTACTION

					// CHECKQUESTS
					case "checkquests":
						action.checkQuests();
						break;
					// END CHECKQUESTS

					// CLOSECHECKQUESTS
					case "closecheckquests":
						action.closeCheckQuests();
						break;
					// END CLOSECHECKQUESTS

					// All other 2+ Argument Commands
					default: {

						// Process Commands
						String[] cmdProcS = cmdProc.split(" ", 2);
						String curCmd = cmdProcS[0];
						if (cmdProcS.length <= 1)
							throw new AQBCInvalidArgumentAmount(curCmd, cI, botFileString, 2, cmdProcS.length);
						String curArgs = cmdProcS[1];

						boolean not = false;
						// Conditionals and Loops
						switch (curCmd) {

						// IFNOT
						case "ifnot":
							not = true;
							// END IFNOT

							// IF
						case "if":
							String[] ifProc = curArgs.split(" : ", 2);
							String condition, execCmd;
							try {
								condition = ifProc[0];
								execCmd = ifProc[1];
							} catch (Exception e) {
								throw new AQBCInvalidArgumentAmount(curCmd, cI, botFileString, 2, cmdProcS.length);
							}
							// Conditional Dictionary
							switch (condition) {
							case "anyquestcomplete":
								if (screen.anyQuestComplete()) {
									c--;
									cmdProc = execCmd;
								} else {
									if (not) {
										c--;
										cmdProc = execCmd;
									}
								}
								break;
							default:
								// Comparison Conditionals
								if (condition.startsWith("check-")) {
									try {
										String[] condProc = condition.split("-")[1].split("=");
										String var = condProc[0];
										String val = condProc[1];
										if (!varTable.containsKey(var))
											throw new AQBCUndefinedVariable(var, cI, botFileString);
										if (val.equals(varTable.get(var))) {
											c--;
											cmdProc = execCmd;
										}
									} catch (AQBCUndefinedVariable e) {
										throw e;
									} catch (Exception e) {
										throw new AQBCInvalidArgumentValue("if check-", cI, botFileString,
												"VARIABLE=VALUE");
									}
									break;
								}
								throw new AQBCInvalidCondition(condition, cI, botFileString);
							}
							break;
						// END IF

						// DO
						case "do":
							String[] doProc = curArgs.split(" : ", 2);
							int repeatVal;
							String execDCmd;
							try {
								repeatVal = Integer.parseInt(doProc[0]);
								if (repeatVal < 0)
									throw new Exception();
							} catch (Exception e) {
								throw new AQBCInvalidArgumentValue(curCmd, cI, botFileString,
										"(int [>= 0]) : (command)");
							}
							try {
								execDCmd = doProc[1];
							} catch (Exception e) {
								throw new AQBCInvalidArgumentAmount(curCmd, cI, botFileString, 2, cmdProcS.length);
							}
							c -= repeatVal;
							cmdProc = execDCmd;
							break;
						// END DO

						// All other Commands
						default:
							switch (curCmd) {
							// SET
							case "set":
								try {
									String[] agProc = curArgs.split(" ");
									String varName = agProc[0];
									String varVal = agProc[1];
									varTable.put(varName, varVal);
								} catch (Exception e) {
									throw new AQBCInvalidArgumentValue(curCmd, cI, botFileString, "string, string");
								}
								break;
							// END SET

							// REM
							case "rem":
								System.out.println(curArgs);
								break;
							// END REM

							// SLEEP
							case "sleep":
								try {
									Thread.sleep(Integer.parseInt(curArgs));
								} catch (Exception e) {
									throw new AQBCInvalidArgumentValue(curCmd, cI, botFileString, "int [>= 1]");
								}
								break;
							// END SLEEP

							// JOINROOM
							case "joinroom":
								try {
									String[] agProc = curArgs.split(" ");
									String room = agProc[0];
									boolean privateInst = Boolean.parseBoolean(agProc[1]);
									action.joinRoom(room, privateInst);
								} catch (Exception e) {
									throw new AQBCInvalidArgumentValue(curCmd, cI, botFileString, "string, boolean");
								}
								break;
							// END JOINROOM

							// MOVEMOUSE
							case "movemouse":
								try {
									String[] agProc = curArgs.split(" ");
									int x = Integer.parseInt(agProc[0]), y = Integer.parseInt(agProc[1]),
											rx = Integer.parseInt(agProc[2]), ry = Integer.parseInt(agProc[3]);
									boolean click = Boolean.parseBoolean(agProc[4]);
									if (x < 1 || x > 705 || rx < 1 || rx > 404 || y < 1 || y > 705 || ry < 1
											|| ry > 404)
										throw new Exception();
									action.moveMouse(x, y, rx, ry, click, smooth);
								} catch (Exception e) {
									throw new AQBCInvalidArgumentValue(curCmd, cI, botFileString,
											"int [>= 1][<=705], int [>= 1][<=404], int [>= 1][<=705], int [>= 1][<=404], boolean");
								}
								break;
							// END MOVEMOUSE

							// CLICKQUEST
							case "clickquest":
								try {
									String[] agProc = curArgs.split(" ");
									int qID = Integer.parseInt(agProc[0]);
									if (qID < 1)
										throw new Exception();
									action.clickQuest(qID);
								} catch (Exception e) {
									throw new AQBCInvalidArgumentValue(curCmd, cI, botFileString, "int [>= 1]");
								}
								break;
							// END CLICKQUEST

							// USESKILL
							case "useskill":
								try {
									String[] agProc = curArgs.split(" ");
									int skillID = Integer.parseInt(agProc[0]), skillCD = Integer.parseInt(agProc[1]);
									if (skillID < 1 || skillID > 6)
										throw new Exception();
									action.useSkill(skillID, skillCD);
								} catch (Exception e) {
									throw new AQBCInvalidArgumentValue(curCmd, cI, botFileString,
											"int [>= 1][<= 6], int [>= 1]");
								}
								break;
							// END USESKILL

							// SYNTAX ERROR
							default:
								throw new AQBCSyntaxError(curCmd, cI, botFileString);
							}
						}
					}
					}
				}
			}

		}

	}

}

class AQBCException extends Exception {
	private static final long serialVersionUID = -6211041561167007967L;

	public AQBCException(String error, int line, String file) {
		super("General Syntax Error on line " + line + " of " + file + ". " + error);
	}
}

class AQBCSyntaxError extends AQBCException {
	private static final long serialVersionUID = 833078322789794402L;

	public AQBCSyntaxError(String cmd, int line, String file) {
		super("Unexpected " + cmd + ", " + cmd + " is not a command.", line, file);
	}
}

class AQBCUndefinedVariable extends AQBCException {
	private static final long serialVersionUID = 5020688399999802898L;

	public AQBCUndefinedVariable(String var, int line, String file) {
		super("Variable " + var + " is not defined.", line, file);
	}
}

class AQBCInvalidArgumentAmount extends AQBCException {
	private static final long serialVersionUID = 5020688399999802898L;

	public AQBCInvalidArgumentAmount(String cmd, int line, String file, int requiredArgs, int haveArgs) {
		super("Command " + cmd + " requires exactly " + requiredArgs + " arguments, saw " + haveArgs, line, file);
	}
}

class AQBCInvalidArgumentValue extends AQBCException {
	private static final long serialVersionUID = 5078362393893680007L;

	public AQBCInvalidArgumentValue(String cmd, int line, String file, String requiredArgs) {
		super("Command " + cmd + " requires arguments <" + requiredArgs + ">.", line, file);
	}
}

class AQBCInvalidCondition extends AQBCException {

	private static final long serialVersionUID = -5341391770696846220L;

	public AQBCInvalidCondition(String cond, int line, String file) {
		super("Conditional " + cond + " is not a valid condition", line, file);
	}
}