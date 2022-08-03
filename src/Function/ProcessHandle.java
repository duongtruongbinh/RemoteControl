package Function;

import java.io.BufferedReader;
import java.io.IOException;
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

    private static boolean checkProcess(String processName) {
        StringBuilder sb = new StringBuilder();
        try {
//            Run command and print to the console
            ProcessBuilder pb = new ProcessBuilder("powershell.exe", "Get-Process", "|select Path");
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
        String listProcess = sb.toString();
        return listProcess.contains(processName);
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
            String[] cmd = new String[]{"powershell.exe", "&'",  processName, "'"};
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return checkProcess(processName);
    }

    public static void main(String[] args) {
//        System.out.println(StartProcess("C:\\Program Files` (x86)\\Steam\\steam.exe"));
//        System.out.println(StartProcess("C:\\Games\\Gunfire Reborn\\Gunfire Reborn.exe"));
//        System.out.println(StartProcess("C:\\GOG Games\\Nova Drift\\NovaDrift.exe"));
//        System.out.println(StartProcess("C:\\Games\\Despot’s Game Dystopian Army Builder\\Despot's Game.exe"));
//        System.out.println(StartProcess("C:\\Garena\\Games\\32787\\LeagueClient\\LeagueClient.exe"));
//        System.out.println(StartProcess("notepad.exe"));
        System.out.println(StartProcess("C:\\GOG Games\\The Slormancer\\The Slormancer.exe"));
    }
}