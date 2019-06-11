public class Blocks {
    private int xPos = 0;
    private int yPos = 0;

    public Blocks() {
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }



    public void arrayInitializer(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = 0;
            }
        }
    }

    public void rotateAblock(int[][] grid) {


        int[][] newGrid = new int[30][30];

    }


}
