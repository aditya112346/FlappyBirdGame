package flappyBird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FlappyBird extends JFrame implements ActionListener, KeyListener {
    private Timer timer;
    private int birdY, birdVelocity;
    private boolean isGameOver;
    private int[] pipeX, pipeY, pipeWidth, pipeGap;
    private final int PIPE_COUNT = 5;
    private final int PIPE_WIDTH = 50;
    private final int PIPE_GAP = 200;
    private final int PIPE_DIST = 300;
    private final int GRAVITY = 2;

    public FlappyBird() {
        setTitle("Flappy Bird");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        addKeyListener(this);

        timer = new Timer(20, this);

        birdY = 300;
        birdVelocity = 0;

        pipeX = new int[PIPE_COUNT];
        pipeY = new int[PIPE_COUNT];
        pipeWidth = new int[PIPE_COUNT];
        pipeGap = new int[PIPE_COUNT];

        for (int i = 0; i < PIPE_COUNT; i++) {
            pipeX[i] = 500 + i * PIPE_DIST;
            pipeY[i] = (int) (Math.random() * 400);
            pipeWidth[i] = PIPE_WIDTH;
            pipeGap[i] = PIPE_GAP;
        }

        isGameOver = false;

        timer.start();

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < PIPE_COUNT; i++) {
            g.setColor(Color.GREEN);
            g.fillRect(pipeX[i], 0, pipeWidth[i], pipeY[i]);
            g.fillRect(pipeX[i], pipeY[i] + pipeGap[i], pipeWidth[i], 600 - (pipeY[i] + pipeGap[i]));
        }

        g.setColor(Color.BLUE);
        g.fillRect(150, birdY, 30, 30);

        if (isGameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over!", 250, 300);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            for (int i = 0; i < PIPE_COUNT; i++) {
                pipeX[i] -= 5;

                if (pipeX[i] < -PIPE_WIDTH) {
                    pipeX[i] = 800;
                    pipeY[i] = (int) (Math.random() * 400);
                }

                if (birdY + 30 > pipeY[i] && birdY < pipeY[i] + PIPE_GAP && 150 + 30 > pipeX[i] && 150 < pipeX[i] + PIPE_WIDTH) {
                    isGameOver = true;
                }
            }

            birdVelocity += GRAVITY;
            birdY += birdVelocity;

            if (birdY < 0 || birdY > 570) {
                isGameOver = true;
            }

            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !isGameOver) {
            birdVelocity = -20;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new FlappyBird();
    }
}
