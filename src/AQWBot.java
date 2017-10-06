import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class AQWBot {

	public static final int magicType = -472;
	public static final String version = "1.07.19r1";

	public static Random random = new Random();

	/**
	 * Presses keyboard key
	 * 
	 * @param robot
	 *            Java Robot
	 * @param k
	 *            Keycode for key to press
	 * @throws InterruptedException
	 */
	public static void pressKey(Robot robot, int k) throws InterruptedException {
		robot.keyPress(k);
		Thread.sleep(random.nextInt(20) + 20);
		robot.keyRelease(k);
		Thread.sleep(random.nextInt(20) + 20);
	}

	/**
	 * Presses mouse button
	 * 
	 * @param robot
	 *            Java Robot
	 * @param b
	 *            Buttoncode for mouse press
	 * @throws InterruptedException
	 */
	public static void pressMouse(Robot robot, int b) throws InterruptedException {
		robot.mousePress(b);
		Thread.sleep(random.nextInt(10) + 10);
		robot.mouseRelease(b);
		Thread.sleep(random.nextInt(10) + 10);
	}

	/**
	 * @return A random number between -size/2 and size/2
	 * @param size
	 *            Total range
	 */
	public static int randLoc(int size) {
		int loc = random.nextInt((int) size / 2);
		boolean neg = random.nextBoolean();
		if (neg)
			loc = 0 - loc;
		return loc;
	}

	/**
	 * @return A random number between 0 and size
	 * @param size
	 *            Total range
	 * @param ver
	 *            (True) Whether the range includes negatives or not
	 */
	public static int randLoc(int size, boolean ver) {
		if (!ver) {
			return randLoc(size);
		}
		return random.nextInt(size);
	}

	/**
	 * Types a string
	 * 
	 * @param robot
	 *            Java Robot
	 * @param s
	 *            String to be typed
	 * @throws InterruptedException
	 */
	public static void typeString(Robot robot, String s) throws InterruptedException {
		char[] chars = s.toUpperCase().toCharArray();
		for (int i = 0; i < chars.length; i++) {
			pressKey(robot, (int) chars[i]);
		}
		Thread.sleep(random.nextInt(125) + 125);
	}

	/**
	 * Rests Player
	 * 
	 * @param robot
	 *            Java Robot
	 * @throws InterruptedException
	 */
	public static void rest(Robot robot) throws InterruptedException {
		System.out.println("Rest");
		pressKey(robot, KeyEvent.VK_ENTER);
		typeString(robot, "/rest");
		pressKey(robot, KeyEvent.VK_ENTER);
		Thread.sleep(random.nextInt(250) + 250);
	}

	/**
	 * @deprecated As of AQW Bot Alhpa v0.9.01r4, use @link
	 *             {@link #clickMouse()}
	 * @param robot
	 *            Java Robot
	 * @param x2
	 *            Final x coordinate
	 * @param y2
	 *            Final y coordinate
	 * @param t
	 *            Total time used to execute the mouse movement
	 * @param n
	 *            Total steps per movement iteration
	 * @param click
	 *            Whether a click should proceed the mouse movement
	 * @throws Exception
	 */
	public static void moveMouse(Robot robot, int x2, int y2, int t, int n, boolean click) throws Exception {
		Point init = MouseInfo.getPointerInfo().getLocation();
		int x1 = init.x, y1 = init.y;
		double dx = (x2 - x1) / ((double) n);
		double dy = (y2 - y1) / ((double) n);
		double dt = t / ((double) n);
		for (int step = 1; step <= n; step++) {
			Thread.sleep((int) dt);
			robot.mouseMove((int) (x1 + dx * step), (int) (y1 + dy * step));
		}
		if (click) {
			Thread.sleep(random.nextInt(250) + 250);
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		}
		Thread.sleep(random.nextInt(250) + 250);
	}

	/**
	 * Clicks mouse within a certain range
	 * 
	 * @param robot
	 *            Java Robot
	 * @param x
	 *            Leftmost x coordinate
	 * @param y
	 *            Topmost y coordinate
	 * @param rx
	 *            X Range
	 * @param ry
	 *            Y Range
	 * @param ox
	 *            Original X, Location of Show Players
	 * @param oy
	 *            Original Y, Location of Show Players
	 * @param cx
	 *            Staring X, Mouse Position at Runtime
	 * @param cy
	 *            Staring Y, Mouse Position at Runtime
	 * @throws Exception
	 */
	public static void clickMouse(Robot robot, int x, int y, int rx, int ry, int ox, int oy, int cx, int cy)
			throws Exception {
		int x2 = x - ox + randLoc(rx, true) + cx;
		int y2 = y - oy + randLoc(ry, true) + cy;
		Point init = MouseInfo.getPointerInfo().getLocation();
		int x1 = init.x, y1 = init.y;
		int t = 1;
		int n = random.nextInt(400) + 600;
		double dx = (x2 - x1) / ((double) n);
		double dy = (y2 - y1) / ((double) n);
		double dt = t / ((double) n);
		for (int step = 1; step <= n; step++) {
			Thread.sleep((int) dt);
			robot.mouseMove((int) (x1 + dx * step), (int) (y1 + dy * step));
		}
		Thread.sleep(random.nextInt(250) + 250);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		Thread.sleep(random.nextInt(250) + 250);
	}

	/**
	 * Works the same was {@link #clickMouse()}, execpt the quest action is
	 * clicked as well
	 * 
	 * @param robot
	 *            Java Robot
	 * @param x
	 *            Leftmost x coordinate
	 * @param y
	 *            Topmost y coordinate
	 * @param rx
	 *            X Range
	 * @param ry
	 *            Y Range
	 * @param ox
	 *            Original X, Location of Show Players
	 * @param oy
	 *            Original Y, Location of Show Players
	 * @param cx
	 *            Staring X, Mouse Position at Runtime
	 * @param cy
	 *            Staring Y, Mouse Position at Runtime
	 * @throws Exception
	 */
	public static void clickQuest(Robot robot, int x, int y, int rx, int ry, int ox, int oy, int cx, int cy)
			throws Exception {
		for (int i = 0; i < 2; i++) {
			clickMouse(robot, x, y, rx, ry, ox, oy, cx, cy);
			System.out.println("Click Quest");
			Thread.sleep(random.nextInt(250) + 1500);

			moveMouse(robot, -589 + cx + randLoc(17), -32 + cy + randLoc(13), 1, random.nextInt(400) + 600, true);
			System.out.println("Quest Action");
			Thread.sleep(random.nextInt(250) + 1500);
		}
	}

	public static void main(String[] args) throws Exception {

		System.out.println("Welcome to Harry's AQW Bot version " + version);

		HashMap<String, String> BOTS = new HashMap<>();
		BOTS.put("XP",
				"Location: Anywhere\nClass: Any Class\nMouse Location: None\nWindow Location (1): Any\nWindow Location (2): None\nGame Size: Any");
		BOTS.put("Etherstorm",
				"Location: Etherwarevil\nClass: Abyssal Angel\nMouse Location: Rest Button\nWindow Location (1): Any\nWindow Location (2): None\nGame Size: Any");
		BOTS.put("Embersea",
				"Location: Fireforge\nClass: Shaman\nMouse Location: Start at \"Show players in room\" button, Auto Adjusting\nWindow Location (1): Any\nWindow Location (2): Any\nGame Size: Tiny");
		BOTS.put("SpiritOrbs",
				"Location: Battleunberb\nClass: Abyssal Angel\nMouse Location: Start at \"Show players in room\" button, Auto Adjusting\nWindow Location (1): None\nWindow Location (2): Any\nGame Size: Tiny");
		BOTS.put("Chronospan",
				"Location: Portalwar\nClass: Necromancer\nMouse Location: Start at \"Show players in room\" button, Auto Adjusting\nWindow Location (1): Any\nWindow Location (2): Any\nGame Size: Tiny");
		BOTS.put("Fishing",
				"Location: Fishing\nClass: Any\nMouse Location: Fishing Rod\nWindow Location (1): Any\nWindow Location (2): None\nGame Size: Any");
		BOTS.put("Mythsong",
				"Location: Beehive\nClass: Blaze Binder\nMouse Location: Start at \"Show players in room\" button, Auto Adjusting\nWindow Location (1): Any\nWindow Location (2): None\nGame Size: Tiny");
		BOTS.put("Brightoak",
				"Location: Elfhame\nClass: Shaman or Necromancer\nMouse Location: Start at \"Show players in room\" button, Auto Adjusting\nWindow Location (1): Any\nWindow Location (2): None\nGame Size: Tiny");
		BOTS.put("Legion",
				"Location: Dreadrock\nClass: Shaman\nMouse Location: Start at \"Show players in room\" button, Auto Adjusting\nWindow Location (1): Any\nWindow Location (2): None\nGame Size: Tiny");
		BOTS.put("Dreadnaught",
				"Location: Laken (Room 2)\nClass: Shaman\nMouse Location: Start at \"Show players in room\" button, Auto Adjusting, Use fast spawn and quest glitch\nWindow Location (1): Any\nWindow Location (2): None\nGame Size: Tiny");
		BOTS.put("Evil",
				"Location: Poisonforest (Room 10 or 11)\nClass: Blaze Binder\nMouse Location: Start at \"Show players in room\" button, Auto Adjusting\nWindow Location (1): Any\nWindow Location (2): None\nGame Size: Tiny");
		BOTS.put("XPF",
				"Location: Battlegroundf \nClass: Shaman with Mana Vamp\nMouse Location: Start at \"Skill 3\" button\nWindow Location (1): Any\nWindow Location (2): None\nGame Size: Any");

		//System.out.println("The following bots are avaiable: ");
		for (int i = 0; i < BOTS.size(); i++) {
			//System.out.println();
		}

		// User chooses bot
		Scanner s = new Scanner(System.in);
		System.out.print("What bot would you like to use? ");
		String[] botP = s.nextLine().split(" ");
		String bot = botP[0];
		int type = botP.length <= 1 ? magicType : Integer.parseInt(botP[1]);
		System.out.println(BOTS.get(bot));
		System.out.print("Start the bot for " + bot + "? (Y) ");
		String a = s.nextLine();

		while (!a.equals("Y")) {
			System.out.print("Start the bot for " + bot + "? (Y) ");
			a = s.nextLine();
		}
		s.close();

		// Initialize Robot Mechanics
		Robot robot = new Robot();

		// Wait for user to position mouse
		for (int i = 3; i > 0; i--) {
			System.out.println(i + "...");
			Thread.sleep(1000);
		}

		// Initialize Pointer Location
		Point init = MouseInfo.getPointerInfo().getLocation();
		int x = init.x, y = init.y;
		System.out.println("Bot Initiated!");

		while (true) {
			if (bot.equals("XP")) {
				if (type != magicType)
					xpBot(robot, type);
				else
					xpBot(robot);
			} else if (bot.equals("Etherstorm")) {
				etherstormBot(robot);
			} else if (bot.equals("Embersea")) {
				emberseaBot(robot, x, y);
			} else if (bot.equals("SpiritOrbs")) {
				spiritOrbsBot(robot, x, y);
			} else if (bot.equals("Chronospan")) {
				chronospanBot(robot, x, y, type);
			} else if (bot.equals("Fishing")) {
				fishingBot(robot);
			} else if (bot.equals("Mythsong")) {
				mythsongBot(robot, x, y);
			} else if (bot.equals("Brightoak")) {
				brightoakBot(robot, x, y);
			} else if (bot.equals("Legion")) {
				legionBot(robot, x, y);
			} else if (bot.equals("Dreadnaught")) {
				dreadnaughtBot(robot, x, y);
			} else if (bot.equals("Evil")) {
				evilBot(robot, x, y);
			} else if (bot.equals("XPF")) {
				xpfBot(robot);
			} else {
				Thread.sleep(random.nextInt(1000) + 1000);
				pressMouse(robot, InputEvent.BUTTON1_DOWN_MASK);
			}
		}
	}

	public static void xpBot(Robot robot) throws InterruptedException {
		pressKey(robot, KeyEvent.VK_3);
		System.out.println("Weaken");
		Thread.sleep(random.nextInt(1000) + 1000);
	}

	public static void xpBot(Robot robot, int type) throws InterruptedException {
		pressKey(robot, (int) (type + "").toCharArray()[0]);
		System.out.println("Skill " + type);
		Thread.sleep(random.nextInt(1000) + 1000);
	}

	public static void xpfBot(Robot robot) throws InterruptedException {
		pressKey(robot, KeyEvent.VK_3);
		Thread.sleep(random.nextInt(100) + 100);
		pressMouse(robot, InputEvent.BUTTON1_DOWN_MASK);
		Thread.sleep(random.nextInt(1000) + 1000);
	}

	public static int eSk3 = 0;
	public static int eSk6 = 0;

	public static void etherstormBot(Robot robot) throws InterruptedException {
		eSk3++;
		eSk6++;
		if (eSk3 >= 14) {
			eSk3 = 0;
			pressKey(robot, KeyEvent.VK_3);
			System.out.println("Aphotic Overdrive");
		}
		if (eSk6 >= 8) {
			eSk6 = 0;
			pressMouse(robot, InputEvent.BUTTON1_DOWN_MASK);
			System.out.println("Mouse 1");
		}
		pressKey(robot, KeyEvent.VK_1);
		System.out.println("Auto Attack");
		Thread.sleep(500);
	}

	public static void emberseaBot(Robot robot, int x, int y) throws Exception {
		moveMouse(robot, -526 + x, -236 + y, 1, 10, true);
		System.out.println("Leaving frist room");
		Thread.sleep(2000);

		moveMouse(robot, -505 + x, -220 + y, 1, 10, true);
		System.out.println("Opening dialog");
		Thread.sleep(1000);

		moveMouse(robot, -350 + x, -80 + y, 1, 10, true);
		System.out.println("Opening quests");
		Thread.sleep(1000);

		moveMouse(robot, -600 + x, -90 + y, 1, 10, true);
		System.out.println("Entering quests");
		Thread.sleep(1000);

		moveMouse(robot, -615 + x, -30 + y, 1, 10, true);
		System.out.println("Accepting quest");
		Thread.sleep(1000);

		moveMouse(robot, -635 + x, 25 + y, 1, 10, true);
		System.out.println("Focusing on chat bar");
		Thread.sleep(100);

		typeString(robot, "/goto miltonius j pool");
		Thread.sleep(100);

		moveMouse(robot, -470 + x, 25 + y, 1, 10, true);
		System.out.println("Sending command");
		Thread.sleep(3000);

		pressKey(robot, KeyEvent.VK_3);
		Thread.sleep(15000);
		System.out.println("Waiting to die");

		moveMouse(robot, -640 + x, -345 + y, 1, 10, true);
		System.out.println("Skipping cutscence");
		Thread.sleep(2000);

		moveMouse(robot, -300 + x, -50 + y, 1, 10, true);
		System.out.println("Deleting from inventory");
		Thread.sleep(100);

		moveMouse(robot, -635 + x, 25 + y, 1, 10, true);
		System.out.println("Focus on chat, changing room");
		Thread.sleep(100);

		typeString(robot, "/join fireforge-3251");
		Thread.sleep(100);

		moveMouse(robot, -470 + x, 25 + y, 1, 10, true);
		System.out.println("Re-joining room");
		Thread.sleep(4000);

	}

	public static void spiritOrbsBot(Robot robot, int x, int y) throws Exception {
		System.out.println("Attacking");
		pressKey(robot, KeyEvent.VK_3);
		Thread.sleep((random.nextInt(10000) + 2000) / 2);

		System.out.println("Generating Pickup Rate");
		if (random.nextInt(7) == 1) {
			System.out.println("Accepting #1 into inventory");
			moveMouse(robot, -388 + x + randLoc(54), -7 + y + randLoc(10), 1, 1000, true);
			Thread.sleep(1500);
			System.out.println("Accepting #2 into inventory");
			moveMouse(robot, -388 + x + randLoc(54), -76 + y + randLoc(10), 1, 1000, true);
			Thread.sleep(1500);
			System.out.println("Accepting #3 into inventory");
			moveMouse(robot, -388 + x + randLoc(54), -145 + y + randLoc(10), 1, 1000, true);
			Thread.sleep(1500);
		} else {
			System.out.print("Failed!\n");
		}

		System.out.println("Waiting for next round");
		Thread.sleep((random.nextInt(10000) + 2000) / 2);
	}

	/**
	 * @param type
	 *            magicType [Default -> *Buggy*, 11 -> No Quest reward given, 12
	 *            -> Alt Acc. and Room 3 Farming; *Buggy*]
	 */
	public static void chronospanBot(Robot robot, int x, int y, int type) throws Exception {

		moveMouse(robot, -612 + x + randLoc(7), -240 + y + randLoc(15), 1, random.nextInt(400) + 600, true);
		System.out.println("Open Dialog");
		Thread.sleep(random.nextInt(300) + 1200);

		moveMouse(robot, -289 + x + randLoc(96), -78 + y + randLoc(17), 1, random.nextInt(400) + 600, true);
		System.out.println("Open Quests");
		Thread.sleep(random.nextInt(300) + 1200);

		for (int i = 0; i <= 1; i++) {
			moveMouse(robot, -626 + x + randLoc(73), -235 + y + randLoc(5), 1, random.nextInt(400) + 600, true);
			System.out.println("Click Quest");
			Thread.sleep(random.nextInt(300) + 1200);

			moveMouse(robot, -589 + x + randLoc(17), -32 + y + randLoc(13), 1, random.nextInt(400) + 600, true);
			System.out.println("Quest Action");
			Thread.sleep(random.nextInt(300) + 1200);

			if (i == 0 && type != 11) {
				System.out.println("Accepting quest reward");
				moveMouse(robot, -388 + x + randLoc(40), -53 + y + randLoc(9), 1, random.nextInt(400) + 600, true);
				Thread.sleep(random.nextInt(250) + 2000);
			}

		}

		moveMouse(robot, -267 + x + randLoc(29), -292 + y + randLoc(67), 1, random.nextInt(400) + 600, true);
		System.out.println("Goto Room 2");
		Thread.sleep(random.nextInt(300) + 2200);

		moveMouse(robot, -248 + x + randLoc(29), -257 + y + randLoc(59), 1, random.nextInt(400) + 600, true);
		System.out.println("Goto Room 3");
		Thread.sleep(random.nextInt(300) + 2200);

		if (type == 12) {
			moveMouse(robot, -678 + x + randLoc(17), -92 + y + randLoc(9), 1, random.nextInt(400) + 600, true);
			System.out.println("Goto Room 4");
			Thread.sleep(random.nextInt(200) + 3000);
		}

		for (int i = 0; i < 10; i++) {
			pressKey(robot, KeyEvent.VK_3);
			Thread.sleep(random.nextInt(100) + 50);
			pressKey(robot, KeyEvent.VK_3);
			System.out.println("Attack");

			System.out.println("Wait for monster death");
			Thread.sleep(random.nextInt(5000) + 40000);
		}

		if (type != 12) {
			moveMouse(robot, -312 + x + randLoc(23), 2 + y + randLoc(7), 1, random.nextInt(400) + 600, true);
			System.out.println("Goto Room 2");
			Thread.sleep(random.nextInt(300) + 2200);

			moveMouse(robot, -329 + x + randLoc(25), -5 + y + randLoc(11), 1, random.nextInt(400) + 600, true);
			System.out.println("Goto Room 1");
			Thread.sleep(random.nextInt(300) + 2500);

		} else {
			moveMouse(robot, -635 + x, 25 + y, 1, random.nextInt(400) + 600, true);
			Thread.sleep(random.nextInt(100) + 50);
			typeString(robot, "/gotomiltonius j pool");
			Thread.sleep(random.nextInt(100) + 50);
			moveMouse(robot, -470 + x, 25 + y, 1, random.nextInt(400) + 600, true);
			System.out.println("Goto AltAcc.");
			Thread.sleep(random.nextInt(300) + 2200);

			moveMouse(robot, 134 + x + randLoc(75), -102 + y + randLoc(75), 1, random.nextInt(400) + 600, true);
			System.out.println("Move Alt #1");
			Thread.sleep(random.nextInt(1000) + 3500);

			moveMouse(robot, 328 + x + randLoc(69), -32 + y + randLoc(69), 1, random.nextInt(400) + 600, true);
			System.out.println("Move Alt #2");
			Thread.sleep(random.nextInt(1000) + 3500);
		}
	}

	public static void fishingBot(Robot robot) throws Exception {
		pressMouse(robot, InputEvent.BUTTON1_DOWN_MASK);
		System.out.println("Cast Fishing Rod");
		Thread.sleep(random.nextInt(5000) + 11000);
	}

	public static void mythsongBot(Robot robot, int x, int y) throws Exception {
		int origx = 2267, origy = 462;

		clickMouse(robot, 1908, 230, 6, 14, origx, origy, x, y);
		System.out.println("Open Dialog");
		Thread.sleep(random.nextInt(300) + 1200);

		clickMouse(robot, 2125, 378, 97, 11, origx, origy, x, y);
		System.out.println("Open Quests");
		Thread.sleep(random.nextInt(300) + 1200);

		for (int i = 0; i <= 1; i++) {
			clickMouse(robot, 1604, 213, 49, 4, origx, origy, x, y);
			System.out.println("Click Quest");
			Thread.sleep(random.nextInt(300) + 1200);

			moveMouse(robot, -589 + x + randLoc(17), -32 + y + randLoc(13), 1, random.nextInt(400) + 600, true);
			System.out.println("Quest Action");
			Thread.sleep(random.nextInt(300) + 1200);
		}

		clickMouse(robot, 1947, 134, 40, 70, origx, origy, x, y);
		System.out.println("Move to next room");
		System.out.println("Waiting for Respawn...");
		Thread.sleep(random.nextInt(1000) + 7000);

		for (int i = 0; i < 5; i++) {
			pressKey(robot, KeyEvent.VK_5);
			System.out.println("Attack");
			Thread.sleep(random.nextInt(100) + 100);

			System.out.println("Waiting for Respawn...");
			Thread.sleep(random.nextInt(4050) + 16100);
		}

		clickMouse(robot, 1875, 133, 112, 47, origx, origy, x, y);
		System.out.println("Move to original room");
		Thread.sleep(random.nextInt(250) + 750);
	}

	public static void brightoakBot(Robot robot, int x, int y) throws Exception {
		int origx = 2267, origy = 462;

		clickQuest(robot, 1608, 345, 91, 3, origx, origy, x, y);

		pressKey(robot, KeyEvent.VK_3);
		System.out.println("Attack");
		Thread.sleep(random.nextInt(500) + 500);
		System.out.println("Next Round started!");

	}

	public static void legionBot(Robot robot, int x, int y) throws Exception {
		int origx = 704, origy = 360;

		clickQuest(robot, 38, 109, 121, 4, origx, origy, x, y);
		Thread.sleep(random.nextInt(250) + 1500);
		clickQuest(robot, 38, 109, 121, 4, origx, origy, x, y);
		Thread.sleep(random.nextInt(250) + 1500);

		clickMouse(robot, 308, 303, 15, 7, origx, origy, x, y);
		System.out.println("Accepting quest reward");
		Thread.sleep(random.nextInt(250) + 2000);

		clickMouse(robot, 312, 18, 5, 5, origx, origy, x, y);
		System.out.println("Unselecting any selected targets");
		Thread.sleep(random.nextInt(125) + 1000);

		for (int i = 1; i <= 18; i++) {
			pressKey(robot, KeyEvent.VK_3);
			System.out.println("Attack " + i);
			Thread.sleep(random.nextInt(1000) + 2500);
		}

		System.out.println("Waiting...");
		Thread.sleep(random.nextInt(2500) + 10000);
		System.out.println("Next Round started!");
	}

	public static void evilBot(Robot robot, int x, int y) throws Exception {
		int origx = 2267, origy = 463;

		System.out.println("Click Quests");
		clickQuest(robot, 1602, 272, 134, 3, origx, origy, x, y);

		System.out.print("Attack");
		for (int i = 1; i <= 11; i++) {
			System.out.print(" " + i);
			pressKey(robot, KeyEvent.VK_5);
			if (i!=10) {
				Thread.sleep(random.nextInt(1000) + 15000);
			} else {
				Thread.sleep(random.nextInt(500) + 1500);
			}
		}
		System.out.println();

	}

	public static void dreadnaughtBot(Robot robot, int x, int y) throws Exception {
		int origx = 2267, origy = 487;

		// ------ Part 1
		rest(robot);

		System.out.println("Click Quests");
		clickQuest(robot, 1608, 237, 66, 3, origx, origy, x, y);
		clickQuest(robot, 1608, 252, 66, 3, origx, origy, x, y);

		System.out.println("Attack");
		long start = System.currentTimeMillis();
		while (true) {
			if (System.currentTimeMillis() - start >= 60000) {
				System.out.println("Done");
				break;
			}

			pressKey(robot, KeyEvent.VK_3);
			Thread.sleep(random.nextInt(100) + 100);
			pressKey(robot, KeyEvent.VK_2);
			Thread.sleep(random.nextInt(100) + 100);
			pressKey(robot, KeyEvent.VK_1);
			Thread.sleep(random.nextInt(100) + 100);
		}

		System.out.println("Move to Room 3");
		clickMouse(robot, 2258, 382, 7, 40, origx, origy, x, y);
		Thread.sleep(random.nextInt(1000) + 5000);

		// ------ Part 2
		rest(robot);

		System.out.println("Click Quests");
		clickQuest(robot, 1608, 237, 66, 3, origx, origy, x, y);
		clickQuest(robot, 1608, 252, 66, 3, origx, origy, x, y);

		System.out.println("Attack");
		start = System.currentTimeMillis();
		while (true) {
			if (System.currentTimeMillis() - start >= 60000) {
				System.out.println("Done");
				break;
			}

			pressKey(robot, KeyEvent.VK_3);
			Thread.sleep(random.nextInt(100) + 100);
			pressKey(robot, KeyEvent.VK_2);
			Thread.sleep(random.nextInt(100) + 100);
			pressKey(robot, KeyEvent.VK_1);
			Thread.sleep(random.nextInt(100) + 100);
		}

		System.out.println("Move to Room 2");
		clickMouse(robot, 1600, 419, 6, 14, origx, origy, x, y);
		Thread.sleep(random.nextInt(1000) + 5000);
	}
}