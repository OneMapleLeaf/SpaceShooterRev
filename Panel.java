package SpaceShooterRev;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Font;
import java.awt.FontMetrics;


public class Panel extends JPanel implements ActionListener, KeyListener, MouseListener {
    final int WIDTH = 800, HEIGHT = 600;
    boolean gameOver = false;
    Timer time = new Timer(16, this);
    Ship myShip;

    boolean wKeyDown = false, aKeyDown = false, sKeyDown = false, dKeyDown = false;

    private List<Bullet> bullets;
    private List<Enemy> enemies;

    private Random random;
    int enemiesToRespawn = 2;

    int score;
    int health;

    public Panel() {
        setSize(WIDTH, HEIGHT);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        myShip = new Ship(WIDTH, HEIGHT, 30);
        setBackground(Color.BLACK);

        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        spawnEnemies(1);
        score = 0;
        health = 100;
    }

    private boolean checkCollision(int firstX, int firstY, int firstWidth, int firstHeight, int secondX, int secondY, int secondWidth, int secondHeight) {
        Rectangle rect1 = new Rectangle(firstX, firstY, firstWidth, firstHeight);
        Rectangle rect2 = new Rectangle(secondX, secondY, secondWidth, secondHeight);
        return rect1.intersects(rect2);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!time.isRunning()) {
            drawStart(g);
        } else {
            drawScore(g);
            drawHealth(g);
            if (!gameOver) {
                drawShip(g);
                drawBullets(g);
                drawEnemies(g);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        movementLogic();
        updateBullets();
        updateEnemies();
        gameLogic();
        repaint();
    }

    public void movementLogic() {
        int y = (wKeyDown) ? -myShip.getSpeed() : (sKeyDown) ? myShip.getSpeed() : 0;
        int x = (aKeyDown) ? -myShip.getSpeed() : (dKeyDown) ? myShip.getSpeed() : 0;
        int maxX = getWidth() - 30;
        int maxY = getHeight() - 30;
        myShip.updateShip(x, y, maxX, maxY);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == e.VK_W) wKeyDown = true;
        if (key == e.VK_A) aKeyDown = true;
        if (key == e.VK_S) sKeyDown = true;
        if (key == e.VK_D) dKeyDown = true;
        if (key == e.VK_SPACE) shootBullet();
        if (key == e.VK_ENTER) time.start();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == e.VK_W) wKeyDown = false;
        if (key == e.VK_A) aKeyDown = false;
        if (key == e.VK_S) sKeyDown = false;
        if (key == e.VK_D) dKeyDown = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            shootBullet();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    //Ship Properties
    private void drawShip(Graphics g) {
        myShip.drawShip(g);
    }

    //Bullet Properties
    private void drawBullets(Graphics g) {
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
    }

    private void shootBullet() {
        int bulletX = myShip.getX() + 13;
        int bulletY = myShip.getY();
        Bullet bullet = new Bullet(bulletX, bulletY);
        bullets.add(bullet);
    }

    private void updateBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.update();
            if (bullet.getY() < 0) {
                bullets.remove(i);
                i--;
            }
        }
    }

    //Enemy Properties
    private void drawEnemies(Graphics g) {
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
    }

    private void updateEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.update(myShip.getX(), myShip.getY());
        }
        if (enemies.isEmpty()) {
            spawnEnemies(enemiesToRespawn);
            enemiesToRespawn++;
        }
    }

    public void spawnEnemies(int numOfEnemies) {
        random = new Random();
        int maxX = getWidth() - 30;
        for (int i = 0; i < numOfEnemies; i++) {
            int enemyX = random.nextInt(maxX);
            int enemyY = 0;
            Enemy enemy = new Enemy(enemyX, enemyY, WIDTH, HEIGHT, 20, 1.5);
            enemies.add(enemy);
        }
    }

    //Game Logic - how would the bullet kill the enemies
    private void gameLogic() {
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<Enemy> enemiesToRemove = new ArrayList<>();
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.update();
            boolean bulletHit = false;
            for (int j = 0; j < enemies.size(); j++) {
                Enemy enemy = enemies.get(j);
                if (checkCollision(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight(), enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight())) {
                    bulletsToRemove.add(bullet);
                    enemiesToRemove.add(enemy);
                    score++;
                    bulletHit = true;
                    break;
                }
                if (checkCollision(myShip.getX(), myShip.getY(), myShip.getSize(), myShip.getSize(), enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight())) {
                    enemies.remove(j);
                    j--;
                    health -= 1;
                    break;
                }
            }
            if (bullet.getY() < 0) {
                bulletsToRemove.add(bullet);
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);

            if (checkCollision(myShip.getX(), myShip.getY(), myShip.getSize(), myShip.getSize(), enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight())) {
                enemiesToRemove.add(enemy);
                health -= 1;
                break;
            }
        }
        for (Enemy enemy : enemiesToRemove) {
            enemies.remove(enemy);
        }
        bullets.removeAll(bulletsToRemove);
        if (health <= 0) {
            health = 0;
            gameOver = true;
        }

    }

    //Printing UI
    private void drawStart(Graphics g) {
        g.setColor(Color.WHITE);
        Font startFont = new Font("TimesRoman", Font.BOLD, 50);
        g.setFont(startFont);
        FontMetrics fm = g.getFontMetrics();
        String startText = "PRESS [ENTER] TO START";
        int startTextWidth = g.getFontMetrics().stringWidth(startText);
        int scoreTextWidth = fm.stringWidth(startText);
        int startTextX = (WIDTH - startTextWidth) / 2;
        int startTextY = HEIGHT / 2;
        g.drawString(startText, startTextX, startTextY);
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        Font scoreFont = new Font("TimesRoman", Font.BOLD, 20);
        g.setFont(scoreFont);
        FontMetrics fm = g.getFontMetrics();
        String scoreText = "SCORE: " + score;
        int scoreTextWidth = fm.stringWidth(scoreText);
        int scoreX = (WIDTH - scoreTextWidth) / 2;
        int scoreY = 30;
        g.drawString(scoreText, scoreX, scoreY);
    }

    private void drawHealth(Graphics g) {
        g.setColor(Color.WHITE);
        Font healthFont = new Font("TimesRoman", Font.BOLD, 20);
        g.setFont(healthFont);
        FontMetrics fm = g.getFontMetrics();
        String healthText = "HEALTH: " + health;
        int healthTextHeight = fm.getHeight();
        int healthX = 10;
        int healthY = HEIGHT - healthTextHeight - 15;
        g.drawString(healthText, healthX, healthY);
    }
}


