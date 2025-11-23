package models;

import java.util.List;

public interface Pathfinding {

    void init(Grid grid, Cell start, Cell goal);

    /**
     * Performs one iteration of the algorithm.
     * @return true if the algorithm has finished (found path or concluded no path).
     */
    boolean step();

    boolean isFinished();

    boolean hasPath();

    /**
     * Returns the reconstructed path from start to goal (if hasPath() == true).
     */
    List<Cell> getPath();
}
