/*
  	Author: Brian Carrigan
  	Date: 1/10/2013
 	Email: brian.c.carrigan@gmail.com
 
   This file is part of the Evolutionary Blackjack Strategy Solver.

    The Evolutionary Blackjack Strategy Solver is free software: you 
    can redistribute it and/or modify it under the terms of the GNU 
    General Public License as published by the Free Software Foundation, 
    either version 3 of the License, or (at your option) any later 
    version.

    The Evolutionary Blackjack Strategy Solver is distributed in 
    the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
    even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
    PARTICULAR PURPOSE.  See the GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with the Evolutionary Blackjack Strategy Solver.  If not, 
    see <http://www.gnu.org/licenses/>.
 */

import java.util.ArrayList;
import java.util.Random;

// A simulator that plays blackjack based on the strategy of the player.
public class BlackjackGameSimulator {

	private Random generator;
	private boolean verbose;
	
	public BlackjackGameSimulator()
	{
		
	}
	
	// The bread and butter. Pass is a blackjack strategy, it will play numberOfHands hands
	// and return how much money was gained or lost. (Spoiler alert: you always lose money
	// in the long run)
	public int simulateNumberOfHands(int numberOfHands, BlackjackStrategy strat)
	{
		int aggregateWinnings = 0;
		generator = new Random();
		verbose = false;
		
		// The Hands (To implement a split, add a split hand)
		ArrayList<SuitlessCard> playerHand = new ArrayList<SuitlessCard>();
		ArrayList<SuitlessCard> dealerHand = new ArrayList<SuitlessCard>();
		
		if(verbose)
		{
			System.out.println("----- New Simulation -----");
		}
		
		// Iterate over every hand. Standard hand is 2 units, so if there is blackjack
		// the player gets 3 units back. Assumes there are enough decks that there is an
		// equal chance of all cards occurring.
		for(int i = 0; i < numberOfHands; i++)
		{
			boolean playersHandOver = false;
			boolean bust = false;
			int moneyOnTheLine = 2;
			
			DealerHand thisDealerHand;
			PlayerHand thisPlayerHand;
			
			// The Hands
			playerHand.clear();
			dealerHand.clear();
			
			// DEAL
			playerHand.add(SuitlessCard.getRandomSuitlessCard(generator));
			playerHand.add(SuitlessCard.getRandomSuitlessCard(generator));
			dealerHand.add(SuitlessCard.getRandomSuitlessCard(generator));
			dealerHand.add(SuitlessCard.getRandomSuitlessCard(generator));
			
			// Deal the dealer's hand. Treat all face cards as tens.
			switch(dealerHand.get(0))
			{
				case  ACE:  
					thisDealerHand = DealerHand.ACE;
					break;
				case  KING: 
				case  QUEEN:
				case  JACK: 
				case  TEN:  
					thisDealerHand = DealerHand.TEN;
					break;
				case  NINE:
					thisDealerHand = DealerHand.NINE;
					break;
				case  EIGHT:
					thisDealerHand = DealerHand.EIGHT;
					break;
				case  SEVEN:
					thisDealerHand = DealerHand.SEVEN;
					break;
				case  SIX:  
					thisDealerHand = DealerHand.SIX;
					break;
				case  FIVE: 
					thisDealerHand = DealerHand.FIVE;
					break;
				case  FOUR: 
					thisDealerHand = DealerHand.FOUR;
					break;
				case  THREE:
					thisDealerHand = DealerHand.THREE;
					break;
				default:
					thisDealerHand = DealerHand.TWO;
					break;
			}
			
			thisPlayerHand = getHandTypeFromArrayList(playerHand, false);
			
			// Verbosity used for troubleshooting...
			if(verbose)
			{
				System.out.println("Hand " + i);
				System.out.println("  " + printArrayList(playerHand) + " vs " + printArrayList(dealerHand));
				System.out.println("  Player actions:");
			}
			
			// Check for dealer blackjack right off the bad. If so, subtract two from the player's money
			// and this hand is over.
			if((dealerHand.get(0) == SuitlessCard.ACE && dealerHand.get(1).getHardValue() == 10) || (dealerHand.get(1) == SuitlessCard.ACE && dealerHand.get(0).getHardValue() == 10))
			{
				if(verbose)
				{
					System.out.println("  Dealer BJ. Auto loss.");
				}
				aggregateWinnings -= 2;
			} else {			
				// Continue dealing based on the strategy.
				while(!playersHandOver)
				{			
					BlackjackAction currentPlay = strat.getActionByCircumstances(thisPlayerHand, thisDealerHand);
					
					switch(currentPlay)
					{
						case HIT:
							// Hit.
							playerHand.add(SuitlessCard.getRandomSuitlessCard(generator));
							thisPlayerHand = getHandTypeFromArrayList(playerHand, false);
							if(verbose)
							{
								System.out.println("    Hit: " + printArrayList(playerHand));
							}
							break;
						case STAND:
							// Stand. Hand is over.
							if(verbose)
							{
								System.out.println("    ST: " + getHandScore(playerHand));
							}
							playersHandOver = true;
							break;
						default:
							// Double down. Double the bet, hand is over.
							playerHand.add(SuitlessCard.getRandomSuitlessCard(generator));
							thisPlayerHand = getHandTypeFromArrayList(playerHand, false);
							if(verbose)
							{
								System.out.println("    DD: " + printArrayList(playerHand));
							}
							moneyOnTheLine += 2;
							playersHandOver = true;
							break;
					}
					
					// If the player busts, set the bust boolean and break the while loop
					if(checkForBust(playerHand))
					{
						if(verbose)
						{
							System.out.println("    Bust");
						}
						bust = true;
						break;
					}				
				}
				
				// If a stand on Blackjack
				if(playerHand.size() == 2 && thisPlayerHand == PlayerHand.SOFT_TWENTYONE)
				{
					aggregateWinnings += 3;
				}
				
				// If the player busted, subtract the bet.
				if(bust)
				{
					aggregateWinnings -= moneyOnTheLine;
				} else {
					// Otherwise finish the dealer's turn and compare the hands.
					int dealerScore = finishDealersTurn(dealerHand);
					int playerScore = getHandScore(playerHand);
					if(dealerScore > playerScore)
					{
						aggregateWinnings -= moneyOnTheLine;
					} else if (dealerScore < playerScore)
					{
						aggregateWinnings += moneyOnTheLine;
					}

				}
				
				
			}
			
			if(verbose)
			{
				System.out.println("  Winnings:" + aggregateWinnings);
			}
		}
		
		return aggregateWinnings;
	}
	
