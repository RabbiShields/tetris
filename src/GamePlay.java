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

    //game settings
    private boolean enablePieceProjection = true;
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
        g.fillRoundRect(1, 20,
                (grid.rBound - grid.lBound + 1) * blockSize + blockGap,
                (grid.floorBound * blockSize) + 10
                , 20, 20);

        int[][] swapGrid = grid.getSwap();
        for (int i = 0; i < blockSize - 1; i++) {
            for (int j = 0; j < blockSize - 1; j++) {
                if (swapGrid[i][j] != 0) {
                    g.setColor(Color.black);
                    g.fillRect(((i) * blockSize) + grid.rBound, (j * blockSize) + blockGap, heightWidth, heightWidth); //block gap
                }
            }
        }

        int[][] gridCoOr = grid.getGrid();
        for (int i = grid.lBound; i < gridCoOr.length; i++) {
            for (int j = 0; j < gridCoOr[i].length; j++) {

                if (gridCoOr[i][j] == 1) {
                    g.setColor(Color.white);
                    g.fillRect(((i - grid.lBound) * blockSize) + blockGap, (j * blockSize) + blockGap, heightWidth, heightWidth);
                }
                if (gridCoOr[i][j] == 2) {
                    g.setColor(Color.green);
                    g.fillRect(((i - grid.lBound) * blockSize) + blockGap, (j * blockSize) + blockGap, heightWidth, heightWidth);
                }
                if (gridCoOr[i][j] == 3) {
                    g.setColor(Color.red);
                    g.fillRect(((i - grid.lBound) * blockSize) + blockGap, (j * blockSize) + blockGap, heightWidth, heightWidth);
                }
                if (gridCoOr[i][j] == 4) {
                    g.setColor(Color.yellow);
                    g.fillRect(((i - grid.lBound) * blockSize) + blockGap, (j * blockSize) + blockGap, heightWidth, heightWidth);
                }

                if (gridCoOr[i][j] == 5) {
                    g.setColor(Color.blue);
                    g.drawRoundRect(((i - grid.lBound) * blockSize) + blockGap,
                            (j * blockSize) + blockGap, heightWidth, heightWidth, 5, 5);
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
        if (enablePieceProjection) grid.pieceProjection();
        completeLineCheck();
        repaint();
    }

    private void score() {
    }


    private void completeLineCheck() {
        for (int j = 25; j > 3; j--) {
            boolean lineComplete = true;
            for (int i = grid.lBound; i <= grid.rBound; i++) {
                if (grid.frozenPiecesGrid[i][j] == 0) {
                    lineComplete = false;
                }
            }
            if (lineComplete) {
                removeLine(j);
            }
        }
    }

    private void removeLine(int completedLineAt) {
        //removes completed line
        for (int i = grid.lBound; i <= grid.rBound; i++) {
            grid.frozenPiecesGrid[i][completedLineAt] = 0;
        }

        repaint();
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //moves all blocks down

        for (int i = grid.lBound; i <= grid.rBound; i++) {
            for (int j = completedLineAt; j > 3; j--) {
                grid.frozenPiecesGrid[i][j] = grid.frozenPiecesGrid[i][j - 1];
                grid.frozenPiecesGrid[i][j - 1] = 0;
            }
        }
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
        if (e.getExtendedKeyCode() == KeyEvent.VK_SHIFT) {
            grid.swap();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
