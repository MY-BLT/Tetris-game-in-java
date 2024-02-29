import java.util.Scanner;

/**
     * This class represents a simple Tetris game where blocks fall down the game grid.
     * The grid size is 10x20 represented by a boolean 2D array.
     * The game will lock blocks when they reach the bottom, clear full rows, and end when a block reaches the top of the grid.
     * Blocks are represented by the 'X' character, filled cells by '■', and empty cells by a space ' '.
     */
    public class Test {

        /**
         * The width of the game grid.
         */
        private static final int GAME_WIDTH = 10;

        /**
         * The height of the game grid.
         */
        private static final int GAME_HEIGHT = 20;

        /**
         * The 2D array representing the game grid.
         */
        private static boolean[][] grid = new boolean[GAME_HEIGHT][GAME_WIDTH];

        /**
         * The current row of the falling block.
         */
        private static int currentRow = 0;

        /**
         * The current column of the falling block.
         */
        private static int currentCol = GAME_WIDTH / 2;

        /**
         * Flag to indicate if the game is over.
         */
        private static boolean gameOver = false;

        /**
         * The main method to run the Tetris game.
         *
         * @param args The command line arguments (unused).
         */
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            while (!gameOver) {
                printGrid();
                if (!moveBlockDown()) {
                    lockBlock();
                    clearRows();
                    gameOver = isGameOver();
                    currentRow = 0;
                    currentCol = GAME_WIDTH / 2;
                }
            }

            System.out.println("Game over! 🙁");
            scanner.close();
        }

        /**
         * Prints the current state of the game grid with the falling block and filled cells.
         */
        private static void printGrid() {
            for (int i = 0; i < GAME_HEIGHT; i++) {
                for (int j = 0; j < GAME_WIDTH; j++) {
                    if (i == currentRow && j == currentCol) {
                        System.out.print("X");
                    } else {
                        System.out.print(grid[i][j] ? "■" : " ");
                    }
                }
                System.out.println();
            }
        }

        /**
         * Moves the block down by one row if possible.
         *
         * @return true if the block successfully moves down, false if it cannot move down further.
         */
        private static boolean moveBlockDown() {
            if (currentRow + 1 < GAME_HEIGHT) {
                currentRow++;
                return true;
            } else {
                return false;
            }
        }

        /**
         * Locks the current block into the grid at its current position.
         */
        private static void lockBlock() {
            grid[currentRow][currentCol] = true;
        }

        /**
         * Clears any full rows in the grid, shifting down rows above them and adding a new empty row at the top.
         */
        private static void clearRows() {
            for (int i = GAME_HEIGHT - 1; i >= 0; i--) {
                boolean rowFull = true;
                for (int j = 0; j < GAME_WIDTH; j++) {
                    if (!grid[i][j]) {
                        rowFull = false;
                        break;
                    }
                }
                if (rowFull) {
                    for (int k = i; k > 0; k--) {
                        System.arraycopy(grid[k - 1], 0, grid[k], 0, GAME_WIDTH);
                    }
                    grid[0] = new boolean[GAME_WIDTH];
                }
            }
        }

        /**
         * Checks if the game is over by looking at the top row of the grid for any filled cells.
         *
         * @return true if the game is over, false otherwise.
         */
        private static boolean isGameOver() {
            for (int i = 0; i < GAME_WIDTH; i++) {
                if (grid[0][i]) {
                    return true;
                }
            }
            return false;
        }

    }

