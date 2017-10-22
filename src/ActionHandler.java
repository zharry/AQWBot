import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Random;

import com.sun.glass.events.KeyEvent;

public class ActionHandler {

	private Random random;
	private Robot robot;

	private int cx, cy;
	private int ox, oy; // Location of Show Players at small, cropped to size

	public ActionHandler(int x, int y) throws AWTException {
		random = new Random();
		robot = new Robot();

		cx = x + 700;
		cy = y + 354;

		ox = 700; // Max Size: 706
		oy = 354; // Max Size: 405
	}

	public int randLoc(int width) {
		return random.nextInt(width);
	}

	public void pressKey(int k) throws InterruptedException {
		robot.keyPress(k);
		Thread.sleep(random.nextInt(20) + 20);
		robot.keyRelease(k);
		Thread.sleep(random.nextInt(125) + 125);
	}

	public void pressMouse(int b) throws InterruptedException {
		robot.mousePress(b);
		Thread.sleep(random.nextInt(10) + 10);
		robot.mouseRelease(b);
		Thread.sleep(random.nextInt(125) + 125);
	}

	public void useSkill(int skillID, int delayTime) throws InterruptedException {
		AQWBot.consoleLog("Skill " + skillID, 0);
		pressKey((skillID + "").charAt(0));
		Thread.sleep(random.nextInt(100) + 100 + delayTime);
	}

	public void typeString(String s) throws Exception {
		AQWBot.consoleLog("Typing and entering '" + s + "'", 0);
		// Move to text area
		moveMouse(62, 378, 10, 10, true, AQWBot.smooth);

		// Clear Any old text
		robot.keyPress(KeyEvent.VK_CONTROL);
		Thread.sleep(random.nextInt(20) + 20);
		robot.keyPress(KeyEvent.VK_A);
		Thread.sleep(random.nextInt(20) + 20);
		robot.keyRelease(KeyEvent.VK_A);
		Thread.sleep(random.nextInt(20) + 20);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		pressKey(KeyEvent.VK_DELETE);

		// Type New String
		char[] chars = s.toUpperCase().toCharArray();
		for (int i = 0; i < chars.length; i++) {
			pressKey((int) chars[i]);
		}
		pressKey(KeyEvent.VK_ENTER);
		Thread.sleep(random.nextInt(125) + 125);
	}

	public void moveMouse(int x, int y, int rx, int ry, boolean click, boolean smooth) throws Exception {
		String out = "Moving and... ";
		int x2 = x - ox + randLoc(rx) + cx;
		int y2 = y - oy + randLoc(ry) + cy;
		if (!smooth) {
			robot.mouseMove(x2, y2);
		} else {
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
		}
		Thread.sleep(random.nextInt(250) + 250);
		if (click) {
			out += "Clicking Mouse";
			pressMouse(InputEvent.BUTTON1_DOWN_MASK);
		}
		AQWBot.consoleLog(out, 0);
		Thread.sleep(random.nextInt(125) + 125);
	}

	public void checkQuests() throws Exception {
		AQWBot.consoleLog("Opening Quest Dialog", 0);
		moveMouse(526, 375, 16, 16, true, AQWBot.smooth);
		moveMouse(512, 332, 28, 8, true, AQWBot.smooth);
		Thread.sleep(random.nextInt(250) + 250);
	}

	public void closeCheckQuests() throws Exception {
		AQWBot.consoleLog("Closing Quest Dialog", 0);
		moveMouse(235, 68, 8, 8, true, AQWBot.smooth);
		Thread.sleep(random.nextInt(250) + 250);
	}

	public void clickQuest(int place) throws Exception {
		AQWBot.consoleLog("Click Quest " + place, 0);
		int r = 4, x = 35, y = (int) Math.round(103 + ((place - 1) * 14.75));
		moveMouse(x, y, r, r, true, AQWBot.smooth);
		Thread.sleep(random.nextInt(500) + 2000);
	}

	public void questAction() throws Exception {
		AQWBot.consoleLog("Click Quest Action", 0);
		moveMouse(104, 317, 12, 12, true, AQWBot.smooth);
		Thread.sleep(random.nextInt(500) + 2000);
	}

	public void rest() throws Exception {
		AQWBot.consoleLog("Resting", 0);
		typeString("/rest");
		Thread.sleep(random.nextInt(125) + 125);
	}

	public void joinRoom(String room, boolean p) throws Exception {
		AQWBot.consoleLog("Joining /" + room, 0);
		typeString("/join " + room + (p ? "-" + AQWBot.roomNumber : ""));
		Thread.sleep(random.nextInt(125) + 125);
	}

	public void moveToTarget() throws InterruptedException {
		AQWBot.consoleLog("Moving to Target (Double Tap 1)", 0);
		pressKey(KeyEvent.VK_1);
		Thread.sleep(random.nextInt(100) + 100);
		pressKey(KeyEvent.VK_1);
		Thread.sleep(random.nextInt(125) + 125);
	}

}
