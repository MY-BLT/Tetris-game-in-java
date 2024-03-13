package Game;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author M.Yasin B.Loghmani
 * <p>Initializes a Game object and prompts the user to input a value to start
 * playing the game.</p>
 */
public class Main {
    static final int NEW_GAME = 1;
    static final int EXIT = 2;
    public static void main(String[] args) {
        System.out.print("\033[H\033[2J"); // clear the screen
        Scanner input = new Scanner(System.in);

        int command = -1;
        do {
            System.out.println("Choose an option:\n1)New game\n2)Exit");
            try {
                command = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Please enter the valid number");
                continue;
            } finally {
                System.out.print("\033[H\033[2J"); // clear the screen
            }
            mainMenu(command);
            
        } while (command != 2);
        
        // game.play();
    //     Board board = new Board();
    //     board.setBoard();
    //    board.printBoard(board.getBoard());
        input.close();
    }

    public static void mainMenu(int command) {
        Game1 game = new Game1();
        switch (command) {
            case NEW_GAME:
                game = new Game1(); 
                game.play();
                break;
            case EXIT:
                try {
                    System.out.println("EXITING.........");
                    Thread.sleep(3000);
                } catch (InterruptedException a) {
                    break;
                }
                break;
            default :
                try {
                    System.out.println("Wrong entry!!\nTry again!!");
                    Thread.sleep(3000);
                    System.out.print("\033[H\033[2J"); // clear the screen
                } catch (InterruptedException a) {
                    break;
                }
        }
    }
}    

