import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ScreenHandler {

	private Robot robot;

	private int screenWidth, screenHeight;
	private final int BLACK = -16777216;
	private final int QUESTCOMPLETE = -6684927;

	public ScreenHandler() throws AWTException {
		robot = new Robot();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();

	}

	public int[] calibrate() throws IOException {
		BufferedImage display = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

		for (int x = 0; x < screenWidth; x++) {
			for (int y = 0; y < screenHeight; y++) {
				// Find a Black Pixel
				if (display.getRGB(x, y) == BLACK) {
					// Search for the Black Bar for Calibration
					boolean found = true;
					for (int ys = y; ys < y + 405; ys++) {
						try {
							if (!(display.getRGB(x, ys) == BLACK && display.getRGB(x + 1, ys) == BLACK
									&& display.getRGB(x + 2, ys) == BLACK && display.getRGB(x + 3, ys) == BLACK)) {
								found = false;
							}
						} catch (Exception e) {
						}
					}
					if (found) {
						File prevCalibrate = new File("previous_calibration.aqwbotconfig");
						FileWriter fileWriter = new FileWriter(prevCalibrate);
						fileWriter.write(x + " " + y);
						fileWriter.close();
						return new int[] { x, y };
					}
				}
			}
		}
		return null;
	}

	public boolean anyQuestComplete() throws IOException {
		BufferedImage display = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		for (int x = 0; x < screenWidth; x++) {
			for (int y = 0; y < screenHeight; y++) {
				// Search for the Lime Green indicating a quest is completed
				if (display.getRGB(x, y) == QUESTCOMPLETE) {
					return true;
				}
			}
		}
		return false;
	}

}