	// Print a hand's contents to the console.
	public String printArrayList(ArrayList<SuitlessCard> printHand)
	{
		String returnString = "";
		for(SuitlessCard card : printHand)
		{
			returnString += card.toString() + " ";
		}
		return returnString;
	}
	
	// Finish the dealer's hand, based on typical house rules.
	public int finishDealersTurn(ArrayList<SuitlessCard> evalHand)
	{
		int handScore = 0;
		if(verbose)
		{
			System.out.println("  Dealer Actions: " + printArrayList(evalHand));
		}	
		
		while((handScore = getHandScore(evalHand)) < 17)
		{			
			evalHand.add(SuitlessCard.getRandomSuitlessCard(generator));
			if(verbose)
			{
				System.out.println("    Hit: " + printArrayList(evalHand));
			}	
			
			if(checkForBust(evalHand))
			{
				return 0;
			}
		}
		
		if(verbose)
		{
			System.out.println("    ST: " + handScore);
		}	
		
		return handScore;
	}
	
	// Check for bust, given a hand.
	public boolean checkForBust(ArrayList<SuitlessCard> evalHand)
	{
		int total = 0;
		for(SuitlessCard card : evalHand)
		{	
			total += card.getHardValue();		
		}	
		return (total > 21);
	}
	
	// Return the score of a hand.
	public int getHandScore(ArrayList<SuitlessCard> evalHand)
	{
		int total = 0;
		
		// Go through all the non-aces
		for(SuitlessCard card : evalHand)
		{
			if(card != SuitlessCard.ACE)
			{
				total += card.getHardValue();
			}
		}
		
		// Go through all the aces
		for(SuitlessCard card : evalHand)
		{
			if(card == SuitlessCard.ACE)
			{
				if(total > 10)
				{
					total += 1;
				} else {
					total += 11;
				}
			}
		}
		
		return total;
	}
	
