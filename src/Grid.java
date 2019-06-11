public class Grid {
    private int[][] grid;
    private int xPos = 0;
    private int yPos = 0;

    Grid() {
        grid = new int[30][30];
    }

    public void shiftAllBlocksDown() {
        for (int i = grid.length-1; i > 1; i--) {
            for (int j = grid[i].length - 1; j > 0; j--) {
                if (grid[i][j] == 1) {
                    grid[i][j + 1] = 1;
                    grid[i][j] = 0;
                }
            }
        }
    }

    public int[][] addABlock(int[][] grid) {
        int xCenter = grid.length / 2;
        grid[xCenter][2] = 1;
        xPos = xCenter - 2;
        yPos = 0;
        return grid;
    }

    int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }


}
