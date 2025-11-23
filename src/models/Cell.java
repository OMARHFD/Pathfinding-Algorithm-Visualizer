package models;

public class Cell {
    private final int row;
    private final int col;

    private boolean wall;
    private boolean visited;
    private boolean inOpenSet;
    private boolean inClosedSet;
    private boolean inPath;

    private Cell parent;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;

    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public boolean isWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isInOpenSet() {
        return inOpenSet;
    }

    public void setInOpenSet(boolean inOpenSet) {
        this.inOpenSet = inOpenSet;
    }

    public boolean isInClosedSet() {
        return inClosedSet;
    }

    public void setInClosedSet(boolean inClosedSet) {
        this.inClosedSet = inClosedSet;
    }

    public boolean isInPath() {
        return inPath;
    }

    public void setInPath(boolean inPath) {
        this.inPath = inPath;
    }

    public Cell getParent() {
        return parent;
    }

    public void setParent(Cell parent) {
        this.parent = parent;
    }

    public void resetSearchState() {
        visited = false;
        inOpenSet = false;
        inClosedSet = false;
        inPath = false;
        parent = null;
    }

}
