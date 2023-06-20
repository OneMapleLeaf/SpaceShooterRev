package SpaceShooterRev;

import java.awt.*;


public class Enemy {
    private int x;
    private int y;
    private int width = 30;
    private int height = 50;
    private int size;
    private double speed;

    public Enemy(int x, int y, int width, int height, int size, double speed) {
        this.x = x;
        this.y = y;
        this.width = width / 10;
        this.height = height / 10;
        this.speed = speed;
        this.size = size;
    }

    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x, y, size, size);
    }

    public void update(int shipX, int shipY) {
        //distance formula
        int dx = shipX - x;
        int dy = shipY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double dirX = dx / distance;
        double dirY = dy / distance;

        x += dirX * speed;
        y += dirY * speed;
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
}