	// Returns a PlayerHand object based on the array of cards in the player's hand.
	public PlayerHand getHandTypeFromArrayList(ArrayList<SuitlessCard> evalHand, boolean split)
	{
		// Soft action
		if(evalHand.contains(SuitlessCard.ACE))
		{
			int tTotal = 0;
			for(SuitlessCard card : evalHand)
			{
				if(card != SuitlessCard.ACE)
				{
					tTotal += card.getHardValue();
				}
			}
			
			if(tTotal == 10)
			{
				return PlayerHand.SOFT_TWENTYONE;
			}			
			if(tTotal == 9)
			{
				return PlayerHand.SOFT_TWENTY;
			}
			if(tTotal == 8)
			{
				return PlayerHand.SOFT_NINETEEN;
			}
			if(tTotal == 7)
			{
				return PlayerHand.SOFT_EIGHTEEN;
			}
			if(tTotal == 6)
			{
				return PlayerHand.SOFT_SEVENTEEN;
			}
			if(tTotal == 5)
			{
				return PlayerHand.SOFT_SIXTEEN;
			}
			if(tTotal == 4)
			{
				return PlayerHand.SOFT_FIFTEEN;
			}
			if(tTotal == 3)
			{
				return PlayerHand.SOFT_FOURTEEN;
			}
			if(tTotal == 2)
			{
				return PlayerHand.SOFT_THIRTEEN;
			}
		} 
		
		// Check for a double:
		if(evalHand.size() == 2 && !split)
		{
			if(evalHand.get(0) == evalHand.get(1))
			{
				switch(evalHand.get(0).getHardValue())
				{
				case 1:
					return PlayerHand.PAIR_ACE;
				case 2:
					return PlayerHand.PAIR_TWO;
				case 3:
					return PlayerHand.PAIR_THREE;
				case 4:
					return PlayerHand.PAIR_FOUR;
				case 5:
					return PlayerHand.PAIR_FIVE;
				case 6:
					return PlayerHand.PAIR_SIX;
				case 7:
					return PlayerHand.PAIR_SEVEN;
				case 8:
					return PlayerHand.PAIR_EIGHT;
				case 9:
					return PlayerHand.PAIR_NINE;
				default:
					return PlayerHand.PAIR_TEN;
				}
			}
		}	
		
		// Hard hand- easy
		int total = 0;
		for(SuitlessCard card : evalHand)
		{
			total += card.getHardValue();
		}
		
		if(total >= 17)
		{
			return PlayerHand.HARD_SEVENTEEN_PLUS;
		}
		if(total == 16)
		{
			return PlayerHand.HARD_SIXTEEN;
		}
		if(total == 15)
		{
			return PlayerHand.HARD_FIFTEEN;
		}
		if(total == 14)
		{
			return PlayerHand.HARD_FOURTEEN;
		}
		if(total == 13)
		{
			return PlayerHand.HARD_THIRTEEN;
		}
		if(total == 12)
		{
			return PlayerHand.HARD_TWELVE;
		}
		if(total == 11)
		{
			return PlayerHand.HARD_ELEVEN;
		}
		if(total == 10)
		{
			return PlayerHand.HARD_TEN;
		}
		if(total == 9)
		{
			return PlayerHand.HARD_NINE;
		}
		if(total == 8)
		{
			return PlayerHand.HARD_EIGHT;
		}
		if(total == 7)
		{
			return PlayerHand.HARD_SEVEN;
		}
		if(total == 6)
		{
			return PlayerHand.HARD_SIX;
		}

		// Otherwise must be 5
		return PlayerHand.HARD_FIVE;
				
	}
}
