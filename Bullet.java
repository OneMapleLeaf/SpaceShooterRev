package SpaceShooterRev;

import java.awt.*;
import java.util.Random;

public class Bullet {
    private int x;
    private int y;
    private int speed = 5;
    int width = 30;
    int height = 50;
    private Random random;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void update() {
        y -= speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(x, y, 5, 10);
    }
}
