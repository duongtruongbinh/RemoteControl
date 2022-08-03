package GUI;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.Date;

public class KeyloggerMenu implements NativeKeyListener {
    private JButton startButton = null;
    private JButton stopButton = null;
    private JTable table = null;
    private JScrollPane scrollPane;
    public KeyloggerMenu() {
        JFrame jFrame = new JFrame("Keylogger");
        jFrame.setMinimumSize(new Dimension(300, 400));
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());

        Object[] columnNames = {"Key", "Time"};
        Object[][] data = {};

        TableModel tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);

        scrollPane = new JScrollPane(table);
        scrollPane.createHorizontalScrollBar();
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        jFrame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        startButton = new JButton("Start");
        startButton.addActionListener(this::actionPerformed);
        startButton.setFocusable(false);
        stopButton = new JButton("Stop");
        stopButton.addActionListener(this::actionPerformed);
        stopButton.setFocusable(false);

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        jFrame.add(buttonPanel, BorderLayout.SOUTH);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation(screenSize.width / 2 - jFrame.getWidth() / 2, screenSize.height / 2 - jFrame.getHeight() / 2);
        jFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
//            Start a new threat and call the keylogger
            try {
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException ex) {
//                Popup a error message
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            GlobalScreen.addNativeKeyListener(this);
        }
        if (e.getSource() == stopButton) {
//            Stop the keylogger
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
//        Update the table with the key pressed
        String key = NativeKeyEvent.getKeyText(e.getKeyCode());
        Timestamp time = new Timestamp(new Date().getTime());
//        Change time stamp to date and time format
        String timeString = time.toString();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.insertRow(model.getRowCount(), new Object[]{key, timeString});
//        Scroll to the bottom of the table
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
//        This function is not used
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
//        This function is not used
    }
}

