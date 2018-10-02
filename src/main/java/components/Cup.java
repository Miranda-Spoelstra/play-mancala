package components;

public class Cup extends Hole {

	public Cup() {
		Player p1 = new Player();
		this.stones = 4;
		this.owner = p1;
		this.nextHole = new Cup(p1, 0, this);
	}
	
	public Cup(Player owner, int index, Cup original) {
		this.stones = 4;
		index++;
		
		if (index == 8) {
			this.owner = owner.getOpponent();
		} else {
			this.owner = owner;
		}
		
		if (index == 6 || index == 13) {
			this.nextHole = new Kalaha(getOwner(), index, original);
		} else {
			this.nextHole = new Cup(getOwner(), index, original);
		}
	}
	
	public void giveAwayStones() {
		int stoneAmount = getStones();
		
		if (stoneAmount != 0 && getOwner().hasTurn()) {
			setStones(0);
			getNextHole().passStones(stoneAmount);
		} else {
			System.out.println("You cannot make this move.");
		}
	}
	
	public void passStones(int stones) {
		addStone();
		if(stones == 1) {
			checkOpposingCup();
			getOwner().switchTurn();
		} else {
			stones--;
			getNextHole().passStones(stones);
		}
	}

	private void checkOpposingCup() {
		if (getStones() == 1 && getOwner().hasTurn()) {
			Cup opposite = findOppositeCup();
			stonesToKahala(opposite);
		}
	}

	private void stonesToKahala(Cup oppositeCup) {	
		if(oppositeCup.getStones() != 0) {
			oppositeCup.giveToKalaha(oppositeCup.getStones(), getOwner());
			oppositeCup.setStones(0);
			giveToKalaha(getStones(), getOwner());
			setStones(0);
		}
	}

	private void addStone() {
		int newStones = getStones() + 1;
		setStones(newStones);
	}
	
	public Cup findOppositeCup() { 
		Hole currentCup = this;
		int nrCups = -1;

		while(currentCup instanceof Cup) {
			nrCups++;
			currentCup = currentCup.getNextHole();
		}
		while(nrCups > 0) {
			nrCups--;
			currentCup = currentCup.getNextHole();
		}
		return (Cup) currentCup;
	}
	
	public void giveToKalaha(int stones, Player currentPlayer) {
		getNextHole().giveToKalaha(stones, currentPlayer);
	}
	
	public boolean gameEnded() {
		if(getStones() == 0) {
			if (getNextHole() instanceof Kalaha) {
				return true;
			}
			Cup nextCup = (Cup) getNextHole();
			return nextCup.gameEnded();
		}
		return false;
	}

	public Kalaha getWinner() {
		return getNextHole().getWinner();
	}
}
