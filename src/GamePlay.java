import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private Timer timer;
    private int i = 0;
    private int j = 0;
    private Grid grid = new Grid();
    private boolean[][] gridCoOr = grid.getGrid();

    GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        int delay = 10;
        timer = new Timer(delay, this);
        timer.start();

    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 700, 800);

        Random r = new Random();
        int which = r.nextInt(3);
        switch (which) {
            case 1:
            case 2:
            case 3:
        }


        for (i = 0; i < gridCoOr.length; i++) {
            for (j = 0; j < gridCoOr[i].length; j++) {
                if (gridCoOr[i][j]) {
                    g.setColor(Color.white);
                    g.fillRect(i * 20, j * 20, 20, 20);
                }
            }
        }


        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        boolean[][] gridCoOr = grid.getGrid();
        Blocks block = new Blocks();
        if (e.getExtendedKeyCode() == KeyEvent.VK_UP) {
            block.addABlock(gridCoOr);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
