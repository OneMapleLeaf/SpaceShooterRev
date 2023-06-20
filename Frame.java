package SpaceShooterRev;

import javax.swing.JFrame;


public class Frame extends JFrame {
    public Frame() {
        setTitle("Space Shooter Rev");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new Panel());
    }

    public static void main(String[] args) {
        new Frame();
    }
}
