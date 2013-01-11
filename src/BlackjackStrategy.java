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

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Random;


public class BlackjackStrategy {

	private EnumMap<PlayerHand, EnumMap<DealerHand, BlackjackAction>> actionMap;
	
	// Create a random one
	public BlackjackStrategy()
	{
		Random random = new Random();
		actionMap = new EnumMap<PlayerHand, EnumMap<DealerHand, BlackjackAction>>(PlayerHand.class);
		for(PlayerHand p : EnumSet.allOf(PlayerHand.class))
		{
			EnumMap<DealerHand, BlackjackAction> tempMap = new EnumMap<DealerHand, BlackjackAction>(DealerHand.class);
			for(DealerHand d : EnumSet.allOf(DealerHand.class))
			{
					int thisRoll = random.nextInt(3);
					switch(thisRoll)
					{
						case 0:
							tempMap.put(d, BlackjackAction.STAND);
							break;
						case 1:
							tempMap.put(d, BlackjackAction.HIT);
							break;
						case 2:
							tempMap.put(d, BlackjackAction.DOUBLE);
							break;

					}
			}
			actionMap.put(p, tempMap);
		}
	}
	
	// Mutates one piece of the strategy at random
	public void mutateOneGene(Random random)
	{
		DealerHand d = DealerHand.values()[random.nextInt(10)];
		PlayerHand p = PlayerHand.values()[random.nextInt(32)];
		BlackjackAction a = BlackjackAction.values()[random.nextInt(3)];
		
		actionMap.get(p).remove(d);
		actionMap.get(p).put(d, a);
	}
	
	// Creates a new strategy based on a 32x10 array of actions.
	public BlackjackStrategy(BlackjackAction actionArray[][])
	{
		actionMap = new EnumMap<PlayerHand, EnumMap<DealerHand, BlackjackAction>>(PlayerHand.class);
		
		for(int i = 0; i < 32; i++)
		{
			EnumMap<DealerHand, BlackjackAction> tempMap = new EnumMap<DealerHand, BlackjackAction>(DealerHand.class);
			for(int j = 0; j < 10; j++)
			{
				tempMap.put(DealerHand.values()[j], actionArray[i][j]);
			}
			actionMap.put(PlayerHand.values()[i], tempMap);
		}
	}
	
	// Clones a strategy
	public BlackjackStrategy(BlackjackStrategy strat)
	{
		actionMap = new EnumMap<PlayerHand, EnumMap<DealerHand, BlackjackAction>>(PlayerHand.class);
		
		for(int i = 0; i < 32; i++)
		{
			EnumMap<DealerHand, BlackjackAction> tempMap = new EnumMap<DealerHand, BlackjackAction>(DealerHand.class);
			for(int j = 0; j < 10; j++)
			{
				tempMap.put(DealerHand.values()[j], strat.getActionByCircumstances(PlayerHand.values()[i], DealerHand.values()[j]));
			}
			actionMap.put(PlayerHand.values()[i], tempMap);
		}
	}
	
	// Returns the action at a given player hand and dealer hand position
	public BlackjackAction getActionByCircumstances(PlayerHand pHand, DealerHand dHand)
	{
		return actionMap.get(pHand).get(dHand);
	}
	
	public void printStrategy()
	{	
		System.out.print("\t");
		
		for(DealerHand d : EnumSet.allOf(DealerHand.class))
		{
			System.out.print(d.convertHandToString());
			System.out.print('\t');
		}
		
		System.out.println();
		
		for(PlayerHand p : EnumSet.allOf(PlayerHand.class))
		{
				System.out.print(p.convertHandToString() + "\t");
			for(DealerHand d : EnumSet.allOf(DealerHand.class))
			{
				System.out.print(actionMap.get(p).get(d).convertActionToString());
				System.out.print('\t');
			}
			System.out.println();
		}
	}
	
}
