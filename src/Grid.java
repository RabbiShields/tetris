public class Grid {
    private int[][] frozenPeicesGrid;
    private int[][] grid;
    private int xPos = 10;
    private int yPos = 2;
    private int[][] blockGrid;
    private boolean colide = false;


    Grid() {
        grid = new int[30][35];
        frozenPeicesGrid = new int[30][35];
        blockGrid = new int[5][5];
        grid = arrayInitializer(grid);
        frozenPeicesGrid = arrayInitializer(frozenPeicesGrid);
        blockGrid = arrayInitializer(blockGrid);
    }

    private int[][] arrayInitializer(int[][] grid) {
        for (int i = 0; i < grid.length - 1; i++) {
            for (int j = 0; j < grid[i].length - 1; j++) {
                grid[i][j] = 0;
            }
        }
        return grid;
    }

    public void startGame() {
        Blocks block = new Blocks();
        blockGrid = block.generateAblock();
    }


    public void shiftAllBlocksDown() {

    }


    private void coilitionDetection() {
        for (int i = xPos; i < xPos + 4; i++) {
            for (int j = yPos; j < yPos + 4; j++) {
                System.out.println(j + "j");
                System.out.println(i + "i");
                if (blockGrid[i - xPos][j - yPos] == 1 && j > 25) {
                    colide = true;
                    yPos--;
                    blockIsDoneFalling();

                }
                if (frozenPeicesGrid[i][j] == 1 && blockGrid[i - xPos][j - yPos] == 1) {
                    colide = true;
                    yPos--;
                    blockIsDoneFalling();

                }
            }
        }
    }

    private void blockIsDoneFalling() {
        for (int i = xPos; i < xPos + 4; i++) {
            for (int j = yPos; j < yPos + 4; j++) {
                if (blockGrid[i - xPos][j - yPos] == 1) {
                    frozenPeicesGrid[i][j] = blockGrid[i - xPos][j - yPos];
                }
            }
        }
        Blocks block = new Blocks();
        xPos = 10;
        yPos = 2;
        blockGrid = block.generateAblock();
        colide = false;
    }


    private void mergeGrids() {
        for (int i = 0; i < grid.length - 1; i++) {
            for (int j = 0; j < grid[i].length - 1; j++) {
                grid[i][j] = frozenPeicesGrid[i][j];
            }
        }
        for (int i = xPos; i < xPos + 4; i++) {
            for (int j = yPos; j < yPos + 4; j++)
                grid[i][j] = blockGrid[i - xPos][j - yPos];
        }
    }

    public void move(int xMove, int yMove) {
        if (1 < xPos && 23 > xPos) xPos += xMove;
        if (!colide) yPos += yMove;
        coilitionDetection();
        colide = false;
        yPos++;

        coilitionDetection();
        if (!colide) {
            mergeGrids();
        }
    }


    public int[][] getGrid() {
        return grid;
    }


}
