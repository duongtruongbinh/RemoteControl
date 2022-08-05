package GUI;

import Client.RecvSend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.Socket;

public class StartPAMenu implements RecvSend {
    private JFrame jFrame;
    private JTextField pathText;
    private JButton startPrs;
    private JButton browseFile;
    private Socket connect;

    public StartPAMenu(String host) {
        jFrame = new JFrame("Start Process/Application");
        jFrame.setMinimumSize(new Dimension(500, 300));
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());

        JPanel pathPanel = new JPanel();
        pathPanel.setLayout(new FlowLayout());

        pathText = new JTextField(25);
        pathText.setMinimumSize(new Dimension(200, 25));
        pathText.addActionListener(this::actionPerformed);
        pathText.setFocusable(true);
        pathPanel.add(pathText);

        startPrs = new JButton("Start");
        startPrs.addActionListener(this::actionPerformed);
        startPrs.setMinimumSize(new Dimension(100, 25));
        startPrs.setFocusable(false);
        pathPanel.add(startPrs);

        browseFile = new JButton("Browse");
        browseFile.addActionListener(this::actionPerformed);
        browseFile.setMinimumSize(new Dimension(100, 25));
        browseFile.setFocusable(false);
        pathPanel.add(browseFile);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation(dim.width / 2 - jFrame.getSize().width / 2, dim.height / 2 - jFrame.getSize().height / 2);
        jFrame.add(pathPanel, BorderLayout.CENTER);

        try {
            connect = new Socket(host, 6001);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can't connect to server", "Error", JOptionPane.ERROR_MESSAGE);
            jFrame.dispose();
        }

        jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    RecvSend.sendMess(connect, "Stop");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        jFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pathText || e.getSource() == startPrs) {
            String path = pathText.getText();
//            Check if path is an executable file
            if (path.toLowerCase().endsWith(".exe")) {
                RecvSend.sendMess(connect, path);
                label:
                while (true) {
                    String mess = RecvSend.getMess(connect);
                    switch (mess) {
                        case "Success" -> {
                            JOptionPane.showMessageDialog(null, "Process started", "Success", JOptionPane.INFORMATION_MESSAGE);
                            break label;
                        }
                        case "Fail" -> {
                            JOptionPane.showMessageDialog(null, "Process failed to start", "Error", JOptionPane.ERROR_MESSAGE);
                            break label;
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "File is not an executable", "Error", JOptionPane.ERROR_MESSAGE);
            }
            pathText.setText("");

        } else if (e.getSource() == browseFile) {
            Frame fileFrame = new Frame();
            FileDialog fd = new FileDialog(fileFrame, "Select File", FileDialog.LOAD);
            fd.setDirectory("C:\\");
            fd.setVisible(true);

            String path = fd.getDirectory() + fd.getFile();
            pathText.setText(path);

            fileFrame.dispose();
        }
    }
}
