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


import java.util.Random;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

// Implementation of the AbstractCandidateFactory. Just randomly makes new strategies.
public class BlackjackStrategyFactory extends AbstractCandidateFactory<BlackjackStrategy>{

	@Override
	public BlackjackStrategy generateRandomCandidate(Random arg0) {
		
		return new BlackjackStrategy();
	}

}
