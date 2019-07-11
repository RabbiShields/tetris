import javax.swing.*;

public class Main {
    static boolean gameStart =false;
    private static JFrame obj = new JFrame();
    private static MenuScreen menu = new MenuScreen();

    public static void main(String[] args) {
        obj.setBounds(10, 10, 700, 800);
        obj.setTitle("Tetris");
        obj.setResizable(true);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePlay gamePlay = new GamePlay();
        obj.add(gamePlay);
        
    }

    static void gameStart(){
        gameStart =true;
        System.out.print("started");
        obj.remove(menu);
        GamePlay gamePlay = new GamePlay();
        obj.add(gamePlay);
    }
}
