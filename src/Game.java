/** The 'Game' class.
 * Purpose: To develop the Old Maid card game with three players (2AI and one user).
 * @authors: Shahroz & Kushagra
 * @version: June 14, 2017
 */

// Import Scanner and Random
import java.util.Scanner;
import java.util.Random;
import java.lang.Thread;

public class Game
{
	// Initialize Scanner and Random
	static Scanner c = new Scanner(System.in);
	static Random random = new Random();
	
	// Initialize arrays of suits and numbers
	static String[] ranks = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "K"};
	static String[] suits = { "\u2660", "\u2665", "\u2666", "\u2663" };
	static String[] deck = new String[51];
	
	// Initialize Player Hands
	static String[] user = new String [18];
	static String[] ai1 = new String [18];
	static String[] ai2 = new String [18];
	
	// Initialize Variables
	static int numCardsRemoved;
	static boolean pairFound;
	static boolean check;
	static int userCount;
	static int ai1Count;
	static int ai2Count;
	static String pair;
	static int card;
	
	
	// Creates deck, shuffles it, and deals cards to players
	public void makeDeck() 
	{		
		// Add the cards to deck (excluding queens)
		for (int i = 0; i < ranks.length; i++) 
		{
			for (int j = 0; j < suits.length; j++) 
			{
				deck[suits.length * i + j] = ranks[i] + " of " + suits[j];
			}
		}
		
		// Add 3 queens to deck
		deck[48] = "Q of " + suits[1];
		deck[49] = "Q of " + suits[2];
		deck[50] = "Q of " + suits[3];

		// Shuffle cards
		for (int i = 0; i < 51; i++) 
		{
			int x = i + (int) (Math.random() * (51 - i));
			String temp = deck[x];
			deck[x] = deck[i];
			deck[i] = temp;
		}

		// Deal out cards to players
		int temp = 0;
		
		for (int i = 0; i < 51; i+=3) 
		{
			ai1[temp] = deck[i];
			ai2[temp] = deck[i+1];
			user[temp] = deck[i+2];
			temp++;
		}
	}

	// Gets user to pick card from AI 2
	public void pickCard() 
	{
		check = false;
		ai2Count = 0;
		userCount = 0;
		
		// Counts number of cards in AI 2's hand
		for (int i = 0; i < 18; i++)
		{
			if (ai2[i] != null)
			{
				ai2Count++;
			}
		}
		
		// Counts number of cards in user's hand
		for (int i = 0; i < 18; i++)
		{
			if (user[i] != null)
			{
				userCount++;
			}
		}
		
		while (!check)
		{
			// Get input for card
			try
			{
				// If Player 2 has no cards, user cannot pick a card
				if (ai2Count == 0)
				{
					System.out.println("Player 2 has no cards to give");
					check = true;
				}
				
				else
				{
					System.out.print("Enter a number from 1 - " + ai2Count + " : ");
					card = Integer.parseInt(c.next());
					
					// If -1 is entered, exit program
					if (card == -1)
					{
						System.out.println("\nThank You for Playing Old Maid!");
						System.exit(1);
					}
					
					// Check if card entered is within boundaries
					if (card < 1 || card > ai2Count)
					{
						System.out.println("A number from 1 to " + ai2Count + " please");
					}
					
					// If card is valid, break out of while loop
					else
					{
						check = true;
					}
				}
			}
			
			// If the input is not valid
			catch (Exception e)
			{
				System.out.println("Enter a number!");
			}	
		}
		
		// Add card to user's hand and remove from AI 2's hand
		if (ai2Count != 0)
		{
			user[userCount] = ai2[card - 1];
			System.out.println("You got a " + ai2[card - 1]);
			ai2[card - 1] = null;
		}
			
		// Move up user cards
		for (int i = 0; i < 18; i++)
		{
			if (i == (user.length - 1))
			{
				break;
			}
			
			if (user[i] == null)
			{
				user[i] = user[i+1];
				user[i+1] = null;
			}			
		}
				
		// Move up AI 2 cards
		for (int i = 0; i < 18; i++)
		{
			if (i == (ai2.length - 1))
			{
				break;
			}
			
			if (ai2[i] == null)
			{
				ai2[i] = ai2[i+1];
				ai2[i+1] = null;
			}			
		}
	}

	// Removes pairs from user's hand
	public void removePair() 
	{
		numCardsRemoved = 0;
		pair = "";
		check = false;
		
		while (!pair.equals("0"))
		{
			System.out.print("\nEnter the value of a card that forms a pair (enter 0 when done): ");
			pair = c.next();
			pairFound = false;
			
			// Check if user entered an actual card (letters)
			for (int x = 0; x < ranks.length; x++)
			{
				if (pair.substring(0, 1).equalsIgnoreCase(ranks[x]) || pair.substring(0, 1).equalsIgnoreCase("Q"))
				{
					check = true;
				}
			}
			
			// Check if user entered an actual card (numbers)
			try
			{
				if (Integer.parseInt(pair) > 1 && Integer.parseInt(pair) <= 10)
				{
					check = true;
				}
			}
			
			catch (Exception e)
			{
				
			}
			
			// If -1 is entered, exit program
			if (pair.equals("-1"))
			{
				System.out.println("\nThank You for playing Old Maid!");
				System.exit(1);
			}
			
			// If they didn't enter an actual card, print out not an actual card
			else if (!check && !pair.equals("0"))
			{
				System.out.println("That is not an actual card");
			}
			
			else
			{
				for (int i = 0; i < 18; i++)
				{
					if (user[i] != null)
					{
						if ((user[i].substring(0, 1)).equalsIgnoreCase(pair.substring(0,  1)))
						{
							for (int j = (i + 1); j < 18; j++)
							{	
								if (user[j] != null)
								{
									if ((user[j].substring(0, 1)).equalsIgnoreCase(pair.substring(0,  1)))
									{
										user[i] = null;
										user[j] = null;
										numCardsRemoved += 2;
										pairFound = true;
										System.out.println("Pair removed");
										break;
									}
								}
							}
							
							if (!pairFound)
							{
								System.out.println("Pair not found");
							}
							
							break;
						}
					}
				}
							
				// Move up user cards
				for (int i = 0; i < numCardsRemoved; i++)
				{
					for (int j = 0; j < 18; j++)
					{
						if (j == (user.length - 1))
						{
							break;
						}
						
						if (user[j] == null)
						{
							user[j] = user[j+1];
							user[j+1] = null;
						}			
					}
				}
			}
		}
		
		// Print number of pairs user removed
		System.out.print(numCardsRemoved / 2);
		System.out.println(" pair(s) removed\n");
		
		// Check for win or loss
		userCount = 0;
		ai1Count = 0;
		ai2Count = 0;
		
		// Count number of cards in user's hand
		for (int i = 0; i < 18; i++)
		{
			if (user[i] != null)
			{
				userCount++;
			}
		}
		
		// Count number of cards in AI 1's hand
		for (int i = 0; i < 18; i++)
		{
			if (ai1[i] != null)
			{
				ai1Count++;
			}
		}
		
		// Count number of cards in AI 2's hand
		for (int i = 0; i < 18; i++)
		{
			if (ai2[i] != null)
			{
				ai2Count++;
			}
		} 
		
		// Check if user lost
		if (userCount == 1 && ai1Count == 0 && ai2Count == 0)
		{
			System.out.println("\nPlayer 1 got rid of all their cards!");
			System.out.println("Player 2 got rid of all their cards!");
			
			System.out.println("You have the last queen");
			System.out.println("You are the Old Maid");	
			System.exit(1);
		}
		
		// Check if AI 1 lost
		else if (userCount == 0 && ai1Count == 1 && ai2Count == 0)
		{
			System.out.println("\nYou got rid of all your cards!");
			System.out.println("Player 2 got rid of all their cards!");
			
			System.out.println("Player 1 has the last queen");
			System.out.println("Player 1 is the Old Maid");	
			System.exit(1);
		}
		
		// Check if AI 2 lost
		else if (userCount == 0 && ai1Count == 0 && ai2Count == 1)
		{
			System.out.println("\nYou got rid of all your cards!");
			System.out.println("Player 1 got rid of all their cards!");
			
			System.out.println("Player 2 has the last queen");
			System.out.println("Player 2 is the Old Maid");	
			System.exit(1);
		}
	}

	// Prints user's hand with user name parameter;
	public void userCards(String x) 
	{
		// Print header
		System.out.println(x + "'s Hand");
		for (int i = 0; i < (x.length() + 7); i++)
		{
			System.out.print("*");
		}
		System.out.println("");
		
		// Print user's hand
		for (int i = 0; i < 18; i++)
		{
			if (user[i] != null)
			{
				System.out.println(user[i]);
			}
		}
	}
	
	// Picks a card for AI 1
	public void ai1PickCard() 
	{
		userCount = 0;
		ai1Count = 0;
		
		// Count number of cards in user's hand
		for (int i = 0; i < 18; i++)
		{
			if (user[i] != null)
			{
				userCount++;
			}
		}
		
		userCount--;
		
		// Count number of cards in AI 1's hand
		for (int i = 0; i < 18; i++)
		{
			if (ai1[i] != null)
			{
				ai1Count++;
			}
		}
		
		// If only one card (index 0) then that card must be picked
		if (userCount == 0)
		{
			card = 0;
		}
		
		// If no cards, then Player 1 can't pick a card
		else if (userCount == -1)
		{
			System.out.println("You have no cards to give to Player 1");
		}
		
		// Otherwise choose a random card
		else
		{
			card = random.nextInt(userCount);
		}
		
		// Add card to AI 1's hand and remove from user's hand
		if (userCount != -1)
		{
			ai1[ai1Count] = user[card];
			System.out.println("Player 1 took your " + user[card]);
			user[card] = null;
		}
				
		// Move up user cards
		for (int i = 0; i < 18; i++)
		{
			if (i == (user.length - 1))
			{
				break;
			}
			
			if (user[i] == null)
			{
				user[i] = user[i+1];
				user[i+1] = null;
			}			
		}
	}

	// Removes pairs from AI 1's hand
	public void ai1RemovePair() 
	{
		pairFound = false;
		numCardsRemoved = 0;
		
		for (int i = 0; i <= 17; i++)
		{
			if (ai1[i] != null)
			{
				pair = ai1[i].substring(0, 1);
				
				for (int j = (i + 1); j < 18; j++)
				{	
					if (ai1[j] != null)
					{
						if (ai1[j].substring(0, 1).equals(pair))
						{
							ai1[i] = null;
							ai1[j] = null;
							numCardsRemoved += 2;
							pairFound = true;
							break;
						}
					}
				}
			}
		}
		
		// Move up AI 1 Cards
		for (int i = 0; i < numCardsRemoved; i++)
		{
			for (int j = 0; j < 18; j++)
			{
				if (j == (ai1.length - 1))
				{
					break;
				}
				
				if (ai1[j] == null)
				{
					ai1[j] = ai1[j+1];
					ai1[j+1] = null;
				}			
			}
		}
		
		// Prints out number of pairs Player 1 removed
		System.out.println("Player 1 removed " + (numCardsRemoved / 2) + " pair(s)");
		
		// Check for win or loss
		userCount = 0;
		ai1Count = 0;
		ai2Count = 0;
		
		// Count number of cards in user's hand
		for (int i = 0; i < 18; i++)
		{
			if (user[i] != null)
			{
				userCount++;
			}
		}
		
		// Count number of cards in AI 1's hand
		for (int i = 0; i < 18; i++)
		{
			if (ai1[i] != null)
			{
				ai1Count++;
			}
		}
		
		// Count number of cards in AI 2's hand
		for (int i = 0; i < 18; i++)
		{
			if (ai2[i] != null)
			{
				ai2Count++;
			}
		} 
		
		// Check if user lost
		if (userCount == 1 && ai1Count == 0 && ai2Count == 0)
		{
			System.out.println("\nPlayer 1 got rid of all their cards!");
			System.out.println("Player 2 got rid of all their cards!");
			
			System.out.println("You have the last queen");
			System.out.println("You are the Old Maid");	
			System.exit(1);
		}
		
		// Check if AI 1 lost
		else if (userCount == 0 && ai1Count == 1 && ai2Count == 0)
		{
			System.out.println("\nYou got rid of all your cards!");
			System.out.println("Player 2 got rid of all their cards!");
			
			System.out.println("Player 1 has the last queen");
			System.out.println("Player 1 is the Old Maid");	
			System.exit(1);
		}
		
		// Check if AI 2 lost
		else if (userCount == 0 && ai1Count == 0 && ai2Count == 1)
		{
			System.out.println("\nYou got rid of all your cards!");
			System.out.println("Player 1 got rid of all their cards!");
			
			System.out.println("Player 2 has the last queen");
			System.out.println("Player 2 is the Old Maid");	
			System.exit(1);
		}
	}
	
	// Picks a card for AI 2
	public void ai2PickCard() 
	{	
		ai1Count = 0;
		ai2Count = 0;
		
		// Count number of cards in AI 1's hand
		for (int i = 0; i < 18; i++)
		{
			if (ai1[i] != null)
			{
				ai1Count++;
			}
		}
		
		ai1Count--;
		
		// Count number of cards in AI 2's hand
		for (int i = 0; i < 18; i++)
		{
			if (ai2[i] != null)
			{
				ai2Count++;
			}
		}
		
		// If only one card (index 0) then that card must be picked
		if (ai2Count == 0)
		{
			card = 0;
		}
		
		// If no cards, then Player 2 can't pick a card
		else if (ai2Count == -1)
		{
			System.out.println("Player 1 has no cards to give to Player 2");
		}
		
		// Otherwise choose a random card
		else
		{
			card = random.nextInt(ai1Count);
		}
		
		// Add card to AI 2's hand and remove from AI 1's hand
		if (ai2Count != -1)
		{
			ai2[ai2Count] = ai1[card];
			ai1[card] = null;
			System.out.println("Player 2 took a card from Player 1");
		}
									
		// Move up AI 1 Cards
		for (int i = 0; i < 18; i++)
		{
			if (i == (ai1.length - 1))
			{
				break;
			}
			
			if (ai1[i] == null)
			{
				ai1[i] = ai1[i+1];
				ai1[i+1] = null;
			}			
		}
	}

	// Removes pairs from AI 2's hand
	public void ai2RemovePair() 
	{
		pairFound = false;
		numCardsRemoved = 0;
		
		for (int i = 0; i <= 17; i++)
		{
			if (ai2[i] != null)
			{
				pair = ai2[i].substring(0, 1);
				
				for (int j = (i + 1); j < 18; j++)
				{	
					if (ai2[j] != null)
					{
						if (ai2[j].substring(0, 1).equals(pair))
						{
							ai2[i] = null;
							ai2[j] = null;
							numCardsRemoved += 2;
							pairFound = true;
							break;
						}
					}
				}
			}
		}
		
		// Move up AI 2 Cards
		for (int i = 0; i < numCardsRemoved; i++)
		{
			for (int j = 0; j < 18; j++)
			{
				if (j == (ai2.length - 1))
				{
					break;
				}
				
				if (ai2[j] == null)
				{
					ai2[j] = ai2[j+1];
					ai2[j+1] = null;
				}			
			}
		}
		
		// Prints out number of pairs Player 2 removed
		System.out.println("Player 2 removed " + (numCardsRemoved / 2) + " pair(s)");
		
		// Check for win or loss
		userCount = 0;
		ai1Count = 0;
		ai2Count = 0;
		
		// Count number of cards in user's hand
		for (int i = 0; i < 18; i++)
		{
			if (user[i] != null)
			{
				userCount++;
			}
		}
		
		// Count number of cards in AI 1's hand
		for (int i = 0; i < 18; i++)
		{
			if (ai1[i] != null)
			{
				ai1Count++;
			}
		}
		
		// Count number of cards in AI 2's hand
		for (int i = 0; i < 18; i++)
		{
			if (ai2[i] != null)
			{
				ai2Count++;
			}
		} 
		
		// Check if user lost
		if (userCount == 1 && ai1Count == 0 && ai2Count == 0)
		{
			System.out.println("\nPlayer 1 got rid of all their cards!");
			System.out.println("Player 2 got rid of all their cards!");
			
			System.out.println("You have the last queen");
			System.out.println("You are the Old Maid");	
			System.exit(1);
		}
		
		// Check if AI 1 lost
		else if (userCount == 0 && ai1Count == 1 && ai2Count == 0)
		{
			System.out.println("\nYou got rid of all your cards!");
			System.out.println("Player 2 got rid of all their cards!");
			
			System.out.println("Player 1 has the last queen");
			System.out.println("Player 1 is the Old Maid");	
			System.exit(1);
		}
		
		// Check if AI 2 lost
		else if (userCount == 0 && ai1Count == 0 && ai2Count == 1)
		{
			System.out.println("\nYou got rid of all your cards!");
			System.out.println("Player 1 got rid of all their cards!");
			
			System.out.println("Player 2 has the last queen");
			System.out.println("Player 2 is the Old Maid");	
			System.exit(1);
		}
	}

	// Prints the number of cards in each player's hand
	public void numCards()
	{
		ai1Count = 0;
		ai2Count = 0;
		userCount = 0;
		
		// Count number of cards in AI 1's hand
		for (int i = 0; i < 18; i++)
		{
			if (ai1[i] != null)
			{
				ai1Count++;
			}
		}
		
		// Count number of cards in AI 2's hand
		for (int i = 0; i < 18; i++)
		{
			if (ai2[i] != null)
			{
				ai2Count++;
			}
		}
		
		// Count number of cards in user's hand
		for (int i = 0; i < 18; i++)
		{
			if (user[i] != null)
			{
				userCount++;
			}
		}
		
		// Print number of cards in each player's hand
		System.out.println("\nPlayer 1 has " + ai1Count + " card(s)");
		System.out.println("Player 2 has " + ai2Count + " card(s)");
		System.out.println("You have " + userCount + " card(s)");
	}
	
	// Print out the rules
	public void rules()
	{
		System.out.println("\nInstructions:\n");
		System.out.println("- There are 51 cards in play, without the Q of \u2660");
		System.out.println("- The goal is to form and discard pairs of cards\n" + 
				"   and not to be left with the odd card at the end");
		System.out.println("- Each player has 17 cards");
		System.out.println("- Each player has a turn to remove pairs and pick a new\n" + 
				"   card from the player to their right");
		System.out.println("- Players may remove pairs as they form and then offer their\n" +
				"   hand to the player to their left so they may pick a card");
		System.out.println("- At the end, the player with the remaining unpaired queen\n" +
				"   is the OLD MAID (hence the loser)");
		System.out.println("- Enter -1 at any time to quit the game");
		System.out.println("- Below is the order of play for visual understanding\n\n" +
				"Player 1    \u2192    Player 2\n" +
				"	           \u2196   \u2197\n" +
				"	Player 3\n");
	}
	
	// Delays output
	public void sleep()
	{
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	// For error checking - prints AI 1's hand
	public void ai1Cards() 
	{
		for (int i = 0; i < 18; i++)
		{
			if (ai1[i] != null)
			{
				System.out.println(ai1[i]);
			}
					
			else
			{
				break;
			}
		}	
	}

	// For error checking - prints AI 2's hand
	public void ai2Cards() 
	{
		for (int i = 0; i < 18; i++)
		{
			if (ai2[i] != null)
			{
				System.out.println(ai2[i]);
			}
			
			else
			{
				break;
			}
		}
	}
}