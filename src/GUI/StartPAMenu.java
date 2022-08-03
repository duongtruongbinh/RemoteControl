package GUI;

import Function.ProcessHandle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StartPAMenu {
    private JFrame jFrame;
    private JTextField pathText;
    private JButton startPrs;
    private JButton browseFile;

    public StartPAMenu() {
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

        jFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pathText || e.getSource() == startPrs) {
            String path = pathText.getText();
//            Check if path is an executable file
            if (path.toLowerCase().endsWith(".exe")) {
                ProcessHandle.StartProcess(path);
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "This is not a executable file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartPAMenu::new);
    }
}
