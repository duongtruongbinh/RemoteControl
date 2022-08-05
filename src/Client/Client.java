package Client;

import Server.SendRecv;

import javax.swing.*;
import java.net.Socket;

public class Client implements RecvSend {
//    public static void main(String[] argv) throws Exception {
//        String option;
//        String data;
//
//        Socket clientSocket = new Socket("127.0.0.1", 6543);
//
//
//        BufferedReader inFromClient =
//                new BufferedReader(new
//                        InputStreamReader(clientSocket.getInputStream()));
//
//
//        DataOutputStream outToClient =
//                new DataOutputStream(clientSocket.getOutputStream());
//
//
//        do {
//            System.out.print("Option: ");
//            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
//            option = keyboard.readLine();
//            sendMess(clientSocket, option);
//
//            data = receiveMess(clientSocket);
//            System.out.println("Server: " + data);
//
//
//        } while (!option.equals("exit"));
//    }
    private StringBuilder ServerData = new StringBuilder();
    private Socket clientSocket = null;

    public Client(String host) {
        try{
            clientSocket = new Socket(host, 6543);
            JOptionPane.showMessageDialog(null, "Connected to " + host, "Connected", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void sendMess(String message) {
        try {
            SendRecv.sendMess(clientSocket, message);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String receiveMess() {
        try {
            return SendRecv.getMess(clientSocket);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "";
        }
    }

    public void close() {
        try {
            clientSocket.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

