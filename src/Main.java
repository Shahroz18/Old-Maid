/** The 'Main' class.
 * Purpose: To develop the Old Maid card game with three players (2AI and one user)
 * @author: Shahroz & Kushagra
 * @version: June 14, 2017
 * @TestCases:
 * - Enter a card that you don't have when removing pairs, it should give you feedback and let you try again
 * - Enter a card that doesn't actually have a pair, it should give you feedback and let your try again
 * - Enter a card that does have a pair when removing pairs, it should remove both cards (pair) and ask again
 * 	  for another pair to remove.
 * - Enter 0 when removing pairs and it should finish the removPair function and more to the next steps
 * - Enter -1 at any time and the game should close
 * - Enter a number that is not in range when you pick a card, it should give you feedback and let you try again
 * - Enter a special characters or invalid numbers (ex: Frog or dhdheuhedakb or -9), it should give you feedback
 *    and let you try again
 * - Enter special characters or invalid cards (ex: Frog or dhdheuhedakb), it should give you feedback
 * 	  let you try again
 * - Enter special character or a random number or your name when asked to create a user name, it should work no
 * 	  matter what the input, as it just gets a string input
 */

// Import Scanner
import java.util.Scanner;

public class Main
{	
	// Initialize Scanner and Game
	static Scanner c = new Scanner(System.in);
	static Game game = new Game();
	
	public static void main (String [] args)
	{
		// Print header
		System.out.println("Welcome to Old Maid");
		System.out.println("*******************");

		// Create and deal out the deck
		game.makeDeck();
		
		// Print out the rules
		game.rules();
		
		// Ask for user name
		System.out.print("Enter your username: ");
		String username = c.next();
		System.out.println("");
		
		// Initial Remove Pairs
		game.sleep();
		game.ai1RemovePair();
		game.sleep();
		game.ai2RemovePair();
		game.sleep();
		System.out.println("");
		game.userCards(username);
		game.removePair();
		game.sleep();
		
		while (true)
		{
			// Print out the number of cards in each player's hand
			game.numCards();
			
			// AI 1's Turn
			System.out.println("\nPlayer 1's Turn:");
			System.out.println("----------------");
			game.sleep();
			game.ai1PickCard();
			game.sleep();
			game.ai1RemovePair();
			game.sleep();
			
			// AI 2's Turn
			System.out.println("\nPlayer 2's Turn:");
			System.out.println("----------------");
			game.ai2PickCard();
			game.sleep();
			game.ai2RemovePair();
			game.sleep();

			// User's Turn
			System.out.println("\nYour Turn!");
			System.out.println("----------------");
			game.userCards(username);
			System.out.println("\nChoose a card from Player 2's hand");
			game.pickCard();
			System.out.println("");
			game.userCards(username);
			game.removePair();
			game.userCards(username);
		}
	}
}
