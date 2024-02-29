

import java.util.Random;

public class Board {
    // Status of every cell in the board
    final int full_Cell = 1;
    final int empty_Cell = 0;
    final int shape_Cell = -1;

    // amount of the board
    final int boardHeight = 20;
    final int boardWidth = 10;
    
    private int[][] board = new int[boardHeight][boardWidth];
    
    public void setBoard() {
        Random rand = new Random();
        Shape shape = new Shape();
        shape.setShape();

        for (int i = 0; i < boardHeight; i++) {
                for (int j = 0; j < boardWidth; j++) {
                    if (i >= shape.row && (j == 0 || j == boardWidth - 1 || i == boardHeight - 1)) {
                        board[i][j] = full_Cell;
                    } else {
                        board[i][j] = empty_Cell;
                    }
                }
            }
        int start  = rand.nextInt(3) + 1 ;

        for (int i = 0; i < shape.row; i++) {  
            for (int j = start; j < shape.column + start; j++) {
                if (shape.shape[i][j - start])
                    board[i][j] = shape_Cell;
            }
        }
    }
    public void printBoard() {
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if (board[i][j] == full_Cell) {
                    System.out.print("# ");
                } else if (board[i][j] == shape_Cell) {
                    System.out.print("* ");
                }else {
                System.out.print("  "); 
                }
            }
            System.out.println(); // Move to the next line after printing each row
        }
    }
    

    /**
     * @return int[][] return the board
     */
    public int[][] getBoard() {
        return board;
    }

}

class Shape {
    Random random = new Random();
        
        int row = random.nextInt(5) + 1;
        int column = random.nextInt(5) + 1;
        int mainColumn = random.nextInt(column);
        int mainRow = random.nextInt(row);
        boolean[][] shape = new boolean[row][column];

        public void setShape () {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    if (i == mainRow || j == mainColumn)
                        shape[i][j] = true;
                    else
                        shape[i][j] = false;
                }
            }
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    if (shape[i][j])
                        System.out.print("* ");
                    else
                        System.out.print("  ");
                }
                System.out.println();
            }
        }
}