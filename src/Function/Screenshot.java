package Function;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Screenshot {
    public static void takeScreenShot(String path) {
//        ref : https://www.geeksforgeeks.org/java-program-take-screenshots/
        try {
            Robot robot = new Robot();
            BufferedImage image = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(image, "png", new File(path));
        } catch (AWTException | IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
