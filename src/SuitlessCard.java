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

// We don't care about suit in blackjack. Cards are, therefore, just
// an enumeration of their numbers.

public enum SuitlessCard {

	ACE,
	KING,
	QUEEN,
	JACK,
	TEN,
	NINE,
	EIGHT,
	SEVEN,
	SIX,
	FIVE,
	FOUR,
	THREE,
	TWO;
	
	public int getHardValue()
	{
		switch(this)
		{
			case ACE:
				return 1;
			case KING:
				return 10;
			case QUEEN:
				return 10;
			case JACK:
				return 10;
			case TEN:
				return 10;
			case NINE:
				return 9;
			case EIGHT:
				return 8;
			case SEVEN:
				return 7;
			case SIX:
				return 6;
			case FIVE:
				return 5;
			case FOUR:
				return 4;
			case THREE:
				return 3;
			default:
				return 2;
		}      
	}
	
	static public SuitlessCard getRandomSuitlessCard(Random R)
	{
		int r = R.nextInt(13);
		switch(r)
		{
			case 0:
				return ACE;
			case 1:
				return KING;
			case 2:
				return QUEEN;
			case 3:
				return JACK;
			case 4:
				return TEN;
			case 5:
				return NINE;
			case 6:
				return EIGHT;
			case 7:
				return SEVEN;
			case 8:
				return SIX;
			case 9:
				return FIVE;
			case 10:
				return FOUR;
			case 11:
				return THREE;
			default:
				return TWO;
		}		
	}
}
