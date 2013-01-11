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


import java.util.List;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

// The fitness function.
public class BlackjackStrategyFitnessFunction implements FitnessEvaluator<BlackjackStrategy>
{

	BlackjackGameSimulator sim;
	int runs;
	
	// Create a new fitness function based on a number of runs.
	public BlackjackStrategyFitnessFunction(int noRuns)
	{
		sim = new BlackjackGameSimulator();
		runs = noRuns;
	}
	
	@Override
	public double getFitness(BlackjackStrategy candidate,
			List<? extends BlackjackStrategy> list) {
			
		// Creates a winning percentage. For example, if you start with $100 and end with $110
		// this function will return 1.1(double)
		return (2*runs + sim.simulateNumberOfHands(runs, candidate))/(2.0*runs);
	}

	@Override
	public boolean isNatural() {
		// Tells the algorithm that a higher number is better.
		return true;
	}
	
	
	
}
