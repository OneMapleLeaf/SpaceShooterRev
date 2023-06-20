package SpaceShooterRev;

import java.awt.*;

public class Ship {
    private int x;
    private int y;
    private int width;
    private int height;
    private int size;
    int speed = 10;

    public Ship(int shipWidth, int shipHeight, int shipSize) {
        this.width = shipWidth;
        this.height = shipHeight;
        this.size = shipSize;
        this.x = (width - size) / 2;
        this.y = (height - size) - 50;
    }

    public void drawShip(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, size, size);
    }

    public void updateShip(double velX, double velY, int maxX, int maxY) {
        x += velX;
        y += velY;
        x = Math.max(0, Math.min(maxX, x));
        y = Math.max(0, Math.min(maxY, y));
        System.out.print("X: ");
        System.out.println(x);
        System.out.print("Y: ");
        System.out.println(y);
    }

    public int getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }


}
