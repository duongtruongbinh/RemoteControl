package Function;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProcessHandle {
    public static String GetProcess() {
        StringBuilder sb = new StringBuilder();
        try {
//            Run command and print to the console
            ProcessBuilder pb = new ProcessBuilder("powershell.exe", "Get-Process", "|select ProcessName,Id,Description");
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(pb.start().getInputStream()));
            String s;
            while ((s = stdInput.readLine()) != null) {
                try {
                    sb.append(s);
                    sb.append("\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sb = new StringBuilder("Error Table");
        }
        return sb.toString();
    }

    public static boolean KillProcess(int processPID) {
        try {
//            Run command and print to the console
            ProcessBuilder pb = new ProcessBuilder("powershell.exe", "Stop-Process", "-Id", Integer.toString(processPID));
            pb.start();
//            Check if the process still running
            String listProcess = GetProcess();
            return !listProcess.contains(Integer.toString(processPID));
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean StartProcess(String processName) {
        try {
//            Run command and print to the console
            ProcessBuilder pb = new ProcessBuilder("powershell.exe", "Start-Process", "-FilePath", processName);
            pb.start();
//            Check if process is run or not
            String listProcess = GetProcess();
            return listProcess.contains(processName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}