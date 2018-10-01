package components;

public class Kalaha extends Hole {

	public Kalaha(Player owner, int index, Cup original) {
		this.stones = 0;
		this.owner = owner;
		index++;
		
		if (index == 14) {
			this.nextHole = original;
		} else {
			this.nextHole = new Cup(owner, index, original);
		}
	}
	
	public void passStones(int stones) {
		if (getOwner().hasTurn()) {
			addStones(1);
			if (stones == 1) {
				getOwner().switchTurn();
			} else {
				stones--;
				getNextHole().passStones(stones);
			}
		} else {
			getNextHole().passStones(stones);
		}
	}
	
	private void addStones(int stones) {
		this.stones = getStones() + stones;
	}

	public void giveToKalaha(int stones, Player target) {
		if (target.equals(getOwner())) {
			addStones(stones);
		} else {
			getNextHole().giveToKalaha(stones, target);
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
