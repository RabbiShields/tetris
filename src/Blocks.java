public class Blocks {


    public Blocks() {
    }

    public void addABlock(boolean[][] grid){
        int xCenter = grid.length/2;
        grid[xCenter][0]= true;
    }



}
