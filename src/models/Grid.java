package models;
import java.util.*;

public class Grid {
    private final int rows;
    private final int cols;
    private Cell[][] cells;

    public  Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells= new Cell[rows][cols];
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                this.cells[i][j]=new Cell(i,j);
            }
        }
    }
    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }
    public Cell[][] getCells() {
        return cells;
    }
    public Cell getCell(int i, int j) {
        if(!inBounds(i,j)) return null;
        return cells[i][j];
    }

    public boolean inBounds(int i, int j) {
        return i>=0 && i<rows && j>=0 && j<cols;
    }

    public List<Cell> getNeighbors(Cell cell) {

        if (inBounds(cell.getRow(), cell.getCol())){

            List<Cell> res = new ArrayList<>();
            if (inBounds( cell.getRow(), cell.getCol() - 1)) {
                res.add(cells[cell.getRow()][cell.getCol() - 1]);
            }
            if (inBounds( cell.getRow(), cell.getCol() + 1)) {
                res.add(cells[cell.getRow()][cell.getCol() + 1]);
            }
            if (inBounds( cell.getRow()-1, cell.getCol())) {
                res.add(cells[cell.getRow() - 1][cell.getCol()]);
            }
            if (inBounds( cell.getRow()+1, cell.getCol())) {
                res.add(cells[cell.getRow() + 1][cell.getCol()]);
            }
            return res;
        }
        return null;
    }
    public void resetSearchState() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                cells[r][c].resetSearchState();
            }
        }
    }



}
