import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private Timer timer;

    //turns off all shapes
    int numBlocks = 4;
    private boolean lShape = true;
    private boolean tShape = false;
    private boolean boxShape = false;
    private boolean lineShape = false;


    GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        int delay = 10;
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {

        //background
        g.setColor(Color.black);
        g.fillRect(0, 0, 700, 800);

        Random r = new Random();
        int which = r.nextInt(3);

        g.setColor(Color.yellow);
        g.draw3DRect(25,25,20,20, true);

        g.dispose();
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getExtendedKeyCode() == KeyEvent.VK_UP) {
        }
        if (e.getExtendedKeyCode() == KeyEvent.VK_DOWN) {
        }
        //if (e.getExtendedKeyCode() == KeyEvent.VK_RIGHT) {moveRight()}
        //if (e.getExtendedKeyCode() == KeyEvent.VK_LEFT){moveRight();}
    }
    // private int moveRight(){
    //     return ;
    // }
    //  private int moveRight(){
    //     return ;
    //  }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
