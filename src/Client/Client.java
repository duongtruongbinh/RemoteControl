package Client;

import Function.ProcessHandle;

public class Client {
    public static void main(String[] args) {
//        try (Socket socket = new Socket("localhost", 8080)) {
//            System.out.println("Connected to server");
//            System.out.println("Sending message");
//            socket.getOutputStream().write("Hello from client".getBytes());
//            System.out.println("Message sent");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        System.out.println(ProcessHandle.GetProcess());
    }

}