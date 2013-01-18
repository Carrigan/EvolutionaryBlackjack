
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
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.ElapsedTime;
import org.uncommons.watchmaker.framework.termination.TargetFitness;

public class BlackjackRuntime {

	public static void main(String[] args) {
		BlackjackStrategy winningStrat;
		
		// Build a list of possible mutations with the following probabilities of occurring
    	ArrayList<EvolutionaryOperator<BlackjackStrategy>> mutations = new ArrayList<EvolutionaryOperator<BlackjackStrategy>>();
    	mutations.add(new BlackjackStrategyMutation(new Probability(.001d), new Probability(.005d)));
    	mutations.add(new BlackjackStrategyCrossover(new Probability(.9d)));
    	EvolutionaryOperator<BlackjackStrategy> pipeline = new EvolutionPipeline<BlackjackStrategy>(mutations);
		
    	// Create the engine
        GenerationalEvolutionEngine<BlackjackStrategy> engine = new GenerationalEvolutionEngine<BlackjackStrategy>(
        		new BlackjackStrategyFactory(),
        		pipeline,
                new BlackjackStrategyFitnessFunction(1000000),	// This is the number of hands played to find the fitness function. Higher number = more accurate fitness!
                new RouletteWheelSelection(),
                new MersenneTwisterRNG());
    	
        engine.setSingleThreaded(false);
        
        // Display the health of each generation
        engine.addEvolutionObserver(new BlackjackObserver());

        
        // Go!
        winningStrat = engine.evolve(40, // individuals per generation
                      					2, // Elites per generation
                      					new ElapsedTime(180000));
        
        winningStrat.printStrategy();
		
	}
}
