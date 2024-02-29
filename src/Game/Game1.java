package Game;
import java.util.Scanner;
/**
 * This class is responsible for updating the board and executing actions on the board during the game.
 */
public class Game1 {
    Scanner input = new Scanner(System.in);

    private Board board = new Board();
    private Shape shape = new Shape();

    // private int score = 0;
    // private boolean isGameOver = false;
    private int height = board.getBoardHeight();
    private int width = board.getBoardWidth();

    private final int FULL_CELL = 1;
    private final int EMPTY_CELL = 0;
    private final int SHAPE_CELL = -1;

    private final String MOVE_LEFT = "L";
    private final String MOVE_RIGHT = "R";
    private final String ROTATE = "O";
    
    /**
     * The `play` function in Java controls the movement and rotation of shapes on a game board based
     * on user input.
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
            boolean canMove = true;
            
            board.printBoard(currentBoard);
            String action ;
            action = input.next();
            
            
            
            switch (action) {
                case MOVE_LEFT:
                for (int i = height - 1; i >= 0; i--) {
                    for (int j = 0; j < width; j++) {
                            if (currentBoard[i][j] == SHAPE_CELL && leftShape -1 > 0) {
                                moveLeft(currentBoard, i, j); 
                                if (!isMoveLeft) isMoveLeft = true;
                            }                            
                        }
                    }
                    break;
                    
                    case MOVE_RIGHT:
                    for (int i = height -1; i >= 0; i--) {
                        for (int j = width - 1; j >= 0; j--) {
                            if (currentBoard[i][j] == SHAPE_CELL && leftShape + column + 1 < width) {
                                moveRight(currentBoard, i, j);
                                if (!isMoveRight) isMoveRight = true;
                            }
                        }
                    }
                    break;
                    
                    case ROTATE:
                    rotate(currentBoard, leftShape, topShape, row, column);
                    row = row + column;
                    column = row - column;
                    row = row -column;
                    break;
                    
                    default:
                    System.out.println("Invalid action!");
                    // isGameOver = true;
                    break;
                }
            if (isMoveLeft) { 
                leftShape--;
                topShape++;
            }
            else if (isMoveRight) {
                leftShape++;
                topShape++;
            }
            if (topShape + row == height - 1)
                canMove = false;
            
            if (!canMove) {
                for (int i = topShape; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        if (currentBoard[i][j] == SHAPE_CELL) {
                            if (i + 1 < height && currentBoard[i + 1][j] == EMPTY_CELL) {
                                currentBoard[i + 1][j] = currentBoard[i][j];
                                currentBoard[i][j] = EMPTY_CELL;
                            }
                            else {
                                currentBoard[i][j] = FULL_CELL;
                            }
                        }
                    }
                }
                this.shape.setShape();
                shape = this.shape.getShape();
                column = this.shape.getColumn();
                row = this.shape.getRow();
                board.updateBoard(shape, row, column);
                currentBoard = board.getBoard();
                leftShape = board.getStartColumn();
                topShape = board.getStartRow();
            }
            
        } while (true);
    }
        
    
    public void moveRight(int[][] currentBoard, int i, int j) {
            currentBoard[i + 1][j + 1] = currentBoard[i][j];
            currentBoard[i][j] = EMPTY_CELL;
            // moveDown(currentBoard, i, ++j);
    }

    private void moveLeft(int[][] currentBoard, int i, int j) {
            currentBoard[i + 1][j - 1] = currentBoard[i][j];
            currentBoard[i][j] = EMPTY_CELL;
            // moveDown(currentBoard, i, --j);
    }

    
    private void rotate(int[][] currentBoard,int left, int top, int row, int column) {

        int bottom = top + row;
        int right = left + column;
        for (int i = top; i < bottom; i++) {
            for (int j = left; j < right; j++) {
                currentBoard[j][i] = currentBoard[i][j];

            }
        }
            
        // for (int i = 0; i < column / 2; i++) {
        //     for (int j = i; j < column - i - 1; j++) {
        //         int temp = currentBoard[top + i][left + j];
        //         currentBoard[top + i][left + j] = currentBoard[bottom - j][left + i];
        //         currentBoard[bottom - j][left + i] = currentBoard[bottom - i][right - j];
        //         currentBoard[bottom - i][right - j] = currentBoard[top + j][right - i];
        //         currentBoard[top + j][right - i] = temp;
        //     }
        // }

    }

    // private void moveDown(int[][] currentBoard, int i, int j) {
    //     if (i + 1 < height) {
    //         currentBoard[i + 1][j] = currentBoard[i][j];
    //         currentBoard[i][j] = EMPTY_CELL;
    //     }
    



        // Implement logic to update score and check game over status
        // These features can be added back as needed
    // }
}
