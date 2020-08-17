package AQWBotRuntime;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

import com.sun.glass.events.KeyEvent;

import commands.Command;

public class AQWBotRuntime {

	public static Robot robot;
	public static Random random;

	public static ScreenHandler screenHandler;

	public static File[] allBotsList;
	public static File botFile;
	
	public static boolean debug;

	// "Compile-time" generated
	public static ArrayList<String[]> commands = new ArrayList<String[]>();
	public static ArrayList<Command> parsedCommands = new ArrayList<Command>();
	public static HashMap<String, Integer> labels = new HashMap<String, Integer>();

	// "Runtime" used
	public static Stack<Integer> branchReturn = new Stack<Integer>();
	public static HashMap<String, String> config = new HashMap<String, String>();
	public static HashMap<String, Integer> variables = new HashMap<String, Integer>();
	public static Integer ProgramCounter = 0;

	public static void pressKey(int k) throws InterruptedException {
		robot.keyPress(k);
		Thread.sleep(10);
		robot.keyRelease(k);
		Thread.sleep(10);
	}

	public static void typeString(String s) throws InterruptedException {
		click(90, 540);

		// Clear Any old text
		robot.keyPress(KeyEvent.VK_CONTROL);
		Thread.sleep(10);
		robot.keyPress(KeyEvent.VK_A);
		Thread.sleep(10);
		robot.keyRelease(KeyEvent.VK_A);
		Thread.sleep(10);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(10);
		pressKey(KeyEvent.VK_DELETE);

		// Type New String
		char[] chars = s.toUpperCase().toCharArray();
		for (int i = 0; i < chars.length; i++) {
			pressKey((int) chars[i]);
		}
		pressKey(KeyEvent.VK_ENTER);
	}

	public static void click(int x, int y) throws InterruptedException {
		AQWBotRuntime.robot.mouseMove(x, y);
		AQWBotRuntime.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		Thread.sleep(20);
		AQWBotRuntime.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		Thread.sleep(20);
	}
}
