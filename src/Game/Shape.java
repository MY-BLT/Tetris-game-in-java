package Game;
import java.util.Random;
/**
 * @author M.Yasin B.Loghmani
 * Shape class in the game will create random shapes for the game
 */

public class Shape {
    Random random = new Random();
        
    private int row;
    private int column;
    private boolean[][] shape;

    public void setShape() {
        int stars = random.nextInt(1, 6);
        int setStars = 1;
        int size = random.nextInt(3, 5);
        
        boolean[][] newShape = new boolean[size][size];
        newShape[random.nextInt(size)][random.nextInt(size)] = true;
        
        while(setStars <= stars) {
            int i = random.nextInt(size);
            int j = random.nextInt(size);

            if (!newShape[i][j] && (i + 1 < size && newShape[i + 1][j] || i - 1 >= 0 && newShape[i - 1][j] || j + 1 < size && newShape[i][j + 1] || j - 1 >= 0 && newShape[i][j - 1])) {
                newShape[i][j] = true;
                setStars++;
            }
        }
        int xMin = 5, xMax = 0;
        int yMin = 5, yMax = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (newShape[i][j]) {
                    if (i > xMax)
                        xMax = i;
                    if (i < xMin)
                        xMin = i;
                    if (j > yMax)
                        yMax = j;
                    if (j < yMin) 
                        yMin = j;
                }
            }
        }
        row = xMax - xMin + 1;
        column =yMax - yMin + 1;

        shape = new boolean[row][column];
        for (int i = xMin; i <= xMax ; i++) {
            for (int j = yMin; j <= yMax; j++) {
                if (newShape[i][j]) {
                    shape[i - xMin][j - yMin] = true;
                } else {
                    shape[i - xMin][j - yMin] = false;
                }
            }
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