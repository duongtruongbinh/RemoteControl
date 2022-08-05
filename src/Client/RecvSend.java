package Client;

import java.io.*;
import java.net.Socket;

public interface RecvSend {
    static void sendMess(Socket connect, String message) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(connect.getOutputStream());
            dataOutputStream.writeBytes(message);
            dataOutputStream.writeBytes("\n");
            dataOutputStream.writeBytes("End message");
            dataOutputStream.writeBytes("\n");
            //System.out.println(message);
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }

    static String getMess(Socket connect) {
        String dataMessage;
        String getLine;
        dataMessage = "";

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            while ((getLine = bufferedReader.readLine()) != null) {
                if (getLine.compareTo("End message") == 0) break;

                if (dataMessage.compareTo("") == 0) dataMessage += getLine;
                else dataMessage += "\n" + getLine;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dataMessage;
    }

    static void getFileScreen(Socket connect, String fileName) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(connect.getInputStream());

        byte[] byteList = new byte[1024];
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        int countByte;

        while (true) {
            countByte = dataInputStream.read(byteList);
            fileOutputStream.write(byteList, 0, countByte);
            if (countByte < 1024) break;
        }
        fileOutputStream.close();
    }

    static void sendCaptureScreen(Socket connect, String fileName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        DataOutputStream dataOutputStream = new DataOutputStream(connect.getOutputStream());
        byte[] byteList = new byte[1024 * 1024];
        int countByte = 0;
        while ((countByte = fileInputStream.read(byteList)) > 0) {
            dataOutputStream.write(byteList, 0, countByte);
        }
        fileInputStream.close();
    }
}
