public class Grid {
    private int[][] frozenPeicesGrid;
    private int[][] grid;
    private int xPos = 10;
    private int yPos = 2;
    private int[][] blockGrid;
    private boolean colide = false;
    boolean projectionScanDone = false;

    Grid() {
        grid = new int[30][35];
        frozenPeicesGrid = new int[30][35];
        blockGrid = new int[5][5];
        grid = arrayInitializer(grid);
        frozenPeicesGrid = arrayInitializer(frozenPeicesGrid);
        blockGrid = arrayInitializer(blockGrid);
    }

    void startGame() {
        Blocks block = new Blocks();
        blockGrid = block.generateAblock();
    }

    void move(int xMove, int yMove) {
        colide = false;
        System.out.println("(x" + xPos + "," + yPos + ")");
        a:
        for (int i = xPos; i < xPos + 4; i++) {
            for (int j = yPos; j < yPos + 4; j++) {
                //System.out.print("(" + i + "," + j + ")");
                if (xMove == 1) {
                    if (frozenPeicesGrid[i+xMove][j] == 1 & blockGrid[i - xPos][j - yPos] == 1) {
                        colide = true;
                        break a;
                    }
                    if (blockGrid[i - xPos][j - yPos] == 1 & i > 24) {
                        colide = true;
                        break a;
                    }
                }
                if (xMove == -1) {
                    if (frozenPeicesGrid[i+xMove][j] == 1 & blockGrid[i - xPos][j - yPos] == 1) {
                        colide = true;
                        break a;
                    }
                    if (blockGrid[i - xPos][j - yPos] == 1 & i < 6) {
                        colide = true;
                        break a;
                    }
                }
                if (yMove == 1) {
                    if (blockGrid[i - xPos][j - yPos] == 1 & j > 25) {
                        colide = true;
                        blockIsDoneFalling();
                        break a;
                    }
                }
                if (frozenPeicesGrid[i][j + 1] == 1 & blockGrid[i - xPos][j - yPos] == 1) {
                    colide = true;
                    blockIsDoneFalling();
                    break a;
                }
            }
        }
        if (!colide) {
            xPos += xMove;
            yPos += yMove;
            mergeGrids();
        }
    }

    int[][] getGrid() {
        return grid;
    }

    void pieceProjection() {
        projectionScanDone = false;
        int tempYPos = yPos;
        while (!projectionScanDone) {
            b: for (int i = xPos; i < xPos + 4; i++) {
                for (int j = tempYPos; j < tempYPos + 4; j++) {
                    //System.out.print("(i" + i + "," + j + ")");
                    //System.out.print(projectionScanDone);
                    if (frozenPeicesGrid[i][j + 1] == 1 & blockGrid[i - xPos][j - tempYPos] == 1) {
                        projectionScanDone = true;
                        break b;
                    }
                    if (blockGrid[i - xPos][j - tempYPos] == 1 & j > 25) {
                        projectionScanDone = true;
                        break b;
                    }
                }
            }
            if  (!projectionScanDone)tempYPos++;
        }
        if (projectionScanDone) {
            for (int i = xPos; i < xPos + 4; i++) {
                for (int j = tempYPos; j < tempYPos + 4; j++) {
                    if (blockGrid[i - xPos][j - tempYPos] == 1) grid[i][j] = blockGrid[i - xPos][j - tempYPos];
                }
            }
        }
    }

    private void blockIsDoneFalling() {
        colide = false;
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
    }


    private void mergeGrids() {
        for (int i = 0; i < grid.length - 1; i++) {
            for (int j = 0; j < grid[i].length - 1; j++) {
                grid[i][j] = frozenPeicesGrid[i][j];
            }
        }
        for (int i = xPos; i < xPos + 4; i++) {
            for (int j = yPos; j < yPos + 4; j++)
                if (blockGrid[i - xPos][j - yPos] == 1) grid[i][j] =
                        blockGrid[i - xPos][j - yPos];
        }
    }
    private int[][] arrayInitializer(int[][] grid) {
        for (int i = 0; i < grid.length - 1; i++) {
            for (int j = 0; j < grid[i].length - 1; j++) {
                grid[i][j] = 0;
            }
        }
        return grid;
    }
}