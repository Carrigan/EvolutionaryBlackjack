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
import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

// The strategy crossover function.
public class BlackjackStrategyCrossover extends AbstractCrossover<BlackjackStrategy>
{

	public BlackjackStrategyCrossover(Probability prob) {
		super(31, prob);				
	}

	@Override
	public List<BlackjackStrategy> mate(BlackjackStrategy parent1,
			BlackjackStrategy parent2, int crossovers, Random random) {
		
		ArrayList<BlackjackStrategy> children = new ArrayList<BlackjackStrategy>();
		
		BlackjackAction child1Array[][] = new BlackjackAction[32][10];
		BlackjackAction child2Array[][] = new BlackjackAction[32][10];
		
		// The meat of the crossover. This function will choose a ROW in a strategy
		// and swap all of the rows below this.
		for(int i = 0; i < 32; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				if(i > random.nextInt(crossovers))
				{
					child1Array[i][j] = parent1.getActionByCircumstances(PlayerHand.values()[i], DealerHand.values()[j]);
					child2Array[i][j] = parent2.getActionByCircumstances(PlayerHand.values()[i], DealerHand.values()[j]);
				} else {
					child1Array[i][j] = parent2.getActionByCircumstances(PlayerHand.values()[i], DealerHand.values()[j]);
					child2Array[i][j] = parent1.getActionByCircumstances(PlayerHand.values()[i], DealerHand.values()[j]);
				}
			}
		}
		
		children.add(new BlackjackStrategy(child1Array));
		children.add(new BlackjackStrategy(child2Array));
		
		return children;
	}

}
