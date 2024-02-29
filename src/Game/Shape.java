package Game;
import java.util.Random;

public class Shape {
    Random random = new Random();
        
        private int row;
        private int column;
        private int mainColumn;
        private int mainRow;
        private boolean[][] shape;

        public void setShape () {
            row = random.nextInt(5) + 1;
            column = random.nextInt(5) + 1;
            mainColumn = random.nextInt(column);
            mainRow = random.nextInt(row);

            shape = new boolean[row][column];

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

    public int getRow() {
            return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean[][] getShape() {
        return shape;
    }
}