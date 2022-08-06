package GUI;

import Client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu {
    private JButton startPA;
    private JButton stopPA;
    private JButton scrShot;
    private JButton getKeyPress;
    private JButton shutDown;
    private SwitchButton switchButton;
    private JTextField IpText;
    private Client client = null;

    public MainMenu() {
        JFrame jFrame = new JFrame("Remote Control");
        jFrame.setMinimumSize(new Dimension(500, 300));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());

//        Set theme for the JFrame
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException |
                         UnsupportedLookAndFeelException e) {
                    break;
                }
                break;
            }
        }

        JPanel ipPanel = new JPanel();
        JLabel IpLabel = new JLabel("IP: ");
        IpText = new JTextField(10);
        switchButton = new SwitchButton();
        switchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    String host = IpText.getText();
                    if (host.equals("")) {
                        JOptionPane.showMessageDialog(null, "Please enter IP address", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (client == null) {
                            client = new Client(host);
                        } else {
                            client.close();
                            client = null;
                        }
                        switchButton.Released(e);
                    }
                }
            }
        });

        ipPanel.add(IpLabel);
        ipPanel.add(IpText);
        ipPanel.add(switchButton);
        jFrame.add(ipPanel, BorderLayout.NORTH);

        JPanel functionPanel = new JPanel();
        functionPanel.setLayout(new GridBagLayout());

        startPA = new JButton("Start Process/Application");
        stopPA = new JButton("Stop Process/Application");
        scrShot = new JButton("Take a Screenshot");
        getKeyPress = new JButton("Get Key Press");
        shutDown = new JButton("Shut Down");

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        startPA.addActionListener(this::actionPerformed);
        functionPanel.add(startPA, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        stopPA.addActionListener(this::actionPerformed);
        functionPanel.add(stopPA, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        scrShot.addActionListener(this::actionPerformed);
        functionPanel.add(scrShot, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        getKeyPress.addActionListener(this::actionPerformed);
        functionPanel.add(getKeyPress, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        shutDown.addActionListener(this::actionPerformed);
        functionPanel.add(shutDown, gbc);

        for (int i = 0; i < functionPanel.getComponentCount(); i++) {
            functionPanel.getComponent(i).setFocusable(false);
        }

        jFrame.add(functionPanel, BorderLayout.CENTER);

        jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (client != null) {
                    client.close();
                }
            }
        });

        jFrame.setLocation(500, 300);
        jFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
//        TODO: Implement this method with function to used to start/stop/screenshot/get key press/shut down
        if (e.getSource() == startPA) {
            if (client != null) {
                client.sendMess("Start");
                SwingUtilities.invokeLater(() -> new StartPAMenu(IpText.getText()));
            }
        } else if (e.getSource() == stopPA) {
            if (client != null) {
                client.sendMess("Kill");
                SwingUtilities.invokeLater(() -> new StopPAMenu(IpText.getText()));
            }
        } else if (e.getSource() == scrShot) {
            if (client != null) {
                client.sendMess("Screenshot");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                SwingUtilities.invokeLater(() -> new ScreenshotMenu(IpText.getText()));
                String path = System.getProperty("user.dir") + "\\screenshot.png";
                System.out.println(path);
                ProcessBuilder pb = new ProcessBuilder("powershell.exe",path);
                try {
                    pb.start();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Can't open image", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == getKeyPress) {
            if (client != null) {
                client.sendMess("Key");
                SwingUtilities.invokeLater(() -> new KeyloggerMenu(IpText.getText()));
            }
        } else if (e.getSource() == shutDown) {
            if (client != null) {
                client.sendMess("Shutdown");
                IpText.setText("");
                client = null;
            }
        }
    }

    public static void main(String[] args) {
//        Create a frame on the event dispatching thread.
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
