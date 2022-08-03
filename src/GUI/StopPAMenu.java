package GUI;

import Function.ApplicationHandle;
import Function.ProcessHandle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StopPAMenu {
    private JFrame jFrame;
    private JTextArea APList;
    private JButton showPrs;
    private JButton showApp;
    private JTextField pidText;

    public StopPAMenu() {
        jFrame = new JFrame("Stop Process/Application");
        jFrame.setMinimumSize(new Dimension(500, 400));
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());

        APList = new JTextArea(ProcessHandle.GetProcess(), 10, 5);
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
        jFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showPrs) {
            APList.setText(ProcessHandle.GetProcess());
        } else if (e.getSource() == showApp) {
            APList.setText(ApplicationHandle.GetApplication());
        } else if (e.getSource() == pidText) {
            ProcessHandle.StartProcess(pidText.getText());
        }
    }
}
