package Server;

import Function.Screenshot;

import java.net.ServerSocket;
import java.net.Socket;

public class HandleScreenShot implements Runnable, SendRecv {
    @Override
    public void run() {
        ServerSocket ss;
        try {
            ss = new ServerSocket(6003);
            Socket s = ss.accept();

            Screenshot.takeScreenShot("ScreenShot.png");
            SendRecv.sendCaptureScreen(s, "ScreenShot.png");

            s.close();
            ss.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
