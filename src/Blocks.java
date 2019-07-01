import java.util.Random;

public class Blocks {
    private int[][] lBlock = {{0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}, {0, 1
            , 1, 0}};
    private int[][] TBlock = {{0, 0, 1, 0}, {0, 1,1, 0}, {0, 0, 1, 0}, {0, 0
            , 0, 0}};
    private int[][] squareBlock = {{0, 0, 0, 0}, {0, 1, 1, 0}, {0, 1, 1, 0},
            {0, 0, 0, 0}};

    public Blocks() {}

    public int[][] generateAblock() {
        int[][] newBlock = new int[4][4];
        Random r = new Random();
        int which = r.nextInt(3);
        System.out.println(" ");
        System.out.print(which + "number");
        switch (which) {
            case 0:
                newBlock = lBlock;
                break;
            case 1:
                newBlock = TBlock;
                break;
            case 2:
                newBlock = squareBlock;
                break;
        }
        return newBlock;
    }

    public void rotateAblock(int[][] grid) {
        int[][] newGrid = new int[30][40];
    }
}