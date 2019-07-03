import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private Timer timer;
    private Grid grid = new Grid();
    private boolean blockInPlay = false;
    private int blockSize = 30;
    private int blockGap = 3;
    private int heightWidth = blockSize - (2 * blockGap);


    GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        int delay = 500;
        timer = new Timer(delay, this);
        timer.start();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //background
        g.setColor(Color.yellow);
        g.fillRect(1, 1, 700, 800);

        g.setColor(Color.black);
        g.fillRoundRect(grid.lBound * blockSize, 20,
                (grid.rBound - grid.lBound+1) * blockSize + blockGap,
                (grid.floorBound * blockSize) + 10
                , 20, 20);

        for (int i = grid.lBound; i <= grid.rBound; i++) {
            g.setColor(Color.white);
            g.drawRoundRect((i * blockSize) + blockGap,
                    (grid.floorBound * blockSize) + blockGap,
                    heightWidth, heightWidth, 5
                    , 5);
        }

        int[][] gridCoOr = grid.getGrid();
        for (int i = 0; i < gridCoOr.length; i++) {
            for (int j = 0; j < gridCoOr[i].length; j++) {
                if (gridCoOr[i][j] != 1 & gridCoOr[i][j] != 0 & gridCoOr[i][j] != 2)
                    gridCoOr[i][j]
                            = 0; // ^turns all null into 0s^

                if (gridCoOr[i][j] == 2) {
                    g.setColor(Color.blue);
                    g.drawRoundRect((i * blockSize) + blockGap,
                            (j * blockSize) + blockGap, heightWidth, heightWidth, 5, 5);
                }
                if (gridCoOr[i][j] == 1) {
                    g.setColor(Color.white);
                    g.fillRect((i * blockSize) + blockGap, (j * blockSize) + blockGap, heightWidth, heightWidth);
                }
            }
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (!blockInPlay) {
            grid.startGame();
            blockInPlay = true;
        }
        grid.move(0, 1);
        grid.pieceProjection();
        grid.completeLineCheck();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getExtendedKeyCode() == KeyEvent.VK_UP) {
            grid.rotate();
        }
        if (e.getExtendedKeyCode() == KeyEvent.VK_RIGHT) {
            grid.move(1, 0);
        }
        if (e.getExtendedKeyCode() == KeyEvent.VK_LEFT) {
            grid.move(-1, 0);
        }
        if (e.getExtendedKeyCode() == KeyEvent.VK_DOWN) {
            grid.move(0, 1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
