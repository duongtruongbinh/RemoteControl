package GUI;

import Client.RecvSend;

import javax.swing.*;
import java.net.Socket;

public class ScreenshotMenu implements RecvSend {
    private Socket connect;

    public ScreenshotMenu(String host) {
        try {
            connect = new Socket(host, 6003);
            RecvSend.getFileScreen(connect, "ScreenshotT.png");
//            Get current directory
            String currentDir = System.getProperty("user.dir");
            JOptionPane.showMessageDialog(null, "Screenshot saved in " + currentDir + "\\ScreenshotT.png", "Screenshot", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can't connect to server", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
