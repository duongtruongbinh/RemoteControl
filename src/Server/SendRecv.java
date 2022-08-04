package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public interface SendRecv {
    static String receiveMess(Socket connect) {
        StringBuilder dataQueue = new StringBuilder();
        try {
            byte[] chunk = new byte[1024 * 10];
            int dataSize;
            DataInputStream fromClient = new DataInputStream(connect.getInputStream());
            do {
                dataSize = fromClient.read(chunk);
                String temp = new String(chunk);
                dataQueue.append(temp);
            } while (dataSize >= 1024 * 10);


        } catch (Exception e) {
            System.out.println(("Error " + e));
        }
        return dequeue(dataQueue);
    }

    static void sendMess(Socket connect, String mess) {
        try {
            DataOutputStream toServer = new DataOutputStream(connect.getOutputStream());
            toServer.writeBytes("<mess>" + mess + "</mess>");
        } catch (Exception e) {
            System.out.println(("Error " + e));
        }
    }

    static String dequeue(StringBuilder dataQueue) {
        String temp = new String(dataQueue);
        int pos = temp.lastIndexOf('<');
        temp = temp.substring(0, pos);

        pos = temp.lastIndexOf('>');
        temp = temp.substring(pos + 1);
        return temp;
    }
}
