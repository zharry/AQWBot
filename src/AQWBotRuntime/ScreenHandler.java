package AQWBotRuntime;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class ScreenHandler {

	Dimension screenSize;
	int screenWidth;
	int screenHeight;

	public ScreenHandler() {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
	}

	public BufferedImage getScreen() {
		return AQWBotRuntime.robot.createScreenCapture(new Rectangle(0, 0, 950, 575));

	}

}
