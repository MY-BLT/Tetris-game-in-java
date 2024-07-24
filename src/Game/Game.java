package Game;

import java.util.Scanner;

/**
 * @author M.Yasin B.Loghmani.T
 * This class is responsible for updating the board and executing actions on the
 * board during the game.
 */
public class Game {
    Scanner input = new Scanner(System.in);

    private Board board = new Board();
    private Shape shape = new Shape();

    private int score = 0;
    private boolean isGameOver = false;
    private int height = board.getBoardHeight();
    private int width = board.getBoardWidth();

    private final int FULL_CELL = 1;
    private final int EMPTY_CELL = 0;
    private final int SHAPE_CELL = -1;

    private final String MOVE_LEFT = "L";
    private final String MOVE_RIGHT = "R";
    private final String ROTATE = "O";
    private final String MOVE_DOWN = "D";

    /**
     * The `play` function in Java controls the movement and rotation of shapes on a
     * game board based
     * on user input.
     */
    /**
     * The `play` function in Java implements the logic for moving shapes in a game,
     * updating the game
     * board, scoring, and checking for game over conditions.
     */
    public void play() {
        shape.setShape();
        boolean[][] shape = this.shape.getShape();
        int column = this.shape.getColumn();
        int row = this.shape.getRow();

        board.setBoard(shape, column, row);
        int[][] currentBoard = board.getBoard();

        int leftShape = board.getStartColumn();
        int topShape = board.getStartRow();

        do {
            boolean isMoveRight = false;
            boolean isMoveLeft = false;
            boolean isRotate = false;
            boolean isMoveDown = false;
            boolean canMove = true;
            
            board.printBoard(currentBoard);
            String action;
            action = input.next();

            try {
                switch (action) {
                    case MOVE_LEFT:
                        boolean canMoveLeft = checkMoveMent(MOVE_LEFT, currentBoard, topShape,
                                leftShape, row, column);
                        if (canMoveLeft) {
                            isMoveLeft = moveLeft(currentBoard);
                        }
                        break;
                    case MOVE_RIGHT:
                        boolean canMoveRight = checkMoveMent(MOVE_RIGHT, currentBoard, topShape,
                                leftShape, row, column);
                        if (canMoveRight) {
                            isMoveRight = moveRight(currentBoard);
                        }
                        break;
                    case ROTATE:
                        boolean canRotate = checkMoveMent(ROTATE, currentBoard, topShape,
                                leftShape, row, column);
                        if (canRotate)
                            isRotate = rotate(currentBoard, leftShape, topShape, row, column);
                        break;
                    default:
                        System.out.println("Moving down!");
                        // isGameOver = true;
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("out of bound!!!!!, Im sorry!!\n" + e.getMessage());
            }

            if (isMoveLeft)
                leftShape--;
            if (isMoveRight)
                leftShape++;
            if (isRotate) {
                row = row + column;
                column = row - column;
                row = row - column;
            }
            boolean canMoveDown = checkMoveMent(MOVE_DOWN, currentBoard, topShape, leftShape, row, column);
            if (canMoveDown) {
                isMoveDown = moveDown(currentBoard, canMoveDown);
                canMove = isMoveDown;
            }
            if (isMoveDown)
                topShape++;

            if (topShape + row == height - 1) {
                canMove = false;
            } else {
                for (int i = topShape; i < topShape + row; i++) {
                    for (int j = leftShape; j < leftShape + column; j++) {
                        if (currentBoard[i][j] == SHAPE_CELL && currentBoard[i + 1][j] == FULL_CELL) {
                            canMove = false;
                            break;
                        }
                    }
                }
            }
            if (!canMove) {
                firmShape(currentBoard, topShape);
                score = setScore(currentBoard, score);
                isGameOver = checkGameOver(currentBoard, score);
                if (!isGameOver) {
                    this.shape.setShape();
                    shape = this.shape.getShape();
                    column = this.shape.getColumn();
                    row = this.shape.getRow();
                    board.updateBoard(shape, row, column);
                    currentBoard = board.getBoard();
                    leftShape = board.getStartColumn();
                    topShape = board.getStartRow();
                }
            }
            System.out.println("\033[H\033[2J");
        } while (!isGameOver);
    }
    
    /**
     * The function `checkGameOver` checks if the game is over based on the current
     * board state and
     * score, displaying a message and clearing the screen if certain conditions are
     * met.
     * 
     * @param currentBoard The `currentBoard` parameter in the `checkGameOver`
     *                     method is a 2D array
     *                     representing the game board. The method iterates over the
     *                     elements of this board to check if any
     *                     cell is equal to `FULL_CELL` or if the score is less than
     *                     0. If either condition is
     * @param score        The `score` parameter in the `checkGameOver` method
     *                     represents the current score of
     *                     the game. The method checks if any cell in the
     *                     `currentBoard` array is equal to `FULL_CELL` or
     *                     if the score is less than 0. If either condition is met,
     *                     the method prints "
     * @return The method `checkGameOver` returns a boolean value - `true` if the
     *         game is over, and
     *         `false` if the game is not over.
     */
    private boolean checkGameOver(int[][] currentBoard, int score) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < currentBoard[i].length; j++) {
                if (currentBoard[i][j] == FULL_CELL || score < 0) {
                    System.out.println("YOU ARE GAME OVER....");
                    board.printBoard(currentBoard);
                    try {
                        Thread.sleep(6000);
                        System.out.print("\033[H\033[2J"); // clear the screen
                    } catch (InterruptedException a) {
                        continue;
                    }
                    return true;
                }
            }
        }
        return false;
    }
    

    /**
     * The function `checkMovement` checks if a specific move action is
     * valid on a game board
     * based on the current state and position of shapes.
     * 
     * @param action       represents the type of
     *                     movement being checked. It could be one of the following
     *                     values: `MOVE_LEFT`, `MOVE_RIGHT`,
     *                     `MOVE_DOWN + MOVE_LEFT`, `MOVE_DOWN + MOVE_RIGHT`, or
     *                     `MOVE_DOWN`.
     * @param currentBoard The `checkMovement` method you provided seems to be
     *                     checking if a certain
     *                     movement action is valid on the game board based on the
     *                     current state of the board and the
     *                     position of a shape.
     * @param topShape     The `topShape` parameter represents the topmost row
     *                     position of the shape on the
     *                     game board. It indicates the starting row index of the
     *                     shape within the `currentBoard` array.
     * @param leftShape    The `leftShape` parameter in the `checkMovement` method
     *                     represents the leftmost
     *                     column position of the shape on the game board. It is
     *                     used to determine the current position of
     *                     the shape horizontally.
     * @param row          The `row` parameter in the `checkMovement` method
     *                     represents the number of rows
     *                     occupied by the shape on the game board. It is used to
     *                     determine the range of rows to check for
     *                     possible movements based on the current position of the
     *                     shape.
     * @param column       The `column` parameter in the `checkMovement` method
     *                     represents the number of
     *                     columns occupied by the shape on the game board. It is
     *                     used to determine the boundaries and
     *                     check for possible collisions when moving the shape left,
     *                     right, or down within the game board
     *                     grid.
     * @return The method `checkMoveMent` returns a boolean value. It returns `true`
     *         if the specified
     *         action can be performed based on the current board state and shape
     *         position, and `false` if the
     *         action would result in an invalid move.
     */
    public boolean checkMoveMent(String action, int[][] currentBoard, int topShape, int leftShape, int row,
            int column) {
        switch (action) {
            case MOVE_LEFT:
                for (int i = topShape; i < topShape + row; i++) {
                    for (int j = leftShape; j < leftShape + column; j++) {
                        if (leftShape - 1 == 0 || currentBoard[i][j] == SHAPE_CELL && currentBoard[i][j - 1] == FULL_CELL)
                            return false;
                    }
                }
                break;
            case MOVE_RIGHT:
                for (int i = topShape; i < topShape + row; i++) {
                    for (int j = leftShape; j < leftShape + column; j++) {
                        if (leftShape + column + 1 == width || currentBoard[i][j] == SHAPE_CELL && currentBoard[i][j + 1] == FULL_CELL)
                            return false;
                    }
                }
                break;
            case MOVE_DOWN:
                for (int j = topShape; j < topShape + row; j++) {
                    for (int i = leftShape; i < leftShape + column; i++) {
                        if (currentBoard[j][i] == SHAPE_CELL && currentBoard[j + 1][i] == FULL_CELL)
                            return false;
                    }
                }
                break;
            case ROTATE:
                if (leftShape + row >= currentBoard[0].length)
                    return false;
                for (int i = topShape; i < topShape + column; i++) {
                    for (int j = leftShape; j < leftShape + row; j++) {
                        if (currentBoard[i][j] == FULL_CELL)
                            return false;
                    }
                }

        }
        return true;
    }

    /**
     * The `firmShape` function fills in the empty cells below a specified top shape
     * in a 2D
     * array based on the current board configuration.
     * 
     * @param currentBoard The `currentBoard` parameter is a 2D array representing
     *                     the current state of
     *                     the game board.
     * @param topShape     The `topShape` parameter in the `firmShape` method
     *                     represents the topmost row
     *                     index of the shape on the game board. It is used to
     *                     determine the starting point for firming up
     *                     the shape by moving it downwards until it reaches a
     *                     filled cell or the bottom of the board.
     */
    public void firmShape(int[][] currentBoard, int topShape) {
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                if (currentBoard[i][j] == SHAPE_CELL) {
                    currentBoard[i][j] = FULL_CELL;
                }
            }
        }
    }

    /**
     * The function `setScore` in Java checks for completed rows and columns in a
     * game board, updates
     * the score accordingly, and prints the current score.
     * 
     * @param currentBoard A 2D array representing the current state of the game
     *                     board. The array
     *                     contains integers representing different cells on the
     *                     board.
     * @param score        The `setScore` method you provided seems to be a part of
     *                     a game where you update the
     *                     score based on completed rows and columns in the game
     *                     board. The `score` parameter in the method
     *                     represents the current score of the player in the game.
     * @return The method `setScore` returns an integer value, which is the updated
     *         score after checking
     *         for completed rows and columns on the current board.
     */
    public int setScore(int[][] currentBoard, int score) {
        // Check for completed rows
        for (int i = 5; i < currentBoard.length - 1; i++) {
            boolean isRowFull = true;
            for (int j = 1; j < currentBoard[i].length - 1; j++) {
                if (currentBoard[i][j] == EMPTY_CELL) {
                    isRowFull = false;
                    break;
                }
            }
            if (isRowFull) {
                score += 100;
                clearRow(currentBoard, i);
            }
        }

        // Check for completed columns
        for (int j = 1; j < currentBoard[0].length - 1; j++) {
            boolean isColumnFull = true;
            for (int i = 5; i < currentBoard.length - 1; i++) {
                if (currentBoard[i][j] == EMPTY_CELL) {
                    isColumnFull = false;
                    break;
                }
            }
            if (isColumnFull) {
                score -= 10;
                clearColumn(currentBoard, j);
            }
        }
        System.out.println("YOUR SCORE UNTILL NOW:" + score);
        return score;
    }

    /**
     * The `clearRow` function in Java clears a specified row in a 2D array by
     * shifting elements down
     * and filling the top row with empty cells.
     * 
     * @param currentBoard The `currentBoard` parameter is a 2D array representing
     *                     the game board where
     *                     the Tetris blocks are placed. Each element in the array
     *                     represents a cell on the board, and the
     *                     value stored in the cell indicates the state of that cell
     *                     (e.g., whether it is occupied by a
     *                     block
     * @param row          The `row` parameter in the `clearRow` method represents
     *                     the row number in the
     *                     `currentBoard` array that you want to clear. This method
     *                     is designed to clear a specific row in
     *                     a 2D array representing a game board.
     */
    private void clearRow(int[][] currentBoard, int row) {
        for (int i = row; i > 4; i--) {
            for (int j = 1; j < width - 1; j++) {
                currentBoard[i][j] = currentBoard[i - 1][j];
            }
        }
        // Clear the top
        for (int j = 1; j < width - 1; j++) {
            currentBoard[5][j] = EMPTY_CELL;
        }
        fallDown(currentBoard);
    }

    /**
     * The clearColumn function clears a specified column in a 2D array, shifts
     * filled cells down, and
     * updates the board accordingly.
     * 
     * @param currentBoard The `currentBoard` parameter is a 2D array representing
     *                     the current state of
     *                     the game board. Each element in the array corresponds to
     *                     a cell on the game board, and the
     *                     values in the cells indicate whether the cell is empty or
     *                     filled.
     * @param column       The `clearColumn` method you provided seems to be
     *                     intended to clear a specific
     *                     column in a game board represented by a 2D array. The
     *                     `column` parameter specifies which column
     *                     to clear.
     */
    private void clearColumn(int[][] currentBoard, int column) {
        for (int i = 5; i < height - 1; i++) {
            currentBoard[i][column] = EMPTY_CELL;
        }
        fallDown(currentBoard);
    }
   /**
    * The `fallDown` function in Java moves filled cells in a 2D array downwards to simulate a falling
    * effect, based on the current state of the board and a checkerboard.
    */
    private void fallDown(int[][] currentBoard) {
        boolean[][] checkerBoard = new boolean[height][width];
        checkerBoard = makeChecker(currentBoard);

        for (int j = width - 2; j > 0; j--) {
            for (int i = height - 2; i >= 0; i--) {
                if (currentBoard[i][j] == FULL_CELL && !checkerBoard[i][j]) {
                    int k = i;
                    while (currentBoard[k + 1][j] == EMPTY_CELL) {
                        k++;
                    }
                    currentBoard[k][j] = FULL_CELL;
                    if (k != i) {
                        currentBoard[i][j] = EMPTY_CELL;
                    }
                }
            }
        }
    }

    /**
     * The function `makeChecker` creates a checkerboard based on the current board
     * state and
     * neighboring cells.
     * 
     * @param currentBoard The `currentBoard` parameter seems to be a 2D array
     *                     representing the current
     *                     state of a game board. The values in this array likely
     *                     indicate whether a cell on the board is
     *                     filled or empty.
     * @return The method `makeChecker` returns a 2D boolean array `checkerBoard`
     *         that represents a
     *         checker pattern based on the input `currentBoard`.
     */
    private boolean[][] makeChecker(int[][] currentBoard) {
        boolean[][] checkerBoard = new boolean[height][width];
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                if (i == height - 1)
                    checkerBoard[i][j] = true;
                else
                    checkerBoard[i][j] = false;
            }
        }
        // boolean down = true;
        for (int j = 1; j < width - 1; j++) {
            for (int i = height - 2; i >= 0; i--) {
                if (currentBoard[i][j] == FULL_CELL &&
                        (checkerBoard[i + 1][j] || checkerBoard[i - 1][j] ||
                                checkerBoard[i][j + 1] || checkerBoard[i][j - 1])) {
                    checkerBoard[i][j] = true;
                }
            }
        }
        for (int j = width - 2; j > 0; j--) {
            for (int i = 1; i < height - 1; i++) {
                if (currentBoard[i][j] == FULL_CELL &&
                        (checkerBoard[i + 1][j] || checkerBoard[i - 1][j] ||
                                checkerBoard[i][j + 1] || checkerBoard[i][j - 1])) {
                    checkerBoard[i][j] = true;
                }
            }
        }
        return checkerBoard;
    }

    /**
     * The function moves all SHAPE_CELL elements to the right in a 2D array by
     * shifting them one
     * position to the right if the cell to the right is empty.
     * 
     * @param currentBoard The `currentBoard` parameter is a 2D array representing
     *                     the current state of
     *                     the game board. Each element in the array corresponds to
     *                     a cell on the board and contains a
     *                     value indicating the content of that cell.
     * @return The method `moveRight` is returning a boolean value `true`.
     */
    public boolean moveRight(int[][] currentBoard) {
        for (int i = height - 1; i >= 0; i--) {
            for (int j = width - 1; j >= 0; j--) {
                if (currentBoard[i][j] == SHAPE_CELL && currentBoard[i][j + 1] == EMPTY_CELL) {
                    currentBoard[i][j + 1] = currentBoard[i][j];
                    currentBoard[i][j] = EMPTY_CELL;
                }
            }
        }
        return true;
    }

    /**
     * The `moveLeft` function in Java shifts all non-empty cells in the current
     * board to the left if
     * there is an empty cell to the left of them.
     * 
     * @param currentBoard It looks like the `moveLeft` method you provided is
     *                     intended to move a shape
     *                     cell to the left in a 2D grid represented by
     *                     `currentBoard`. However, there are a couple of
     *                     issues in your code that need to be addressed:
     * @return The method `moveLeft` is returning a boolean value `true`.
     */
    private boolean moveLeft(int[][] currentBoard) {
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                if (currentBoard[i][j] == SHAPE_CELL && currentBoard[i][j - 1] == EMPTY_CELL) {
                    currentBoard[i][j - 1] = currentBoard[i][j];
                    currentBoard[i][j] = EMPTY_CELL;
                }
            }
        }
        return true;
    }

    /**
     * The function `moveDown` attempts to move a shape down in a 2D array
     * representing a game board
     * and returns a boolean indicating if the move was successful.
     * 
     * @param currentBoard The `currentBoard` parameter is a 2D array representing
     *                     the current state of
     *                     the game board. Each element in the array corresponds to
     *                     a cell on the game board, and the
     *                     values in the cells determine the state of that cell
     *                     (e.g., SHAPE_CELL or EMPTY_CELL).
     * @param canMoveDown  The `canMoveDown` parameter in the `moveDown` method is a
     *                     boolean flag that
     *                     indicates whether the current shape can move down on the
     *                     game board. If `canMoveDown` is `true`,
     *                     it means that the shape is allowed to move down if there
     *                     is an empty cell below it
     * @return The method `moveDown` returns a boolean value indicating whether a
     *         move down was made
     *         successfully (`true`) or not (`false`).
     */
    private boolean moveDown(int[][] currentBoard, boolean canMoveDown) {
        boolean isMoveDown = false;
        try {
            for (int i = height - 1; i >= 0; i--) {
                for (int j = 0; j < width; j++) {
                    if (currentBoard[i][j] == SHAPE_CELL && currentBoard[i + 1][j] == EMPTY_CELL) {
                        currentBoard[i + 1][j] = currentBoard[i][j];
                        currentBoard[i][j] = EMPTY_CELL;
                        if (canMoveDown)
                            isMoveDown = true;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("can not move down");
        }
        return isMoveDown;
    }

    /**
     * The function `rotate` takes a 2D array representing a game board, positions
     * of the top-left
     * corner, dimensions of a shape, and rotates the shape within the board if
     * possible.
     * 
     * @param currentBoard The `currentBoard` parameter is a 2D array representing
     *                     the current state of
     *                     the game board. Each element in the array corresponds to
     *                     a cell on the board, and the values in
     *                     the cells determine the state of that cell (e.g., empty,
     *                     filled). The method `rotate` takes
     * @param left         The `left` parameter in the `rotate` method represents
     *                     the leftmost position of the
     *                     shape on the game board. It is a `Position` object that
     *                     contains the value of the leftmost
     *                     column where the shape is located.
     * @param top          The `top` parameter in the `rotate` method represents the
     *                     top-left position of the
     *                     shape on the game board. It contains the row value where
     *                     the shape starts from the top of the
     *                     board.
     * @param row          The `row` parameter in the `rotate` method represents the
     *                     number of rows in the shape
     *                     that you want to rotate. It is used to determine the
     *                     dimensions of the new rotated shape that
     *                     will be created during the rotation process.
     * @param column       The `column` parameter in the `rotate` method represents
     *                     the number of columns in
     *                     the shape that you want to rotate. It is used to
     *                     determine the dimensions of the new rotated
     *                     shape that will be created during the rotation process.
     *                     The `column` parameter specifies the
     *                     width of the shape before rotation.
     * @return The method `rotate` returns a boolean value indicating whether the
     *         rotation operation
     *         was successful or not. If the rotation was performed, the method
     *         returns `true`, otherwise it
     *         returns `false`.
     */
    private boolean rotate(int[][] currentBoard, int left, int top, int row, int column) {

        int bottom = top + row - 1;
        int right = left + column - 1;
        int xCenter = top;
        int yCenter = left;
        boolean isRotate = false;
        int[][] shape = new int[column][row];

        for (int i = top; i < bottom + 1; i++) {
            for (int j = left; j < right + 1; j++) {
                int xNew = xCenter - yCenter + j;
                int yNew = xCenter + yCenter - i + row - 1;
                int x = xNew - top;
                int y = yNew - left;

                if (currentBoard[i][j] != FULL_CELL) {// this block is for getting copy from the rotated shape ;
                    shape[x][y] = currentBoard[i][j];
                    currentBoard[i][j] = EMPTY_CELL;
                } else {
                    shape[x][y] = EMPTY_CELL;
                }
            }
        }
        for (int i = top; i < top + column; i++) {
            for (int j = left; j < left + row; j++) {
                currentBoard[i][j] = shape[i - top][j - left];
                isRotate = true;
            }
        }
        return isRotate;

    }
}
