package GUI;

import Client.RecvSend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.Socket;

public class StopPAMenu implements RecvSend {
    private JFrame jFrame;
    private JTextArea APList;
    private JButton showPrs;
    private JButton showApp;
    private JTextField pidText;
    private Socket connect;

    public StopPAMenu(String host) {
        jFrame = new JFrame("Stop Process/Application");
        jFrame.setMinimumSize(new Dimension(500, 400));
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());

        APList = new JTextArea("", 10, 5);
        APList.setEditable(false);
        APList.setCaretPosition(0);
        APList.setFont(new Font("Consolas", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(APList);
        scrollPane.createHorizontalScrollBar();
        jFrame.add(scrollPane, BorderLayout.CENTER);

        pidText = new JTextField(25);
        pidText.addActionListener(this::actionPerformed);

        showPrs = new JButton("Process");
        showPrs.addActionListener(this::actionPerformed);
        showPrs.setFocusable(false);
        showApp = new JButton("Application");
        showApp.addActionListener(this::actionPerformed);
        showApp.setFocusable(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        buttonPanel.add(pidText);
        buttonPanel.add(showPrs);
        buttonPanel.add(showApp);
        buttonPanel.setPreferredSize(new Dimension(500, 50));
        jFrame.add(buttonPanel, BorderLayout.SOUTH);

//        Open jFrame in center of screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation(dim.width / 2 - jFrame.getSize().width / 2, dim.height / 2 - jFrame.getSize().height / 2);

        try {
            connect = new Socket(host, 6002);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can't connect to server", "Error", JOptionPane.ERROR_MESSAGE);
        }

        jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    RecvSend.sendMess(connect, "Stop");
                    connect.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        jFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showPrs) {
            RecvSend.sendMess(connect, "Process");
            String prs;
            do {
                prs = RecvSend.receiveMess(connect);
            }
            while (prs.equals(""));
            APList.setText(prs);
        } else if (e.getSource() == showApp) {
            RecvSend.sendMess(connect, "Application");
            String prs;
            do {
                prs = RecvSend.receiveMess(connect);
            }
            while (prs.equals(""));
            APList.setText(prs);
        } else if (e.getSource() == pidText) {
            String pid = pidText.getText();
            if (pid.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter PID", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                RecvSend.sendMess(connect, "Kill");
                RecvSend.sendMess(connect, pid);
                String result = RecvSend.receiveMess(connect);
                if (result.equals("Success")) {
                    JOptionPane.showMessageDialog(null, "Kill process successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Kill process fail", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
