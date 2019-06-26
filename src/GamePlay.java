import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private Timer timer;
    private Grid grid = new Grid();
    private int[][] gridCoOr = grid.getGrid();
    private boolean blockInPlay = false;


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
        g.setColor(Color.black);
        g.fillRect(1, 1, 700, 800);

        gridCoOr = grid.getGrid();
        for (int i = 0; i < gridCoOr.length; i++) {
            for (int j = 0; j < gridCoOr[i].length; j++) {
                if (gridCoOr[i][j] != 1 && gridCoOr[i][j] != 0) gridCoOr[i][j]
                        = 0; // turns all null into 0s
                if (gridCoOr[i][j] == 1) {
                    g.setColor(Color.white);
                    g.fillRect((i * 20)+3, (j * 20)+3, 14, 14);
                }
            }
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(!blockInPlay) {
            grid.startGame();
            blockInPlay = true;
        }
        grid.move(0,1);
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getExtendedKeyCode() == KeyEvent.VK_UP) {
            grid.move(0,-1);
        }
        if (e.getExtendedKeyCode() == KeyEvent.VK_RIGHT) {
            grid.move(1,0);
        }
        if (e.getExtendedKeyCode() == KeyEvent.VK_LEFT) {
            grid.move(-1,0);
        }
        if (e.getExtendedKeyCode() == KeyEvent.VK_DOWN) {
            grid.move(0,1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
