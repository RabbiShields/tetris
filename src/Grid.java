public class Grid {
    private int height;
    private int width;
    private boolean[][] grid = new boolean[30][30];

    public Grid() {
        this.height = 20;
        this.width = 20;
    }

    boolean[][] getGrid() {
        return grid;
    }

    public Grid(int height, int width) {
        this.height = height;
        this.width = width;
    }

}
