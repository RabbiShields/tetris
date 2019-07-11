public class Grid {
    int lBound = 5;
    int rBound = 13;
    int floorBound = 21;
    int[][] frozenPiecesGrid;
    int[][] grid;
    int[][] blockGrid;
    int[][] swap = new int[4][4];
    private boolean collide = false;
    int xPos = (lBound + rBound) / 2;
    int yPos = 0;
    boolean gameInPlay = true;

    Grid() {
        grid = new int[30][35];
        frozenPiecesGrid = new int[30][35];
        blockGrid = new int[4][4];
        arrayInitializer(grid);
        arrayInitializer(frozenPiecesGrid);
        arrayInitializer(blockGrid);
        arrayInitializer(swap);
    }

    void startGame() {
        Blocks block = new Blocks();
        blockGrid = block.generateAblock();
        Blocks blocks = new Blocks();
        swap = blocks.generateAblock();
    }

    void swap() {
        int[][] temp = blockGrid;
        blockGrid = swap;
        swap = temp;
    }

    int[][] getSwap() {
        return swap;
    }

    void move(int xMove, int yMove) {
        collide = false;
        System.out.println("(x" + xPos + "," + yPos + ")");
        a:
        for (int i = xPos; i < xPos + blockGrid.length; i++) {
            for (int j = yPos; j < yPos + blockGrid.length; j++) {
                //System.out.print("(" + i + "," + j + ")");
                if (xMove == 1) {
                    if (frozenPiecesGrid[i + xMove][j] != 0 & blockGrid[i - xPos][j - yPos] != 0) {
                        collide = true;
                        break a;
                    }
                    if (blockGrid[i - xPos][j - yPos] != 0 & i >= rBound) {
                        collide = true;
                        break a;
                    }
                }
                if (xMove == -1) {
                    if (frozenPiecesGrid[i + xMove][j] != 0 & blockGrid[i - xPos][j - yPos] != 0) {
                        collide = true;
                        break a;
                    }
                    if (blockGrid[i - xPos][j - yPos] != 0 & i <= lBound) {
                        collide = true;
                        break a;
                    }
                }
                if (yMove == 1) {
                    if (blockGrid[i - xPos][j - yPos] != 0 & j >= floorBound) {
                        collide = true;
                        blockIsDoneFalling();
                        break a;
                    }
                }
                if (frozenPiecesGrid[i][j + 1] != 0 & blockGrid[i - xPos][j - yPos] != 0) {
                    collide = true;
                    blockIsDoneFalling();
                    break a;
                }
            }
        }
        if (!collide) {
            xPos += xMove;
            yPos += yMove;
            mergeGrids();
        }
    }

    int[][] getGrid() {
        return grid;
    }

    int getBlockGridLength() {
        return blockGrid.length;
    }

    //projects a where a falling piece will land
    // makes a tempYPos and checks if that fake y position will cause a
// collition keeps adding one to that Y Pos to see if it collides.
    void dropOrPieceProjection(boolean dropPiece) {
        boolean projectionScanDone = false;
        int tempYPos = yPos;
        while (!projectionScanDone) {
            b:
            for (int i = xPos; i < xPos + blockGrid.length; i++) {
                for (int j = tempYPos; j < tempYPos + blockGrid.length; j++) {
                    //System.out.print("(i" + i + "," + j + ")");
                    //System.out.print(projectionScanDone);
                    if (frozenPiecesGrid[i][j + 1] != 0 & blockGrid[i - xPos][j - tempYPos] != 0) {
                        projectionScanDone = true;
                        break b;
                    }
                    if (blockGrid[i - xPos][j - tempYPos] != 0 & j >= floorBound) {
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
                    if (dropPiece)
                        if (blockGrid[i - xPos][j - tempYPos] != 0)
                            frozenPiecesGrid[i][j] = blockGrid[i - xPos][j - tempYPos];
                    if (!dropPiece)
                        if (blockGrid[i - xPos][j - tempYPos] != 0 & grid[i][j] == 0)
                            grid[i][j] = 5;
                }
            }
            if (dropPiece & gameInPlay) {
                Blocks block = new Blocks();
                xPos = (lBound + rBound) / 2;
                yPos = 0;
                swap();
                swap = block.generateAblock();
            }
        }
    }

    void rotate() {
        int[][] newPieceArray =
                new int[blockGrid.length][blockGrid[0].length];
        int columnForNew = blockGrid.length - 1;
        int rowForNew = 0;
        for (int[] ints : blockGrid) {
            for (int anInt : ints) {
                newPieceArray[rowForNew][columnForNew] = anInt;
                rowForNew++;
                if (rowForNew == blockGrid.length)
                    rowForNew = 0;
            }
            columnForNew--;
        }


        collide = false;
        boolean outOfBounds = true;
        int tempX = xPos;
        for (int k = 0; k < 8; k++) {
            collide = false;
            outOfBounds = false;
            e:
            for (int i = tempX; i < tempX + blockGrid.length; i++) {
                for (int j = yPos; j < yPos + blockGrid.length; j++) {
                    if (frozenPiecesGrid[i][j] != 0 & newPieceArray[i - tempX][j - yPos] != 0) {
                        collide = true;
                    }

                    if (newPieceArray[i - tempX][j - yPos] != 0) {
                        if (i > rBound) {
                            outOfBounds = true;
                            tempX--;
                            break e;
                        }
                        if (i < lBound) {
                            outOfBounds = true;
                            tempX++;
                            break e;
                        }
                    }
                }
            }
            if (!outOfBounds & !collide) {
                xPos = tempX;
                break;
            }
        }
        if (!collide) blockGrid = newPieceArray;
    }

    private void blockIsDoneFalling() {
        collide = false;
        for (int i = xPos; i < xPos + blockGrid.length; i++) {
            for (int j = yPos; j < yPos + blockGrid.length; j++) {
                if (blockGrid[i - xPos][j - yPos] != 0) {
                    frozenPiecesGrid[i][j] = blockGrid[i - xPos][j - yPos];
                }
            }
        }
        if (gameInPlay){
        Blocks block = new Blocks();
        xPos = (lBound + rBound) / 2;
        yPos = 0;
        swap();
        swap = block.generateAblock();
        }
    }

    void mergeGrids() {
        for (int i = 0; i < grid.length - 1; i++) {
            for (int j = 0; j < grid[i].length - 1; j++) {
                grid[i][j] = frozenPiecesGrid[i][j];
            }
        }
        for (int i = xPos; i < xPos + blockGrid.length; i++) {
            for (int j = yPos; j < yPos + blockGrid.length; j++)
                if (blockGrid[i - xPos][j - yPos] != 0) grid[i][j] =
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


}