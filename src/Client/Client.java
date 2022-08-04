package Client;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {
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

    public static String dequeue(StringBuilder dataQueue) {
        String temp = new String(dataQueue);
        int pos = temp.lastIndexOf('<');
        temp = temp.substring(0, pos);

        pos = temp.lastIndexOf('>');
        temp = temp.substring(pos + 1);
        return temp;
    }

    public static String receiveMess(Socket connect) {
        StringBuilder dataQueue = new StringBuilder();
        try {
            byte[] chunk = new byte[1024];
            int dataSize;
            DataInputStream fromClient = new DataInputStream(connect.getInputStream());
            do {
                dataSize = fromClient.read(chunk);
                String temp = new String(chunk);
                dataQueue.append(temp);
            } while (dataSize >= 1024);


        } catch (Exception e) {
            System.out.println(("Error " + e));
        }
        return dequeue(dataQueue);
    }

    public void disconnect() {
        try {
            clientSocket.close();
        } catch (Exception e) {
            System.out.println(("Error " + e));
        }
    }

    public static void sendMess(Socket connect, String mess) {

        try {
            DataOutputStream toServer = new DataOutputStream(connect.getOutputStream());
            toServer.writeBytes("<mess>" + mess + "</mess>");

        } catch (Exception e) {
            System.out.println(("Error " + e));
        }

    }
}

