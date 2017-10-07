import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class ScreenHandler {

	private Random random;
	private Robot robot;

	private int screenWidth, screenHeight;
	private final int BLACK = -16777216;

	public ScreenHandler() throws AWTException {
		random = new Random();
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
					if (found)
						return new int[] { x, y };
				}
			}
		}
		return null;
	}

}
