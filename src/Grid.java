public class Grid {
    private int[][] frozenPeicesGrid;
    private int[][] grid;

    private int[][] blockGrid;
    private boolean colide = false;
    private int lBound = 10;
    private int rBound = 18;
    private int floorBound = 25;
    private int xPos = lBound;
    private int yPos = 2;

    Grid() {
        grid = new int[30][35];
        frozenPeicesGrid = new int[30][35];
        blockGrid = new int[5][5];
        arrayInitializer(grid);
        arrayInitializer(frozenPeicesGrid);
        arrayInitializer(blockGrid);
    }

    void startGame() {
        Blocks block = new Blocks();
        blockGrid = block.generateAblock();
    }

    void move(int xMove, int yMove) {
        colide = false;
        System.out.println("(x" + xPos + "," + yPos + ")");
        a:
        for (int i = xPos; i < xPos + blockGrid.length; i++) {
            for (int j = yPos; j < yPos + blockGrid.length; j++) {
                //System.out.print("(" + i + "," + j + ")");
                if (xMove == 1) {
                    if (frozenPeicesGrid[i + xMove][j] == 1 & blockGrid[i - xPos][j - yPos] == 1) {
                        colide = true;
                        break a;
                    }
                    if (blockGrid[i - xPos][j - yPos] == 1 & i >= rBound) {
                        colide = true;
                        break a;
                    }
                }
                if (xMove == -1) {
                    if (frozenPeicesGrid[i + xMove][j] == 1 & blockGrid[i - xPos][j - yPos] == 1) {
                        colide = true;
                        break a;
                    }
                    if (blockGrid[i - xPos][j - yPos] == 1 & i <= lBound) {
                        colide = true;
                        break a;
                    }
                }
                if (yMove == 1) {
                    if (blockGrid[i - xPos][j - yPos] == 1 & j >= floorBound) {
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

    //projects a where a falling piece will land
    // makes a tempYPos and checks if that fake y position will cause a
// collition keeps adding one to that Y Pos to see if it collides.
    void pieceProjection() {
        boolean projectionScanDone = false;
        int tempYPos = yPos;
        while (!projectionScanDone) {
            b:
            for (int i = xPos; i < xPos + blockGrid.length; i++) {
                for (int j = tempYPos; j < tempYPos + blockGrid.length; j++) {
                    //System.out.print("(i" + i + "," + j + ")");
                    //System.out.print(projectionScanDone);
                    if (frozenPeicesGrid[i][j + 1] == 1 & blockGrid[i - xPos][j - tempYPos] == 1) {
                        projectionScanDone = true;
                        break b;
                    }
                    if (blockGrid[i - xPos][j - tempYPos] == 1 & j >= floorBound) {
                        projectionScanDone = true;
                        break b;
                    }
                }
            }
            if (!projectionScanDone) tempYPos++;
        }
        if (projectionScanDone) {
            for (int i = xPos; i < xPos + blockGrid.length; i++) {
                for (int j = tempYPos; j < tempYPos + blockGrid.length; j++) {
                    if (blockGrid[i - xPos][j - tempYPos] == 1) grid[i][j] = 2;
                }
            }
        }
    }

    private void blockIsDoneFalling() {
        colide = false;
        for (int i = xPos; i < xPos + blockGrid.length; i++) {
            for (int j = yPos; j < yPos + blockGrid.length; j++) {
                if (blockGrid[i - xPos][j - yPos] == 1) {
                    frozenPeicesGrid[i][j] = blockGrid[i - xPos][j - yPos];
                }
            }
        }
        Blocks block = new Blocks();
        xPos = lBound + 2 ;
        yPos = 2;
        blockGrid = block.generateAblock();
    }


    private void mergeGrids() {
        for (int i = 0; i < grid.length - 1; i++) {
            for (int j = 0; j < grid[i].length - 1; j++) {
                grid[i][j] = frozenPeicesGrid[i][j];
            }
        }
        for (int i = xPos; i < xPos + blockGrid.length; i++) {
            for (int j = yPos; j < yPos + blockGrid.length; j++)
                if (blockGrid[i - xPos][j - yPos] == 1) grid[i][j] =
                        blockGrid[i - xPos][j - yPos];
        }
    }

    private void arrayInitializer(int[][] grid) {
        for (int i = 0; i < grid.length - 1; i++) {
            for (int j = 0; j < grid[i].length - 1; j++) {
                grid[i][j] = 0;
            }
        }

    }

    public void completeLineCheck() {
        boolean lineComplete = true;
        for (int i = lBound; i < rBound; i++) {
            if (frozenPeicesGrid[floorBound][i] == 0) {
                lineComplete = false;
                break;
            }
        }
        if (lineComplete) {
            removeLine();
        }
    }

    private void removeLine() {
        for (int i = lBound; i < rBound; i++) {
            frozenPeicesGrid[floorBound][i] = 0;
        }
    }
}