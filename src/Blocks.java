import java.util.Random;

public class Blocks {
    private boolean[][] lBlock = {{false, false, true, false}, {false, false,
            true, false}, {false, true, true, false}, {false, false, false, false}};
    private boolean[][] tBlock = {{false, false, true, false}, {false, true,
            true, false}, {false, false, true, false}, {false, false, false, false}};
    private boolean[][] squareBlock = {{false, false, false, false}, {false,
            true, true, false}, {false, true, true, false}, {false, false, false, false}};
    private boolean[][] iBlock = {{false, false, false, false}, {true, true,
            true, true}, {false, false, false, false}, {false, false, false, false}};
    private int blockSize = 4;

    public Blocks() {
    }

    public int[][] generateAblock() {
        int[][] newBlock = new int[blockSize][blockSize];

        //color chooser
        Random c = new Random();
        int color = c.nextInt(3)+1;

        //block chooser
        Random r = new Random();
        int which = r.nextInt(4);
        System.out.println(" ");
        System.out.print(which + "number");
        switch (which) {
            case 0:
                newBlock = blockMaker(color, lBlock);
                break;
            case 1:
                newBlock = blockMaker(color, tBlock);
                break;
            case 2:
                newBlock = blockMaker(color, squareBlock);
                break;
            case 3:
                newBlock = blockMaker(color, iBlock);
                break;
        }
        return newBlock;
    }

    private int[][] blockMaker(int color, boolean[][] tempBlock) {
        int[][] newBlock = new int[blockSize][blockSize];
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                if (tempBlock[i][j]) newBlock[i][j] = color;
                if (!tempBlock[i][j]) newBlock[i][j] = 0;
            }
        }
        return newBlock;
    }


}