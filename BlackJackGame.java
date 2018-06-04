/*
 * Programmer: White, James
 * Chemeketa Community College
 * December 5, 2016
 * Class: CIS133J
 * Assignment: Lab 9
 * File Name: BlackJackGame.java
 */

/**
 *
 * @author <a href= "mailto:jwhit185@my.chemeketa.edu" >James White</a>
 */
import java.util.Scanner;

public class BlackJackGame
        extends BlackJackHand
{

    Scanner scanner = new Scanner(System.in);
    
    public void run()
    {

        // Used for betting
        int bank;
        int bet;

        // For determining the winner
        String winner = "";

        // The player starts with $100
        bank = 100;

        System.out.println("****BlackJack****");
        System.out.println("-----------------");

        while (true)
        {
            System.out.println("Total money: $"
                    + bank);
            do
            {
                System.out.println("Please enter your bet. "
                        + "(0 to leave the table)");

                bet = scanner.nextInt();

                // Check if the user entered a correct value
                if (bet
                        < 0
                        || bet
                        > bank)
                {
                    System.out.println("You must enter a value between 0 and "
                            + bank
                            + ".");
                }
            }
            while (bet
                    < 0
                    || bet
                    > bank);
            // Check if the user chose to quit
            if (bet
                    == 0)
            {
                break;
            }

            // Check the result of the game and award, or punish accordingly
            winner = StartPlaying();
            if (winner.equals("player"))
            {
                bank += bet;
            }
            else
            {
                bank -= bet;
            }

            // Check to see if the player still has money
            if (bank
                    == 0)
            {
                System.out.println("You ran out of money!");
                break;
            }
        }

        // Show the player how much they quit with
        System.out.println("You had $"
                + bank);
    }

    private String StartPlaying()
    {
        // Instantiate a deck, and two hands
        Deck deck = new Deck();
        BlackJackHand dealer;
        dealer = new BlackJackHand();
        BlackJackHand player;
        player = new BlackJackHand();
        
        // Shuffle the deck and deal four cards
        deck.shuffle();

        // Two to the dealer
        dealer.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());

        // Two to the player
        player.addCard(deck.dealCard());
        player.addCard(deck.dealCard());

        //check if either person has blackjack
        if (player.getBlackjackValue() == 21)
        {
            // Show the dealer's hand
            System.out.println("Dealer has the "
                    + dealer.getCard(0)
                    + " and the "
                    + dealer.getCard(1)
                    + ".");
            System.out.println("A total of: "
                    + player.getBlackjackValue());

            // Show the player's hand
            System.out.println("You have the "
                    + player.getCard(0)
                    + " and the "
                    + player.getCard(1)
                    + ".");
            System.out.println("A total of: "
                    + player.getBlackjackValue());
            System.out.println("You win!");
            return "player";
        }
        if (dealer.getBlackjackValue() == 21)
        {
            // Show the dealer's hand
            System.out.println("Dealer has the "
                    + dealer.getCard(0)
                    + " and the "
                    + dealer.getCard(1)
                    + ".");
            System.out.println("A total of: "
                    + dealer.getBlackjackValue());

            // Show the player's hand
            System.out.println("You have the "
                    + player.getCard(0)
                    + " and the "
                    + player.getCard(1)
                    + ".");
            System.out.println("A total of: "
                    + player.getBlackjackValue());
            System.out.println("You lose.");
            return "dealer";
        }
        
        // Check if either player busts
        if (player.getBlackjackValue() > 21)
        {
            // Show the player's hand
            System.out.println("You have the "
                    + player.getCard(0)
                    + " and the "
                    + player.getCard(1)
                    + ".");
            System.out.println("A total of: "
                    + player.getBlackjackValue());

            System.out.println("You started over 21, bust!");
            return "dealer";
        }
        else
        {
            if (dealer.getBlackjackValue() > 21)
            {
                // Show the dealer's hand
                System.out.println("Dealer has the "
                    + dealer.getCard(0)
                    + " and the "
                    + dealer.getCard(1)
                    + ".");
                System.out.println("A total of: "
                    + player.getBlackjackValue());
                System.out.println("The dealer started over 21, bust!");
                return "player";
            }
        }

        // If noone has blackjack, continue playing
        while (true)
        {
            // Display the player's cards and ask to hit,
            // or stand
            System.out.println("Your cards are:");
            for (int i = 0;
                    i
                    < player.getCardCount();
                    i++)
            {
                System.out.println(" "
                        + player.getCard(i));
            }
            System.out.println("For a total of: "
                    + player.getBlackjackValue());
            System.out.println("The Dealer shows: "
                    + dealer.getCard(0));
            System.out.println("Hit, or stand? (H/S)");

            // read the player's response
            // Used to store the use's hit, or stand.
            char action;

            do
            {
                action = Character.toUpperCase(scanner.next().
                        charAt(0));

                //check the player's response
                if (action
                        != 'H'
                        && action
                        != 'S')
                {
                    System.out.println("Please enter either "
                            + "H, or S.");
                }
            } while (action != 'H' && action != 'S');

            if (action== 'S')
            {
                // The player stands and the loop ends
                break;
            }
            else
            {
                player.addCard(deck.dealCard());
                System.out.println();

                // display the new card
                System.out.println("Your new card is the "
                        + player.getCard(player.getCardCount()
                                - 1));
                System.out.println("New total: "
                        + player.getBlackjackValue());

                // Check if the player busted
                if (player.getBlackjackValue() > 21)
                {
                    System.out.println("Over 21! You lose.");

                    //Show the dealer's other card
                    System.out.println("The dealer's other "
                            + "card was: "
                            + dealer.getCard(1));
                    return "dealer";
                }    
            }
        }
        
        // The player stands with 21 or less, start dealer's turn
        
        System.out.println("Dealer's cards are");
        System.out.println(" " + dealer.getCard(0));
        System.out.println(" " + dealer.getCard(1));
        
        // hit until the dealer is over 16
        while (dealer.getBlackjackValue() <=16)
        {
            dealer.addCard(deck.dealCard());
            System.out.println("Dealer hits and gets the " 
                    + dealer.getCard(dealer.getCardCount()-1));
        }
        System.out.println("The dealer's total is: " 
                    + dealer.getBlackjackValue());
        if(dealer.getBlackjackValue() > 21)
        {
            System.out.println("The dealer busts, you win!");
            return "player";
        }
        else
        {
            if(dealer.getBlackjackValue() == player.getBlackjackValue())
            {
                System.out.println("You tie! The dealer wins.");
                return "dealer";
            }
            else
            {
                if(dealer.getBlackjackValue() > player.getBlackjackValue())
                {
                    System.out.println("The dealer wins with a total of " 
                            + dealer.getBlackjackValue());
                    return "dealer";
                }
                else
                {
                    System.out.println("You win with a total of " 
                            + player.getBlackjackValue());
                    return "player";
                }
            }
        }
    }
}
