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

            System.out.printf("SCORE: %d%n", score);
            
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

    public void firmShape(int[][] currentBoard, int topShape) {
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                if (currentBoard[i][j] == SHAPE_CELL) {
                    currentBoard[i][j] = FULL_CELL;
                }
            }
        }
    }

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

    private void clearColumn(int[][] currentBoard, int column) {
        for (int i = 5; i < height - 1; i++) {
            currentBoard[i][column] = EMPTY_CELL;
        }
        fallDown(currentBoard);
    }

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
