public class Grid {
    int lBound = 5;
    int rBound = 13;
    int floorBound = 21;
    private int[][] frozenPiecesGrid;
    private int[][] grid;
    private int[][] blockGrid;
    private boolean colide = false;
    private int xPos = (lBound + rBound) / 2;
    private int yPos = 0;


    Grid() {
        grid = new int[30][35];
        frozenPiecesGrid = new int[30][35];
        blockGrid = new int[4][4];
        arrayInitializer(grid);
        arrayInitializer(frozenPiecesGrid);
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
                    if (frozenPiecesGrid[i + xMove][j] == 1 & blockGrid[i - xPos][j - yPos] == 1) {
                        colide = true;
                        break a;
                    }
                    if (blockGrid[i - xPos][j - yPos] == 1 & i >= rBound) {
                        colide = true;
                        break a;
                    }
                }
                if (xMove == -1) {
                    if (frozenPiecesGrid[i + xMove][j] == 1 & blockGrid[i - xPos][j - yPos] == 1) {
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
                if (frozenPiecesGrid[i][j + 1] == 1 & blockGrid[i - xPos][j - yPos] == 1) {
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
                    if (frozenPiecesGrid[i][j + 1] == 1 & blockGrid[i - xPos][j - tempYPos] == 1) {
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
                    frozenPiecesGrid[i][j] = blockGrid[i - xPos][j - yPos];
                }
            }
        }
        Blocks block = new Blocks();
        xPos = (lBound + rBound) / 2;
        ;
        yPos = 1;
        blockGrid = block.generateAblock();
    }

    private void mergeGrids() {
        for (int i = 0; i < grid.length - 1; i++) {
            for (int j = 0; j < grid[i].length - 1; j++) {
                grid[i][j] = frozenPiecesGrid[i][j];
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
        for (int j = 25; j > 3; j--) {
            boolean lineComplete = true;
            for (int i = lBound; i <= rBound; i++) {
                if (frozenPiecesGrid[i][j] == 0) {
                    lineComplete = false;
                }
            }
            if (lineComplete) {
                removeLine(j);
            }
        }
    }

    private void removeLine(int completedLineAt) {
        //removes completed line
        for (int i = lBound; i <= rBound; i++) {
            frozenPiecesGrid[i][completedLineAt] = 0;
        }

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //moves all blocks down

        for (int i = lBound; i <= rBound; i++) {
            for (int j = completedLineAt; j > 3; j--) {
                frozenPiecesGrid[i][j] = frozenPiecesGrid[i][j - 1];
                frozenPiecesGrid[i][j - 1] = 0;
            }
        }
    }


    public void rotate() {
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


        colide = false;
        boolean outOfBounds = true;
        int tempX = xPos;
        for (int k = 0; k < 8; k++) {
            colide = false;
            outOfBounds = false;
            e:
            for (int i = tempX; i < tempX + blockGrid.length; i++) {
                for (int j = yPos; j < yPos + blockGrid.length; j++) {
                    if (frozenPiecesGrid[i][j] == 1 & newPieceArray[i - tempX][j - yPos] == 1) {
                        colide = true;
                    }

                    if (newPieceArray[i - tempX][j - yPos] == 1) {
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
            if (!outOfBounds & !colide) {
                xPos = tempX;
                break;
            }
        }
        if (!colide) blockGrid = newPieceArray;
    }

}