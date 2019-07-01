import java.util.Random;

public class Blocks {
    private int[][] lBlock = {{0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}, {0, 1
            , 1, 0}};
    private int[][] dotBlock = {{0, 0, 0, 0}, {0, 0, 0, 0}, {1, 0, 0, 0}, {0, 0
            , 0, 0}};
    private int[][] squareBlock = {{0, 0, 0, 0}, {0, 1, 1, 0}, {0, 1, 1, 0},
            {0, 0, 0, 0}};

    public Blocks() {}

    public int[][] generateAblock() {
        int[][] newBlock = new int[5][5];
        Random r = new Random();
        int which = r.nextInt(3) + 1;
        System.out.println(" ");
        System.out.print(which + "number");
        switch (which) {
            case 1:
                newBlock = lBlock;
                break;
            case 2:
                newBlock = dotBlock;
                break;
            case 3:
                newBlock = squareBlock;
                break;
        }
        return newBlock;
    }

    public void rotateAblock(int[][] grid) {
        int[][] newGrid = new int[30][40];
    }
}