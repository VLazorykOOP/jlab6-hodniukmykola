import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RotatingLine extends JPanel implements ActionListener {
    private double angle = 0; 
    private Timer timer;
    private static final int LINE_LENGTH = 150; 

    public RotatingLine() {
        timer = new Timer(30, this); 
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int x1 = getWidth() / 2; 
        int y1 = getHeight() / 2;
        int x2 = (int) (x1 + LINE_LENGTH * Math.cos(angle));
        int y2 = (int) (y1 + LINE_LENGTH * Math.sin(angle));

        float hue = (float) (angle / (2 * Math.PI)); 
        g2d.setColor(Color.getHSBColor(hue, 1.0f, 1.0f));
        g2d.setStroke(new BasicStroke(3)); 
        g2d.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        angle += 0.05; 
        if (angle >= 2 * Math.PI) {
            angle = 0;
        }
        repaint(); 
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rotating Line");
        RotatingLine rotatingLine = new RotatingLine();
        frame.add(rotatingLine);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }
}
