package Server;

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

        option = SendRecv.receiveMess(connectionSocket);
        while (!option.equals("Exit")) {
            if (option.equals("Start")) {
            }
            if (option.equals("Kill")) {
//                Call Server.KillPA in other threat
                Thread killPA = new Thread(new HandleStopPAMenu());
                killPA.start();
            }
            option = SendRecv.receiveMess(connectionSocket);
        }

        try {
            connectionSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}