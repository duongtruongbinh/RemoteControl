package Server;

import Function.ProcessHandle;

import java.net.ServerSocket;
import java.net.Socket;

public class HandleStartPAMenu implements Runnable, SendRecv {
    private StringBuilder sb = new StringBuilder();
    @Override
    public void run() {
        ServerSocket ss;
        try {
            ss = new ServerSocket(6001);
            Socket s = ss.accept();

            String path;
            do {
                path = SendRecv.getMess(s);
                if (!path.equals("")) {
                    if (ProcessHandle.StartProcess(path))
                        SendRecv.sendMess(s, "Success");
                    else
                        SendRecv.sendMess(s, "Fail");
                }
            }
            while (!path.equals("Stop"));
            s.close();
            ss.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
