package Game;

// import java.util.Scanner;

/**
 * @author M.Yasin B.Loghmani
 * <p>Initializes a Game object and prompts the user to input a value to start
 * playing the game.</p>
 */
public class Main {
    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");
        // Scanner input = new Scanner(System.in);
        Game1 game = new Game1();

    
        game.play();
    //     Board board = new Board();
    //     board.setBoard();
    //    board.printBoard(board.getBoard());
    }
}    

