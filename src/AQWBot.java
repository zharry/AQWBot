import java.awt.MouseInfo;
import java.awt.Point;
import java.util.Random;
import java.util.Scanner;

public class AQWBot {

	public static final String version = "17.10.07a1";
	public static Random random = new Random();

	public static boolean smooth;
	public static int roomNumber;

	public static void main(String[] args) throws Exception {
		Scanner s = new Scanner(System.in);

		System.out.println("Welcome to Harry's AQW Bot version " + version);
		System.out.print("Activate Smooth Mouse Movement? (y/N)");
		smooth = s.nextLine().equals("y") ? true : false;
		System.out.print("Private Instance Room ID: ");
		String roomP = s.nextLine();
		roomNumber = Integer.parseInt(roomP.equals("") ? random.nextInt(79999) + "" : roomP) + 10000;

		// User chooses bot
		System.out.print("What bot would you like to use? ");
		String[] botArgs = s.nextLine().split(" ");
		String bot = botArgs[0];
		System.out.print("Start the bot for " + bot + "? (Y) ");
		String a = s.nextLine();

		// Confirm Start
		while (!(a.equals("") || a.equals("Y"))) {
			System.out.print("Start the bot for " + bot + "? (Y) ");
			a = s.nextLine();
		}
		s.close();

		// Wait for user to position mouse
		System.out.println("Open ONE instance of 'aq.com/play-now' on your primary display");
		System.out.println("Use display quality: Low");
		System.out.println("Position Mouse just to the right of the Artix Logo");
		System.out.println("Calibrating...");
		for (int i = 3; i > 0; i--) {
			System.out.println(i + "...");
			Thread.sleep(1000);
		}
		Point init = MouseInfo.getPointerInfo().getLocation();
		int x = init.x, y = init.y;

		// Calibrate Display
		ScreenHandler screen = new ScreenHandler();
		int[] xy = screen.calibrate();

		// Initialize Bot Handlers
		ActionHandler action = new ActionHandler(xy[0], xy[1]);
		System.out.println("Bot Initiated!");
		System.out.println("AQW Flash Player Begins at : " + xy[0] + ", " + xy[1] + " Size (704x405)");
		System.out.println("Smooth Mouse movement: " + (smooth ? "Enabled" : "Disabled"));
		System.out.println("Bot is running for: " + bot);
		System.out.println("With Private Room Instance Number: " + roomNumber);
		Thread.sleep(30000);
		action.typeString("hi");

	}

}