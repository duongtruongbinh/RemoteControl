package Server;

import Function.ApplicationHandle;
import Function.ProcessHandle;

import java.net.ServerSocket;
import java.net.Socket;

public class HandleStopPAMenu implements Runnable, SendRecv {
    @Override
    public void run() {
        ServerSocket ss;
        try {
            ss = new ServerSocket(6002);
            Socket s = ss.accept();

            String option;
            do {
                option = SendRecv.receiveMess(s);
                System.out.println("Request:" + option);
                if (option.equals("Application")) {
                    SendRecv.sendMess(s, ApplicationHandle.GetApplication());
                }
                if (option.equals("Process")) {
                    SendRecv.sendMess(s, ProcessHandle.GetProcess());
                }
                if (option.equals("Kill")) {
                    String processPID = SendRecv.receiveMess(s);
                    if (ProcessHandle.KillProcess(Integer.getInteger(processPID))) {
                        SendRecv.sendMess(s, "Success");
                    } else {
                        SendRecv.sendMess(s, "Fail");
                    }
                }
            }
            while (!option.equals("Stop"));
            s.close();
            ss.close();
        } catch (
                Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
