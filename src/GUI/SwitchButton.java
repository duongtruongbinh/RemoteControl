package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class SwitchButton extends Component {
    private javax.swing.Timer timer;
    private float location = 2;

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    private boolean isOn; // true = on, false = off
    private final float speed = 1f;
    private java.util.List<SwitchEvenList> evens = new java.util.ArrayList<>();

    private Color onColor = new Color(56, 180, 39, 255);
    private Color offColor = new Color(119, 112, 112);
    private Color borderColor = new Color(255, 255, 255);

    public SwitchButton() {
        setBackground(offColor);
        setPreferredSize(new Dimension(50, 30));
        setForeground(Color.white);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        // The animation of the switch button
        timer = new javax.swing.Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isOn) {
                    // Set the location of the switch to the right
                    int endLocation = getWidth() - getHeight() + 2;
                    if (location < endLocation) {
                        location += speed;
                        repaint();
                    } else {
                        timer.stop();
                        location = endLocation;
                        repaint();
                    }
                } else {
                    // Set the location to the left of the switch button.
                    int endLocation = 2;
                    if (location > endLocation) {
                        location -= speed;
                        repaint();
                    } else {
                        timer.stop();
                        location = endLocation;
                        repaint();
                    }
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        float alpha = getAlpha();

        if (alpha < 1) {
            g2.setColor(offColor);
            g2.fillRoundRect(0, 0, width, height, 30, 30);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(onColor);
        g2.fillRoundRect(0, 0, width, height, 30, 30);
        g2.setComposite(AlphaComposite.SrcOver);
        g2.setColor(borderColor);
        g2.fillOval((int) location, 3, 25, height - 6);

        super.paint(g);
    }

    //    Get the current state of the switch button.
    private float getAlpha() {
        float width = getWidth() - getHeight() + 2;
        float alpha = (location - 2) / width;
        if (alpha < 0) {
            alpha = 0;
        } else if (alpha > 1) {
            alpha = 1;
        }
        return alpha;
    }

    private void runEvens() {
        for (SwitchEvenList even : evens) {
            even.isOn(isOn);
        }
    }

    public void addEven(SwitchEvenList even) {
        evens.add(even);
    }

    public void removeEven(SwitchEvenList even) {
        evens.remove(even);
    }

    public Color getOnColor() {
        return onColor;
    }

    public void setOnColor(Color onColor) {
        this.onColor = onColor;
    }

    public Color getOffColor() {
        return offColor;
    }

    public void setOffColor(Color offColor) {
        this.offColor = offColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void Released(MouseEvent me) {
        if (SwingUtilities.isLeftMouseButton(me)) {
            isOn = !isOn;
            timer.start();
            runEvens();
        }
    }
}
