import java.util.Random;

public class Blocks {
    private int[][] lBlock = {{0, 0, 1, 0}, {0, 0, 1, 0}, {0, 1, 1, 0}, {0, 0
            , 0, 0}};
    private int[][] tBlock = {{0, 0, 1, 0}, {0, 1,1, 0}, {0, 0, 1, 0}, {0, 0
            , 0, 0}};
    private int[][] squareBlock = {{0, 0, 0, 0}, {0, 1, 1, 0}, {0, 1, 1, 0},
            {0, 0, 0, 0}};
    private int[][] iBlock = {{0, 0, 0, 0}, {1, 1, 1, 1}, {0, 0, 0, 0},
            {0, 0, 0, 0}};
    private int blockSize=4;

    public Blocks() {}

    public int[][] generateAblock() {
        int[][] newBlock = new int[blockSize][blockSize];
        Random r = new Random();
        int which = r.nextInt(4);
        System.out.println(" ");
        System.out.print(which + "number");
        switch (which) {
            case 0:
                newBlock = lBlock;
                break;
            case 1:
                newBlock = tBlock;
                break;
            case 2:
                newBlock = squareBlock;
                break;
            case 3:
                newBlock = iBlock;
                break;
        }
        return newBlock;
    }
}