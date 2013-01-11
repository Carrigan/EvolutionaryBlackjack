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


// A player hand represents all of the possible hands in blackjack that a player
// can have.
public enum PlayerHand {

	HARD_SEVENTEEN_PLUS,
	HARD_SIXTEEN,
	HARD_FIFTEEN,
	HARD_FOURTEEN,
	HARD_THIRTEEN,
	HARD_TWELVE,
	HARD_ELEVEN,
	HARD_TEN,
	HARD_NINE,
	HARD_EIGHT,
	HARD_SEVEN,
	HARD_SIX,
	HARD_FIVE,
	SOFT_TWENTYONE,
	SOFT_TWENTY,
	SOFT_NINETEEN,
	SOFT_EIGHTEEN,
	SOFT_SEVENTEEN,
	SOFT_SIXTEEN,
	SOFT_FIFTEEN,
	SOFT_FOURTEEN,
	SOFT_THIRTEEN,
	PAIR_ACE,
	PAIR_TEN,
	PAIR_NINE,
	PAIR_EIGHT,
	PAIR_SEVEN,
	PAIR_SIX,
	PAIR_FIVE,
	PAIR_FOUR,
	PAIR_THREE,
	PAIR_TWO;
	
	
	public String convertHandToString()
	{
		switch(this)
		{
			case HARD_SEVENTEEN_PLUS:
				return "H17";
			case HARD_SIXTEEN:
				return "H16";
			case HARD_FIFTEEN:
				return "H15";
			case HARD_FOURTEEN:
				return "H14";
			case HARD_THIRTEEN:
				return "H13";
			case HARD_TWELVE:
				return "H12";
			case HARD_ELEVEN:
				return "H11";
			case HARD_TEN:
				return "H10";
			case HARD_NINE:
				return "H9";
			case HARD_EIGHT:
				return "H8";
			case HARD_SEVEN:
				return "H7";
			case HARD_SIX:
				return "H6";
			case HARD_FIVE:
				return "H5";
			case SOFT_TWENTYONE:
				return "S21";
			case SOFT_TWENTY:
				return "S20";
			case SOFT_NINETEEN:
				return "S19";
			case SOFT_EIGHTEEN:
				return "S18";
			case SOFT_SEVENTEEN:
				return "S17";
			case SOFT_SIXTEEN:
				return "S16";
			case SOFT_FIFTEEN:
				return "S15";
			case SOFT_FOURTEEN:
				return "S14";
			case SOFT_THIRTEEN:
				return "S13";
			case PAIR_ACE:
				return "PA";
			case PAIR_TEN:
				return "P10";
			case PAIR_NINE:
				return "P9";
			case PAIR_EIGHT:
				return "P8";
			case PAIR_SEVEN:
				return "P7";
			case PAIR_SIX:
				return "P6";
			case PAIR_FIVE:
				return "P5";
			case PAIR_FOUR:
				return "P4";
			case PAIR_THREE:
				return "P3";
			default:
				return "P2";		
		}
	}
	
}
