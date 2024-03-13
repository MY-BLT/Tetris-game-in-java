package Game;

import java.util.Random;
/**
 * @author M.Yasin B.Loghmani
 * <p>This class is for storing the board status
 * and place of shape  during the game</p>
 */
public class Board {
    Random rand = new Random();
    // Cell status constants
    private final int FULL_CELL = 1;
    private final int EMPTY_CELL = 0;
    private final int SHAPE_CELL = -1;

    private final int boardHeight = 20;
    private final int boardWidth = 10;

    private int[][] board = new int[boardHeight][boardWidth];
    private int startColumn;
    private int startRow;

    /**
     * <p>Initialize at first of every part</p>
     * @param shape the random shape that is created
     * @param column column of the  shape 
     * @param row  row of the shape
     */
    public void setBoard(boolean[][] shape, int column, int row) {
        // Shape shape = new Shape();
        // shape.setShape();

        // int row = shape.getRow();
        // int column = shape.getColumn();

        startRow = 5 - row;
        startColumn = rand.nextInt(boardWidth - column - 1) + 1;

        for (int i = startRow; i < 5; i++) {  
            for (int j = startColumn; j < column + startColumn; j++) {
                if (shape[i - startRow][j - startColumn])
                    board[i][j] = SHAPE_CELL;
                else 
                    board[i][j] = EMPTY_CELL;
            }
        }
        for (int i = 5; i < boardHeight; i++) {
                for (int j = 0; j < boardWidth; j++) {
                    if (j == 0 || j == boardWidth - 1 || i == boardHeight - 1) {
                        board[i][j] = FULL_CELL;
                    } else {
                        board[i][j] = EMPTY_CELL;
                    }
                }
            }
        }

        /**
         * The function `updateBoard` updates a game board with a given shape at a specified row and
         * column position.
         * 
         * @param shape A 2D boolean array representing the shape to be updated on the board. The shape
         * is defined by true values in the array.
         * @param row The `row` parameter in the `updateBoard` method represents the number of rows in
         * the shape that needs to be updated on the game board.
         * @param column The `column` parameter in the `updateBoard` method represents the number of
         * columns in the shape that you want to update on the board. It is used to determine the width
         * of the shape that will be placed on the board starting from the specified column index.
         */
        public void updateBoard(boolean[][] shape , int row, int column) {
            startRow = 5 - row;
            startColumn = rand.nextInt(boardWidth - column - 1) + 1;

            for (int i = startRow; i < 5; i++) {  
                for (int j = startColumn; j < column + startColumn; j++) {
                    if (shape[i - startRow][j - startColumn])
                        board[i][j] = SHAPE_CELL;
                    else 
                        board[i][j] = EMPTY_CELL;
                }
            }
        }


    /**
     * <p>print the board that have set</p>
     */
    public void printBoard(int[][] currentBoard) {
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                switch (currentBoard[i][j]) {
                    case FULL_CELL:
                        System.out.print("# ");
                        break;
                    case SHAPE_CELL:
                        System.out.print("* ");
                        break;
                    default:
                        System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    public int[][] getBoard() {
        return board;
    }
    
    /** 
     * @return int
     */
    public int getBoardHeight() {
        return boardHeight;
    }
    public int getBoardWidth() {
        return boardWidth;
    }

    public int getStartColumn() {
        return this.startColumn;
    }
    public int getStartRow() {
        return this.startRow;
    }
} 
