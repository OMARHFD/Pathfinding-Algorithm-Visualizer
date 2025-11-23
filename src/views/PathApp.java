package views;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import models.*;

public class PathApp extends Application {

    private static final int ROWS = 100;
    private static final int COLS = 100;
    private static final double CELL_SIZE = 10;

    private TextField startField;
    private TextField goalField;


    private Grid grid;
    private Rectangle[][] rects;

    private Pathfinding algorithm;
    private Timeline timeline;

    @Override
    public void start(Stage stage) {
        grid = new Grid(ROWS, COLS);

        Pane gridPane = createGridPane();

        // --- Text fields for coordinates ---
        startField = new TextField("0,0");      // default start
        startField.setPrefWidth(70);

        goalField = new TextField("10,10");     // default goal
        goalField.setPrefWidth(70);

        // --- Buttons ---
        Button runBfsButton = new Button("Run BFS");
        runBfsButton.setOnAction(e -> startBfs());

        Button runDfsButton = new Button("Run DFS");
        runDfsButton.setOnAction(e -> startDfs());

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> resetGrid());

        // --- Top bar (top-right) ---
        HBox topBar = new HBox(10,
                new Label("Start (r,c):"), startField,
                new Label("Goal (r,c):"),  goalField,
                runBfsButton, runDfsButton, resetButton);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER_RIGHT);

        BorderPane root = new BorderPane();
        root.setCenter(gridPane);
        root.setTop(topBar);

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.setTitle("Pathfinding Visualizer");
        stage.show();
    }

    private Pane createGridPane() {
        Pane pane = new Pane();
        pane.setPrefSize(COLS * CELL_SIZE, ROWS * CELL_SIZE);

        rects = new Rectangle[ROWS][COLS];

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                rect.setLayoutX(c * CELL_SIZE);
                rect.setLayoutY(r * CELL_SIZE);
                rect.setStroke(Color.DARKGRAY);
                rect.setFill(Color.LIGHTGRAY);

                final int row = r;
                final int col = c;

                rect.setOnMouseClicked(e -> {
                    // toggle wall on click
                    Cell cell = grid.getCell(row, col);
                    cell.setWall(!cell.isWall());
                    refreshGrid();
                });

                rects[r][c] = rect;
                pane.getChildren().add(rect);
            }
        }

        return pane;
    }

    private void startBfs() {
        Cell defaultStart = grid.getCell(0, 0);
        Cell defaultGoal  = grid.getCell(10, 10);

        Cell start = getCellFromField(startField, defaultStart);
        Cell goal  = getCellFromField(goalField,  defaultGoal);

        algorithm = new BFS();  // your BFS class implementing Pathfinding
        algorithm.init(grid, start, goal);

        startTimeline();
    }

    private void startDfs() {
        Cell defaultStart = grid.getCell(0, 0);
        Cell defaultGoal  = grid.getCell(10, 10);

        Cell start = getCellFromField(startField, defaultStart);
        Cell goal  = getCellFromField(goalField,  defaultGoal);

        algorithm = new DFS();  // your DFS class implementing Pathfinding
        algorithm.init(grid, start, goal);

        startTimeline();
    }


    private void startTimeline() {
        if (timeline != null) {
            timeline.stop();
        }

        timeline = new Timeline(new KeyFrame(Duration.millis(40), e -> {
            boolean finished = algorithm.step();
            refreshGrid();
            if (finished) {
                timeline.stop();
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void resetGrid() {
        // Stop animation if running
        if (timeline != null) {
            timeline.stop();
        }

        // Clear search state, but keep walls
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Cell cell = grid.getCell(r, c);
                // keep cell.isWall() as is
                cell.resetSearchState();   // make sure this exists in Cell
            }
        }

        algorithm = null;
        refreshGrid();
    }

    private void refreshGrid() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Cell cell = grid.getCell(r, c);
                Rectangle rect = rects[r][c];

                if (cell.isWall())          rect.setFill(Color.BLACK);
                else if (cell.isInPath())   rect.setFill(Color.RED);
                else if (cell.isInClosedSet()) rect.setFill(Color.GREEN);
                else if (cell.isInOpenSet())   rect.setFill(Color.BLUE);
                else                         rect.setFill(Color.LIGHTGRAY);
            }
        }
    }
    private Cell getCellFromField(TextField field, Cell defaultCell) {
        try {
            String text = field.getText().trim();
            String[] parts = text.split(",");
            if (parts.length != 2) return defaultCell;

            int row = Integer.parseInt(parts[0].trim());
            int col = Integer.parseInt(parts[1].trim());

            if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
                return defaultCell; // out of bounds → fallback
            }

            return grid.getCell(row, col);
        } catch (Exception e) {
            // any parse error → fallback
            return defaultCell;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
