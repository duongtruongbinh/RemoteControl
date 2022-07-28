package Funtion;

public class ComputerControl {
    public static void ShutDown(){
        try {
            ProcessBuilder pb = new ProcessBuilder("powershell.exe", "Stop-Computer", "-Force");
            pb.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ReStart(){
        try {
            ProcessBuilder pb = new ProcessBuilder("powershell.exe", "Restart-Computer", "-Force");
            pb.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
