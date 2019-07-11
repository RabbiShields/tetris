import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private Timer timer;
    private Grid grid = new Grid();
    private boolean blockInPlay = false;
    private int score = 0;

    //game settings
    private boolean enablePieceProjection = true;
    private int blockSize = 30;
    private int blockGap = 3;
    private int heightWidth = blockSize - (2 * blockGap);
    private int playBoardX = 50;
    private int playBoardY = 50;
    private int swapBoardX = 350;
    private int swapBoardY = 47;
    private int GapBetweenPieceAndBoard = 2 * blockGap;


    GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        int delay = 100;
        timer = new Timer(delay, this);
        timer.start();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //background
        g.setColor(Color.yellow);
        g.fillRect(1, 1, 700, 800);


        //swap renderer
        g.setColor(Color.black);
        g.fillRoundRect(swapBoardX, swapBoardY,
                grid.getBlockGridLength() * blockSize + GapBetweenPieceAndBoard,
                grid.getBlockGridLength() * blockSize + GapBetweenPieceAndBoard, 10, 10);
        int[][] swapGrid = grid.getSwap();
        for (int i = 0; i < swapGrid.length; i++) {
            for (int j = 0; j < swapGrid.length; j++) {
                if (swapGrid[i][j] != 0) {
                    g.setColor(Color.white);
                    g.fillRect(i * blockSize + swapBoardX + GapBetweenPieceAndBoard, (j * blockSize + 1) + swapBoardY + GapBetweenPieceAndBoard, heightWidth, heightWidth);
                }
            }
        }

        //play board
        g.setColor(Color.black);
        g.fillRoundRect(playBoardX - GapBetweenPieceAndBoard / 2,
                playBoardY - GapBetweenPieceAndBoard / 2,
                (grid.rBound - grid.lBound + 1) * blockSize + GapBetweenPieceAndBoard,
                (grid.floorBound * blockSize) + blockSize + GapBetweenPieceAndBoard
                , 10, 10);


        int[][] gridCoOr = grid.getGrid();
        for (int i = grid.lBound; i < gridCoOr.length; i++) {
            for (int j = 0; j < gridCoOr[i].length; j++) {
                int xBlockPos = ((i - grid.lBound) * blockSize) + blockGap + playBoardX;
                int yBlockPos = (j * blockSize) + blockGap + playBoardY;
                if (gridCoOr[i][j] == 1) {
                    g.setColor(Color.white);
                    g.fillRect(xBlockPos, yBlockPos, heightWidth, heightWidth);
                }
                if (gridCoOr[i][j] == 2) {
                    g.setColor(Color.green);
                    g.fillRect(xBlockPos, yBlockPos, heightWidth, heightWidth);
                }
                if (gridCoOr[i][j] == 3) {
                    g.setColor(Color.red);
                    g.fillRect(xBlockPos, yBlockPos, heightWidth, heightWidth);
                }
                if (gridCoOr[i][j] == 4) {
                    g.setColor(Color.yellow);
                    g.fillRect(xBlockPos, yBlockPos, heightWidth, heightWidth);
                }
                if (gridCoOr[i][j] == 5) {
                    g.setColor(Color.white);
                    g.drawRoundRect(xBlockPos, yBlockPos, heightWidth, heightWidth, 5, 5);
                }
            }
        }

        //parses score into list so score board can handle muility digit scores
        int a = getScore();
        ArrayList<Integer> scoreParsed = new ArrayList<>();
        while (a > 9) {
            scoreParsed.add(a % 10);
            a = a / 10;
        }
        scoreParsed.add(a);


        // score board
        int scoreBoardX = 350;
        int scoreBoardY = 200;
        g.setColor(Color.black);
        g.fillRoundRect(scoreBoardX, scoreBoardY, 120, 90, 10, 10);

        for (int i = 0; i < scoreParsed.size(); i++) {

            int width = 3;
            int height = 18;
            int peak = 6;
            int eightLineNumX = scoreBoardX + 70 - i * 2 * (height + width * 2);
            int eightLineNumY = scoreBoardY + 5;

            int[] ledXPos = {eightLineNumX, eightLineNumX + height + peak * 2, eightLineNumX + height + peak * 2, eightLineNumX, eightLineNumX, eightLineNumX, eightLineNumX,};
            int[] ledYPos = {eightLineNumY, eightLineNumY, eightLineNumY + height + peak * 2, eightLineNumY + (height + peak * 2) * 2, eightLineNumY + (height + peak * 2), eightLineNumY + height + peak * 2, eightLineNumY};
            int[][] numbers = {{0, 1, 2, 3, 4, 6}, {1, 2}, {0, 1, 5, 4, 3}, {0, 1, 2, 3, 5}, {6, 5, 1, 2}, {0, 6, 5, 2, 3}, {0, 2, 3, 4, 5, 6}, {0, 1, 2}, {0, 1, 2, 3, 4, 5, 6}, {5, 6, 0, 1, 2}};


            for (int j : numbers[scoreParsed.get(i)]) {
                int xPoint = 10 + ledXPos[j];
                int yPoint = 10 + ledYPos[j];
                int[] xPoints = {xPoint, xPoint + width, xPoint + width, xPoint, xPoint - width, xPoint - width};
                int[] yPoints = {yPoint, yPoint + peak, yPoint + peak + height, yPoint + 2 * peak + height, yPoint + peak + height, yPoint + peak};
                g.setColor(Color.red);
                if (j == 0 | j == 5 | j == 3) { // changes x,y for the horizontal bars, the number 3 has all three bars
                    yPoints = new int[]{yPoint, yPoint + width, yPoint + width, yPoint, yPoint - width, yPoint - width};
                    xPoints = new int[]{xPoint, xPoint + peak, xPoint + peak + height, xPoint + 2 * peak + height, xPoint + peak + height, xPoint + peak};
                }
                g.fillPolygon(xPoints, yPoints, 6);
            }
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        if (grid.gameInPlay) {
            timer.start();
            if (!blockInPlay) {
                grid.startGame();
                blockInPlay = true;
            }
            grid.move(0, 1);
            if (enablePieceProjection) grid.dropOrPieceProjection(false);
            completeLineCheck();

            backUpScan();
        }
    }

    private void score() {
        score++;
        if (score == 10) {
            winGame();
        }
    }

    private int getScore() {
        return score;
    }

    private void winGame() {
    }

    private void loseGame() {
        grid.gameInPlay = false;
        grid.mergeGrids();
        while (score > 0) {
            score--;
            repaint();
        }
        System.out.println(score);

        grid.blockGrid = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        grid.swap = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        for (int k = grid.floorBound; k >3; k--) {

            System.out.println(" k"+k);
                for (int i = grid.lBound; i <= grid.rBound; i++) {
                grid.grid[i][k] = 0;
            }

            repaint();
            try {
                Thread.sleep(900);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //moves all blocks down

            for (int i = grid.lBound; i <= grid.rBound; i++) {
                for (int j = k; j > 5; j--) {
                    grid.grid[i][j] = grid.grid[i][j - 1];
                    grid.grid[i][j - 1] = 0;

                }

            }

        }
    }

    private void backUpScan() {
        d:
        for (int i = grid.xPos; i < grid.xPos + grid.blockGrid.length; i++) {
            for (int j = grid.yPos; j < grid.yPos + grid.blockGrid.length; j++) {
                if (grid.frozenPiecesGrid[i][j] != 0 & grid.blockGrid[i - grid.xPos][j - grid.yPos] != 0) {
                    loseGame();
                    break d;
                }
            }
        }
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
            repaint();
        }


        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //moves all blocks down

        for (int i = grid.lBound; i <= grid.rBound; i++) {
            for (int j = completedLineAt; j > 3; j--) {
                grid.frozenPiecesGrid[i][j] = grid.frozenPiecesGrid[i][j - 1];
                grid.frozenPiecesGrid[i][j - 1] = 0;repaint();
            }

        }

        if (grid.gameInPlay) score();
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
        if (e.getExtendedKeyCode() == KeyEvent.VK_SPACE) {
            grid.dropOrPieceProjection(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
