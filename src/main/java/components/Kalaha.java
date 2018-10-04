package components;

public class Kalaha extends Hole {

	public Kalaha(Player owner, int holeNumber, Cup startCup) {
		this.stones = 0;
		this.owner = owner;
		holeNumber++;
		
		if (holeNumber == 14) {
			this.nextHole = startCup;
		} else {
			this.nextHole = new Cup(owner, holeNumber, startCup);
		}
	}
	
	private void addStones(int stones) {
		this.stones = getStones() + stones;
	}
	
	public void passStones(int stones) {
		if (getOwner().hasTurn()) {
			addStones(1);
			if (stones > 1) {
				stones--;
				getNextHole().passStones(stones);
			}
		} else {
			getNextHole().passStones(stones);
		}
	}

	public void giveToKalaha(int stones, Player currentPlayer) {
		if (currentPlayer.equals(getOwner())) {
			addStones(stones);
		} else {
			getNextHole().giveToKalaha(stones, currentPlayer);
		}
	}
	
	public Kalaha getWinner() {
		Kalaha opponent = (Kalaha) findHole(7);
		if (opponent.getStones() > getStones()) {
			return opponent;
		} else {
			return this;
		}
	}
}
